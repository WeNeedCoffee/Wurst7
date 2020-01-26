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

public interface ShouldDrawSideListener extends Listener {
	public static class ShouldDrawSideEvent extends Event<ShouldDrawSideListener> {
		private final BlockState state;
		private Boolean rendered;

		public ShouldDrawSideEvent(BlockState state) {
			this.state = state;
		}

		@Override
		public void fire(ArrayList<ShouldDrawSideListener> listeners) {
			for (ShouldDrawSideListener listener : listeners) {
				listener.onShouldDrawSide(this);
			}
		}

		@Override
		public Class<ShouldDrawSideListener> getListenerType() {
			return ShouldDrawSideListener.class;
		}

		public BlockState getState() {
			return state;
		}

		public Boolean isRendered() {
			return rendered;
		}

		public void setRendered(boolean rendered) {
			this.rendered = rendered;
		}
	}

	void onShouldDrawSide(ShouldDrawSideEvent event);
}
