package com.gottesgleich.MyIRCBot.plugin;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.gottesgleich.MyIRCBot.MyIRCBot;
import com.gottesgleich.MyIRCBot.configuration.FileConfiguration;
import com.gottesgleich.MyIRCBot.event.api.IRCEventListener;

public class PluginLoader 
{
	
	private List<IRCPlugin> plugins = new ArrayList<IRCPlugin>();
	
	public void loadPlugins()
	{
		File directory = new File("plugins");
		if(!directory.exists())
		{
			directory.mkdir();
		}
		if(!directory.isDirectory())
		{
			return;
		}
		for(File f : directory.listFiles())
		{
			if(!f.getName().endsWith(".jar"))
			{
				continue;
			}
			try
			{
				ClassLoader loader = new URLClassLoader(new URL[] {f.toURI().toURL()}, MyIRCBot.class.getClassLoader());
				Properties p = new Properties();
				p.load(loader.getResourceAsStream("plugin.properties"));			
				String main = p.getProperty("main");
				String name = p.getProperty("name");
				Class<?> clazz = Class.forName(main, false, loader);
				Object o = clazz.newInstance();
				if(o instanceof IRCPlugin)
				{
					IRCPlugin plugin = (IRCPlugin) o;
					plugin.initialize(new FileConfiguration(new File("plugins" + File.separator + name + File.separator + name + ".properties")), name);
					this.plugins.add(plugin);
					
				}
			}catch(Exception e)
			{
				MyIRCBot.logError(e);
			}	
		}
	}
	
	public void enablePlugins()
	{
		for(IRCPlugin p : this.plugins)
		{
			MyIRCBot.log("Loading " + p.getName());
			p.onEnable();
		}
	}
	
	public void disablePlugins()
	{
		for(IRCPlugin p : this.plugins)
		{
			MyIRCBot.log("Unloading " + p.getName());
			p.onDisable();
		}
		this.plugins.clear();
	}

}
