/**
Copyright 2013 Philipp Rissle

This file is part of MyIRCBot.

MyIRCBot is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

MyIRCBot is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with MyIRCBot.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.gottesgleich.MyIRCBot.event;

import com.gottesgleich.MyIRCBot.IRCChannel;
import com.gottesgleich.MyIRCBot.IRCClient;
import com.gottesgleich.MyIRCBot.IRCConnection;
import com.gottesgleich.MyIRCBot.event.api.IRCEvent;

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
