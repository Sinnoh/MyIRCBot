package me.sinnoh.MyIRCBot.event;

import me.sinnoh.MyIRCBot.IRCConnection;
import me.sinnoh.MyIRCBot.event.api.IRCEvent;

public class ClientMessageToBotEvent extends IRCEvent
{
	
	private String clientname;
	private String msg;
	
	public ClientMessageToBotEvent(IRCConnection con, String clientname, String msg) 
	{
		super(con);
		this.clientname = clientname;
		this.msg = msg;
	}
	
	public String getMessage()
	{
		return this.msg;
	}
	
	public String getClientName()
	{
		return this.clientname;
	}
}
