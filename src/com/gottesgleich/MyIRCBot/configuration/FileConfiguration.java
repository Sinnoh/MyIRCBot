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
package com.gottesgleich.MyIRCBot.configuration;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Properties;

import com.gottesgleich.MyIRCBot.plugin.IRCPlugin;

public class FileConfiguration
{

	private File file;
	private Properties prop;

	public FileConfiguration(File f)
	{
		try
		{
			this.file = f;
			this.prop = new Properties();
			if (f.exists())
			{
				this.prop.load(new FileReader(this.file));
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void addDefaults(String path, String value)
	{
		if (this.prop.getProperty(path) == null)
		{
			this.prop.put(path, value);
		}
	}

	public void addDefaults(String path, Integer value)
	{
		if (this.prop.getProperty(path) == null)
		{
			this.prop.put(path, String.valueOf(value));
		}
	}

	public void addDefaults(String path, Double value)
	{
		if (this.prop.getProperty(path) == null)
		{
			this.prop.put(path, String.valueOf(value));
		}
	}

	public void addDefaults(String path, Long value)
	{
		if (this.prop.getProperty(path) == null)
		{
			this.prop.put(path, String.valueOf(value));
		}
	}

	public void addDefaults(String path, Boolean value)
	{
		if (this.prop.getProperty(path) == null)
		{
			this.prop.put(path, String.valueOf(value));
		}
	}

	public void save(IRCPlugin p)
	{
		try
		{
			if (!this.file.exists())
			{
				this.file.getParentFile().mkdirs();
				this.file.createNewFile();
			}
			FileWriter fw = new FileWriter(this.file);
			this.prop.store(fw, p.getName());
			fw.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void save(String name)
	{
		try
		{
			if (!this.file.exists())
			{
				// this.file.getParentFile().mkdirs();
				this.file.createNewFile();
			}
			FileWriter fw = new FileWriter(this.file);
			this.prop.store(fw, name);
			fw.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void set(String path, String value)
	{
		this.prop.put(path, String.valueOf(value));
	}

	public void set(String path, Integer value)
	{
		this.prop.put(path, String.valueOf(value));
	}

	public void set(String path, Double value)
	{
		this.prop.put(path, String.valueOf(value));
	}

	public void set(String path, Long value)
	{
		this.prop.put(path, String.valueOf(value));
	}

	public void set(String path, Boolean value)
	{
		this.prop.put(path, String.valueOf(value));
	}

	public String getString(String path)
	{
		if (this.prop.getProperty(path) == null)
		{
			return null;
		} else
		{
			return this.prop.getProperty(path);
		}
	}

	public Integer getInt(String path)
	{
		if (this.prop.getProperty(path) == null)
		{
			return null;
		} else
		{
			try
			{
				return Integer.valueOf(this.prop.getProperty(path));
			} catch (Exception e)
			{
				return null;
			}
		}
	}

	public Double getDouble(String path)
	{
		if (this.prop.getProperty(path) == null)
		{
			return null;
		} else
		{
			try
			{
				return Double.valueOf(this.prop.getProperty(path));
			} catch (Exception e)
			{
				return null;
			}
		}
	}

	public Long getLong(String path)
	{
		if (this.prop.getProperty(path) == null)
		{
			return null;
		} else
		{
			try
			{
				return Long.valueOf(this.prop.getProperty(path));
			} catch (Exception e)
			{
				return null;
			}
		}
	}

	public Boolean getBoolean(String path)
	{
		if (this.prop.getProperty(path) == null)
		{
			return null;
		} else
		{
			try
			{
				return Boolean.valueOf(this.prop.getProperty(path));
			} catch (Exception e)
			{
				return null;
			}
		}
	}

	public void reloadConfig()
	{
		try
		{
			this.prop = new Properties();
			this.prop.load(new FileReader(this.file));
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
