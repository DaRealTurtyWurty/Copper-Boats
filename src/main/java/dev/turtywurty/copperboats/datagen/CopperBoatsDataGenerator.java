package dev.turtywurty.copperboats.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class CopperBoatsDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(CopperBoatsEnglishLanguageProvider::new);
        pack.addProvider(CopperBoatsRecipeProvider::new);
        pack.addProvider(CopperBoatsModelProvider::new);
    }
}
