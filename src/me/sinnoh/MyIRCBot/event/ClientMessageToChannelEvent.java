package me.sinnoh.MyIRCBot.event;

import me.sinnoh.MyIRCBot.IRCChannel;
import me.sinnoh.MyIRCBot.IRCClient;
import me.sinnoh.MyIRCBot.IRCConnection;
import me.sinnoh.MyIRCBot.event.api.IRCEvent;

public class ClientMessageToChannelEvent extends IRCEvent
{

	private IRCChannel channel;
	private IRCClient client;
	private String msg;
	
	public ClientMessageToChannelEvent(IRCConnection con, IRCClient client, String msg, IRCChannel channel)
	{
		super(con);
		this.channel = channel;
		this.client = client;
		this.msg = msg;
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
	
}
