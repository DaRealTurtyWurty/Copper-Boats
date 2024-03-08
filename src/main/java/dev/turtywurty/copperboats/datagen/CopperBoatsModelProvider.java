package dev.turtywurty.copperboats.datagen;

import dev.turtywurty.copperboats.CopperBoats;
import dev.turtywurty.copperboats.item.CopperBoatItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

public class CopperBoatsModelProvider extends ItemModelProvider {
    public CopperBoatsModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, CopperBoats.MOD_ID,existingFileHelper);
    }

    @Override
    public void registerModels() {
        for (CopperBoatItem item : CopperBoatItem.getBoatItems()) {
            if (!item.isWaxed()) {
                basicItem(item);
            } else {
                String itemName = Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item)).toString();
                withExistingParent(itemName, "item/generated").texture("layer0", Objects.requireNonNull(ResourceLocation.tryParse(itemName.replace("waxed_", ""))).withPrefix("item/"));
            }
        }
    }
}
