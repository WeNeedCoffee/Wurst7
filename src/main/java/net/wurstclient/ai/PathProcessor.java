/*
 * Copyright (C) 2014 - 2020 | Alexander01998 | All rights reserved.
 *
 * This source code is subject to the terms of the GNU General Public License,
 * version 3. If a copy of the GPL was not distributed with this file, You can
 * obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */
package net.wurstclient.ai;

import java.util.ArrayList;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.wurstclient.WurstClient;
import net.wurstclient.mixinterface.IKeyBinding;

public abstract class PathProcessor {
	protected static final WurstClient WURST = WurstClient.INSTANCE;
	protected static final MinecraftClient MC = WurstClient.MC;

	private static final KeyBinding[] CONTROLS = new KeyBinding[] { MC.options.keyForward, MC.options.keyBack, MC.options.keyRight, MC.options.keyLeft, MC.options.keyJump, MC.options.keySneak };

	public static final void lockControls() {
		// disable keys
		for (KeyBinding key : CONTROLS) {
			key.setPressed(false);
		}

		// disable sprinting
		WurstClient.MC.player.setSprinting(false);
	}

	public static final void releaseControls() {
		// reset keys
		for (KeyBinding key : CONTROLS) {
			key.setPressed(((IKeyBinding) key).isActallyPressed());
		}
	}

	protected final ArrayList<PathPos> path;
	protected int index;

	protected boolean done;

	protected int ticksOffPath;

	public PathProcessor(ArrayList<PathPos> path) {
		if (path.isEmpty())
			throw new IllegalStateException("There is no path!");

		this.path = path;
	}

	protected final void facePosition(BlockPos pos) {
		WURST.getRotationFaker().faceVectorClientIgnorePitch(new Vec3d(pos).add(0.5, 0.5, 0.5));
	}

	public final int getIndex() {
		return index;
	}

	public final int getTicksOffPath() {
		return ticksOffPath;
	}

	public final boolean isDone() {
		return done;
	}

	public abstract void process();
}