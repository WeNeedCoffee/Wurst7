/*
 * Copyright (C) 2014 - 2020 | Wurst-Imperium | All rights reserved.
 *
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */
package net.wurstclient.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.item.MiningToolItem;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterial;
import net.wurstclient.WurstClient;
import net.wurstclient.mixinterface.IMiningToolItem;

@Mixin(MiningToolItem.class)
public class MiningToolItemMixin extends ToolItem implements IMiningToolItem {
	@Shadow
	@Final
	protected float attackDamage;

	@Shadow
	@Final
	protected float attackSpeed;

	private MiningToolItemMixin(WurstClient wurst, ToolMaterial material, Settings settings) {
		super(material, settings);
	}

	@Override
	public float getAttackDamage() {
		return attackDamage;
	}

	@Override
	public float getAttackSpeed() {
		return attackSpeed;
	}
}