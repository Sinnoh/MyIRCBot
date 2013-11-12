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
package com.gottesgleich.MyIRCBot.commands;

import com.gottesgleich.MyIRCBot.IRCConnection;
import com.gottesgleich.MyIRCBot.MyIRCBot;
import com.gottesgleich.MyIRCBot.commands.api.IRCCommand;

public class StopCommand extends IRCCommand
{

	public StopCommand(IRCConnection con, String name, String usage, int args, String... aliases)
	{
		super(con, name, usage, args, aliases);
	}

	@Override
	public void execute(String[] args)
	{
		try
		{
			MyIRCBot.getPluginLoader().disablePlugins();
			getConnection().exit();
			MyIRCBot.exit();
		}
		catch(Exception e)
		{
			MyIRCBot.logError(e);
		}
	}

}
