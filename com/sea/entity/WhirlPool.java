package com.sea.entity;
import java.awt.*;
public class WhirlPool extends Entity
{
	private double size;
	public WhirlPool(double x,double y,double size)
	{
		super(x,y,1,9,1073741824000L,0);
		this.size=size;
		this.requestEntitiesWithin(this.size);
	}
	public double size()
	{
		return this.size;
	}
	public void tick()
	{
		Entity en=null;
		for(int i=0;i<this.requested.length;i++)
		{
			en=this.requested[i];
			en.accelerate(-1/Math.atan2(en.getY()-this.getY(),en.getX()-this.getX()),(this.size-this.getDistanceBetween(en))/2);
			if(this.getDistanceBetween(en)<=this.size*3/2+en.getRadius())
			{
				en.takeDamage((long)(this.size*3000));
			}
		}
	}
	public void render(Graphics2D g,int x,int y)
	{
		g.setColor(new Color(128));
		g.fillOval(x-(int)this.size*10,y-(int)this.size*10,(int)this.size*5<<2,(int)this.size*5<<2);
	}
}