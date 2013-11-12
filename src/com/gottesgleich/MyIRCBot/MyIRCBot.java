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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.gottesgleich.MyIRCBot.commands.DebugCommand;
import com.gottesgleich.MyIRCBot.commands.JoinCommand;
import com.gottesgleich.MyIRCBot.commands.ReloadCommand;
import com.gottesgleich.MyIRCBot.commands.StopCommand;
import com.gottesgleich.MyIRCBot.commands.api.IRCCommandManager;
import com.gottesgleich.MyIRCBot.configuration.FileConfiguration;
import com.gottesgleich.MyIRCBot.event.api.IRCEventManager;
import com.gottesgleich.MyIRCBot.plugin.PluginLoader;

public class MyIRCBot
{
	private static String version = "1.1";
	private static IRCEventManager eventmng;
	private static PluginLoader pluginloader;
	private static boolean debug;
	private static FileConfiguration config;
	private static File logfile;
	private static FileWriter logwriter;
	private static IRCCommandManager cmdmanager;
	private static UserInputDumper ui;

	public static void main(String[] args) throws IOException
	{
		try
		{
			setUpLogFile();
			loadConfig();
			debug = config.getBoolean("debug");
			pluginloader = new PluginLoader();
			eventmng = new IRCEventManager();
			pluginloader.loadPlugins();
			pluginloader.enablePlugins();
			MyIRCBot.log("Starting MyIRCBot v." + version);
			IRCConnection con = new IRCConnection(config.getString("host"), config.getString("nick"), config.getInt("port"));
			setupCommands(con);
			con.joinChannel("Sinnoh");
			MyIRCBot.ui = new UserInputDumper(con);
			ui.start();
		}
		catch(Exception e)
		{
			logError(e);
		}
	}

	public static void setupCommands(IRCConnection con)
	{
		cmdmanager = new IRCCommandManager();
		cmdmanager.addCommand(new DebugCommand(con, "debug", "/debug", 0));
		cmdmanager.addCommand(new JoinCommand(con, "join", "/join <Channel>", 1, "j"));
		cmdmanager.addCommand(new ReloadCommand(con, "reload", "/reload", 0));
		cmdmanager.addCommand(new StopCommand(con, "stop", "/stop", 0, "exit", "quit"));
	}

	private static void loadConfig()
	{
		config = new FileConfiguration(new File("config.properties"));
		config.addDefaults("host", "portlane.se.quakenet.org");
		config.addDefaults("nick", "GBot");
		config.addDefaults("port", 6669);
		config.addDefaults("debug", false);
		config.save("MyIRCBot v." + version + " by Sinnoh");
	}

	private static void setUpLogFile()
	{
		try
		{
			logfile = new File("myircbot.log");
			if(!logfile.exists())
			{
				logfile.createNewFile();
			}
		}
		catch(Exception e)
		{
			logError(e);
		}

	}

	public static IRCEventManager getEventManager()
	{
		return MyIRCBot.eventmng;
	}

	public static IRCCommandManager getCommandManager()
	{
		return MyIRCBot.cmdmanager;
	}

	public static PluginLoader getPluginLoader()
	{
		return MyIRCBot.pluginloader;
	}

	public static FileConfiguration getConfig()
	{
		return config;
	}

	public static void exit()
	{
		try
		{
			logwriter.close();
		}
		catch(Exception e)
		{
			logError(e);
		}
		ui.setRunning(false);
		System.exit(0);
	}

	public static void log(String msg, Boolean debug)
	{
		if(debug && !MyIRCBot.debug)
		{
			return;
		}
		System.out.println("> " + msg);
		try
		{
			logwriter = new FileWriter(logfile, true);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
			logwriter.write(sdf.format(new Date(System.currentTimeMillis())) + "  " + msg + "\n");
			logwriter.flush();
			logwriter.close();
		}
		catch(Exception e)
		{
			logError(e);
		}
	}

	public static void log(String msg)
	{
		log(msg, false);
	}

	public static void logError(Exception e)
	{
		log(e.getMessage());
		e.printStackTrace();
	}

	public static boolean getDebug()
	{
		return debug;
	}

	public static void setDebug(boolean b)
	{
		debug = b;
	}
}
