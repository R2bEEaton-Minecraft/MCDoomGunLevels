package cc.spea.mcdoomgunlevels.events;

import mod.azure.doom.items.weapons.DoomBaseItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
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
                CompoundTag tag = itemStack.getOrCreateTag();
                int kills = tag.getInt("killCount");
                tag.putInt("killCount", kills + 1);
                itemStack.setTag(tag);
            }
            if (le instanceof Player plr) plr.displayClientMessage(Component.literal("you killed something wow nice"), false);
        }
    }
}
