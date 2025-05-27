package net.daichang.dcmods.addons.slashblade;

import mods.flammpfeil.slashblade.capability.slashblade.ISlashBladeState;
import mods.flammpfeil.slashblade.event.SlashBladeEvent;
import mods.flammpfeil.slashblade.registry.specialeffects.SpecialEffect;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class DaiChangSpecialEffect extends SpecialEffect {
    public DaiChangSpecialEffect(int requestLevel) {
        super(requestLevel);
    }

    public DaiChangSpecialEffect(int requestLevel, boolean isCopiable, boolean isRemovable) {
        super(requestLevel, isCopiable, isRemovable);
    }

    public DaiChangSpecialEffect() {
        super(35, true, true);
    }

    @SubscribeEvent
    public static void onSlashBladeUpdate(SlashBladeEvent.UpdateEvent event) {
        ISlashBladeState state = event.getSlashBladeState();
        if (state.hasSpecialEffect(SBInits.DC_EDGE.getId())) {
            if (!event.isSelected()) {
                return;
            }
            if (event.getEntity() instanceof Player living) {
                int level = living.experienceLevel;
                if (SpecialEffect.isEffective(SBInits.DC_EDGE.getId(), level)) {
                    living.addEffect(new MobEffectInstance(MobEffects.HEAL, 100, 1));
                    living.addEffect(new MobEffectInstance(MobEffects.SATURATION, 100, 2));
                }
            }
        }
    }

    @SubscribeEvent
    public static void onSlashBladeAttack(SlashBladeEvent.UpdateAttackEvent event) {

    }

//    @SubscribeEvent
//    public static void onSlashBladeHit(SlashBladeEvent.HitEvent event) {
//        ISlashBladeState state = event.getSlashBladeState();
//        if (state.hasSpecialEffect(SBInits.DC_EDGE.getId())) {
//            if (!(event.getUser() instanceof Player player)) {
//                return;
//            }
//            int level = player.experienceLevel;
//            if (SpecialEffect.isEffective(SBInits.DC_EDGE.getId(), level))
//                DaiChangSB.onDCSBHitEntity(event.getBlade(), event.getTarget(), event.getUser());
//        }
//    }

    @Override
    public Component getDescription() {
        return Component.literal(super.getDescription().getString()).withStyle(ChatFormatting.BLUE);
    }
}
