package net.daichang.dcmods.bytes.mixins;

import net.daichang.dcmods.common.entities.BossEntity;
import net.daichang.dcmods.utils.DeprecatedMixin;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.BossHealthOverlay;
import net.minecraft.client.gui.components.LerpingBossEvent;
import net.minecraft.world.BossEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.daichang.dcmods.event.DCForgeEventHandler.BOSSES;

@Mixin(BossHealthOverlay.class)
@DeprecatedMixin
public abstract class MixinHealthOverlay {
    @Inject(method = "drawBar(Lnet/minecraft/client/gui/GuiGraphics;IILnet/minecraft/world/BossEvent;)V", at = @At("RETURN"))
    private void drawBar(GuiGraphics guiGraphics, int pX, int pY, BossEvent pBossEvent, CallbackInfo ci) {
        if (pBossEvent instanceof LerpingBossEvent bossEvent) {
            BossEntity boss = null;
            if (BOSSES.isEmpty())
                return;
            for (BossEntity mob : BOSSES) {
                if (mob.getUUID().equals(bossEvent.getId())) {
                    boss = mob;
                    break;
                }
            }
            if (boss != null) {
                float maxHealth = boss.getMaxHealth();
                float health = boss.getHealth();
                Font font = boss.getBossBarFont();
                String displayHealth = String.format("%.1f/%.1f", health, maxHealth);
                int healthWidth = font.width(displayHealth);
                guiGraphics.blit(boss.getBossBarOverlay(), pX, pY, 0.0F, 0.0F, 202, 19, 202, 19);
                guiGraphics.drawString(font, displayHealth, pX - (healthWidth / 2), pY, -26368, false);
            }
        }
    }
}