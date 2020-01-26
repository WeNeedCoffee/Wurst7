/*
 * Copyright (C) 2014 - 2020 | Alexander01998 | All rights reserved.
 *
 * This source code is subject to the terms of the GNU General Public License,
 * version 3. If a copy of the GPL was not distributed with this file, You can
 * obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */
package net.wurstclient.events;

import java.util.ArrayList;
import net.minecraft.block.BlockState;
import net.wurstclient.event.Event;
import net.wurstclient.event.Listener;

public interface GetAmbientOcclusionLightLevelListener extends Listener {
	public static class GetAmbientOcclusionLightLevelEvent extends Event<GetAmbientOcclusionLightLevelListener> {
		private final BlockState state;
		private float lightLevel;
		private final float defaultLightLevel;

		public GetAmbientOcclusionLightLevelEvent(BlockState state, float lightLevel) {
			this.state = state;
			this.lightLevel = lightLevel;
			defaultLightLevel = lightLevel;
		}

		@Override
		public void fire(ArrayList<GetAmbientOcclusionLightLevelListener> listeners) {
			for (GetAmbientOcclusionLightLevelListener listener : listeners) {
				listener.onGetAmbientOcclusionLightLevel(this);
			}
		}

		public float getDefaultLightLevel() {
			return defaultLightLevel;
		}

		public float getLightLevel() {
			return lightLevel;
		}

		@Override
		public Class<GetAmbientOcclusionLightLevelListener> getListenerType() {
			return GetAmbientOcclusionLightLevelListener.class;
		}

		public BlockState getState() {
			return state;
		}

		public void setLightLevel(float lightLevel) {
			this.lightLevel = lightLevel;
		}
	}

	void onGetAmbientOcclusionLightLevel(GetAmbientOcclusionLightLevelEvent event);
}
