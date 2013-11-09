package me.sinnoh.MyIRCBot;

public class IrcProtocol 
{
	
	public static enum Action
	{
		MSG,
		JOIN,
		LEAVE,
		NOTICE,
		NAMES,
		RAW,
		PONG,
		MODE,
		KICK,
		TOPIC,
		PMSG;
	}
	
	private Action action;
	private String value;
	private String channel;
	
	public IrcProtocol(Action action, String val, String channel)
	{
		this.value = val;
		this.action = action;
		  if(!channel.startsWith("#"))
		  {
			  channel = "#" + channel;
		  }
		this.channel = channel;
	}
	
	
	
	public String parse()
	{
		switch(this.action)
		{
		case JOIN: return "Join " + channel + "\n\r";
		case LEAVE: return "PART " + channel + "\n\r";
		case MSG: return "PRIVMSG " + channel + " :" + value + "\n\r";
		case PMSG: return "PRIVMSG " + value.split("::")[0] + " :" + value.split("::")[1] + "\n\r";
		case NOTICE: return "NOTICE " + value.split("::")[0] + " :" + value.split("::")[1] + "\n\r";
		case NAMES: return "NAMES " + channel + "\n\r";
		case PONG: return "PONG :" + value + "\n\r";
		case RAW: return value + "\n\r";
		case MODE: return "MODE " + channel + " "+ value.split("::")[1] + " " + value.split("::")[0] + "\n\r";
		case KICK: return "KICK " + channel + " " +  value.split("::")[0] + " " + value.split("::")[1] + "\n\r";
		case TOPIC: return "TOPIC " + channel + " " + value;
		}
		return null;
	}
	
	public Action getAction()
	{
		return action;
	}
	
	public String getChannel()
	{
		return channel;
	}
}
