package dev.turtywurty.copperboats.entity;

import dev.turtywurty.copperboats.init.EntityTypeInit;
import dev.turtywurty.copperboats.item.CopperBoatItem;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class CopperBoat extends Boat {
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
}
