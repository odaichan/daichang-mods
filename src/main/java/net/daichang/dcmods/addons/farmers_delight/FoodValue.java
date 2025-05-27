package net.daichang.dcmods.addons.farmers_delight;

import net.daichang.dcmods.inits.DCEffects;
import net.daichang.dcmods.utils.helpers.EffectHelper;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class FoodValue {
    private static final int HALF, FIVE,TEN, BRIEF, SHORT, MEDIUM, LONG, SUPER_LONG;

    public static final FoodProperties stewedOceanHeart;

    static {
        HALF = 1;
        FIVE = 5;
        TEN = 10;
        BRIEF = 30;
        SHORT = 60;
        MEDIUM = 120;
        LONG = 300;
        SUPER_LONG = 1200;
        stewedOceanHeart = new FoodProperties.Builder().alwaysEat()
                .meat()
                .nutrition(5738)
                .saturationMod(86.3F)
                .effect(EffectHelper.addEffect(DCEffects.Heal.get(), TEN, 4) , 10)
                .effect(EffectHelper.addEffect(DCEffects.Speed.get(), TEN, 2) , 10)
                .effect(EffectHelper.addEffect(DCEffects.EnchantressMercy.get(), TEN, 1) , 10)
                .effect(EffectHelper.addEffect(MobEffects.DARKNESS, TEN, 1) , 10)
                .build();
    }
}