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

public class IRCProtocol
{

	public static enum Action
	{
		MSG, JOIN, LEAVE, NOTICE, NAMES, RAW, PONG, MODE, KICK, TOPIC, PMSG;
	}

	private Action action;
	private String value;
	private String channel;

	public IRCProtocol(Action action, String val, String channel)
	{
		this.value = val;
		this.action = action;
		if(!channel.startsWith("#"))
		{
			channel = "#" + channel;
		}
		this.channel = channel;
	}

	public String parse()
	{
		switch (this.action)
		{
		case JOIN:
			return "Join " + channel + "\n\r";
		case LEAVE:
			return "PART " + channel + "\n\r";
		case MSG:
			return "PRIVMSG " + channel + " :" + value + "\n\r";
		case PMSG:
			return "PRIVMSG " + value.split("::")[0] + " :" + value.split("::")[1] + "\n\r";
		case NOTICE:
			return "NOTICE " + value.split("::")[0] + " :" + value.split("::")[1] + "\n\r";
		case NAMES:
			return "NAMES " + channel + "\n\r";
		case PONG:
			return "PONG :" + value + "\n\r";
		case RAW:
			return value + "\n\r";
		case MODE:
			return "MODE " + channel + " " + value.split("::")[1] + " " + value.split("::")[0] + "\n\r";
		case KICK:
			return "KICK " + channel + " " + value.split("::")[0] + " " + value.split("::")[1] + "\n\r";
		case TOPIC:
			return "TOPIC " + channel + " " + value;
		}
		return null;
	}

	public Action getAction()
	{
		return action;
	}

	public String getChannel()
	{
		return channel;
	}
}
