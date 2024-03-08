package dev.turtywurty.copperboats.client;

import dev.turtywurty.copperboats.CopperBoats;
import dev.turtywurty.copperboats.client.model.NormalCopperBoatHullModel;
import dev.turtywurty.copperboats.client.model.RaftCopperBoatHullModel;
import dev.turtywurty.copperboats.client.renderer.CopperBoatRenderer;
import dev.turtywurty.copperboats.init.EntityTypeInit;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CopperBoats.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EntityTypeInit.COPPER_BOAT.get(), context -> new CopperBoatRenderer(context, false));
        event.registerEntityRenderer(EntityTypeInit.COPPER_CHEST_BOAT.get(), context -> new CopperBoatRenderer(context, true));
    }

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(NormalCopperBoatHullModel.LAYER_LOCATION, NormalCopperBoatHullModel::createMainLayer);
        event.registerLayerDefinition(RaftCopperBoatHullModel.LAYER_LOCATION, RaftCopperBoatHullModel::createMainLayer);
    }
}
