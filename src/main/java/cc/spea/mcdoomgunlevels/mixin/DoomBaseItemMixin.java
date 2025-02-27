package cc.spea.mcdoomgunlevels.mixin;

import cc.spea.mcdoomgunlevels.interfaces.BulletEntityMixinInterface;
import mod.azure.doom.entities.projectiles.BulletEntity;
import mod.azure.doom.items.weapons.DoomBaseItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(DoomBaseItem.class)
public class DoomBaseItemMixin {
	@Inject(method = "singleFire", at = @At("TAIL"), remap = false, locals = LocalCapture.CAPTURE_FAILHARD)
	private void singleFire(ItemStack itemStack, Level level, Player player, CallbackInfo ci, Projectile bullet) {
		player.displayClientMessage(Component.literal("test"), false);
		player.displayClientMessage(Component.literal(String.valueOf(((BulletEntityMixinInterface) bullet).getProjectileDamage())), false);
		((BulletEntityMixinInterface) bullet).setProjectileDamage(100);
		player.displayClientMessage(Component.literal(String.valueOf(((BulletEntityMixinInterface) bullet).getProjectileDamage())), false);
	}
}