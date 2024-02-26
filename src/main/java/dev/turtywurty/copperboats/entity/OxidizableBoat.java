package dev.turtywurty.copperboats.entity;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public interface OxidizableBoat {
    int MAX_OXIDATION_LEVEL = 4;

    int getOxidationLevel();

    void setOxidationLevel(int level);

    default void runTick(Boat boat, Level level, BlockPos pos) {
        if (getOxidationLevel() == MAX_OXIDATION_LEVEL)
            return;

        if (level.getRandom().nextInt((level.isRainingAt(pos) && !boat.isInWater()) ? 600 : 1200) == 0) {
            setOxidationLevel(Math.min(getOxidationLevel() + 1, MAX_OXIDATION_LEVEL));
        }
    }

    default void onAxeInteraction(Boat boat, Level level, LivingEntity interactor, ItemStack interactStack) {
        if (interactor instanceof ServerPlayer serverPlayer) {
            CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger(serverPlayer, boat.blockPosition(), interactStack);
        }

        level.playSound(interactor, boat.blockPosition(), SoundEvents.AXE_SCRAPE, SoundSource.BLOCKS, 1.0F, 1.0F);
        if (interactor instanceof Player player) {
            level.levelEvent(player, 3005, boat.blockPosition(), 0);
        } else {
            level.levelEvent(3005, boat.blockPosition(), 0);
        }

        if (interactStack.isDamageableItem() && (!(interactor instanceof Player player) || !player.getAbilities().instabuild)) {
            interactStack.hurtAndBreak(1, interactor,
                    entity -> entity.broadcastBreakEvent(interactor.getUsedItemHand()));
        }
    }

    static <T extends Entity & OxidizableBoat> EntityDataAccessor<Integer> defineOxidationLevel(Class<T> entityClazz) {
        return SynchedEntityData.defineId(entityClazz, EntityDataSerializers.INT);
    }
}
