package net.wurstclient.hacks;

import net.wurstclient.Category;
import net.wurstclient.SearchTags;
import net.wurstclient.hack.Hack;
import net.wurstclient.settings.CheckboxSetting;
import net.wurstclient.settings.SliderSetting;

@SearchTags({ "auto store"})
public final class AutoStoreHack extends Hack {
	private final SliderSetting delay = new SliderSetting("Delay", "Delay between moving stacks of items.\n" + "Should be at least 70ms for NoCheat+ servers.", 100, 0, 500, 10, v -> (int) v + "ms");

	public AutoStoreHack() {
		super("AutoSteal", "Automatically stores everything\n" + "into all chests that you open.");
		setCategory(Category.ITEMS);
		addSetting(delay);
	}

	public long getDelay() {
		return delay.getValueI();
	}
	@Override
	public void onEnable() {
		WURST.getHax().autoStealHack.setEnabled(false);
	}
}