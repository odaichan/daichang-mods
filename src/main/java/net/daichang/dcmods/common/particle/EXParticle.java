package net.daichang.dcmods.common.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;

public class EXParticle extends TextureSheetParticle {
    public static ExParticleProvider provider(SpriteSet spriteSet) {
        return new ExParticleProvider(spriteSet);
    }

    public static class ExParticleProvider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public ExParticleProvider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new EXParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
        }
    }

    private final SpriteSet spriteSet;

    protected EXParticle(ClientLevel world, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
        super(world, x, y, z, vx, vy, vz);
        this.spriteSet = spriteSet;
        this.setSize(0.4f, 0.4f);
        this.lifetime = 40;
        this.gravity = 0f;
        this.hasPhysics = false;
        this.pickSprite(spriteSet);
    }

    @Override
    public int getLightColor(float partialTick) {
        // 设置粒子发光亮度（15 是最大亮度）
        return 15 << 20 | 15 << 4;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public void tick() {
        super.tick();
    }

}
