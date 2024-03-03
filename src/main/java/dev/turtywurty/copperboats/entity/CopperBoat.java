package dev.turtywurty.copperboats.entity;

import dev.turtywurty.copperboats.init.EntityTypeInit;
import dev.turtywurty.copperboats.item.CopperBoatItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class CopperBoat extends Boat implements OxidizableBoat {
    public static final EntityDataAccessor<Integer> OXIDATION_LEVEL = OxidizableBoat.defineOxidationLevel(CopperBoat.class);
    public static final EntityDataAccessor<Boolean> WAXED = OxidizableBoat.defineWaxed(CopperBoat.class);

    public CopperBoat(EntityType<CopperBoat> entityType, Level level) {
        super(entityType, level);
    }

    public CopperBoat(Level level, double x, double y, double z) {
        this(EntityTypeInit.COPPER_BOAT, level);
        this.setPos(x, y, z);
        this.xo = x;
        this.yo = y;
        this.zo = z;
    }

    @Override
    public @NotNull Item getDropItem() {
        return CopperBoatItem.getBoatItem(getVariant(), false, getOxidationLevel(), isWaxed());
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(OXIDATION_LEVEL, 0);
        this.entityData.define(WAXED, false);
    }

    @Override
    public int getOxidationLevel() {
        return this.entityData.get(OXIDATION_LEVEL);
    }

    @Override
    public void setOxidationLevel(int level) {
        this.entityData.set(OXIDATION_LEVEL, level);
    }

    @Override
    public boolean isWaxed() {
        return this.entityData.get(WAXED);
    }

    @Override
    public void setWaxed(boolean waxed) {
        this.entityData.set(WAXED, waxed);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        setOxidationLevel(compoundTag.getInt("OxidationLevel"));
        setWaxed(compoundTag.getBoolean("Waxed"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("OxidationLevel", getOxidationLevel());
        compoundTag.putBoolean("Waxed", isWaxed());
    }

    @Override
    public @NotNull InteractionResult interact(Player player, InteractionHand interactionHand) {
        ItemStack stack = player.getItemInHand(interactionHand);
        if(player.isShiftKeyDown() && interactionHand == InteractionHand.MAIN_HAND) {
            if(stack.getItem() instanceof AxeItem) {
                return onAxeInteraction(this, level(), player, stack);
            }

            if(stack.is(Items.HONEYCOMB)) {
                return onHoneyCombInteraction(this, player, stack);
            }
        }

        return super.interact(player, interactionHand);
    }
}
