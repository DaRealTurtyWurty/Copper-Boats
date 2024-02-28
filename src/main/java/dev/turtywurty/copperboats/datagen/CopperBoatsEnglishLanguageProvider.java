package dev.turtywurty.copperboats.datagen;

import dev.turtywurty.copperboats.entity.OxidizableBoat;
import dev.turtywurty.copperboats.init.CreativeTabInit;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.vehicle.Boat;
import org.apache.commons.lang3.text.WordUtils;

public class CopperBoatsEnglishLanguageProvider extends FabricLanguageProvider {
    protected CopperBoatsEnglishLanguageProvider(FabricDataOutput dataOutput) {
        super(dataOutput, "en_us");
    }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        for (Boat.Type type : Boat.Type.values()) {
            for (int level = 0; level <= OxidizableBoat.MAX_OXIDATION_LEVEL; level++) {
                if(type == Boat.Type.BAMBOO) {
                    translationBuilder.add("item.copperboats." + "copper_" + type.getName() + "_raft_" + level, getName(type, false, level, false));
                    translationBuilder.add("item.copperboats." + "copper_" + type.getName() + "_chest_raft_" + level, getName(type, true, level, false));
                    translationBuilder.add("item.copperboats." + "waxed_copper_" + type.getName() + "_raft_" + level, getName(type, false, level, true));
                    translationBuilder.add("item.copperboats." + "waxed_copper_" + type.getName() + "_chest_raft_" + level, getName(type, true, level, true));
                    continue;
                }

                translationBuilder.add("item.copperboats." + "copper_" + type.getName() + "_boat_" + level, getName(type, false, level, false));
                translationBuilder.add("item.copperboats." + "copper_" + type.getName() + "_chest_boat_" + level, getName(type, true, level, false));
                translationBuilder.add("item.copperboats." + "waxed_copper_" + type.getName() + "_boat_" + level, getName(type, false, level, true));
                translationBuilder.add("item.copperboats." + "waxed_copper_" + type.getName() + "_chest_boat_" + level, getName(type, true, level, true));
            }
        }

        translationBuilder.add(BuiltInRegistries.CREATIVE_MODE_TAB.getResourceKey(CreativeTabInit.TAB).orElseThrow(), "Copper Boats");
    }

    private static String getName(Boat.Type type, boolean isChest, int level, boolean isWaxed) {
        return (isWaxed ? "Waxed " : "") + getOxidizationLevel(level) + "Copper-Plated " + getType(type) + (isChest ? " Chest" : "") + (type == Boat.Type.BAMBOO ? " Raft" : " Boat");
    }

    private static String getOxidizationLevel(int level) {
        return switch (level) {
            case 1 -> "Exposed ";
            case 2 -> "Weathered ";
            case 3 -> "Oxidized ";
            default -> "";
        };
    }

    @SuppressWarnings("deprecation")
    private static String getType(Boat.Type type) {
        return WordUtils.capitalize(type.getName().replace("_", " "));
    }
}
