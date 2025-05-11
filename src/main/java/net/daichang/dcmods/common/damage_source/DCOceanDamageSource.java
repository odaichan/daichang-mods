package net.daichang.dcmods.common.damage_source;

import net.minecraft.core.Holder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;

public class DCOceanDamageSource extends DamageSource {
    public DCOceanDamageSource(Holder<DamageType> damageTypeHolder, @Nullable Entity entity) {
        super(damageTypeHolder, entity);
    }
}
