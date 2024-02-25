package dev.turtywurty.copperboats.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import org.jetbrains.annotations.NotNull;

public class CopperBoatModel extends BoatModel {
    public CopperBoatModel(ModelPart modelPart) {
        super(modelPart);
    }

    public static void createChildren(PartDefinition partDefinition) {
        BoatModel.createChildren(partDefinition);

        partDefinition.addOrReplaceChild("hull", CubeListBuilder.create().texOffs(31, 123).addBox(-8.0F, -3.0F, -15.0F, 16.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(67, 64).addBox(8.0F, -3.0F, -14.0F, 1.0F, 3.0F, 28.0F, new CubeDeformation(0.0F))
                .texOffs(67, 96).addBox(-9.0F, -3.0F, -14.0F, 1.0F, 3.0F, 28.0F, new CubeDeformation(0.0F))
                .texOffs(4, 99).addBox(-8.0F, 0.0F, -14.0F, 16.0F, 1.0F, 28.0F, new CubeDeformation(0.0F))
                .texOffs(31, 96).addBox(-8.0F, -3.0F, 14.0F, 16.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 6.0F, 0.0F, 0.0F, (float) (Math.PI / 2), 0.0F));
    }

    public static LayerDefinition createMainLayer() {
        var meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();

        createChildren(partDefinition);

        return LayerDefinition.create(meshDefinition, 128, 128);
    }

    protected ImmutableList.@NotNull Builder<ModelPart> createPartsBuilder(ModelPart modelPart) {
        ImmutableList.Builder<ModelPart> builder = super.createPartsBuilder(modelPart);
        builder.add(modelPart.getChild("hull"));
        return builder;
    }
}