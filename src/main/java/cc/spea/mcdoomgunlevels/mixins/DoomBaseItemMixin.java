package cc.spea.mcdoomgunlevels.mixins;

import cc.spea.mcdoomgunlevels.helpers.HelperMethods;
import cc.spea.mcdoomgunlevels.interfaces.BulletEntityMixinInterface;
import mod.azure.doom.items.weapons.DoomBaseItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

import static cc.spea.mcdoomgunlevels.MCDoomGunLevels.*;

@Mixin(value = DoomBaseItem.class, remap = false)
public class DoomBaseItemMixin {
	@Inject(method = "singleFire", at = @At("TAIL"), locals = LocalCapture.CAPTURE_FAILHARD)
	private void singleFire(ItemStack itemStack, Level level, Player player, CallbackInfo ci, Projectile bullet) {
		int kills = HelperMethods.getKillCount(itemStack);
		int currentLevel = HelperMethods.getCurrentLevel(kills);

		// player.displayClientMessage(Component.literal("test"), false);
		// player.displayClientMessage(Component.literal(String.valueOf(((BulletEntityMixinInterface) bullet).getProjectileDamage())), false);

		float newDamage = ((BulletEntityMixinInterface) bullet).getProjectileDamage();
		for (int i = 1; i <= currentLevel; i++) {
			if (i <= 5) {
				newDamage += newDamage * 0.05f;
			} else if (i <= 9) {
				newDamage += newDamage * 0.1f;
			} else {
				newDamage += newDamage * 0.2f;
			}
		}

		((BulletEntityMixinInterface) bullet).setProjectileDamage(newDamage);

		// player.displayClientMessage(Component.literal(String.valueOf(((BulletEntityMixinInterface) bullet).getProjectileDamage())), false);
	}

	@Inject(method = "m_7373_", at = @At("TAIL"))
	private void appendHoverText(ItemStack itemStack, Level level, List<Component> tooltip, @NotNull TooltipFlag tooltipFlag, CallbackInfo ci) {
		int kills = HelperMethods.getKillCount(itemStack);
		int currentLevel = HelperMethods.getCurrentLevel(kills);
		double killsForCurrentLevel = currentLevel / 2.0 * (2 * STARTING_KILLS_FOR_LEVEL + KILLS_FOR_ADDITIONAL_LEVELS * (currentLevel - 1));
		int adjustedKills = (int) (kills - killsForCurrentLevel);
		int killsRequiredForNextLevel = STARTING_KILLS_FOR_LEVEL + (currentLevel * KILLS_FOR_ADDITIONAL_LEVELS);

		tooltip.add(Component.translatable("gui.mcdoomgunlevels.level_number", currentLevel, MAX_LEVELS)
				.withStyle(ChatFormatting.GOLD));
		if (currentLevel < MAX_LEVELS) {
			tooltip.add(Component.translatable("gui.mcdoomgunlevels.next_kills", adjustedKills, killsRequiredForNextLevel)
					.withStyle(ChatFormatting.GOLD));
		}
		tooltip.add(Component.translatable("gui.mcdoomgunlevels.total_kills", kills)
				.withStyle(ChatFormatting.GOLD));
	}
}
