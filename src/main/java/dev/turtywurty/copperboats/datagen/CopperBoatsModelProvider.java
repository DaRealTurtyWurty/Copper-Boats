package dev.turtywurty.copperboats.datagen;

import dev.turtywurty.copperboats.item.CopperBoatItem;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.resources.ResourceLocation;

import java.util.Objects;

public class CopperBoatsModelProvider extends FabricModelProvider {
    public CopperBoatsModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {}

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerator) {
        for (CopperBoatItem item : CopperBoatItem.getBoatItems()) {
            if (!item.isWaxed()) {
                itemModelGenerator.generateFlatItem(item, ModelTemplates.FLAT_ITEM);
            } else {
                String itemName = BuiltInRegistries.ITEM.getKey(item).toString().replace("waxed_", "");
                ModelTemplates.FLAT_ITEM.create(
                        ModelLocationUtils.getModelLocation(item),
                        TextureMapping.layer0(Objects.requireNonNull(ResourceLocation.tryParse(itemName)).withPrefix("item/")),
                        itemModelGenerator.output);
            }
        }
    }
}
