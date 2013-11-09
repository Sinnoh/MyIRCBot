package me.sinnoh.MyIRCBot.event;

import me.sinnoh.MyIRCBot.IRCChannel;
import me.sinnoh.MyIRCBot.IRCClient;
import me.sinnoh.MyIRCBot.IRCConnection;
import me.sinnoh.MyIRCBot.event.api.IRCEvent;

public class ClientJoinedChannelEvent extends IRCEvent
{
	
	private IRCClient client;
	private IRCChannel channel;
	
	
	public ClientJoinedChannelEvent(IRCConnection con, IRCClient client, IRCChannel channel)
	{
		super(con);
		this.client = client;
		this.channel = channel;
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
