package me.sinnoh.MyIRCBot.event;

import me.sinnoh.MyIRCBot.IRCChannel;
import me.sinnoh.MyIRCBot.IRCConnection;
import me.sinnoh.MyIRCBot.event.api.IRCEvent;

public class BotLeftChannelEvent extends IRCEvent
{

	private IRCChannel channel;
	
	public BotLeftChannelEvent(IRCConnection con, IRCChannel ch) 
	{
		super(con);
		this.channel = ch;
	}
	
	public IRCChannel getChannel()
	{
		return this.channel;
	}

}
