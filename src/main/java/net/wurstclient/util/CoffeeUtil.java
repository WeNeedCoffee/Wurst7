package net.wurstclient.util;

import net.minecraft.block.AbstractButtonBlock;
import net.minecraft.block.AnvilBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.TrapdoorBlock;

public class CoffeeUtil {

	public static boolean isClickSafe(Block block) {
		if (block instanceof DoorBlock || block instanceof BlockWithEntity || block instanceof TrapdoorBlock || block instanceof AnvilBlock || block instanceof AbstractButtonBlock)
			return false;
		
		return true;
	}
}
