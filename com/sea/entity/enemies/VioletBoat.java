package com.sea.entity.enemies;
import javax.imageio.ImageIO;
import com.sea.entity.*;
import com.sea.main.Cannon;
import com.sea.world.BaseWorld;
public class VioletBoat extends EnemyBoat
{
	public VioletBoat(double x,double y)
	{
		super(x,y,12.8,8,65536000,20,null,1300,"Violet Boat");
		this.autodrive=this::drive;
		try
		{
			this.setTexture(ImageIO.read(this.getClass().getResource(BaseWorld.texturepath+"enemies/violet_boat.png")));
		}
		catch(Exception e)
		{
			e.printStackTrace(System.err);
		}
		this.addCannon(new Cannon(0,12.8,64,200,15,this.getDirection()));
		this.addCannon(new Cannon(-12.8,0,8,200,15,this.getDirection()-Math.PI/2));
		this.addCannon(new Cannon(12.8,0,8,200,15,this.getDirection()+Math.PI/2));
	}
	public void tick()
	{
		for(int i=0;i<this.getCannons().size();i++)
		{
			if(i==1)
			{
				this.getCannon(i).setDirection(this.getDirection()-Math.PI/2);
			}
			else if (i==2)
			{
				this.getCannon(i).setDirection(this.getDirection()+Math.PI/2);
			}
			else
			{
				this.getCannon(i).setDirection(this.getDirection());
			}
			this.getCannon(i).tick();
		}
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
			double dx=(e[target].getX()+Math.round(e[target].getVelocityX()*32768)/65536)-this.getX();
			double dy=(e[target].getY()+Math.round(e[target].getVelocityY()*32768)/65536)-this.getY();
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