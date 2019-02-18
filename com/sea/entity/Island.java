package com.sea.entity;
import java.awt.*;
public class Island extends Entity
{
	public Island(double x,double y,double radius)
	{
		super(x,y,radius,1,65536000000L,0);
	}
	public void tick()
	{
		
	}
	public void render(Graphics2D g,int x,int y)
	{
		g.setColor(new Color(48,192,48));
		g.fillOval(x-(int)(this.getRadius()*10),y-(int)(this.getRadius()*10),(int)(this.getRadius()*20),(int)(this.getRadius()*20));
	}
}