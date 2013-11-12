package com.gottesgleich.MyIRCBot.commands.api;

import java.util.ArrayList;
import java.util.List;

import com.gottesgleich.MyIRCBot.MyIRCBot;

public class IRCCommandManager {

	private List<IRCCommand> commands = new ArrayList<>();

	public void addCommand(IRCCommand cmd) {
		if (!this.commands.contains(cmd)) {
			this.commands.add(cmd);
		}
	}

	public void executeCommand(String cmd, String[] args) {
		for (IRCCommand command : this.commands) {
			if (command.getName().equalsIgnoreCase(cmd)
					|| command.getAliases().contains(cmd.toLowerCase())) {
				if (args.length < command.getArgs()) {
					MyIRCBot.log(command.getUsage());
					return;
				}
				command.execute(args);
				return;
			}
		}
		MyIRCBot.log("Unknown Command");
	}

}
