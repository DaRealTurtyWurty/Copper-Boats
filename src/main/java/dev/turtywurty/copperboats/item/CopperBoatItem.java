package dev.turtywurty.copperboats.item;

import com.google.common.collect.ImmutableList;
import dev.turtywurty.copperboats.entity.CopperBoat;
import dev.turtywurty.copperboats.entity.CopperChestBoat;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class CopperBoatItem extends Item {
    private static final Predicate<Entity> ENTITY_PREDICATE = EntitySelector.NO_SPECTATORS.and(Entity::isPickable);
    private static final Map<Triple<Boat.Type, Boolean, Integer>, CopperBoatItem> BOAT_BY_TYPE = new HashMap<>();

    private final Boat.Type type;
    private final boolean hasChest;
    private final int oxidizedLevel;

    public CopperBoatItem(Boat.Type type, boolean hasChest, int oxidizedLevel) {
        super(new Item.Properties().stacksTo(1));

        this.type = type;
        this.hasChest = hasChest;
        this.oxidizedLevel = oxidizedLevel;

        BOAT_BY_TYPE.put(Triple.of(type, hasChest, oxidizedLevel), this);
    }

    public static CopperBoatItem getBoatItem(Boat.Type type, boolean hasChest, int oxidizedLevel) {
        return BOAT_BY_TYPE.get(Triple.of(type, hasChest, oxidizedLevel));
    }

    public static ImmutableList<CopperBoatItem> getBoatItems() {
        return ImmutableList.copyOf(BOAT_BY_TYPE.values());
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        HitResult hitResult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.ANY);

        if (hitResult.getType() == HitResult.Type.MISS) {
            return InteractionResultHolder.pass(itemStack);
        } else {
            Vec3 vec3 = player.getViewVector(1.0F);
            List<Entity> list = level.getEntities(player, player.getBoundingBox().expandTowards(vec3.scale(5.0)).inflate(1.0), ENTITY_PREDICATE);
            if (!list.isEmpty()) {
                Vec3 eyePos = player.getEyePosition();

                for (Entity entity : list) {
                    AABB aABB = entity.getBoundingBox().inflate(entity.getPickRadius());
                    if (aABB.contains(eyePos)) {
                        return InteractionResultHolder.pass(itemStack);
                    }
                }
            }

            if (hitResult.getType() == HitResult.Type.BLOCK) {
                Boat boat = getBoat(level, hitResult, itemStack, player);
                boat.setVariant(this.type);
                boat.setYRot(player.getYRot());
                if (!level.noCollision(boat, boat.getBoundingBox())) {
                    return InteractionResultHolder.fail(itemStack);
                } else {
                    if (!level.isClientSide) {
                        level.addFreshEntity(boat);
                        level.gameEvent(player, GameEvent.ENTITY_PLACE, hitResult.getLocation());
                        if (!player.getAbilities().instabuild) {
                            itemStack.shrink(1);
                        }
                    }

                    player.awardStat(Stats.ITEM_USED.get(this));
                    return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
                }
            } else {
                return InteractionResultHolder.pass(itemStack);
            }
        }
    }

    private Boat getBoat(Level level, HitResult hitResult, ItemStack itemStack, Player player) {
        Vec3 vec3 = hitResult.getLocation();
        Boat boat = this.hasChest ? new CopperChestBoat(level, vec3.x, vec3.y, vec3.z) : new CopperBoat(level, vec3.x, vec3.y, vec3.z);
        if (level instanceof ServerLevel serverLevel) {
            EntityType.createDefaultStackConfig(serverLevel, itemStack, player).accept(boat);
        }

        return boat;
    }
}
