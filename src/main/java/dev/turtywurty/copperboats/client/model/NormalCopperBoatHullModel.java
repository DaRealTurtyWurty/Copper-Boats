package dev.turtywurty.copperboats.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.turtywurty.copperboats.CopperBoats;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import org.jetbrains.annotations.NotNull;

public class NormalCopperBoatHullModel extends Model {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(CopperBoats.id("normal"), "main");

    private final ModelPart hull;

    public NormalCopperBoatHullModel(ModelPart root) {
        super(RenderType::entitySolid);

        this.hull = root.getChild("hull");
    }

    public static LayerDefinition createMainLayer() {
        var meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();

        partDefinition.addOrReplaceChild("hull", CubeListBuilder.create().texOffs(27, 59).addBox(-8.0F, -3.0F, -15.0F, 16.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(63, 0).addBox(8.0F, -3.0F, -14.0F, 1.0F, 3.0F, 28.0F, new CubeDeformation(0.0F))
                .texOffs(63, 32).addBox(-9.0F, -3.0F, -14.0F, 1.0F, 3.0F, 28.0F, new CubeDeformation(0.0F))
                .texOffs(0, 35).addBox(-8.0F, 0.0F, -14.0F, 16.0F, 1.0F, 28.0F, new CubeDeformation(0.0F))
                .texOffs(27, 32).addBox(-8.0F, -3.0F, 14.0F, 16.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 6.0F, 0.0F, 0.0F, (float) (Math.PI / 2), 0.0F));

        return LayerDefinition.create(meshDefinition, 128, 64);
    }

    @Override
    public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        this.hull.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}