package net.wurstclient.util;

import net.minecraft.block.AbstractButtonBlock;
import net.minecraft.block.AnvilBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.block.TrapdoorBlock;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.wurstclient.WurstClient;

public class CoffeeUtil {
	boolean mcmmo = true;

	public static boolean isClickSafe(Block block) {
		if (block instanceof DoorBlock || block instanceof BlockWithEntity || block instanceof TrapdoorBlock || block instanceof AnvilBlock || block instanceof AbstractButtonBlock || block instanceof ShulkerBoxBlock || block instanceof ChestBlock)
			return false;

		return true;
	}

	public static int getBestWeapon(LivingEntity enemy) {
		float best = -1;
		boolean axe = false;
		int b = -1;
		for (int i = 0; i < 9; i++) {
			try {
				ItemStack item = WurstClient.MC.player.inventory.getInvStack(i);
				if (best == -1) {
					axe = isAxe(item.getItem());
					best = EnchantmentHelper.getAttackDamage(item, enemy.getGroup());
					b = i;
					continue;
				} else {
					float dmg = EnchantmentHelper.getAttackDamage(item, enemy.getGroup());
					if (dmg >= best || (dmg >= (best - 2) && isAxe(item.getItem()) && !axe)) {
						best = dmg;
						b = i;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return b == -1 ? 1 : b;
	}

	public static boolean isAxe(Item item) {
		return item.equals(Items.WOODEN_AXE) || item.equals(Items.STONE_AXE) || item.equals(Items.IRON_AXE) || item.equals(Items.GOLDEN_AXE) || item.equals(Items.DIAMOND_AXE);
	}

	public boolean isDiamond(Item item) {
		return false;
	}
}