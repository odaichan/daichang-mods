package net.daichang.dcmods.common.item.tools.creative;

import net.daichang.dcmods.inits.DCItems;
import net.daichang.dcmods.inits.DCSounds;
import net.daichang.dcmods.utils.Utils;
import net.daichang.dcmods.utils.helpers.EntityHelper;
import net.daichang.dcmods.utils.lists.GetHealthList;
import net.daichang.dcmods.utils.lists.items.CreativeItemList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DCLoliPickaxe extends Item {
    public DCLoliPickaxe() {
        super(new Properties().stacksTo(1).fireResistant().rarity(Rarity.RARE));
        CreativeItemList.addItem(this);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, Player pPlayer, @NotNull InteractionHand pUsedHand) {
        double range = 200.0D;
        double x = pPlayer.getX();
        double y = pPlayer.getY();
        double z = pPlayer.getZ();
        AABB aabb = new AABB(x - range, y - range, z - range, x + range, y + range, z + range);
        if (pPlayer.isShiftKeyDown()) {
            for (Entity entity : pLevel.getEntitiesOfClass(Entity.class, aabb)) killEntity(entity, pPlayer);
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }

    @Override
    public boolean hurtEnemy(@NotNull ItemStack pStack, @NotNull LivingEntity pTarget, @NotNull LivingEntity pAttacker) {
        killEntity(pTarget, pAttacker);
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        killEntity(entity, player);
        return super.onLeftClickEntity(stack, player, entity);
    }

    public static void killEntity(Entity target, Entity attacker) {
        if (target instanceof LivingEntity living && !isHasLoliPickaxe(living)) {
            DamageSource source = EntityHelper.dc_damage(attacker);
            attacker.playSound(DCSounds.LOLI_SUCCRSS.get());
            Utils.Override_DATA_HEALTH_ID(living, 0.0F);
            GetHealthList.addHealth(living);
            try {
                living.dropAllDeathLoot(source);
            } catch (Exception ignored) {}
            living.setHealth(0.0F);
            living.entityData.set(LivingEntity.DATA_HEALTH_ID, 0.0F);
            Utils.Override_DATA_HEALTH_ID(living, 0.0F);
            EntityHelper.forceSetHealth(living, 0.0F);
            living.gameEvent(GameEvent.ENTITY_DIE);
            living.die(source);
            if (!living.getPersistentData().contains("dc_death") && !(living instanceof Player)) living.getPersistentData().putInt("dc_death", 0);
        }
        if (!(target instanceof LivingEntity)) {
            Entity.RemovalReason reason = Entity.RemovalReason.KILLED;
            target.remove(reason);
            target.setRemoved(reason);
            target.onRemovedFromWorld();
            target.onClientRemoval();
        }
    }

    public static boolean isHasLoliPickaxe(LivingEntity living) {
        boolean isHas = false;
        if (living instanceof Player player && player.getInventory().contains(DCItems.LoliPickaxe.get().getDefaultInstance())){
            living.setHealth(living.getMaxHealth());
            isHas = true;
        }
        return isHas;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("tooltip.dc_m.loli_pickaxe"));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
