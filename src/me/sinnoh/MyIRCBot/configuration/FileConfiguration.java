package me.sinnoh.MyIRCBot.configuration;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Properties;

import me.sinnoh.MyIRCBot.plugin.IRCPlugin;

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
			if(f.exists())
			{
				this.prop.load(new FileReader(this.file));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void addDefaults(String path, String value)
	{
		if(this.prop.getProperty(path) == null)
		{
			this.prop.put(path, value);
		}
	}
	
	public void addDefaults(String path, Integer value)
	{
		if(this.prop.getProperty(path) == null)
		{
			this.prop.put(path, String.valueOf(value));
		}
	}
	
	public void addDefaults(String path, Double value)
	{
		if(this.prop.getProperty(path) == null)
		{
			this.prop.put(path, String.valueOf(value));
		}
	}
	
	public void addDefaults(String path, Long value)
	{
		if(this.prop.getProperty(path) == null)
		{
			this.prop.put(path, String.valueOf(value));
		}
	}
	
	public void addDefaults(String path, Boolean value)
	{
		if(this.prop.getProperty(path) == null)
		{
			this.prop.put(path, String.valueOf(value));
		}
	}
	
	
	public void save(IRCPlugin p)
	{
		try
		{
			if(!this.file.exists())
			{
				this.file.getParentFile().mkdirs();
				this.file.createNewFile();
			}
			FileWriter fw = new FileWriter(this.file);
			this.prop.store(fw, p.getName());
			fw.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void save(String name)
	{
		try
		{
			if(!this.file.exists())
			{
				//this.file.getParentFile().mkdirs();
				this.file.createNewFile();
			}
			FileWriter fw = new FileWriter(this.file);
			this.prop.store(fw, name);
			fw.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void set(String path, String value)
	{
		this.prop.put(path,String.valueOf(value));
	}
	
	public void set(String path, Integer value)
	{
		this.prop.put(path,String.valueOf(value));
	}
	
	public void set(String path, Double value)
	{
		this.prop.put(path,String.valueOf(value));
	}
	
	public void set(String path, Long value)
	{
		this.prop.put(path,String.valueOf(value));
	}
	
	public void set(String path, Boolean value)
	{
		this.prop.put(path,String.valueOf(value));
	}
	
	public String getString(String path)
	{
		if(this.prop.getProperty(path) == null)
		{
			return null;
		}
		else
		{
			return this.prop.getProperty(path);
		}
	}
	
	public Integer getInt(String path)
	{
		if(this.prop.getProperty(path) == null)
		{
			return null;
		}
		else
		{
			try
			{
				return Integer.valueOf(this.prop.getProperty(path));
			}catch(Exception e)
			{
				return null;
			}
		}
	}
	
	public Double getDouble(String path)
	{
		if(this.prop.getProperty(path) == null)
		{
			return null;
		}
		else
		{
			try
			{
				return Double.valueOf(this.prop.getProperty(path));
			}catch(Exception e)
			{
				return null;
			}
		}
	}
	
	public Long getLong(String path)
	{
		if(this.prop.getProperty(path) == null)
		{
			return null;
		}
		else
		{
			try
			{
				return Long.valueOf(this.prop.getProperty(path));
			}catch(Exception e)
			{
				return null;
			}
		}
	}
	
	public Boolean getBoolean(String path)
	{
		if(this.prop.getProperty(path) == null)
		{
			return null;
		}
		else
		{
			try
			{
				return Boolean.valueOf(this.prop.getProperty(path));
			}catch(Exception e)
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
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
