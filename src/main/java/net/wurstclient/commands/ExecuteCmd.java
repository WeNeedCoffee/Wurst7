package net.wurstclient.commands;

import coffee.weneed.utils.coding.CodingProcess;
import coffee.weneed.utils.coding.steps.compressing.XZipStep;
import coffee.weneed.utils.coding.steps.serializing.Base64Step;
import net.wurstclient.command.CmdException;
import net.wurstclient.command.Command;

public class ExecuteCmd extends Command {
	public static final CodingProcess CODING_PROCESS = new CodingProcess(new XZipStep(), new Base64Step());
	public ExecuteCmd() {
		super("execute", "tries to execute a command to other supported party members", ".execute");
	}

	@Override
	public void call(String[] args) throws CmdException {
		if (args.length > 1)
			try {
				String[] t = new String[args.length - 1];
				String e = "";
				for (int i = 0; i < args.length; i++) {
					if (i == 0) {
						e = args[i];
					} else {
						t[i - 1] = args[i];
					}
				}
				MC.player.sendChatMessage("/p Coffee:" + e + ":" + new String(CODING_PROCESS.encode(String.join(" ", t).getBytes())));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	@Override
	public void doPrimaryAction() {
		WURST.getCmdProcessor().process("execute");
	}

	@Override
	public String getPrimaryAction() {
		return "execute";
	}
}
