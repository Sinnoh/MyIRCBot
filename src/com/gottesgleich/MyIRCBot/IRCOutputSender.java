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

import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.List;

public class IRCOutputSender extends Thread
{
	private IRCConnection con;
	private BufferedWriter bw;
	public Boolean running = true;
	private List<IRCProtocol> msgs = new ArrayList<IRCProtocol>();
	private List<IRCProtocol> important = new ArrayList<IRCProtocol>();
	private Long nextping;

	public IRCOutputSender(IRCConnection con, BufferedWriter bw)
	{
		this.bw = bw;
		this.con = con;
		this.nextping = System.currentTimeMillis() + 10000;
	}

	public List<IRCProtocol> getImportants()
	{
		return this.important;
	}

	public List<IRCProtocol> getMSGS()
	{
		return this.msgs;
	}

	@Override
	public void run()
	{
		while(running)
		{
			try
			{
				if(this.nextping < System.currentTimeMillis())// Check if the
																// server is
																// still alive
				{
					this.nextping = System.currentTimeMillis() + 10000;
					MyIRCBot.log("Checking, if server is alive", true);
					MyIRCBot.log("OUT: PING " + con.getHost(), true);
					this.bw.write("PING " + con.getHost() + "\n\r");
					this.bw.flush();
				}
				if(this.important.size() != 0)
				{
					IRCProtocol p = this.important.get(0);
					this.bw.write(p.parse());
					this.bw.flush();
					MyIRCBot.log("OUT: " + p.parse().replace("\r", ""), true);
					this.important.remove(0);
				}
				else if(this.msgs.size() != 0)
				{
					if(con.isConnected())
					{
						if(this.msgs.size() == 0)
						{
							continue;
						}
						IRCProtocol p = this.msgs.get(0);
						this.bw.write(p.parse());
						this.bw.flush();
						MyIRCBot.log("OUT: " + p.parse().replace("\r", ""), true);
						this.msgs.remove(0);
					}
				}
			}
			catch(Exception e)
			{
				MyIRCBot.log("Connection lost");
				con.reconnect(Long.valueOf(10));
			}
		}
	}

	public void addMessage(IRCProtocol pro)
	{
		this.addMessage(pro, false);
	}

	public void addMessage(IRCProtocol pro, Boolean b)
	{
		if(b)
		{
			this.important.add(pro);
		}
		else
		{
			this.msgs.add(pro);
		}
	}

	public void setRunning(Boolean b)
	{
		this.running = false;
	}

	public BufferedWriter getBufferedWriter()
	{
		return this.bw;
	}

}
