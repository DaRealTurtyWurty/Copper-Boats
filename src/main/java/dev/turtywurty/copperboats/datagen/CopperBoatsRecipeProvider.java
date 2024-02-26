package dev.turtywurty.copperboats.datagen;

import dev.turtywurty.copperboats.entity.OxidizableBoat;
import dev.turtywurty.copperboats.item.CopperBoatItem;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.properties.WoodType;

import java.util.Locale;
import java.util.Optional;

public class CopperBoatsRecipeProvider extends FabricRecipeProvider {
    public CopperBoatsRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    private static void createBoatRecipe(RecipeOutput exporter, Optional<Item> input, CopperBoatItem output, int oxidizedLevel) {
        if (output == null)
            return;

        Item copperBlock = null; // TODO: Get copper block using the oxidized level

        input.ifPresent(item -> ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION, output)
                .define('B', item)
                .define('C', Items.COPPER_BLOCK)
                .pattern("CBC")
                .pattern("CCC")
                .unlockedBy("has_item", has(item))
                .unlockedBy("has_copper_block", has(Items.COPPER_BLOCK))
                .save(exporter));
    }

    @Override
    public void buildRecipes(RecipeOutput exporter) {
        for (Boat.Type type : Boat.Type.values()) {
            for (int level = 0; level < OxidizableBoat.MAX_OXIDATION_LEVEL; level++) {
                Optional<Item> boatOpt = BuiltInRegistries.ITEM.getOptional(ResourceLocation.tryParse(type.getName() + "_boat"))
                        .or(() -> BuiltInRegistries.ITEM.getOptional(ResourceLocation.tryParse(type.getName() + "_raft")));
                Optional<Item> chestBoatOpt = BuiltInRegistries.ITEM.getOptional(ResourceLocation.tryParse(type.getName() + "_chest_boat"))
                        .or(() -> BuiltInRegistries.ITEM.getOptional(ResourceLocation.tryParse(type.getName() + "_chest_raft")));
                var copperBoat = CopperBoatItem.getBoatItem(type, false, level);
                var copperChestBoat = CopperBoatItem.getBoatItem(type, true, level);

                createBoatRecipe(exporter, boatOpt, copperBoat, level);
                createBoatRecipe(exporter, chestBoatOpt, copperChestBoat, level);
            }
        }
    }
}
