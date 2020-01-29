/*
 * Copyright (C) 2014 - 2020 | Alexander01998 | All rights reserved.
 *
 * This source code is subject to the terms of the GNU General Public License,
 * version 3. If a copy of the GPL was not distributed with this file, You can
 * obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */
package net.wurstclient.command;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.TreeMap;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import net.wurstclient.commands.AddAltCmd;
import net.wurstclient.commands.AnnoyCmd;
import net.wurstclient.commands.AuthorCmd;
import net.wurstclient.commands.BindsCmd;
import net.wurstclient.commands.BlinkCmd;
import net.wurstclient.commands.ClearCmd;
import net.wurstclient.commands.CopyItemCmd;
import net.wurstclient.commands.DamageCmd;
import net.wurstclient.commands.DropCmd;
import net.wurstclient.commands.EnchantCmd;
import net.wurstclient.commands.ExcavateCmd;
import net.wurstclient.commands.FeaturesCmd;
import net.wurstclient.commands.FollowCmd;
import net.wurstclient.commands.FriendsCmd;
import net.wurstclient.commands.GetPosCmd;
import net.wurstclient.commands.GiveCmd;
import net.wurstclient.commands.GmCmd;
import net.wurstclient.commands.GoToCmd;
import net.wurstclient.commands.HelpCmd;
import net.wurstclient.commands.LeaveCmd;
import net.wurstclient.commands.ModifyCmd;
import net.wurstclient.commands.PathCmd;
import net.wurstclient.commands.RenameCmd;
import net.wurstclient.commands.SayCmd;
import net.wurstclient.commands.SetCheckboxCmd;
import net.wurstclient.commands.SetModeCmd;
import net.wurstclient.commands.SetSliderCmd;
import net.wurstclient.commands.TCmd;
import net.wurstclient.commands.TacoCmd;
import net.wurstclient.commands.TpCmd;
import net.wurstclient.commands.VClipCmd;
import net.wurstclient.commands.ViewNbtCmd;

public final class CmdList {
	public final AddAltCmd addAltCmd = new AddAltCmd();
	public final AnnoyCmd annoyCmd = new AnnoyCmd();
	public final AuthorCmd authorCmd = new AuthorCmd();
	public final BindsCmd bindsCmd = new BindsCmd();
	public final BlinkCmd blinkCmd = new BlinkCmd();
	public final ClearCmd clearCmd = new ClearCmd();
	public final CopyItemCmd copyitemCmd = new CopyItemCmd();
	public final DamageCmd damageCmd = new DamageCmd();
	public final DropCmd dropCmd = new DropCmd();
	public final EnchantCmd enchantCmd = new EnchantCmd();
	public final ExcavateCmd excavateCmd = new ExcavateCmd();
	public final FeaturesCmd featuresCmd = new FeaturesCmd();
	public final FollowCmd followCmd = new FollowCmd();
	public final FriendsCmd friendsCmd = new FriendsCmd();
	public final GetPosCmd getPosCmd = new GetPosCmd();
	// public final GhostHandCmd ghostHandCmd = new GhostHandCmd();
	public final GiveCmd giveCmd = new GiveCmd();
	public final GmCmd gmCmd = new GmCmd();
	public final GoToCmd goToCmd = new GoToCmd();
	public final HelpCmd helpCmd = new HelpCmd();
	// public final InvseeCmd invseeCmd = new InvseeCmd();
	// public final IpCmd ipCmd = new IpCmd();
	// public final JumpCmd jumpCmd = new JumpCmd();
	public final LeaveCmd leaveCmd = new LeaveCmd();
	public final ModifyCmd modifyCmd = new ModifyCmd();
	public final PathCmd pathCmd = new PathCmd();
	// public final PotionCmd potionCmd = new PotionCmd();
	// public final ProtectCmd protectCmd = new ProtectCmd();
	public final RenameCmd renameCmd = new RenameCmd();
	// public final RepairCmd repairCmd = new RepairCmd();
	// public final RvCmd rvCmd = new RvCmd();
	// public final SvCmd svCmd = new SvCmd();
	public final SayCmd sayCmd = new SayCmd();
	public final SetCheckboxCmd setCheckboxCmd = new SetCheckboxCmd();
	public final SetModeCmd setModeCmd = new SetModeCmd();
	public final SetSliderCmd setSliderCmd = new SetSliderCmd();
	public final TacoCmd tacoCmd = new TacoCmd();
	public final TCmd tCmd = new TCmd();
	// public final ThrowCmd throwCmd = new ThrowCmd();
	public final TpCmd tpCmd = new TpCmd();
	public final VClipCmd vClipCmd = new VClipCmd();
	public final ViewNbtCmd viewNbtCmd = new ViewNbtCmd();

	private final TreeMap<String, Command> cmds = new TreeMap<>((o1, o2) -> o1.compareToIgnoreCase(o2));

	public CmdList() {
		try {
			for (Field field : CmdList.class.getDeclaredFields()) {
				if (!field.getName().endsWith("Cmd")) {
					continue;
				}

				Command cmd = (Command) field.get(this);
				cmds.put(cmd.getName(), cmd);
			}

		} catch (Exception e) {
			String message = "Initializing Wurst commands";
			CrashReport report = CrashReport.create(e, message);
			throw new CrashException(report);
		}
	}

	public int countCmds() {
		return cmds.size();
	}

	public Collection<Command> getAllCmds() {
		return cmds.values();
	}

	public Command getCmdByName(String name) {
		return cmds.get("." + name);
	}
}
