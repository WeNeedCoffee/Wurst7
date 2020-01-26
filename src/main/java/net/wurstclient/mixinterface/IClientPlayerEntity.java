/*
 * Copyright (C) 2014 - 2020 | Alexander01998 | All rights reserved.
 *
 * This source code is subject to the terms of the GNU General Public License,
 * version 3. If a copy of the GPL was not distributed with this file, You can
 * obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */
package net.wurstclient.mixinterface;

import net.minecraft.util.math.Vec3d;

public interface IClientPlayerEntity {
	float getLastPitch();

	float getLastYaw();

	void setMovementMultiplier(Vec3d movementMultiplier);

	void setNoClip(boolean noClip);
}
