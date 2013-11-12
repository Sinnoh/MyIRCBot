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
package com.gottesgleich.MyIRCBot.commands.api;

import java.util.ArrayList;
import java.util.List;

import com.gottesgleich.MyIRCBot.IRCConnection;

public abstract class IRCCommand
{

	private String name;
	private String usage;
	private int args;
	private List<String> aliases;
	private IRCConnection con;

	public IRCCommand(IRCConnection con, String name, String usage, int args, String... aliases)
	{
		this.con = con;
		this.name = name;
		this.usage = usage;
		this.args = args;
		this.aliases = new ArrayList<String>();
		for(String alias : aliases)
		{
			this.aliases.add(alias.toLowerCase());
		}
	}

	public String getName()
	{
		return this.name;
	}

	public String getUsage()
	{
		return this.usage;
	}

	public int getArgs()
	{
		return this.args;
	}

	public List<String> getAliases()
	{
		return this.aliases;
	}

	public IRCConnection getConnection()
	{
		return this.con;
	}

	public abstract void execute(String args[]);

}
