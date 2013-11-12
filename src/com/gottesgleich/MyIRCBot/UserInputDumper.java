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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class UserInputDumper extends Thread
{
	private Boolean running = true;
	private BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	private IRCConnection con;

	public UserInputDumper(IRCConnection con)
	{
		this.con = con;
	}

	@Override
	public void run()
	{
		while (running)
		{
			try
			{
				String line;
				while ((line = in.readLine()) != null && running)
				{
					if (line.startsWith("/"))
					{
						line = line.replaceFirst("/", "");
						String[] ar = line.split(" ");
						MyIRCBot.getCommandManager().executeCommand(ar[0], Arrays.copyOfRange(ar, 1, ar.length));
					} else
					{
						if (line.startsWith("#"))
						{
							if (con.getActiveChannel() == null)
							{
								continue;
							}
							con.getActiveChannel().sendRAWMessageToChannel(line.substring(1));
						} else
						{
							if (con.getActiveChannel() == null)
							{
								continue;
							}
							con.getActiveChannel().sendMessageToChannel(line);
						}
					}
				}
			} catch (IOException e)
			{
				MyIRCBot.logError(e);
			}
		}
	}

	public void setRunning(Boolean b)
	{
		this.running = false;
	}

}
