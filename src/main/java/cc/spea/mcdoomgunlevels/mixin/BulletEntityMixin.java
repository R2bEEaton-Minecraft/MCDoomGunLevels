package cc.spea.mcdoomgunlevels.mixin;

import cc.spea.mcdoomgunlevels.interfaces.BulletEntityMixinInterface;
import mod.azure.doom.entities.projectiles.BulletEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(BulletEntity.class)
public class BulletEntityMixin implements BulletEntityMixinInterface {
    @Shadow private float projectiledamage;

    @Unique
    public float getProjectileDamage() {
        return this.projectiledamage;
    }

    @Unique
    public void setProjectileDamage(float newDamage) {
        this.projectiledamage = newDamage;
    }
}
