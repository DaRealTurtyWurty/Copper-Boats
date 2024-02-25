package dev.turtywurty.copperboats.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.RaftModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import org.jetbrains.annotations.NotNull;

public class CopperRaftModel extends RaftModel {
    public CopperRaftModel(ModelPart modelPart) {
        super(modelPart);
    }

    public static void createChildren(PartDefinition partDefinition) {
        RaftModel.createChildren(partDefinition);

        partDefinition.addOrReplaceChild("hull", CubeListBuilder.create().texOffs(67, 64).addBox(8.0F, -4.0F, -14.0F, 1.0F, 4.0F, 28.0F, new CubeDeformation(0.0F))
                .texOffs(67, 96).addBox(-9.0F, -4.0F, -14.0F, 1.0F, 4.0F, 28.0F, new CubeDeformation(0.0F))
                .texOffs(4, 99).addBox(-8.0F, 0.0F, -14.0F, 16.0F, 1.0F, 28.0F, new CubeDeformation(0.0F))
                .texOffs(48, 78).addBox(-8.0F, -1.0F, -14.0F, 16.0F, 1.0F, 0.0F, new CubeDeformation(0.1F))
                .texOffs(53, 83).addBox(7.0F, -3.0F, -14.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.1F))
                .texOffs(53, 83).addBox(-8.0F, -3.0F, -14.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.1F))
                .texOffs(57, 83).addBox(-8.0F, -3.0F, 14.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.1F))
                .texOffs(57, 83).addBox(7.0F, -3.0F, 14.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.1F))
                .texOffs(48, 94).addBox(-8.0F, -1.0F, 14.0F, 16.0F, 1.0F, 0.0F, new CubeDeformation(0.1F))
                .texOffs(96, 109).addBox(-8.0F, -4.0F, -14.0F, 16.0F, 1.0F, 0.0F, new CubeDeformation(0.1F))
                .texOffs(96, 125).addBox(-8.0F, -4.0F, 14.0F, 16.0F, 1.0F, 0.0F, new CubeDeformation(0.1F)),
                PartPose.offsetAndRotation(0.0F, 6.0F, 0.0F, 0.0F, (float) (-Math.PI / 2), 0.0F));
    }

    public static LayerDefinition createMainLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();

        createChildren(partDefinition);

        return LayerDefinition.create(meshDefinition, 128, 128);
    }

    @Override
    protected ImmutableList.@NotNull Builder<ModelPart> createPartsBuilder(ModelPart modelPart) {
        ImmutableList.Builder<ModelPart> builder = super.createPartsBuilder(modelPart);
        builder.add(modelPart.getChild("hull"));
        return builder;
    }
}
