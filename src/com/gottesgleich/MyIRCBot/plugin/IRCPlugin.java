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

import com.gottesgleich.MyIRCBot.configuration.FileConfiguration;

public abstract class IRCPlugin
{
	private FileConfiguration config;
	private String name;

	public abstract void onEnable();

	public abstract void onDisable();

	public FileConfiguration getConfig()
	{
		return this.config;
	}

	public String getName()
	{
		return this.name;
	}

	protected void initialize(FileConfiguration config, String name)
	{
		this.name = name;
		this.config = config;
	}

}
