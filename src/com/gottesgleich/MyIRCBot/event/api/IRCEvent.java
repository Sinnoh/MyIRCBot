package com.gottesgleich.MyIRCBot.event.api;

import com.gottesgleich.MyIRCBot.IRCConnection;

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
