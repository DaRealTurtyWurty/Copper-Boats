package dev.turtywurty.copperboats.init;

import dev.turtywurty.copperboats.CopperBoats;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Items;

import java.util.Optional;

public class CreativeTabInit {
    public static final Component TAB_TITLE = Component.translatable(CopperBoats.id("creative_tab").toString());

    public static final CreativeModeTab TAB = register("creative_tab",
            FabricItemGroup.builder()
                    .title(TAB_TITLE)
                    .displayItems((itemDisplayParameters, output) -> BuiltInRegistries.ITEM.keySet()
                            .stream()
                            .filter(key -> key.getNamespace().equals(CopperBoats.MODID))
                            .map(BuiltInRegistries.ITEM::getOptional)
                            .map(Optional::orElseThrow)
                            .forEach(output::accept))
                    .icon(Items.CHERRY_BOAT::getDefaultInstance)
                    .build());

    public static <T extends CreativeModeTab> T register(String name, T creativeTab) {
        return Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, CopperBoats.id(name), creativeTab);
    }

    public static void init() {
        // This method is used to load the class
    }
}
