package dev.turtywurty.copperboats.init;

import dev.turtywurty.copperboats.CopperBoats;
import dev.turtywurty.copperboats.entity.OxidizableBoat;
import dev.turtywurty.copperboats.item.CopperBoatItem;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;

public class ItemInit {
    public static <T extends Item> T register(String id, T item) {
        return Registry.register(BuiltInRegistries.ITEM, CopperBoats.id(id), item);
    }

    public static void init() {
        for (Boat.Type type : Boat.Type.values()) {
            for (int level = 0; level < OxidizableBoat.MAX_OXIDATION_LEVEL; level++) {
                if (type == Boat.Type.BAMBOO) {
                    register("copper_" + type.getName() + "_raft_" + level, new CopperBoatItem(type, false, level));
                    register("copper_" + type.getName() + "_chest_raft_" + level, new CopperBoatItem(type, true, level));
                    continue;
                }

                register("copper_" + type.getName() + "_boat_" + level, new CopperBoatItem(type, false, level));
                register("copper_" + type.getName() + "_chest_boat_" + level, new CopperBoatItem(type, true, level));
            }
        }
    }
}
