/*
 * Copyright (C) 2014 - 2020 | Alexander01998 | All rights reserved.
 *
 * This source code is subject to the terms of the GNU General Public License,
 * version 3. If a copy of the GPL was not distributed with this file, You can
 * obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */
package net.wurstclient.events;

import java.util.ArrayList;
import net.minecraft.util.shape.VoxelShape;
import net.wurstclient.event.Event;
import net.wurstclient.event.Listener;

public interface CactusCollisionShapeListener extends Listener {
	public static class CactusCollisionShapeEvent extends Event<CactusCollisionShapeListener> {
		private VoxelShape collisionShape;

		@Override
		public void fire(ArrayList<CactusCollisionShapeListener> listeners) {
			for (CactusCollisionShapeListener listener : listeners) {
				listener.onCactusCollisionShape(this);
			}
		}

		public VoxelShape getCollisionShape() {
			return collisionShape;
		}

		@Override
		public Class<CactusCollisionShapeListener> getListenerType() {
			return CactusCollisionShapeListener.class;
		}

		public void setCollisionShape(VoxelShape collisionShape) {
			this.collisionShape = collisionShape;
		}
	}

	void onCactusCollisionShape(CactusCollisionShapeEvent event);
}
