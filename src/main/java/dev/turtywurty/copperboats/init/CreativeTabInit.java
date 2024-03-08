package dev.turtywurty.copperboats.init;

import dev.turtywurty.copperboats.CopperBoats;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Objects;

public class CreativeTabInit {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CopperBoats.MOD_ID);

    public static final Component TAB_TITLE = Component.translatable(CopperBoats.id("creative_tab").toString());

    public static final RegistryObject<CreativeModeTab> TAB = CREATIVE_TABS.register("creative_tab",
            () -> CreativeModeTab.builder()
                    .title(TAB_TITLE)
                    .displayItems((itemDisplayParameters, output) -> BuiltInRegistries.ITEM.keySet()
                            .stream()
                            .filter(key -> key.getNamespace().equals(CopperBoats.MOD_ID))
                            .map(ForgeRegistries.ITEMS::getValue)
                            .filter(Objects::nonNull)
                            .forEach(output::accept))
                    .icon(Items.CHERRY_BOAT::getDefaultInstance)
                    .build());
}
