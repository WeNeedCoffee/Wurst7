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

public interface KeyPressListener extends Listener {
	public static class KeyPressEvent extends Event<KeyPressListener> {
		private final int keyCode;
		private final int scanCode;
		private final int action;
		private final int modifiers;

		public KeyPressEvent(int keyCode, int scanCode, int action, int modifiers) {
			this.keyCode = keyCode;
			this.scanCode = scanCode;
			this.action = action;
			this.modifiers = modifiers;
		}

		@Override
		public void fire(ArrayList<KeyPressListener> listeners) {
			for (KeyPressListener listener : listeners) {
				listener.onKeyPress(this);
			}
		}

		public int getAction() {
			return action;
		}

		public int getKeyCode() {
			return keyCode;
		}

		@Override
		public Class<KeyPressListener> getListenerType() {
			return KeyPressListener.class;
		}

		public int getModifiers() {
			return modifiers;
		}

		public int getScanCode() {
			return scanCode;
		}
	}

	void onKeyPress(KeyPressEvent event);
}
