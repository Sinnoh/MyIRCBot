package me.sinnoh.MyIRCBot.plugin;


import me.sinnoh.MyIRCBot.configuration.FileConfiguration;

public abstract class IRCPlugin 
{
	private FileConfiguration config;
	private String name;
	
	public abstract void onEnable();

	public abstract void onDisable();
	
	public FileConfiguration getConfig()
	{
		return this.config;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	protected void initialize(FileConfiguration config, String name)
	{
		this.name = name;
		this.config = config;
	}
	

}
