package dev.turtywurty.copperboats.entity;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

public interface OxidizableBoat {
    int MAX_OXIDATION_LEVEL = 3;

    static <T extends Entity & OxidizableBoat> EntityDataAccessor<Integer> defineOxidationLevel(Class<T> entityClazz) {
        return SynchedEntityData.defineId(entityClazz, EntityDataSerializers.INT);
    }

    static <T extends Entity & OxidizableBoat> EntityDataAccessor<Boolean> defineWaxed(Class<T> entityClazz) {
        return SynchedEntityData.defineId(entityClazz, EntityDataSerializers.BOOLEAN);
    }

    int getOxidationLevel();

    void setOxidationLevel(int level);

    boolean isWaxed();

    void setWaxed(boolean waxed);

    default InteractionResult onAxeInteraction(Boat boat, Level level, Player player, ItemStack interactStack) {
        int currentOxidationLevel = getOxidationLevel();
        if (currentOxidationLevel == 0) {
            if (!isWaxed())
                return InteractionResult.PASS;

            if (player instanceof ServerPlayer serverPlayer) {
                CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger(serverPlayer, boat.blockPosition(), interactStack);
            }

            level.playSound(player, boat.blockPosition(), SoundEvents.AXE_WAX_OFF, SoundSource.BLOCKS, 1.0F, 1.0F);
            level.levelEvent(player, 3004, boat.blockPosition(), 0);

            if (interactStack.isDamageableItem() && !player.getAbilities().instabuild) {
                interactStack.hurtAndBreak(1, player, entity -> entity.broadcastBreakEvent(player.getUsedItemHand()));
            }

            setWaxed(false);

            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        if (player instanceof ServerPlayer serverPlayer) {
            CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger(serverPlayer, boat.blockPosition(), interactStack);
        }

        level.playSound(player, boat.blockPosition(), SoundEvents.AXE_SCRAPE, SoundSource.BLOCKS, 1.0F, 1.0F);
        level.levelEvent(player, 3005, boat.blockPosition(), 0);

        if (interactStack.isDamageableItem() && !player.getAbilities().instabuild) {
            interactStack.hurtAndBreak(1, player, entity -> entity.broadcastBreakEvent(player.getUsedItemHand()));
        }

        setOxidationLevel(Math.max(currentOxidationLevel - 1, 0));

        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    default InteractionResult onHoneyCombInteraction(Boat boat, Player player, ItemStack stack) {
        if (isWaxed())
            return InteractionResult.PASS;

        Level level = boat.level();
        if (player instanceof ServerPlayer serverPlayer) {
            CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger(serverPlayer, boat.blockPosition(), stack);
        }

        level.gameEvent(GameEvent.ENTITY_ACTION, boat.blockPosition(), GameEvent.Context.of(player));
        level.levelEvent(player, 3003, boat.blockPosition(), 0);

        if (!player.getAbilities().instabuild)
            stack.shrink(1);

        setWaxed(true);
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    default void onRandomTick(RandomSource randomSource) {
        if(isWaxed() || randomSource.nextFloat() >= 0.05688889F)
            return;

        int currentOxidationLevel = getOxidationLevel();
        float chance = currentOxidationLevel == 0 ? 0.75F : 1.0F;
        if (randomSource.nextFloat() < chance) {
            if (currentOxidationLevel >= MAX_OXIDATION_LEVEL)
                return;

            setOxidationLevel(Math.min(currentOxidationLevel + 1, MAX_OXIDATION_LEVEL));
        }
    }
}
