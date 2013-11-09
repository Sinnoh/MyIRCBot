package me.sinnoh.MyIRCBot.event.api;

import me.sinnoh.MyIRCBot.IRCConnection;

public abstract class IRCEvent 
{
	
	private IRCConnection con;
	
	public IRCEvent(IRCConnection con) 
	{
		this.con = con;
	}

	
	public IRCConnection getConnection()
	{
		return this.con;
	}
}
