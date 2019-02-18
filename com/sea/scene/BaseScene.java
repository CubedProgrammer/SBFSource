package com.sea.scene;
import java.awt.*;
import com.sea.main.*;
public abstract class BaseScene implements Scene
{
	protected boolean requireSwitch;
	protected Scene scene;
	protected Cursor cursor;
	public BaseScene()
	{
		this.scene=null;
	}
	public void setRequireSwitch(boolean s)
	{
		this.requireSwitch=s;
	}
	public void setScene(Scene s)
	{
		this.scene=s;
	}
	public void setCursor(int c)
	{
		this.cursor=new Cursor(c);
	}
	public void setCursor(Cursor c)
	{
		this.cursor=c;
	}
	public boolean isSwitchRequired()
	{
		return this.requireSwitch;
	}
	public Scene getScene()
	{
		return this.scene;
	}
	public Cursor getCursor()
	{
		return this.cursor;
	}
}