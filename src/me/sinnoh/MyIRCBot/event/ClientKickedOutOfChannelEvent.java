package me.sinnoh.MyIRCBot.event;

import me.sinnoh.MyIRCBot.IRCChannel;
import me.sinnoh.MyIRCBot.IRCClient;
import me.sinnoh.MyIRCBot.IRCConnection;
import me.sinnoh.MyIRCBot.event.api.IRCEvent;

public class ClientKickedOutOfChannelEvent extends IRCEvent
{

	private IRCClient client;
	private String reason;
	private IRCClient kickedby;
	private IRCChannel channel;
	
	public ClientKickedOutOfChannelEvent(IRCConnection con, IRCClient client, IRCClient kickedby, IRCChannel channel, String reason) 
	{
		super(con);
		this.client = client;
		this.kickedby = kickedby;
		this.reason = reason;
		this.channel = channel;
	}
	
	public IRCClient getClient()
	{
		return this.client;
	}
	
	public IRCClient getKicker()
	{
		return this.kickedby;
	}
	
	public String getReason()
	{
		return this.reason;
	}
	
	public IRCChannel getChannel()
	{
		return this.channel;
	}

}
