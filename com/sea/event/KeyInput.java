package com.sea.event;
import java.awt.event.*;
public class KeyInput extends KeyAdapter
{
	private boolean keys[];
	private boolean pressed;
	public KeyInput()
	{
		this.keys=new boolean[256];
	}
	public void keyPressed(KeyEvent e)
	{
		this.pressed=true;
		this.keys[e.getKeyCode()]=true;
	}
	public void keyReleased(KeyEvent e)
	{
		this.pressed=false;
		this.keys[e.getKeyCode()]=false;
	}
	public final boolean isAnyKeyPressed()
	{
		return this.pressed;
	}
	public final boolean isThisKeyPressed(int key)
	{
		return this.keys[key];
	}
}