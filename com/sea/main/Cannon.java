package com.sea.main;
import com.sea.entity.*;
public class Cannon
{
	private double x,y;
	private long size;
	private double firepower;
	private int initialload;
	private int load;
	private double direction;
	public Cannon(double x,double y,long size,double firepower,int load,double direction)
	{
		this.x=x;
		this.y=y;
		this.size=size;
		this.firepower=firepower;
		this.initialload=load;
		this.load=this.initialload;
		this.direction=direction;
		this.direction=direction;
		while(this.direction>=Math.PI*2)
		{
			this.direction-=Math.PI*2;
		}
		while(this.direction<0)
		{
			this.direction+=Math.PI*2;
		}
	}
	public double getX()
	{
		return this.x;
	}
	public double getY()
	{
		return this.y;
	}
	public long getSize()
	{
		return this.size;
	}
	public double getFirepower()
	{
		return this.firepower;
	}
	public int getLoad()
	{
		return this.load;
	}
	public int getInitialLoad()
	{
		return this.initialload;
	}
	public double getDirection()
	{
		return this.direction;
	}
	public void setX(int x)
	{
		this.x=x;
	}
	public void setY(int y)
	{
		this.y=y;
	}
	public void setSize(long size)
	{
		this.size=size;
	}
	public void setFirepower(double firepower)
	{
		this.firepower=firepower;
	}
	public void setLoad(int load)
	{
		this.load=load;
	}
	public void setInitialLoad(int initialload)
	{
		this.initialload=initialload;
		this.load=initialload;
	}
	public void setDirection(double direction)
	{
		double d=this.direction;
		this.direction=direction;
		while(this.direction>=Math.PI)
		{
			this.direction-=Math.PI*2;
		}
		while(this.direction<Math.PI/-2)
		{
			this.direction+=Math.PI*2;
		}
		double distance=Math.sqrt(this.x*this.x+this.y*this.y);
		double angle=Math.atan2(this.x,this.y)-d;
		this.x=Math.sin(angle+direction)*distance;
		this.y=Math.cos(angle+direction)*distance;
	}
	public void tick()
	{
		if(this.load>0)
		{
			this.load--;
		}
	}
	public Bullet fire(double x,double y)
	{
		if(this.load>0)
		{
			return null;
		}
		else
		{
			Bullet b=new Bullet(x,y,this.size,this.firepower);
			b.setDirection(this.direction);
			this.load=this.initialload;
			return b;
		}
	}
}