package com.gottesgleich.MyIRCBot.commands;

import com.gottesgleich.MyIRCBot.IRCConnection;
import com.gottesgleich.MyIRCBot.MyIRCBot;
import com.gottesgleich.MyIRCBot.commands.api.IRCCommand;

public class StopCommand extends IRCCommand
{

	public StopCommand(IRCConnection con, String name, String usage, int args, String... aliases) 
	{
		super(con, name, usage, args, aliases);
	}

	@Override
	public void execute(String[] args) 
	{
		try
		{
			MyIRCBot.getPluginLoader().disablePlugins();
			getConnection().exit();
			MyIRCBot.exit();	
		}catch(Exception e)
		{
			MyIRCBot.logError(e);
		}
	}

}
