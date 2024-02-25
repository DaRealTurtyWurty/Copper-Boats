package dev.turtywurty.copperboats.init;

import dev.turtywurty.copperboats.CopperBoats;
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
            if (type == Boat.Type.BAMBOO) {
                register("copper_" + type.getName() + "_raft", new CopperBoatItem(type, false));
                register("copper_" + type.getName() + "_chest_raft", new CopperBoatItem(type, true));
                continue;
            }

            register("copper_" + type.getName() + "_boat", new CopperBoatItem(type, false));
            register("copper_" + type.getName() + "_chest_boat", new CopperBoatItem(type, true));
        }
    }
}
