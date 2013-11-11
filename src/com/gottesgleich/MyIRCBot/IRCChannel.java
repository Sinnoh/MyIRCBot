package com.gottesgleich.MyIRCBot;

import java.util.ArrayList;
import java.util.List;

import com.gottesgleich.MyIRCBot.IrcProtocol.Action;

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
		//this.updateClientList();
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
		this.con.getOutput().addMessage(new IrcProtocol(Action.LEAVE,"", this.name));
		this.con.getChannels().remove(this);
		if(this.con.getActiveChannel().equals(this))
		{
			this.con.setActiveChannel(this.con.getChannels().size() != 0 ? this.con.getChannels().get(0) : null);
		}
	}
	
	public void sendMessageToChannel(String msg)
	{
		this.con.getOutput().addMessage(new IrcProtocol(Action.MSG, msg, this.name));
	}
	  
	public void sendRAWMessageToChannel(String msg)
	{
		this.getConnection().getOutput().addMessage(new IrcProtocol(Action.RAW, msg, this.name));
	}
	
	public void updateClientList()
	{
		this.getConnection().getOutput().addMessage(new IrcProtocol(Action.NAMES, "", this.name)); 
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
		this.getConnection().getOutput().addMessage(new IrcProtocol(Action.KICK ,client + "::" + reason , this.name));
	}
	
	public void setMode(String user, String mode)
	{
		this.getConnection().getOutput().addMessage(new IrcProtocol(Action.MODE, user + "::" + mode, this.name)); 
	}
	
	public void setTopic(String topic)
	{
		this.getConnection().getOutput().addMessage(new IrcProtocol(Action.TOPIC, topic, this.name));
	}

}
