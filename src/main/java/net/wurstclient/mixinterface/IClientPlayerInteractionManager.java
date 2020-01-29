/*
 * Copyright (C) 2014 - 2020 | Alexander01998 | All rights reserved.
 *
 * This source code is subject to the terms of the GNU General Public License,
 * version 3. If a copy of the GPL was not distributed with this file, You can
 * obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */
package net.wurstclient.mixinterface;

import net.minecraft.item.ItemStack;
import net.minecraft.server.network.packet.PlayerActionC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

public interface IClientPlayerInteractionManager {
	float getCurrentBreakingProgress();

	void rightClickBlock(BlockPos pos, Direction side, Vec3d hitVec);

	void rightClickBlock(BlockPos pos, Direction side, Vec3d hitVec, Hand hand);

	void rightClickItem();

	void sendPlayerActionC2SPacket(PlayerActionC2SPacket.Action action, BlockPos blockPos, Direction direction);

	void setBlockHitDelay(int delay);

	void setBreakingBlock(boolean breakingBlock);

	void setOverrideReach(boolean overrideReach);

	ItemStack windowClick_PICKUP(int slot);

	ItemStack windowClick_QUICK_MOVE(int slot);

	ItemStack windowClick_THROW(int slot);
}
