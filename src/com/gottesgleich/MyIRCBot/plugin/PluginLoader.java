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
package com.gottesgleich.MyIRCBot.plugin;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.gottesgleich.MyIRCBot.MyIRCBot;
import com.gottesgleich.MyIRCBot.configuration.FileConfiguration;

public class PluginLoader
{

	private List<IRCPlugin> plugins = new ArrayList<IRCPlugin>();

	public void loadPlugins()
	{
		File directory = new File("plugins");
		if (!directory.exists())
		{
			directory.mkdir();
		}
		if (!directory.isDirectory())
		{
			return;
		}
		for (File f : directory.listFiles())
		{
			if (!f.getName().endsWith(".jar"))
			{
				continue;
			}
			try
			{
				ClassLoader loader = new URLClassLoader(new URL[] { f.toURI().toURL() }, MyIRCBot.class.getClassLoader());
				Properties p = new Properties();
				p.load(loader.getResourceAsStream("plugin.properties"));
				String main = p.getProperty("main");
				String name = p.getProperty("name");
				Class<?> clazz = Class.forName(main, false, loader);
				Object o = clazz.newInstance();
				if (o instanceof IRCPlugin)
				{
					IRCPlugin plugin = (IRCPlugin) o;
					plugin.initialize(new FileConfiguration(new File("plugins" + File.separator + name + File.separator + name + ".properties")), name);
					this.plugins.add(plugin);

				}
			} catch (Exception e)
			{
				MyIRCBot.logError(e);
			}
		}
	}

	public void enablePlugins()
	{
		for (IRCPlugin p : this.plugins)
		{
			MyIRCBot.log("Loading " + p.getName());
			p.onEnable();
		}
	}

	public void disablePlugins()
	{
		for (IRCPlugin p : this.plugins)
		{
			MyIRCBot.log("Unloading " + p.getName());
			p.onDisable();
		}
		this.plugins.clear();
	}

}
