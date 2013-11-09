package me.sinnoh.MyIRCBot.commands;

import me.sinnoh.MyIRCBot.IRCConnection;
import me.sinnoh.MyIRCBot.MyIRCBot;
import me.sinnoh.MyIRCBot.commands.api.IRCCommand;

public class ReloadCommand extends IRCCommand
{

	public ReloadCommand(IRCConnection con, String name, String usage, int args, String... aliases) 
	{
		super(con, name, usage, args, aliases);
	}

	@Override
	public void execute(String[] args)
	{
		synchronized (MyIRCBot.getEventManager()) 
		{
			MyIRCBot.getEventManager().unregisterListeners();
			MyIRCBot.getPluginLoader().disablePlugins();
			MyIRCBot.getPluginLoader().loadScripts();
			MyIRCBot.getPluginLoader().loadPlugins();
			MyIRCBot.getPluginLoader().enablePlugins();
			MyIRCBot.getConfig().reloadConfig();
		}
	}

}
