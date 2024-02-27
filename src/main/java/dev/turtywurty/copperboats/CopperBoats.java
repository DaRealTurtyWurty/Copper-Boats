package dev.turtywurty.copperboats;

import dev.turtywurty.copperboats.init.CreativeTabInit;
import dev.turtywurty.copperboats.init.EntityTypeInit;
import dev.turtywurty.copperboats.init.ItemInit;
import net.fabricmc.api.ModInitializer;

import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CopperBoats implements ModInitializer {
	public static final String MODID = "copperboats";
	public static final Logger LOGGER = LoggerFactory.getLogger(CopperBoats.class);

	public static ResourceLocation id(String id) {
		return new ResourceLocation(MODID, id);
	}

	@Override
	public void onInitialize() {
		ItemInit.init();
		EntityTypeInit.init();
		CreativeTabInit.init();

		LOGGER.info("Copper Boats has been initialized!");
	}
}