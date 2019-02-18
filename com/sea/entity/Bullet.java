package com.sea.entity;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import com.sea.world.BaseWorld;
public class Bullet extends Entity
{
	private BufferedImage texture;
	private int accelerationtime;
	private int owner;
	public Bullet(double x,double y,long maxHealth,double maxVelocity)
	{
		super(x,y,Math.pow(2,Math.floor(Math.min(6,Math.max(3,Math.log(maxHealth)/Math.log(2))))-1)/10,2,maxHealth,maxVelocity);
		this.accelerationtime=30;
		this.owner=-1;
		this.setCanOverlap(true);
		try
		{
			this.texture=ImageIO.read(this.getClass().getResource(BaseWorld.texturepath+"ammunition/bullet.png"));
			if(this.getRadius()*10==4)
			{
				this.texture=this.texture.getSubimage(80,32,8,8);
			}
			else if(this.getRadius()*10==8)
			{
				this.texture=this.texture.getSubimage(64,32,16,16);
			}
			else if(this.getRadius()*10==16)
			{
				this.texture=this.texture.getSubimage(64,0,32,32);
			}
			else if(this.getRadius()*10==32)
			{
				this.texture=this.texture.getSubimage(0,0,64,64);
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	public int getAccelerationTime()
	{
		return this.accelerationtime;
	}
	public void setAccelerationTime(int accelerationtime)
	{
		this.accelerationtime=accelerationtime;
	}
	public int getOwner()
	{
		return this.owner;
	}
	public void setOwner(int owner)
	{
		this.owner=owner;
	}
	public long getMemorySize()
	{
		return super.getMemorySize()+8+this.texture.getWidth()*this.texture.getHeight();
	}
	public void tick()
	{
		if(this.accelerationtime>0)
		{
			this.accelerationtime--;
		}
		else
		{
			double xdecrease=Math.abs(this.getVelocityX())/this.getVelocity();
			double ydecrease=Math.abs(this.getVelocityY())/this.getVelocity();
			if(Math.abs(this.getVelocityX())>=1)
			{
				this.setVelocityX(this.getVelocityX()-this.getVelocityX()/Math.abs(this.getVelocityX())*xdecrease);
			}
			else
			{
				this.setVelocityX(0);
			}
			if(Math.abs(this.getVelocityY())>=1)
			{
				this.setVelocityY(this.getVelocityY()-this.getVelocityY()/Math.abs(this.getVelocityY())*ydecrease);
			}
			else
			{
				this.setVelocityY(0);
			}
		}
	}
	public void render(Graphics2D g,int x,int y)
	{
		g.drawImage(this.texture,x-(int)(this.getRadius()*10),y-(int)(this.getRadius()*10),(int)(this.getRadius()*20),(int)(this.getRadius()*20),(a,b,c,d,e,f)->
		{
			return true;
		});
	}
}