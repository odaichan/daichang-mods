package net.daichang.dcmods.addons.farmers_delight;

import net.daichang.dcmods.inits.DCEffects;
import net.daichang.dcmods.utils.helpers.EffectHelper;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class FoodValue {
    private static final int HALF, FIVE,TEN, BRIEF, SHORT, MEDIUM, LONG, SUPER_LONG;

    public static final FoodProperties stewedOceanHeart;

    static {
        HALF = 10;
        FIVE = 100;
        TEN = 200;
        BRIEF = 600;
        SHORT = 1200;
        MEDIUM = 3600;
        LONG = 6000;
        SUPER_LONG = 24000;
        stewedOceanHeart = new FoodProperties.Builder().alwaysEat()
                .meat()
                .nutrition(5738)
                .saturationMod(86.3F)
                .effect(EffectHelper.addEffect(DCEffects.Heal.get(), TEN, 4) , 10)
                .effect(EffectHelper.addEffect(DCEffects.Speed.get(), TEN, 2) , 10)
                .effect(EffectHelper.addEffect(DCEffects.EnchantressMercy.get(), TEN, 1) , 10)
                .effect(EffectHelper.addEffect(MobEffects.DARKNESS, FIVE, 1) , 10)
                .build();
    }
}