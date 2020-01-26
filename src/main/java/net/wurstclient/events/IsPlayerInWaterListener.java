/*
 * Copyright (C) 2014 - 2020 | Alexander01998 | All rights reserved.
 *
 * This source code is subject to the terms of the GNU General Public License,
 * version 3. If a copy of the GPL was not distributed with this file, You can
 * obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */
package net.wurstclient.events;

import java.util.ArrayList;
import net.wurstclient.event.Event;
import net.wurstclient.event.Listener;

public interface IsPlayerInWaterListener extends Listener {
	public static class IsPlayerInWaterEvent extends Event<IsPlayerInWaterListener> {
		private boolean inWater;
		private final boolean normallyInWater;

		public IsPlayerInWaterEvent(boolean inWater) {
			this.inWater = inWater;
			normallyInWater = inWater;
		}

		@Override
		public void fire(ArrayList<IsPlayerInWaterListener> listeners) {
			for (IsPlayerInWaterListener listener : listeners) {
				listener.onIsPlayerInWater(this);
			}
		}

		@Override
		public Class<IsPlayerInWaterListener> getListenerType() {
			return IsPlayerInWaterListener.class;
		}

		public boolean isInWater() {
			return inWater;
		}

		public boolean isNormallyInWater() {
			return normallyInWater;
		}

		public void setInWater(boolean inWater) {
			this.inWater = inWater;
		}
	}

	void onIsPlayerInWater(IsPlayerInWaterEvent event);
}
