package dev.turtywurty.copperboats.datagen;

import dev.turtywurty.copperboats.entity.OxidizableBoat;
import dev.turtywurty.copperboats.item.CopperBoatItem;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class CopperBoatsRecipeProvider extends RecipeProvider {
    public CopperBoatsRecipeProvider(PackOutput output) {
        super(output);
    }

    private static void createBoatRecipes(RecipeOutput exporter, Optional<Item> input, CopperBoatItem output, int oxidizedLevel, boolean waxed) {
        if (output == null)
            return;

        Item copperBlock = getCopperBlockForLevel(oxidizedLevel, waxed);
        input.ifPresent(item -> ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION, output)
                .define('B', item)
                .define('C', copperBlock)
                .pattern("CBC")
                .pattern("CCC")
                .unlockedBy("has_item", has(item))
                .unlockedBy("has_copper_block", has(copperBlock))
                .save(exporter));
    }

    private static Item getCopperBlockForLevel(int level, boolean waxed) {
        return switch (level) {
            case 1 -> waxed ? Items.WAXED_EXPOSED_COPPER : Items.EXPOSED_COPPER;
            case 2 -> waxed ? Items.WAXED_WEATHERED_COPPER : Items.WEATHERED_COPPER;
            case 3 -> waxed ? Items.WAXED_OXIDIZED_COPPER : Items.OXIDIZED_COPPER;
            default -> waxed ? Items.WAXED_COPPER_BLOCK : Items.COPPER_BLOCK;
        };
    }

    @Override
    public void buildRecipes(@NotNull RecipeOutput exporter) {
        for (Boat.Type type : Boat.Type.values()) {
            for (int level = 0; level <= OxidizableBoat.MAX_OXIDATION_LEVEL; level++) {
                Optional<Item> boatOpt = Optional.ofNullable(ForgeRegistries.ITEMS.getValue(ResourceLocation.tryParse(type.getName() + "_boat")))
                        .map(item -> item == Items.AIR ? null : item)
                        .or(() -> Optional.ofNullable(ForgeRegistries.ITEMS.getValue(ResourceLocation.tryParse(type.getName() + "_raft"))));

                Optional<Item> chestBoatOpt = Optional.ofNullable(ForgeRegistries.ITEMS.getValue(ResourceLocation.tryParse(type.getName() + "_chest_boat")))
                        .map(item -> item == Items.AIR ? null : item)
                        .or(() -> Optional.ofNullable(ForgeRegistries.ITEMS.getValue((ResourceLocation.tryParse(type.getName() + "_chest_raft")))));

                var copperBoat = CopperBoatItem.getBoatItem(type, false, level, false);
                var copperChestBoat = CopperBoatItem.getBoatItem(type, true, level, false);
                var waxedCopperBoat = CopperBoatItem.getBoatItem(type, false, level, true);
                var waxedCopperChestBoat = CopperBoatItem.getBoatItem(type, true, level, true);

                createBoatRecipes(exporter, boatOpt, copperBoat, level, false);
                createBoatRecipes(exporter, chestBoatOpt, copperChestBoat, level, false);
                createBoatRecipes(exporter, boatOpt, waxedCopperBoat, level, true);
                createBoatRecipes(exporter, chestBoatOpt, waxedCopperChestBoat, level, true);
            }
        }
    }
}
