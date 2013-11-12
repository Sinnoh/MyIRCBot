package com.gottesgleich.MyIRCBot.commands;

import com.gottesgleich.MyIRCBot.IRCConnection;
import com.gottesgleich.MyIRCBot.MyIRCBot;
import com.gottesgleich.MyIRCBot.commands.api.IRCCommand;

public class DebugCommand extends IRCCommand
{

	public DebugCommand(IRCConnection con, String name, String usage, int args, String... aliases)
	{
		super(con, name, usage, args, aliases);
	}

	@Override
	public void execute(String[] args)
	{
		if (MyIRCBot.getDebug())
		{
			MyIRCBot.setDebug(false);
			MyIRCBot.log("Disabled debug mode");
		} else
		{
			MyIRCBot.setDebug(true);
			MyIRCBot.log("Enabled debug mode");
		}
	}

}
