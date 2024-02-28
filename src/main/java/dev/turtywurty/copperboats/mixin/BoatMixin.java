package dev.turtywurty.copperboats.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalFloatRef;
import dev.turtywurty.copperboats.entity.CopperBoat;
import dev.turtywurty.copperboats.entity.CopperChestBoat;
import dev.turtywurty.copperboats.entity.OxidizableBoat;
import net.minecraft.world.entity.vehicle.Boat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Boat.class)
public class BoatMixin {
    @Inject(
            method = "controlBoat",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/vehicle/Boat;setDeltaMovement(Lnet/minecraft/world/phys/Vec3;)V"
            )
    )
    private void controlBoat(CallbackInfo callback, @Local LocalFloatRef speedModifier) {
        Boat boat = (Boat) (Object) this;
        if (boat instanceof OxidizableBoat oxidizable) {
            speedModifier.set((speedModifier.get() * 1.5F) * (1.0F - (0.1F * oxidizable.getOxidationLevel())));
        }
    }
}
