package me.sinnoh.MyIRCBot.event;

import me.sinnoh.MyIRCBot.IRCChannel;
import me.sinnoh.MyIRCBot.IRCConnection;
import me.sinnoh.MyIRCBot.event.api.IRCEvent;

public class BotJoinedChannelEvent extends IRCEvent 
{

	private IRCChannel channel;
	
	public BotJoinedChannelEvent(IRCConnection con, IRCChannel ch) 
	{
		super(con);
		this.channel = ch;
	}
	
	public IRCChannel getChannel()
	{
		return this.channel;
	}

}
