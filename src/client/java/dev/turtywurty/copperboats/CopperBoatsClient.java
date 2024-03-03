package dev.turtywurty.copperboats;

import dev.turtywurty.copperboats.init.EntityTypeInit;
import dev.turtywurty.copperboats.model.NormalCopperBoatHullModel;
import dev.turtywurty.copperboats.model.RaftCopperBoatHullModel;
import dev.turtywurty.copperboats.renderer.CopperBoatRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class CopperBoatsClient implements ClientModInitializer {
    private static void registerModelLayers() {
        EntityModelLayerRegistry.registerModelLayer(NormalCopperBoatHullModel.LAYER_LOCATION, NormalCopperBoatHullModel::createMainLayer);
        EntityModelLayerRegistry.registerModelLayer(RaftCopperBoatHullModel.LAYER_LOCATION, RaftCopperBoatHullModel::createMainLayer);
    }

    @Override
    public void onInitializeClient() {
        CopperBoats.LOGGER.info("Copper Boats has been initialized on the client side!");

        EntityRendererRegistry.register(EntityTypeInit.COPPER_BOAT, context -> new CopperBoatRenderer(context, false));
        EntityRendererRegistry.register(EntityTypeInit.COPPER_CHEST_BOAT, context -> new CopperBoatRenderer(context, true));

        registerModelLayers();
    }
}