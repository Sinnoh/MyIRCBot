package com.gottesgleich.MyIRCBot.commands;

import com.gottesgleich.MyIRCBot.IRCConnection;
import com.gottesgleich.MyIRCBot.commands.api.IRCCommand;

public class JoinCommand extends IRCCommand {

	public JoinCommand(IRCConnection con, String name, String usage, int args,
			String... aliases) {
		super(con, name, usage, args, aliases);
	}

	@Override
	public void execute(String[] args) {
		getConnection().joinChannel(args[0]);
	}

}
