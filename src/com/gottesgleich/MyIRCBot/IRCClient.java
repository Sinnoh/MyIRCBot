package com.gottesgleich.MyIRCBot;

import com.gottesgleich.MyIRCBot.IrcProtocol.Action;

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
		this.con.getOutput().addMessage(new IrcProtocol(Action.PMSG, this.name + "::" + msg, ""));
	}
	  
	public void notice(String msg)
	{
		this.con.getOutput().addMessage(new IrcProtocol(Action.NOTICE, this.name + "::" + msg, ""));
	}
	
	public IRCChannel getChannel()
	{
		return this.channel;
	}

}
