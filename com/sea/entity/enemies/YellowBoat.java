package com.sea.entity.enemies;
import javax.imageio.ImageIO;
import com.sea.entity.*;
import com.sea.main.Cannon;
import com.sea.world.*;
public class YellowBoat extends EnemyBoat
{
	public YellowBoat(double x,double y)
	{
		super(x,y,12.8,5,16384000,10,null,30,"Yellow Boat");
		this.autodrive=this::drive;
		try
		{
			this.setTexture(ImageIO.read(this.getClass().getResource(BaseWorld.texturepath+"enemies/yellow_boat.png")));
		}
		catch(Exception e)
		{
			e.printStackTrace(System.err);
		}
		this.addCannon(new Cannon(0,12.8,8,120,30,this.getDirection()));
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
			if(dx*dx+dy*dy>2500)
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