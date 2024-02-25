package dev.turtywurty.copperboats.init;

import dev.turtywurty.copperboats.CopperBoats;
import dev.turtywurty.copperboats.entity.CopperBoat;
import dev.turtywurty.copperboats.entity.CopperChestBoat;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public final class EntityTypeInit {
    public static final EntityType<CopperBoat> COPPER_BOAT = register("copper_boat",
            EntityType.Builder.<CopperBoat>of(CopperBoat::new, MobCategory.MISC)
                    .sized(1.375F, 0.5625F)
                    .clientTrackingRange(10));

    public static final EntityType<CopperChestBoat> COPPER_CHEST_BOAT = register("copper_chest_boat",
            EntityType.Builder.<CopperChestBoat>of(CopperChestBoat::new, MobCategory.MISC)
                    .sized(1.375F, 0.5625F)
                    .clientTrackingRange(10));

    public static <T extends Entity> EntityType<T> register(String id, EntityType.Builder<T> builder) {
        return Registry.register(BuiltInRegistries.ENTITY_TYPE, CopperBoats.id(id), builder.build(id));
    }

    public static void init() {
        // This method is used just to load the class
    }
}
