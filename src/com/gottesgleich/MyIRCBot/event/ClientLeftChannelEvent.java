package com.gottesgleich.MyIRCBot.event;

import com.gottesgleich.MyIRCBot.IRCClient;
import com.gottesgleich.MyIRCBot.IRCConnection;
import com.gottesgleich.MyIRCBot.event.api.IRCEvent;

public class ClientLeftChannelEvent extends IRCEvent
{
	
	private IRCClient client;
	private String channel;
	
	
	public ClientLeftChannelEvent(IRCConnection con, IRCClient client, String channel)
	{
		super(con);
		this.client = client;
		this.channel = channel;
	}
	
	public IRCClient getClient()
	{
		return this.client;
	}
	
	public String getChannel()
	{
		return this.channel;
	}
}
