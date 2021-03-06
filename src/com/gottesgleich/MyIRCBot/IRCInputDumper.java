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

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.gottesgleich.MyIRCBot.IRCProtocol.Action;
import com.gottesgleich.MyIRCBot.event.BotJoinedChannelEvent;
import com.gottesgleich.MyIRCBot.event.BotLeftChannelEvent;
import com.gottesgleich.MyIRCBot.event.ClientJoinedChannelEvent;
import com.gottesgleich.MyIRCBot.event.ClientKickedOutOfChannelEvent;
import com.gottesgleich.MyIRCBot.event.ClientLeftChannelEvent;
import com.gottesgleich.MyIRCBot.event.ClientMessageToBotEvent;
import com.gottesgleich.MyIRCBot.event.ClientMessageToChannelEvent;

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

	@Override
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
					if(line.matches(":.+!.+@.+(QUIT|JOIN|PART|NICK|QUIT|KICK|PRIVMSG|MODE).*"))
					{
						try
						{
							line = line.replaceFirst(":", "");
							String sender = line.split("!")[0];
							String senderhost = line.split(" ")[0];
							String action = line.split(" ")[1];
							String value = line.split(" ")[2];
							MyIRCBot.log("Sender: " + sender, true);
							MyIRCBot.log("Senderhost: " + senderhost, true);
							MyIRCBot.log("Action: " + action, true);
							MyIRCBot.log("Value: " + value, true);
							if(action.equalsIgnoreCase("JOIN"))// Client joined
																// channel
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
									MyIRCBot.log(sender + " (" + senderhost + ") " + "joined " + value);
									MyIRCBot.getEventManager().callEvent(new ClientJoinedChannelEvent(con, ch.getClient(sender), ch, senderhost));
								}
							}
							else if(action.equalsIgnoreCase("PART"))
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
									MyIRCBot.log(sender + " (" + senderhost + ") " + "left " + value);
									MyIRCBot.getEventManager().callEvent(new ClientLeftChannelEvent(con, ch.getClient(sender), value, senderhost));
								}
							}
							else if(action.equalsIgnoreCase("QUIT"))
							{
								for(IRCChannel ch : con.getChannels())
								{
									ch.updateClientList();
								}
								MyIRCBot.log(sender + " (" + senderhost + ") " + " timed out");
							}
							else if(action.equalsIgnoreCase("KICK"))
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
									MyIRCBot.getEventManager().callEvent(new ClientKickedOutOfChannelEvent(con, ch.getClient(kicked), ch.getClient(sender), ch, reason));
								}
							}
							else if(action.equalsIgnoreCase("PRIVMSG"))
							{
								String msg = line.split(" ")[3].substring(1);
								for(int i = 4; i < line.split(" ").length; i++)
								{
									msg = msg + " " + line.split(" ")[i];
								}
								if(value.startsWith("#"))// Message to channel
								{
									IRCChannel ch = this.con.getChannel(value);
									MyIRCBot.getEventManager().callEvent(new ClientMessageToChannelEvent(con, ch.getClient(sender), msg, ch, senderhost));
									MyIRCBot.log(value + " " + sender + ": " + msg);
								}
								else if(value.equalsIgnoreCase(con.getUserName()))// Message
																					// to
																					// bot
								{
									MyIRCBot.getEventManager().callEvent(new ClientMessageToBotEvent(con, sender, msg, senderhost));
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

						}
						catch(Exception e)
						{
							continue;
						}

					}
					else if(line.split(" :")[0].equalsIgnoreCase("PING"))
					{
						con.getOutput().addMessage(new IRCProtocol(Action.PONG, line.split(" :")[1], ""), true);
					}
					else if(line.toLowerCase().matches(":" + this.con.getHost() + "\\s\\d+.+"))
					{
						int id = Integer.valueOf(line.replace(":" + this.con.getHost() + " ", "").split(" ")[0]);
						String value = line.replace(":" + con.getHost() + " " + id + " " + con.getUserName() + " ", "");
						if(id == 376)// End of MOTD
						{
							con.setConnected(true);
							MyIRCBot.log("- End of /MOTD -");

						}
						else if(id == 375)// Start of MOTD
						{
							MyIRCBot.log("- Message of the Day -");
						}
						else if(id == 372)// MOTD
						{
							MyIRCBot.log(value.replaceFirst(":- ", ""));
						}
						else if(id == 353)// Names List
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
								MyIRCBot.log(s.getName(), true);
							}
							MyIRCBot.log("Updated Client list for " + ch.getName() + ": " + clients.size() + " Clients", true);
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
