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
package com.gottesgleich.MyIRCBot;

import java.util.ArrayList;
import java.util.List;

import com.gottesgleich.MyIRCBot.IRCProtocol.Action;

public class IRCChannel
{

	private String name;
	private IRCConnection con;
	private List<IRCClient> clients = new ArrayList<IRCClient>();

	public IRCChannel(String name, IRCConnection con)
	{
		if(!name.startsWith("#"))
		{
			name = "#" + name;
		}
		this.name = name;
		this.con = con;
	}

	public String getName()
	{
		return this.name;
	}

	public List<IRCClient> getClients()
	{
		return this.clients;
	}

	public void setClients(List<IRCClient> clients)
	{
		this.clients = clients;
	}

	public IRCConnection getConnection()
	{
		return this.con;
	}

	public void leave()
	{
		this.con.getOutput().addMessage(new IRCProtocol(Action.LEAVE, "", this.name));
		this.con.getChannels().remove(this);
		if(this.con.getActiveChannel().equals(this))
		{
			this.con.setActiveChannel(this.con.getChannels().size() != 0 ? this.con.getChannels().get(0) : null);
		}
	}

	public void sendMessageToChannel(String msg)
	{
		this.con.getOutput().addMessage(new IRCProtocol(Action.MSG, msg, this.name));
	}

	public void sendRAWMessageToChannel(String msg)
	{
		this.getConnection().getOutput().addMessage(new IRCProtocol(Action.RAW, msg, this.name));
	}

	public void updateClientList()
	{
		this.getConnection().getOutput().addMessage(new IRCProtocol(Action.NAMES, "", this.name));
	}

	public IRCClient getClient(String name)
	{
		for(IRCClient c : this.clients)
		{
			if(c.getName().equalsIgnoreCase(name))
			{
				return c;
			}
		}
		return new IRCClient(name, false, this.con, this);
	}

	public void kickClient(String client, String reason)
	{
		this.getConnection().getOutput().addMessage(new IRCProtocol(Action.KICK, client + "::" + reason, this.name));
	}

	public void setMode(String user, String mode)
	{
		this.getConnection().getOutput().addMessage(new IRCProtocol(Action.MODE, user + "::" + mode, this.name));
	}

	public void setTopic(String topic)
	{
		this.getConnection().getOutput().addMessage(new IRCProtocol(Action.TOPIC, topic, this.name));
	}

}
