package com.gottesgleich.MyIRCBot.event;

import com.gottesgleich.MyIRCBot.IRCChannel;
import com.gottesgleich.MyIRCBot.IRCClient;
import com.gottesgleich.MyIRCBot.IRCConnection;
import com.gottesgleich.MyIRCBot.event.api.IRCEvent;

public class ClientMessageToChannelEvent extends IRCEvent
{

	private IRCChannel channel;
	private IRCClient client;
	private String msg;
	private String hostmask;
	
	public ClientMessageToChannelEvent(IRCConnection con, IRCClient client, String msg, IRCChannel channel, String hostmask)
	{
		super(con);
		this.channel = channel;
		this.client = client;
		this.msg = msg;
		this.hostmask = hostmask;
	}
	
	public String getMessage()
	{
		return this.msg;
	}
	
	public IRCClient getClient()
	{
		return this.client;
	}
	
	public IRCChannel getChannel()
	{
		return this.channel;
	}
	
	public String getHostmask()
	{
		return this.hostmask;
	}
	
}