package dev.turtywurty.copperboats;

import dev.turtywurty.copperboats.init.CreativeTabInit;
import dev.turtywurty.copperboats.init.EntityTypeInit;
import dev.turtywurty.copperboats.init.ItemInit;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(CopperBoats.MOD_ID)
public class CopperBoats {
    public static final String MOD_ID = "copperboats";

    public CopperBoats() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        EntityTypeInit.ENTITY_TYPES.register(bus);
        ItemInit.ITEMS.register(bus);
        CreativeTabInit.CREATIVE_TABS.register(bus);
    }

    public static ResourceLocation id(String id) {
        return new ResourceLocation(MOD_ID, id);
    }
}
