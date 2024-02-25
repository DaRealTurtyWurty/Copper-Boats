package dev.turtywurty.copperboats.mixin;

import com.google.common.collect.ImmutableMap;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.datafixers.util.Pair;
import dev.turtywurty.copperboats.CopperBoats;
import dev.turtywurty.copperboats.CopperBoatsClient;
import dev.turtywurty.copperboats.entity.CopperBoat;
import dev.turtywurty.copperboats.entity.CopperChestBoat;
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
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;
import java.util.stream.Stream;

@Debug(export = true)
@Mixin(BoatRenderer.class)
public class BoatRendererMixin {
    @Unique
    private Map<Boat.Type, Pair<ResourceLocation, ListModel<Boat>>> copperboats$copperBoatResources;

    @Inject(
            method = "<init>",
            at = @At("TAIL")
    )
    private void copperboats$init(EntityRendererProvider.Context context, boolean isChest, CallbackInfo callback) {
        this.copperboats$copperBoatResources = Stream.of(Boat.Type.values())
                .collect(ImmutableMap.toImmutableMap(type -> type, type -> Pair.of(
                        CopperBoats.id(copperboats$getCopperTextureLocation(type, isChest)),
                        copperboats$createCopperBoatModel(context, type, isChest))));
    }

    @Unique
    private ListModel<Boat> copperboats$createCopperBoatModel(EntityRendererProvider.Context context, Boat.Type type, boolean isChest) {
        ModelLayerLocation modelLayerLocation = isChest ? CopperBoatsClient.createChestBoatModelName(type) : CopperBoatsClient.createBoatModelName(type);
        ModelPart modelPart = context.bakeLayer(modelLayerLocation);
        if (type == Boat.Type.BAMBOO) {
            return isChest ? new CopperChestRaftModel(modelPart) : new CopperRaftModel(modelPart);
        } else {
            return isChest ? new CopperChestBoatModel(modelPart) : new CopperBoatModel(modelPart);
        }
    }

    @Unique
    private String copperboats$getCopperTextureLocation(Boat.Type type, boolean isChest) {
        return isChest ? "textures/entity/copper_chest_boat/" + type.getName() + ".png" : "textures/entity/copper_boat/" + type.getName() + ".png";
    }

    @ModifyExpressionValue(
            method = "render(Lnet/minecraft/world/entity/vehicle/Boat;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/Map;get(Ljava/lang/Object;)Ljava/lang/Object;"
            )
    )
    private Object copperboats$render$get(Object original, @Local(argsOnly = true) Boat entity) {
        if (entity instanceof CopperBoat || entity instanceof CopperChestBoat) {
            return this.copperboats$copperBoatResources.get(entity.getVariant());
        }

        return original;
    }
}
