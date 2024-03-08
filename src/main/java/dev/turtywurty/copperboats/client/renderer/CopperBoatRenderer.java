package dev.turtywurty.copperboats.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.datafixers.util.Pair;
import com.mojang.math.Axis;
import dev.turtywurty.copperboats.CopperBoats;
import dev.turtywurty.copperboats.entity.OxidizableBoat;
import dev.turtywurty.copperboats.client.model.NormalCopperBoatHullModel;
import dev.turtywurty.copperboats.client.model.RaftCopperBoatHullModel;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.WaterPatchModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.vehicle.Boat;
import org.joml.Quaternionf;

import java.util.ArrayList;
import java.util.List;

public class CopperBoatRenderer extends BoatRenderer {
    private static final List<ResourceLocation> NORMAL_TEXTURES = new ArrayList<>(), RAFT_TEXTURES = new ArrayList<>();

    static {
        for (int level = 0; level <= OxidizableBoat.MAX_OXIDATION_LEVEL; level++) {
            NORMAL_TEXTURES.add(CopperBoats.id("textures/entity/copper_boat/normal_" + level + ".png"));
            RAFT_TEXTURES.add(CopperBoats.id("textures/entity/copper_boat/raft_" + level + ".png"));
        }
    }

    private final Model normalHull, raftHull;

    public CopperBoatRenderer(EntityRendererProvider.Context context, boolean isChest) {
        super(context, isChest);

        this.normalHull = new NormalCopperBoatHullModel(context.bakeLayer(NormalCopperBoatHullModel.LAYER_LOCATION));
        this.raftHull = new RaftCopperBoatHullModel(context.bakeLayer(RaftCopperBoatHullModel.LAYER_LOCATION));
    }

    @Override
    public void render(Boat boat, float f, float g, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        poseStack.translate(0.0F, 0.375F, 0.0F);
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - f));
        float h = (float)boat.getHurtTime() - g;
        float j = boat.getDamage() - g;
        if (j < 0.0F) {
            j = 0.0F;
        }

        if (h > 0.0F) {
            poseStack.mulPose(Axis.XP.rotationDegrees(Mth.sin(h) * h * j / 10.0F * (float)boat.getHurtDir()));
        }

        float k = boat.getBubbleAngle(g);
        if (!Mth.equal(k, 0.0F)) {
            poseStack.mulPose(new Quaternionf().setAngleAxis(boat.getBubbleAngle(g) * (float) (Math.PI / 180.0), 1.0F, 0.0F, 1.0F));
        }

        Pair<ResourceLocation, ListModel<Boat>> pair = this.boatResources.get(boat.getVariant());
        ResourceLocation resourceLocation = pair.getFirst();
        ListModel<Boat> listModel = pair.getSecond();

        poseStack.scale(-1.0F, -1.0F, 1.0F);
        poseStack.mulPose(Axis.YP.rotationDegrees(90.0F));

        listModel.setupAnim(boat, g, 0.0F, -0.1F, 0.0F, 0.0F);
        VertexConsumer vertexConsumer = buffer.getBuffer(listModel.renderType(resourceLocation));

        listModel.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

        // Copper boat hull
        if (boat instanceof OxidizableBoat oxidizable) {
            if(boat.getVariant() == Boat.Type.BAMBOO) {
                VertexConsumer consumer = buffer.getBuffer(this.raftHull.renderType(RAFT_TEXTURES.get(oxidizable.getOxidationLevel())));
                this.raftHull.renderToBuffer(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
            } else {
                VertexConsumer consumer = buffer.getBuffer(this.normalHull.renderType(NORMAL_TEXTURES.get(oxidizable.getOxidationLevel())));
                this.normalHull.renderToBuffer(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
            }
        }

        if (!boat.isUnderWater()) {
            VertexConsumer vertexConsumer2 = buffer.getBuffer(RenderType.waterMask());
            if (listModel instanceof WaterPatchModel waterPatchModel) {
                waterPatchModel.waterPatch().render(poseStack, vertexConsumer2, packedLight, OverlayTexture.NO_OVERLAY);
            }
        }

        poseStack.popPose();
    }
}
