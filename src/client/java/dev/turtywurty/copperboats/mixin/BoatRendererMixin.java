package dev.turtywurty.copperboats.mixin;

import com.google.common.collect.ImmutableMap;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.datafixers.util.Pair;
import dev.turtywurty.copperboats.CopperBoats;
import dev.turtywurty.copperboats.CopperBoatsClient;
import dev.turtywurty.copperboats.entity.CopperBoat;
import dev.turtywurty.copperboats.entity.CopperChestBoat;
import dev.turtywurty.copperboats.entity.OxidizableBoat;
import dev.turtywurty.copperboats.model.CopperBoatModel;
import dev.turtywurty.copperboats.model.CopperChestBoatModel;
import dev.turtywurty.copperboats.model.CopperChestRaftModel;
import dev.turtywurty.copperboats.model.CopperRaftModel;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.vehicle.Boat;
import org.apache.commons.lang3.tuple.Triple;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Debug(export = true)
@Mixin(BoatRenderer.class)
public class BoatRendererMixin {
    @Unique
    private Map<Boat.Type, Pair<List<ResourceLocation>, ListModel<Boat>>> copperboats$copperBoatResources;

    @Inject(
            method = "<init>",
            at = @At("TAIL")
    )
    private void copperboats$init(EntityRendererProvider.Context context, boolean isChest, CallbackInfo callback) {
        this.copperboats$copperBoatResources = Stream.of(Boat.Type.values())
                .collect(ImmutableMap.toImmutableMap(type -> type, type -> Pair.of(
                        copperboats$getTextures(type, isChest),
                        copperboats$createCopperBoatModel(context, type, isChest))));
    }

    @Unique
    private static ListModel<Boat> copperboats$createCopperBoatModel(EntityRendererProvider.Context context, Boat.Type type, boolean isChest) {
        ModelLayerLocation modelLayerLocation = isChest ? CopperBoatsClient.createChestBoatModelName(type) : CopperBoatsClient.createBoatModelName(type);
        ModelPart modelPart = context.bakeLayer(modelLayerLocation);
        if (type == Boat.Type.BAMBOO) {
            return isChest ? new CopperChestRaftModel(modelPart) : new CopperRaftModel(modelPart);
        } else {
            return isChest ? new CopperChestBoatModel(modelPart) : new CopperBoatModel(modelPart);
        }
    }

    @Unique
    private static String copperboats$getCopperTextureLocation(Boat.Type type, boolean isChest, int oxidationLevel) {
        return isChest ? "textures/entity/copper_chest_boat/" + type.getName() + "_" + oxidationLevel + ".png" :
                "textures/entity/copper_boat/" + type.getName() + "_" + oxidationLevel + ".png";
    }

    @Unique
    private static List<ResourceLocation> copperboats$getTextures(Boat.Type type, boolean isChest) {
        return IntStream.range(0, OxidizableBoat.MAX_OXIDATION_LEVEL)
                .mapToObj(level -> CopperBoats.id(copperboats$getCopperTextureLocation(type, isChest, level)))
                .toList();
    }

    @ModifyExpressionValue(
            method = "render(Lnet/minecraft/world/entity/vehicle/Boat;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/Map;get(Ljava/lang/Object;)Ljava/lang/Object;"
            )
    )
    private Object copperboats$render$get(Object original, @Local(argsOnly = true) Boat entity) {
        if (entity instanceof OxidizableBoat boat) {
            Pair<List<ResourceLocation>, ListModel<Boat>> pair = this.copperboats$copperBoatResources.get(entity.getVariant());
            return Pair.of(pair.getFirst().get(boat.getOxidationLevel()), pair.getSecond());
        }

        return original;
    }
}
