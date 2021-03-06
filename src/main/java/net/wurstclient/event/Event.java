/*
 * Copyright (C) 2014 - 2018 | Wurst-Imperium | All rights reserved.
 *
 * This source code is subject to the terms of the GNU General Public License,
 * version 3. If a copy of the GPL was not distributed with this file, You can
 * obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */
package net.wurstclient.event;

import java.util.ArrayList;

public abstract class Event<T extends Listener> {
	public abstract void fire(ArrayList<T> listeners);

	public abstract Class<T> getListenerType();
}
