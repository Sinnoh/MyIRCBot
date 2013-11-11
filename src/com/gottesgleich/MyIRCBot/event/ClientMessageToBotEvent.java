package com.gottesgleich.MyIRCBot.event;

import com.gottesgleich.MyIRCBot.IRCConnection;
import com.gottesgleich.MyIRCBot.event.api.IRCEvent;

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
