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
		return  this.args;
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
