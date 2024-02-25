package dev.turtywurty.copperboats;

import dev.turtywurty.copperboats.init.EntityTypeInit;
import dev.turtywurty.copperboats.model.CopperBoatModel;
import dev.turtywurty.copperboats.model.CopperChestBoatModel;
import dev.turtywurty.copperboats.model.CopperChestRaftModel;
import dev.turtywurty.copperboats.model.CopperRaftModel;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.model.ChestRaftModel;
import net.minecraft.client.model.RaftModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.world.entity.vehicle.Boat;

public class CopperBoatsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        CopperBoats.LOGGER.info("Copper Boats has been initialized on the client side!");

        EntityRendererRegistry.register(EntityTypeInit.COPPER_BOAT, context -> new BoatRenderer(context, false));
        EntityRendererRegistry.register(EntityTypeInit.COPPER_CHEST_BOAT, context -> new BoatRenderer(context, true));

        registerModelLayers();
    }

    private static void registerModelLayers() {
        LayerDefinition normal = CopperBoatModel.createMainLayer();
        LayerDefinition normalChest = CopperChestBoatModel.createMainLayer();
        LayerDefinition raft = CopperRaftModel.createMainLayer();
        LayerDefinition raftChest = CopperChestRaftModel.createMainLayer();
        for(Boat.Type type : Boat.Type.values()) {
            if (type == Boat.Type.BAMBOO) {
                EntityModelLayerRegistry.registerModelLayer(createBoatModelName(type), () -> raft);
                EntityModelLayerRegistry.registerModelLayer(createChestBoatModelName(type), () -> raftChest);
            } else {
                EntityModelLayerRegistry.registerModelLayer(createBoatModelName(type), () -> normal);
                EntityModelLayerRegistry.registerModelLayer(createChestBoatModelName(type), () -> normalChest);
            }
        }
    }

    public static ModelLayerLocation createBoatModelName(Boat.Type type) {
        return createLocation("copper_boat/" + type.getName(), "main");
    }

    public static ModelLayerLocation createChestBoatModelName(Boat.Type type) {
        return createLocation("copper_chest_boat/" + type.getName(), "main");
    }

    private static ModelLayerLocation createLocation(String id, String name) {
        return new ModelLayerLocation(CopperBoats.id(id), name);
    }
}