package dev.turtywurty.copperboats.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import org.jetbrains.annotations.NotNull;

public class CopperChestRaftModel extends CopperRaftModel {
    public CopperChestRaftModel(ModelPart modelPart) {
        super(modelPart);
    }

    public static LayerDefinition createMainLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();

        CopperRaftModel.createChildren(partDefinition);
        partDefinition.addOrReplaceChild(
                "chest_bottom",
                CubeListBuilder.create().texOffs(0, 76).addBox(0.0F, 0.0F, 0.0F, 12.0F, 8.0F, 12.0F),
                PartPose.offsetAndRotation(-2.0F, -10.1F, -6.0F, 0.0F, (float) (-Math.PI / 2), 0.0F));

        partDefinition.addOrReplaceChild(
                "chest_lid",
                CubeListBuilder.create().texOffs(0, 59).addBox(0.0F, 0.0F, 0.0F, 12.0F, 4.0F, 12.0F),
                PartPose.offsetAndRotation(-2.0F, -14.1F, -6.0F, 0.0F, (float) (-Math.PI / 2), 0.0F));

        partDefinition.addOrReplaceChild(
                "chest_lock",
                CubeListBuilder.create().texOffs(0, 59).addBox(0.0F, 0.0F, 0.0F, 2.0F, 4.0F, 1.0F),
                PartPose.offsetAndRotation(-1.0F, -11.1F, -1.0F, 0.0F, (float) (-Math.PI / 2), 0.0F));

        return LayerDefinition.create(meshDefinition, 128, 128);
    }

    @Override
    protected ImmutableList.@NotNull Builder<ModelPart> createPartsBuilder(ModelPart modelPart) {
        ImmutableList.Builder<ModelPart> builder = super.createPartsBuilder(modelPart);
        builder.add(modelPart.getChild("chest_bottom"));
        builder.add(modelPart.getChild("chest_lid"));
        builder.add(modelPart.getChild("chest_lock"));
        return builder;
    }
}
