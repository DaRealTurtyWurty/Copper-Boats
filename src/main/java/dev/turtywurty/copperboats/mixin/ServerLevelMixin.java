package dev.turtywurty.copperboats.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import dev.turtywurty.copperboats.entity.OxidizableBoat;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.storage.WritableLevelData;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;
import java.util.function.Supplier;

@Debug(export = true)
@Mixin(ServerLevel.class)
public abstract class ServerLevelMixin extends Level {
    protected ServerLevelMixin(WritableLevelData writableLevelData, ResourceKey<Level> resourceKey, RegistryAccess registryAccess, Holder<DimensionType> holder, Supplier<ProfilerFiller> supplier, boolean bl, boolean bl2, long l, int i) {
        super(writableLevelData, resourceKey, registryAccess, holder, supplier, bl, bl2, l, i);
    }

    @ModifyExpressionValue(
            method = "tickChunk",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/level/ServerLevel;getBlockRandomPos(IIII)Lnet/minecraft/core/BlockPos;",
                    ordinal = 2
            )
    )
    private BlockPos copperboats$tickChunk$getBlockRandomPos(BlockPos original) {
        List<Entity> entities = getEntities((Entity) null, new AABB(original), OxidizableBoat.class::isInstance);
        for (Entity entity : entities) {
            ((OxidizableBoat) entity).onRandomTick(this.random);
        }

        return original;
    }

    @ModifyExpressionValue(
            method = "tickChunk",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/chunk/LevelChunkSection;isRandomlyTicking()Z"
            )
    )
    private boolean copperboats$tickChunk$isRandomlyTicking(boolean original, @Local(argsOnly = true) LevelChunk chunk,
                                                            @Local(ordinal = 1) int minX, @Local(ordinal = 2) int minZ,
                                                            @Local LevelChunkSection levelChunkSection,
                                                            @Local(ordinal = 3) int sectionIndex) {
        int sectionY = chunk.getSectionYFromSectionIndex(sectionIndex);
        var sectionBounds = new AABB(minX, sectionY, minZ, minX + 16, sectionY + 16, minZ + 16);
        return original || !getEntities((Entity) null, sectionBounds, OxidizableBoat.class::isInstance).isEmpty();
    }
}
