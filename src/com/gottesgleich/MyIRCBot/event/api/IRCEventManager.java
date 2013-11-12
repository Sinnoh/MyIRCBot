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
package com.gottesgleich.MyIRCBot.event.api;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class IRCEventManager
{

	private List<IRCEventListener> listeners = new ArrayList<IRCEventListener>();

	public void registerListener(IRCEventListener listener)
	{
		if(!this.listeners.contains(listener))
		{
			this.listeners.add(listener);
		}
	}

	public void callEvent(IRCEvent event)
	{
		synchronized(listeners)
		{
			for(IRCEventListener listener : listeners)
			{
				for(Method m : listener.getClass().getMethods())
				{
					if(m.isAnnotationPresent(IRCEventHandler.class))
					{
						try
						{
							m.invoke(listener, event);
						}
						catch(Exception e)
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
