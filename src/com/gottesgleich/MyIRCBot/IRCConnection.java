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
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.gottesgleich.MyIRCBot.IRCProtocol.Action;

public class IRCConnection
{
	private String host;
	private int port;
	private String username;
	private IRCInputDumper in;
	private IRCOutputSender out;
	private BufferedWriter bw;
	private BufferedReader br;
	private Socket socket;
	private Boolean isConnected = false;
	private List<IRCChannel> channels = new ArrayList<IRCChannel>();
	private IRCChannel activechannel = null;

	public IRCConnection(String host, String user) throws UnknownHostException, IOException
	{
		this(host, user, 6667);
	}

	public IRCConnection(String host, String user, int port) throws UnknownHostException, IOException
	{
		this.host = host;
		this.port = port;
		this.username = user;
		connect();
		register();
	}

	private void connect()
	{
		try
		{
			MyIRCBot.log("Connecting to " + this.host + ":" + port);
			this.socket = new Socket(host, port);
			this.bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			this.br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			this.in = new IRCInputDumper(this, br);
			this.in.start();
			this.out = new IRCOutputSender(this, bw);
			this.out.start();
		} catch (Exception e)
		{
			MyIRCBot.logError(e);
			this.connect();
		}
	}

	private void register() throws IOException
	{
		MyIRCBot.log("Logging onto the server as " + this.username);
		this.out.addMessage(new IRCProtocol(Action.RAW, "NICK " + this.username + "\n\r", ""), true);
		this.out.addMessage(new IRCProtocol(Action.RAW, "USER " + this.username + " localhost " + this.host + ": " + this.username + "\n\r", ""), true);
	}

	public void joinChannel(String cname)
	{
		this.out.addMessage(new IRCProtocol(Action.JOIN, "", cname));
	}

	public boolean isInChannel()
	{
		return this.channels.size() == 0 ? false : true;
	}

	public void exit() throws IOException
	{
		for (int i = 0; i < this.channels.size(); i++)
		{
			this.channels.get(i).leave();
		}
		this.out.setRunning(false);
		this.in.setRunning(false);
		this.bw.close();
		this.br.close();
		this.socket.close();
	}

	public void setActiveChannel(IRCChannel ch)
	{
		this.activechannel = ch;
		if (ch != null)
		{
			MyIRCBot.log("Your active channel has been set to " + ch.getName());
		}
	}

	public Boolean isConnected()
	{
		return this.isConnected;
	}

	public IRCInputDumper getInput()
	{
		return this.in;
	}

	public IRCOutputSender getOutput()
	{
		return this.out;
	}

	public List<IRCChannel> getChannels()
	{
		return this.channels;
	}

	public String getUserName()
	{
		return this.username;
	}

	public void setConnected(Boolean b)
	{
		this.isConnected = b;
	}

	public void reconnect(Long time)
	{
		try
		{
			List<IRCProtocol> importants = this.out.getImportants();
			List<IRCProtocol> msgs = this.out.getMSGS();
			this.out.setRunning(false);
			this.in.setRunning(false);
			this.br.close();
			this.bw.close();
			List<IRCChannel> channels = this.channels;
			this.channels.clear();
			MyIRCBot.log("You will reconnect in " + time + " seconds!");
			Thread.currentThread();
			Thread.sleep(time * 1000);
			this.connect();
			this.register();
			for (IRCChannel c : channels)
			{
				this.joinChannel(c.getName());
			}
			for (IRCProtocol p : importants)
			{
				this.out.addMessage(p, true);
			}
			for (IRCProtocol p : msgs)
			{
				this.out.addMessage(p);
			}
		} catch (Exception e)
		{
			MyIRCBot.logError(e);
		}
	}

	public void setUserName(String name)
	{
		this.username = name;
	}

	public String getHost()
	{
		return this.host;
	}

	public IRCChannel getChannel(String name)
	{
		if (!name.startsWith("#"))
		{
			name = "#" + name;
		}
		for (IRCChannel ch : this.channels)
		{
			if (ch.getName().equalsIgnoreCase(name))
			{
				return ch;
			}
		}
		return null;
	}

	public IRCChannel getActiveChannel()
	{
		return this.activechannel;
	}

	public IRCClient getExternalClient(String name)
	{
		return new IRCClient(name, false, this, this.activechannel);
	}
}
