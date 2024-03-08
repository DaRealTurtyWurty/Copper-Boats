package dev.turtywurty.copperboats.init;

import dev.turtywurty.copperboats.CopperBoats;
import dev.turtywurty.copperboats.entity.OxidizableBoat;
import dev.turtywurty.copperboats.item.CopperBoatItem;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemInit {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, CopperBoats.MOD_ID);

    static {
        for (Boat.Type type : Boat.Type.values()) {
            for (int level = 0; level <= OxidizableBoat.MAX_OXIDATION_LEVEL; level++) {
                final int levelCopy = level;
                if (type == Boat.Type.BAMBOO) {
                    ITEMS.register("copper_" + type.getName() + "_raft_" + level,
                            () -> new CopperBoatItem(type, false, levelCopy, false));

                    ITEMS.register("copper_" + type.getName() + "_chest_raft_" + level,
                            () -> new CopperBoatItem(type, true, levelCopy, false));

                    ITEMS.register("waxed_copper_" + type.getName() + "_raft_" + level,
                            () -> new CopperBoatItem(type, false, levelCopy, true));

                    ITEMS.register("waxed_copper_" + type.getName() + "_chest_raft_" + level,
                            () -> new CopperBoatItem(type, true, levelCopy, true));
                    continue;
                }

                ITEMS.register("copper_" + type.getName() + "_boat_" + level,
                        () -> new CopperBoatItem(type, false, levelCopy, false));

                ITEMS.register("copper_" + type.getName() + "_chest_boat_" + level,
                        () -> new CopperBoatItem(type, true, levelCopy, false));

                ITEMS.register("waxed_copper_" + type.getName() + "_boat_" + level,
                        () -> new CopperBoatItem(type, false, levelCopy, true));

                ITEMS.register("waxed_copper_" + type.getName() + "_chest_boat_" + level,
                        () -> new CopperBoatItem(type, true, levelCopy, true));
            }
        }
    }
}
