package dev.turtywurty.copperboats.util;

import dev.turtywurty.copperboats.CopperBoats;
import dev.turtywurty.copperboats.datagen.CopperBoatsEnglishLanguageProvider;
import dev.turtywurty.copperboats.datagen.CopperBoatsModelProvider;
import dev.turtywurty.copperboats.datagen.CopperBoatsRecipeProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CopperBoats.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEvents {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = event.getGenerator().getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        generator.addProvider(event.includeServer(), new CopperBoatsRecipeProvider(output));
        generator.addProvider(event.includeClient(), new CopperBoatsModelProvider(output, existingFileHelper));
        generator.addProvider(event.includeClient(), new CopperBoatsEnglishLanguageProvider(output));
    }
}
