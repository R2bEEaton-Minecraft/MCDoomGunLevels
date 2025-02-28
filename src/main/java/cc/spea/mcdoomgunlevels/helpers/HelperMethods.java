package cc.spea.mcdoomgunlevels.helpers;

import net.minecraft.world.item.ItemStack;

import static cc.spea.mcdoomgunlevels.MCDoomGunLevels.*;

public class HelperMethods {
    public static int getKillCount(ItemStack itemStack) {
        return itemStack.getOrCreateTag().getInt("killCount");
    }

    public static int getCurrentLevel(int kills) {
        long numerator = (long) (-(2 * STARTING_KILLS_FOR_LEVEL - KILLS_FOR_ADDITIONAL_LEVELS) +
                Math.sqrt(Math.pow(2 * STARTING_KILLS_FOR_LEVEL - KILLS_FOR_ADDITIONAL_LEVELS, 2) +
                        8 * KILLS_FOR_ADDITIONAL_LEVELS * kills));
        int denominator = 2 * KILLS_FOR_ADDITIONAL_LEVELS;
        int currentLevel = (int) Math.floorDiv(numerator, denominator);
        return Math.min(currentLevel, MAX_LEVELS);
    }
}
