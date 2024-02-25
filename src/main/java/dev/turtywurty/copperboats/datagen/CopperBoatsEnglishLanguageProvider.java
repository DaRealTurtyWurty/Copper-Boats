package dev.turtywurty.copperboats.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.world.entity.vehicle.Boat;
import org.apache.commons.lang3.text.WordUtils;

public class CopperBoatsEnglishLanguageProvider extends FabricLanguageProvider {
    protected CopperBoatsEnglishLanguageProvider(FabricDataOutput dataOutput) {
        super(dataOutput, "en_us");
    }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        for (Boat.Type type : Boat.Type.values()) {
            if(type == Boat.Type.BAMBOO) {
                translationBuilder.add("item.copperboats." + "copper_" + type.getName() + "_raft", getName(type, false));
                translationBuilder.add("item.copperboats." + "copper_" + type.getName() + "_chest_raft", getName(type, true));
                continue;
            }

            translationBuilder.add("item.copperboats." + "copper_" + type.getName() + "_boat", getName(type, false));
            translationBuilder.add("item.copperboats." + "copper_" + type.getName() + "_chest_boat", getName(type, true));
        }
    }

    @SuppressWarnings("deprecation")
    private static String getName(Boat.Type type, boolean isChest) {
        return "Copper-Plated " + WordUtils.capitalize(type.getName().replace("_", " ")) + (isChest ? " Chest" : "") + (type == Boat.Type.BAMBOO ? " Raft" : " Boat");
    }
}
