package me.sinnoh.MyIRCBot;

import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.List;


public class IRCOutputSender extends Thread
{
	private IRCConnection con;
	private BufferedWriter bw;
	public Boolean running = true;
	private List<IrcProtocol> msgs = new ArrayList<IrcProtocol>();
	private List<IrcProtocol> important = new ArrayList<IrcProtocol>();
	private Long nextping;
	
	public IRCOutputSender(IRCConnection con, BufferedWriter bw)
	{
		this.bw = bw;
		this.con = con;
		this.nextping = System.currentTimeMillis() + 10000;
	}
	
	public List<IrcProtocol> getImportants()
	{
		return this.important;
	}
	
	public List<IrcProtocol> getMSGS()
	{
		return this.msgs;
	}
		
	public void run()
	{
		while(running)
		{
			try
			{
				if(this.nextping < System.currentTimeMillis())//Check if the server is still alive
				{
					this.nextping = System.currentTimeMillis() + 10000;
					MyIRCBot.log("Checking, if server is alive", true);
					MyIRCBot.log("OUT: PING " + con.getHost(), true);
					this.bw.write("PING " + con.getHost() + "\n\r");
					this.bw.flush();
				}
				if(this.important.size() != 0)
				{
					IrcProtocol p = this.important.get(0);
					this.bw.write(p.parse());
					this.bw.flush();
					MyIRCBot.log("OUT: " + p.parse().replace("\r", ""), true);
					this.important.remove(0);
				}
				else if(this.msgs.size() != 0)
				{
					if(con.isConnected())
					{
						if(this.msgs.size() == 0)
						{
							continue;
						}
						IrcProtocol p = this.msgs.get(0);
						this.bw.write(p.parse());
						this.bw.flush();
						MyIRCBot.log("OUT: " + p.parse().replace("\r", ""), true);
						this.msgs.remove(0);
					}
				}
			}catch(Exception e)
			{
				MyIRCBot.log("Connection lost");
				con.reconnect(Long.valueOf(10));
			}
		}
	}
	
	public void addMessage(IrcProtocol pro)
	{
		this.addMessage(pro, false);
	}
	public void addMessage(IrcProtocol pro, Boolean b)
	{
		if(b)
		{
			this.important.add(pro);
		}
		else
		{
			this.msgs.add(pro);
		}
	}
	
	public void setRunning(Boolean b)
	{
		this.running = false;
	}
	
	public BufferedWriter getBufferedWriter()
	{
		return this.bw;
	}

}
