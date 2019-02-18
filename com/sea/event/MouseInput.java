package com.sea.event;
import java.awt.event.*;
public class MouseInput extends MouseAdapter
{
	private int x,y;
	private boolean pressed;
	private boolean onComponent;
	private boolean[]buttons;
	public MouseInput()
	{
		this.buttons=new boolean[8];
	}
	public void mouseMoved(MouseEvent e)
	{
		this.x=e.getX();
		this.y=e.getY();
	}
	public void mousePressed(MouseEvent e)
	{
		this.pressed=true;
		this.buttons[e.getButton()]=true;
	}
	public void mouseReleased(MouseEvent e)
	{
		this.pressed=false;
		this.buttons[e.getButton()]=false;
	}
	public void mouseEntered(MouseEvent e)
	{
		this.onComponent=true;
	}
	public void mouseExited(MouseEvent e)
	{
		this.onComponent=false;
	}
	public int getMouseX()
	{
		return this.x;
	}
	public int getMouseY()
	{
		return this.y;
	}
	public boolean isMousePressed()
	{
		return this.pressed;
	}
	public boolean isThisButtonPressed(int button)
	{
		return this.buttons[button];
	}
	public boolean isMouseOnComponent()
	{
		return this.onComponent;
	}
}