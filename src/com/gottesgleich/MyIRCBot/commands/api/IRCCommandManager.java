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

import com.gottesgleich.MyIRCBot.MyIRCBot;

public class IRCCommandManager
{

	private List<IRCCommand> commands = new ArrayList<>();

	public void addCommand(IRCCommand cmd)
	{
		if (!this.commands.contains(cmd))
		{
			this.commands.add(cmd);
		}
	}

	public void executeCommand(String cmd, String[] args)
	{
		for (IRCCommand command : this.commands)
		{
			if (command.getName().equalsIgnoreCase(cmd) || command.getAliases().contains(cmd.toLowerCase()))
			{
				if (args.length < command.getArgs())
				{
					MyIRCBot.log(command.getUsage());
					return;
				}
				command.execute(args);
				return;
			}
		}
		MyIRCBot.log("Unknown Command");
	}

}
