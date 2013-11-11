package com.gottesgleich.MyIRCBot.event;

import com.gottesgleich.MyIRCBot.IRCClient;
import com.gottesgleich.MyIRCBot.IRCConnection;
import com.gottesgleich.MyIRCBot.event.api.IRCEvent;

public class ClientLeftChannelEvent extends IRCEvent
{
	
	private IRCClient client;
	private String channel;
	private String hostmask;
	
	
	public ClientLeftChannelEvent(IRCConnection con, IRCClient client, String channel, String hostmask)
	{
		super(con);
		this.client = client;
		this.channel = channel;
		this.hostmask = hostmask;
	}
	
	public IRCClient getClient()
	{
		return this.client;
	}
	
	public String getChannel()
	{
		return this.channel;
	}
	
	public String getHostmask()
	{
		return this.hostmask;
	}
}
