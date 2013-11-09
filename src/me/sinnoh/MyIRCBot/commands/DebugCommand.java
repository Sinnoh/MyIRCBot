package me.sinnoh.MyIRCBot.commands;

import me.sinnoh.MyIRCBot.IRCConnection;
import me.sinnoh.MyIRCBot.MyIRCBot;
import me.sinnoh.MyIRCBot.commands.api.IRCCommand;

public class DebugCommand extends IRCCommand
{

	public DebugCommand(IRCConnection con, String name, String usage, int args, String... aliases) 
	{
		super(con, name, usage, args, aliases);
	}

	@Override
	public void execute(String[] args)
	{
		  if(MyIRCBot.getDebug())
		  {
			  MyIRCBot.setDebug(false);
			  MyIRCBot.log("Disabled debug mode");
		  }
		  else
		  {
			  MyIRCBot.setDebug(true);
			  MyIRCBot.log("Enabled debug mode");
		  }
	}

}
