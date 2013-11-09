package me.sinnoh.MyIRCBot;


import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.sinnoh.MyIRCBot.IrcProtocol.Action;
import me.sinnoh.MyIRCBot.event.BotJoinedChannelEvent;
import me.sinnoh.MyIRCBot.event.BotLeftChannelEvent;
import me.sinnoh.MyIRCBot.event.ClientJoinedChannelEvent;
import me.sinnoh.MyIRCBot.event.ClientKickedOutOfChannelEvent;
import me.sinnoh.MyIRCBot.event.ClientLeftChannelEvent;
import me.sinnoh.MyIRCBot.event.ClientMessageToBotEvent;
import me.sinnoh.MyIRCBot.event.ClientMessageToChannelEvent;

public class IRCInputDumper extends Thread 
{
	  private IRCConnection con;
	  private Boolean running = true;
	  private BufferedReader br;
	  protected IRCInputDumper(IRCConnection con, BufferedReader br) throws IOException
	  {
		  this.br = br;
		  this.con = con;
	  }
	  
	public void run()
	{
		while(running)
		{
		    try
		    {
		      String line;
		      while(running && (line = this.br.readLine()) != null)
		      {
		    	  MyIRCBot.log("INPUT: " + line, true);
		    	  if((line.contains("JOIN") || line.contains("PART") || line.contains("NICK") || line.contains("QUIT") || line.contains("KICK") || line.contains("PRIVMSG") || line.contains("MODE")))
		    	  {
		    		  try
		    		  {
			    		  String sender = line.split(" ")[0].split("!")[0].replace("~", "").replace(":", "");
			    		  String senderhost = line.split(" ")[0].split("!")[1];
			    		  String action = line.split(" ")[1];
			    		  String value = line.split(" ")[2];
			    		  MyIRCBot.log("Sender: " + sender, true);
			    		  MyIRCBot.log("Senderhost: " + senderhost, true);
			    		  MyIRCBot.log("Action: " + action, true);
			    		  MyIRCBot.log("Value: " + value, true);
			    		  if(action.equalsIgnoreCase("join"))//Client joined channel
			    		  {
			    			  if(sender.equalsIgnoreCase(con.getUserName()))
			    			  {
			    				  MyIRCBot.log("You joined " + value);
			    				  IRCChannel ch = new IRCChannel(value, this.con);
			    				  this.con.getChannels().add(ch);
			    				  this.con.setActiveChannel(ch);
			    				  MyIRCBot.getEventManager().callEvent(new BotJoinedChannelEvent(this.con, ch));
			    			  }
			    			  else
			    			  {
			    				  IRCChannel ch = this.con.getChannel(value);
			    				  ch.updateClientList();
			    				  MyIRCBot.log(sender + " joined " + value);
			    				  MyIRCBot.getEventManager().callEvent(new ClientJoinedChannelEvent(con, ch.getClient(sender), ch));
			    			  }
			    		  }
			    		  else if(action.equalsIgnoreCase("PART") || action.equalsIgnoreCase("QUIT"))//Client left channel
			    		  {
			    			  IRCChannel ch = this.con.getChannel(value);
			    			  if(sender.equalsIgnoreCase(con.getUserName()))
			    			  {
			    				  ch.leave();
			    				  MyIRCBot.log("You left " + value);
			    				  MyIRCBot.getEventManager().callEvent(new BotLeftChannelEvent(this.con, ch));
			    			  }
			    			  else
			    			  {
			    				  ch.updateClientList();
			    				  MyIRCBot.log(sender + " left " + value);
			    				  MyIRCBot.getEventManager().callEvent(new ClientLeftChannelEvent(con, ch.getClient(sender), value));
			    			  }
			    		  }
			    		  else if(action.equalsIgnoreCase("KICK"))//Client was kicked from channel
			    		  {
			    			  String kicked = line.split(" ")[3];
			    			  String reason = line.split(" ")[4].substring(1);
			    			  IRCChannel ch = this.con.getChannel(value);
			    			  for(int i = 5; i < line.split(" ").length; i++)
			    			  {
			    				  reason += " " + line.split(" ")[i];
			    			  }
			    			  if(kicked.equalsIgnoreCase(con.getUserName()))
			    			  {
			    				  ch.leave();
			    				  MyIRCBot.log("You were kicked out of " + value + " for " + reason + " by " + sender);
			    				  MyIRCBot.getEventManager().callEvent(new BotLeftChannelEvent(this.con, ch));
			    				  con.joinChannel(value);
			    			  }
			    			  else
			    			  {
			    				  ch.updateClientList();
			    				  MyIRCBot.log(kicked + " was kicked out of " + value + " for " + reason + " by " + sender);
			    				  MyIRCBot.getEventManager().callEvent(new ClientKickedOutOfChannelEvent(con, ch.getClient(kicked), ch.getClient(sender),ch, reason));
			    			  }
			    		  }
			    		  else if(action.equalsIgnoreCase("PRIVMSG"))//Message to bot or channel
			    		  {
		    				  String msg = line.split(" ")[3].substring(1);
		    				  for(int i = 4; i<line.split(" ").length; i++)
		    				  {
		    					  msg = msg + " " + line.split(" ")[i];
		    				  }
			    			  if(value.startsWith("#"))//Message to channel
			    			  {
			    				  IRCChannel ch = this.con.getChannel(value);
			    				  MyIRCBot.getEventManager().callEvent(new ClientMessageToChannelEvent(con, ch.getClient(sender), msg, ch));
			    				  MyIRCBot.log(value + " " + sender + ": " + msg);
			    			  }
			    			  else if(value.equalsIgnoreCase(con.getUserName()))//Message to bot
			    			  {
			    				  MyIRCBot.getEventManager().callEvent(new ClientMessageToBotEvent(con, sender, msg));
			    				  MyIRCBot.log(sender + " -> " + con.getUserName() + " : " + msg);
			    			  }
			    		  }
			    		  else if(action.equalsIgnoreCase("MODE"))
			    		  {
			    			  String mode = line.split(" ")[3];
			    			  String client = line.split(" ")[4];
			    			  IRCChannel ch = this.con.getChannel(value);
			    			  if(mode.equalsIgnoreCase("-o"))
			    			  {
			    				  ch.getClient(client).setOp(false);
			    				  MyIRCBot.log(client + " is no longer an Operator on " + value);
			    			  }
			    			  else if(mode.equalsIgnoreCase("+o"))
			    			  {
			    				  ch.getClient(client).setOp(true);
			    				  MyIRCBot.log(client + " is now an Operator on " + value);
			    			  }
			    		  }
			    		  else if(action.equalsIgnoreCase("NICK"))
			    		  {
			    			  if(sender.equalsIgnoreCase(con.getUserName()))
			    			  {
			    				  con.setUserName(value.substring(1));
			    				  MyIRCBot.log("You are now known as " + value.substring(1));
			    			  }
			    			  else
			    			  {
			    				  for(IRCChannel ch : this.con.getChannels())
			    				  {
			    					  ch.getClient(sender).setNick(value.substring(1));
			    				  }
			    			  	MyIRCBot.log(sender + " is now known as " + value.substring(1));
			    			  }
			    		  }
			    		  
		    		  }catch(Exception e)
		    		  {
		    			  continue;
		    		  }
		    		  
		    		  
		    	  }
		    	  else if(line.split(" :")[0].equalsIgnoreCase("PING"))//React to pings to prevent auto-disconnect
		    	  {
		    		  con.getOutput().addMessage(new IrcProtocol(Action.PONG,line.split(" :")[1] , ""), true);
		    	  }
		    	  else if(line.toLowerCase().matches(":" + this.con.getHost() + "\\s\\d+.+"))
		    	  {
		    		  int id = Integer.valueOf(line.replace(":" + this.con.getHost() + " ", "").split(" ")[0]);
		    		  String value = line.replace(":" + con.getHost() + " " + id + " " + con.getUserName() + " ", "");
		    		  if(id == 376)//End of MOTD
		    		  {
		    			  con.setConnected(true);
		    			  MyIRCBot.log("- End of /MOTD -");
		    			  
		    		  }
		    		  else if(id == 375)//Start of MOTD
		    		  {
		    			  MyIRCBot.log("- Message of the Day -");
		    		  }
		    		  else if(id == 372)//MOTD
		    		  {
		    			  MyIRCBot.log(value.replaceFirst(":- ", ""));
		    		  }
		    		  else if(id == 353)//Names List
		    		  {
		    			  String cname = value.replaceFirst("= ", "").split(" ")[0];
		    			  if(con.getChannel(cname) == null)
		    			  {
		    				  return;
		    			  }
		    			  IRCChannel ch = con.getChannel(cname);
		    			  ch.getClients().clear();
		    			  List<IRCClient> clients = new ArrayList<IRCClient>();
		    			  for(String cl : value.replaceFirst("= " + cname + " :", "").split(" "))
		    			  {
		    				  Boolean isop = false;
		    				  if(cl.startsWith("@"))
		    				  {
		    					  cl = cl.replaceFirst("@", "");
		    					  isop = true;
		    				  }
		    				  if(cl.equalsIgnoreCase(con.getUserName()))
		    				  {
		    					  continue;
		    				  }
		    				  clients.add(new IRCClient(cl, isop, con, ch));
		    			  }
		    			  ch.setClients(clients);
		    			  for(IRCClient s : clients)
		    			  {
		    				  MyIRCBot.log(s.getName(),true);
		    			  }
		    			  MyIRCBot.log("Updated Client list for " + ch.getName() + ": " + clients.size() + " Clients");
		    		  }
		    		  else if(id == 433)
		    		  {
			    		  MyIRCBot.log("The username " + con.getUserName() + " is already in use. Trying to reconnect as " + con.getUserName() + "_");
			    		  con.setUserName(con.getUserName() + "_");
			    		  con.reconnect(Long.valueOf(2));
		    		  }
		    	  }
		    	  else if(line.equals(("ERROR :Your host is trying to (re)connect too fast -- throttled")))
		    	  {
		    		  MyIRCBot.log("Lost connection to the server - Your host is trying to (re)connect too fast");
		    		  con.reconnect(Long.valueOf(60));
		    	  }
		    		  
		      }
		    }
		    catch(Exception e)
		    {
		    	MyIRCBot.logError(e);
		    }
		}
	  }
	
	public void setRunning(Boolean b)
	{
		this.running = false;
	}
}
