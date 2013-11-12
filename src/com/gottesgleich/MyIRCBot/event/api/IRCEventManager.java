package com.gottesgleich.MyIRCBot.event.api;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class IRCEventManager
{

	private List<IRCEventListener> listeners = new ArrayList<IRCEventListener>();

	public void registerListener(IRCEventListener listener)
	{
		if (!this.listeners.contains(listener))
		{
			this.listeners.add(listener);
		}
	}

	public void callEvent(IRCEvent event)
	{
		synchronized (listeners)
		{
			for (IRCEventListener listener : listeners)
			{
				for (Method m : listener.getClass().getMethods())
				{
					if (m.isAnnotationPresent(IRCEventHandler.class))
					{
						try
						{
							m.invoke(listener, event);
						} catch (Exception e)
						{
							continue;
						}
					}
				}
			}
		}
	}

	public void unregisterListeners()
	{
		this.listeners.clear();
	}

}
