package me.sinnoh.MyIRCBot;


import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;







import me.sinnoh.MyIRCBot.commands.DebugCommand;
import me.sinnoh.MyIRCBot.commands.JoinCommand;
import me.sinnoh.MyIRCBot.commands.ReloadCommand;
import me.sinnoh.MyIRCBot.commands.StopCommand;
import me.sinnoh.MyIRCBot.commands.api.IRCCommandManager;
import me.sinnoh.MyIRCBot.configuration.FileConfiguration;
import me.sinnoh.MyIRCBot.event.api.IRCEventManager;
import me.sinnoh.MyIRCBot.plugin.PluginLoader;



public class MyIRCBot 
{
	private static IRCEventManager eventmng;
	private static PluginLoader pluginloader;
	private static boolean debug;
	private static FileConfiguration config;
	private static File logfile;
	private static FileWriter logwriter;
	private static IRCCommandManager cmdmanager;
	
	public static void main(String[] args) throws IOException
	{
        try 
        {
        	setUpLogFile();
        	loadConfig();
        	debug = config.getBoolean("debug");
        	pluginloader = new PluginLoader();
        	eventmng = new IRCEventManager();
        	pluginloader.loadScripts();
        	pluginloader.loadPlugins();
        	pluginloader.enablePlugins();
    		MyIRCBot.log("Starting MyIRCBot v.1.0");
			IRCConnection con = new IRCConnection(config.getString("host"), config.getString("nick"), config.getInt("port"));
			setupCommands(con);
			con.joinChannel("Sinnoh");
			UserInputDumper ui = new UserInputDumper(con);
			ui.start();
		}catch (Exception e) 
		{	
			logError(e);
		}
	}
	
	public static PluginLoader getPluginLoader()
	{
		return MyIRCBot.pluginloader;
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
		config.addDefaults("host", "nl.quakenet.org");
		config.addDefaults("nick", "MyBot");
		config.addDefaults("port", 6669);
		config.addDefaults("debug", false);
		config.save("MyIRCBot by Sinnoh");
	}
	
	private static void setUpLogFile()
	{
		try
		{
			logfile = new File("MyIrcBot.log");
			if(!logfile.exists())
			{
				logfile.createNewFile();
			}
		}catch(Exception e)
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
	
	public static FileConfiguration getConfig()
	{
		return config;
	}
	
	public static void exit()
	{
		try
		{
			logwriter.close();
		}catch(Exception e)
		{
			logError(e);
		}
		System.exit(0);
	}
	
	public static void log(String msg, Boolean debug)
	{	
		if(debug && !MyIRCBot.debug)
		{
			return;
		}
		System.out.println(msg);
		try
		{
			logwriter = new FileWriter(logfile, true);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
			logwriter.write(sdf.format(new Date(System.currentTimeMillis())) + "  " + msg + "\n");
			logwriter.flush();
			logwriter.close();
		}catch(Exception e)
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



