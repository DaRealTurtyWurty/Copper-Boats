package dev.turtywurty.copperboats.init;

import dev.turtywurty.copperboats.CopperBoats;
import dev.turtywurty.copperboats.entity.CopperBoat;
import dev.turtywurty.copperboats.entity.CopperChestBoat;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class EntityTypeInit {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, CopperBoats.MOD_ID);

    public static final RegistryObject<EntityType<CopperBoat>> COPPER_BOAT = ENTITY_TYPES.register("copper_boat",
            () -> EntityType.Builder.<CopperBoat>of(CopperBoat::new, MobCategory.MISC)
                    .sized(1.375F, 0.5625F)
                    .clientTrackingRange(10)
                    .build(CopperBoats.id("copper_boat").toString()));

    public static final RegistryObject<EntityType<CopperChestBoat>> COPPER_CHEST_BOAT = ENTITY_TYPES.register("copper_chest_boat",
            () -> EntityType.Builder.<CopperChestBoat>of(CopperChestBoat::new, MobCategory.MISC)
                    .sized(1.375F, 0.5625F)
                    .clientTrackingRange(10)
                    .build(CopperBoats.id("copper_chest_boat").toString()));
}
