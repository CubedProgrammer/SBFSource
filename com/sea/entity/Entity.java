package com.sea.entity;
import java.awt.Graphics2D;
public abstract class Entity
{
	private double x,y;
	private double radius;
	private double velocityX,velocityY;
	private double direction;
	private final int ID;
	private long maxHealth;
	private long health;
	private double maxVelocity;
	private boolean shotRequested;
	private boolean canOverlap;
	public Entity(double x,double y,double radius,int ID,long maxHealth,double maxVelocity)
	{
		this.x=x;
		this.y=y;
		this.radius=radius;
		this.ID=ID;
		this.maxHealth=maxHealth;
		this.health=this.maxHealth;
		this.maxVelocity=maxVelocity;
		this.shotRequested=false;
	}
	public double getX()
	{
		return this.x;
	}
	public double getY()
	{
		return this.y;
	}
	public double getRadius()
	{
		return this.radius;
	}
	public double getVelocityX()
	{
		return this.velocityX;
	}
	public double getVelocityY()
	{
		return this.velocityY;
	}
	public double getVelocity()
	{
		return Math.sqrt(this.velocityX*this.velocityX+this.velocityY*this.velocityY);
	}
	public int getID()
	{
		return this.ID;
	}
	public long getMaxHealth()
	{
		return this.maxHealth;
	}
	public long getHealth()
	{
		return this.health;
	}
	public double getDirection()
	{
		return this.direction;
	}
	public double getMaxVelocity()
	{
		return this.maxVelocity;
	}
	public boolean canOverlap()
	{
		return this.canOverlap;
	}
	public boolean isShotRequested()
	{
		return this.shotRequested;
	}
	public void setVelocityByDirection(double velocity)
	{
		this.setVelocityX(Math.sin(this.direction)*velocity);
		this.setVelocityY(Math.cos(this.direction)*velocity);
	}
	public void setVelocityX(double velocityX)
	{
		if(velocityX!=velocityX)
		{
			this.x+=(double)Math.round(this.velocityX/15*16384)/65536;
		}
		if(Math.sqrt(velocityX*velocityX+this.velocityY*this.velocityY)<=this.maxVelocity)
		{
			this.velocityX=velocityX;
		}
		else
		{
			this.velocityX=Math.sqrt(this.maxVelocity*this.maxVelocity-this.velocityY*this.velocityY)*velocityX/Math.abs(velocityX);
		}
	}
	public void setVelocityY(double velocityY)
	{
		if(velocityY!=velocityY)
		{
			this.y+=(double)Math.round(this.velocityY/15*16384)/65536;
		}
		if(Math.sqrt(this.velocityX*this.velocityX+velocityY*velocityY)<=this.maxVelocity)
		{
			this.velocityY=velocityY;
		}
		else
		{
			this.velocityY=Math.sqrt(this.maxVelocity*this.maxVelocity-this.velocityX*this.velocityX)*velocityY/Math.abs(velocityY);
		}
	}
	public void takeDamage(long damage)
	{
		this.health=damage>this.health?0:damage<this.health-this.maxHealth?this.maxHealth:this.health-damage;
	}
	public void setMaxHealth(long maxHealth)
	{
		this.maxHealth=maxHealth;
	}
	public void setDirection(double direction)
	{
		this.direction=direction;
		while(this.direction>=Math.PI)
		{
			this.direction-=Math.PI*2;
		}
		while(this.direction<-Math.PI)
		{
			this.direction+=Math.PI*2;
		}
	}
	public void setMaxVelocity(double maxVelocity)
	{
		this.maxVelocity=maxVelocity;
	}
	public double getDistanceBetween(Entity e)
	{
		double x=e.x-this.x;
		double y=e.y-this.y;
		return Math.sqrt(x*x+y*y);
	}
	public void setCanOverlap(boolean canOverlap)
	{
		this.canOverlap=canOverlap;
	}
	public void setShotRequested(boolean shotRequested)
	{
		this.shotRequested=shotRequested;
	}
	public Entity[]shoot()
	{
		return null;
	}
	public long getMemorySize()
	{
		return 78;
	}
	public void run()
	{
		this.x+=(double)Math.round(this.velocityX/15*16384)/65536;
		this.y+=(double)Math.round(this.velocityY/15*16384)/65536;
		this.tick();
	}
	public abstract void tick();
	public abstract void render(Graphics2D g,int x,int y);
}