package dev.turtywurty.copperboats.entity;

import dev.turtywurty.copperboats.init.EntityTypeInit;
import dev.turtywurty.copperboats.item.CopperBoatItem;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class CopperBoat extends Boat implements OxidizableBoat {
    public static final EntityDataAccessor<Integer> OXIDATION_LEVEL = OxidizableBoat.defineOxidationLevel(CopperBoat.class);

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
        return CopperBoatItem.getBoatItem(getVariant(), false);
    }

    @Override
    public void tick() {
        super.tick();

        if (!level().isClientSide) {
            runTick(this, level(), blockPosition());
        }
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(OXIDATION_LEVEL, 0);
    }

    @Override
    public int getOxidationLevel() {
        return this.entityData.get(OXIDATION_LEVEL);
    }

    @Override
    public void setOxidationLevel(int level) {
        this.entityData.set(OXIDATION_LEVEL, level);
    }
}
