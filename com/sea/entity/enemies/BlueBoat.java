package com.sea.entity.enemies;
import javax.imageio.ImageIO;
import com.sea.entity.*;
import com.sea.main.Cannon;
import com.sea.world.*;
public class BlueBoat extends EnemyBoat
{
	public BlueBoat(double x,double y)
	{
		super(x,y,12.8,7,65536000,20,null,300,"Blue Boat");
		this.autodrive=this::drive;
		try
		{
			this.setTexture(ImageIO.read(this.getClass().getResource(BaseWorld.texturepath+"enemies/blue_boat.png")));
		}
		catch(Exception e)
		{
			e.printStackTrace(System.err);
		}
		this.addCannon(new Cannon(1,12.8,8,120,15,this.getDirection()));
		this.addCannon(new Cannon(-1,12.8,8,120,15,this.getDirection()));
	}
	public void drive(Entity...e)
	{
		int target=-1;
		for(int i=0;i<e.length;i++)
		{
			if(e[i].getID()==0)
			{
				target=i;
			}
		}
		if(target>=0)
		{
			double dx=e[target].getX()-this.getX();
			double dy=e[target].getY()-this.getY();
			this.setDirection(Math.atan2(dx,dy));
			dx=Math.abs(dx);
			dy=Math.abs(dy);
			dx-=this.getRadius();
			dy-=this.getRadius();
			if(dx*dx+dy*dy>2916)
			{
				this.setVelocityByDirection(this.getVelocity()+1);
			}
			else
			{
				this.setVelocityX(0);
				this.setVelocityY(0);
			}
			this.setShotRequested(true);
		}
		else
		{
			this.setVelocityX(0);
			this.setVelocityY(0);
		}
	}
}