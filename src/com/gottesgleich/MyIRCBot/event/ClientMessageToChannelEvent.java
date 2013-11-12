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
