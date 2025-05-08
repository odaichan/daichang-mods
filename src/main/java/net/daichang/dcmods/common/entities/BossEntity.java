package net.daichang.dcmods.common.entities;

import net.daichang.dcmods.event.DCForgeEventHandler;
import net.daichang.dcmods.inits.DCAttributes;
import net.daichang.dcmods.utils.helpers.EntityHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class BossEntity extends Monster {
    private final BossMusic music = new BossMusic(this);

    public BossEntity(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public ResourceLocation getBossBar() {
        return null;
    }

    public ResourceLocation getBossBarOn() {
        return null;
    }

    public ResourceLocation getBossBarMask() {
        return null;
    }

    public SoundEvent getBossMusic() {
        return null;
    }

    public boolean isHasMask() {
        return false;
    }

    public Font getBossBarFont() {
        return Minecraft.getInstance().font;
    }

    @Override
    public boolean doHurtTarget(@NotNull Entity pEntity) {
        float damage = 0;
        damage = (float) (damage + this.getAttributeValue(Attributes.ATTACK_DAMAGE));
        damage = (float) (damage + this.getAttributeValue(DCAttributes.DC_SUPER_DAMAGE.get()));
        if (!(pEntity instanceof Player)) pEntity.hurt(EntityHelper.dc_damage(this), damage);
        return super.doHurtTarget(pEntity);
    }

    @Override
    public void kill() {

    }

    @Override
    public void die(DamageSource pDamageSource) {
        super.die(pDamageSource);
        DCForgeEventHandler.bossList.remove(this);
    }

    @Override
    public void remove(RemovalReason pReason) {
        super.remove(pReason);
        DCForgeEventHandler.bossList.remove(this);
    }

    @Override
    public void tick() {
        super.tick();
        if (level().isClientSide()) BossMusic.playMusic(music ,this);
    }

    @Override
    public void setRemoved(RemovalReason pRemovalReason) {
        super.setRemoved(pRemovalReason);
        DCForgeEventHandler.bossList.remove(this);
    }

    @Override
    public void onClientRemoval() {
        super.onClientRemoval();
        DCForgeEventHandler.bossList.remove(this);
    }

    @Override
    public void onRemovedFromWorld() {
        super.onRemovedFromWorld();
        DCForgeEventHandler.bossList.remove(this);
    }
}
