package com.gottesgleich.MyIRCBot.event;

import com.gottesgleich.MyIRCBot.IRCChannel;
import com.gottesgleich.MyIRCBot.IRCConnection;
import com.gottesgleich.MyIRCBot.event.api.IRCEvent;

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
