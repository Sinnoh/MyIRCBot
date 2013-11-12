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

import com.gottesgleich.MyIRCBot.IRCProtocol.Action;

public class IRCClient
{

	private String name;
	private Boolean isop;
	private IRCConnection con;
	private IRCChannel channel;

	public IRCClient(String name, Boolean isop, IRCConnection con, IRCChannel ch)
	{
		this.name = name;
		this.isop = isop;
		this.con = con;
		this.channel = ch;
	}

	public String getName()
	{
		return this.name;
	}

	public Boolean isOp()
	{
		return this.isop;
	}

	public void setOp(Boolean b)
	{
		this.isop = b;
	}

	public void setNick(String s)
	{
		this.name = s;
	}

	public IRCConnection getConnection()
	{
		return this.con;
	}

	public void sendMessage(String msg)
	{
		this.con.getOutput().addMessage(new IRCProtocol(Action.PMSG, this.name + "::" + msg, ""));
	}

	public void notice(String msg)
	{
		this.con.getOutput().addMessage(new IRCProtocol(Action.NOTICE, this.name + "::" + msg, ""));
	}

	public IRCChannel getChannel()
	{
		return this.channel;
	}

}
