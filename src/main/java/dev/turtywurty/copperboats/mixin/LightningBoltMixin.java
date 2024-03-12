package dev.turtywurty.copperboats.mixin;

import dev.turtywurty.copperboats.entity.OxidizableBoat;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Optional;

@Mixin(LightningBolt.class)
public class LightningBoltMixin {
    @Inject(
            method = "randomStepCleaningCopper",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/Level;getBlockState(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;"
            )
    )
    private static void copperboats$randomStepCleaningCopper(Level level, BlockPos pos, CallbackInfoReturnable<Optional<BlockPos>> callback) {
        List<Entity> entities = level.getEntities((Entity)null, new AABB(pos.above()), OxidizableBoat.class::isInstance);
        for(Entity entity : entities) {
            OxidizableBoat boat = (OxidizableBoat) entity;
            boat.setOxidationLevel(Math.max(0, boat.getOxidationLevel() - 1));
        }
    }
}
