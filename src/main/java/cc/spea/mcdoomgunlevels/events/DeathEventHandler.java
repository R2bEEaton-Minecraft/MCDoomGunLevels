package cc.spea.mcdoomgunlevels.events;

import cc.spea.mcdoomgunlevels.helpers.HelperMethods;
import mod.azure.doom.items.weapons.DoomBaseItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class DeathEventHandler {
    @SubscribeEvent
    public void onLivingDeath(LivingDeathEvent event){
        Entity e = event.getSource().getEntity();
        if (e instanceof LivingEntity le) {
            ItemStack itemStack = le.getMainHandItem();
            if (!itemStack.isEmpty() && itemStack.getItem() instanceof DoomBaseItem) {
                // Increment kills
                CompoundTag tag = itemStack.getOrCreateTag();
                int kills = HelperMethods.getKillCount(itemStack);
                tag.putInt("killCount", kills + 1);
                itemStack.setTag(tag);
                // If leveled up, play sound on player
                if (le instanceof Player plr && HelperMethods.getCurrentLevel(kills) != HelperMethods.getCurrentLevel(kills + 1)) {
                    plr.level().playSound(null, plr.blockPosition(), SoundEvents.PLAYER_LEVELUP, SoundSource.NEUTRAL);
                    // plr.displayClientMessage(Component.literal("you leveled up your gun"), false);
                }
            }
            // if (le instanceof Player plr) plr.displayClientMessage(Component.literal("you killed something wow nice"), false);
        }
    }
}
