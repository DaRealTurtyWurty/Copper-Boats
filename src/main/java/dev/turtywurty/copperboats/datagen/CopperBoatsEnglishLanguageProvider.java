package dev.turtywurty.copperboats.datagen;

import dev.turtywurty.copperboats.CopperBoats;
import dev.turtywurty.copperboats.entity.OxidizableBoat;
import dev.turtywurty.copperboats.init.CreativeTabInit;
import net.minecraft.data.PackOutput;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraftforge.common.data.LanguageProvider;
import org.apache.commons.lang3.text.WordUtils;

public class CopperBoatsEnglishLanguageProvider extends LanguageProvider {
    public CopperBoatsEnglishLanguageProvider(PackOutput dataOutput) {
        super(dataOutput, CopperBoats.MOD_ID, "en_us");
    }

    @Override
    public void addTranslations() {
        for (Boat.Type type : Boat.Type.values()) {
            for (int level = 0; level <= OxidizableBoat.MAX_OXIDATION_LEVEL; level++) {
                if(type == Boat.Type.BAMBOO) {
                    add("item.copperboats." + "copper_" + type.getName() + "_raft_" + level, getName(type, false, level, false));
                    add("item.copperboats." + "copper_" + type.getName() + "_chest_raft_" + level, getName(type, true, level, false));
                    add("item.copperboats." + "waxed_copper_" + type.getName() + "_raft_" + level, getName(type, false, level, true));
                    add("item.copperboats." + "waxed_copper_" + type.getName() + "_chest_raft_" + level, getName(type, true, level, true));
                    continue;
                }

                add("item.copperboats." + "copper_" + type.getName() + "_boat_" + level, getName(type, false, level, false));
                add("item.copperboats." + "copper_" + type.getName() + "_chest_boat_" + level, getName(type, true, level, false));
                add("item.copperboats." + "waxed_copper_" + type.getName() + "_boat_" + level, getName(type, false, level, true));
                add("item.copperboats." + "waxed_copper_" + type.getName() + "_chest_boat_" + level, getName(type, true, level, true));
            }
        }

        add(CreativeTabInit.TAB.getId().toString(), "Copper Boats");
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
