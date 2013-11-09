package me.sinnoh.MyIRCBot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class UserInputDumper extends Thread
{
	private Boolean running = true;
	private BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	private IRCConnection con;
	
	public UserInputDumper(IRCConnection con)
	{
		this.con = con;
	}
	
	public void run()
	{
		while(running)
		{
		    try
		    {
		      String line;
		      while((line = in.readLine()) != null && running)
		      {
		    	  if(line.startsWith("/"))
		    	  {
		    		  line = line.replaceFirst("/", "");
		    		  String[] ar = line.split(" ");
		    		  MyIRCBot.getCommandManager().executeCommand(ar[0], Arrays.copyOfRange(ar, 1, ar.length));
		    	  }
		    	  else
		    	  {
		    		  if(line.startsWith("#"))
		    		  {
		    			  if(con.getActiveChannel() == null)
		    			  {
		    				  continue;
		    			  }
		    			  con.getActiveChannel().sendRAWMessageToChannel(line.substring(1));
		    		  }
		    		  else
		    		  {
		    			  if(con.getActiveChannel() == null)
		    			  {
		    				  continue;
		    			  }
		    			  con.getActiveChannel().sendMessageToChannel(line);
		    		  }
		    	  }
		      }
		    }
		    catch( IOException e )
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
