package com.sea.entity;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import com.sea.main.Cannon;
public class EnemyBoat extends Entity
{
	protected EnemyAutodrive autodrive;
	public final int reward;
	private BufferedImage texture;
	private ArrayList<Cannon>cannons;
	private String name;
	public EnemyBoat(double x,double y,double radius,int ID,long maxHealth,double maxVelocity,EnemyAutodrive autodrive,int reward,String name)
	{
		super(x,y,radius,ID,maxHealth,maxVelocity);
		this.autodrive=autodrive;
		this.reward=reward;
		this.cannons=new ArrayList<Cannon>();
		this.texture=new BufferedImage(4,4,2);
		this.name=name;
		Graphics2D g=this.texture.createGraphics();
		g.setColor(new Color(0,0,0));
		g.fillRect(0,0,4,4);
	}
	public ArrayList<Cannon>getCannons()
	{
		return this.cannons;
	}
	public Cannon getCannon(int index)
	{
		return this.cannons.get(index);
	}
	public BufferedImage getTexture()
	{
		return this.texture;
	}
	public void setTexture(BufferedImage texture)
	{
		this.texture=texture;
	}
	public EnemyAutodrive getAutoDrive()
	{
		return this.autodrive;
	}
	public String getName()
	{
		return this.name;
	}
	public long getMemorySize()
	{
		return super.getMemorySize()+4+this.cannons.size()*48+this.texture.getWidth()*this.texture.getHeight()+this.name.length();
	}
	public void addCannon(Cannon c)
	{
		this.cannons.add(c);
	}
	public void setName(String name)
	{
		this.name=name;
	}
	public Bullet[]shoot()
	{
		Bullet[]bullets=new Bullet[this.getCannons().size()];
		for(int i=0;i<bullets.length;i++)
		{
			bullets[i]=this.getCannon(i).fire(this.getX()+this.getCannon(i).getX(),this.getY()+this.getCannon(i).getY());
		}
		return bullets;
	}
	public void tick()
	{
		for(int i=0;i<this.cannons.size();i++)
		{
			this.cannons.get(i).setDirection(this.getDirection());
			this.cannons.get(i).tick();
		}
	}
	public void render(Graphics2D g,int x,int y)
	{
		g.translate(x,y);
		g.rotate(this.getDirection()*-1);
		g.drawImage(this.texture,(int)(this.getRadius()*-10),(int)(this.getRadius()*-10),(int)(this.getRadius()*20),(int)(this.getRadius()*20),(a,b,c,d,e,f)->
		{
			return true;
		});
		g.rotate(this.getDirection());
		g.translate(-x,-y);
		g.setColor(new Color(240,0,0));
		g.fillRect(x-(int)(this.getRadius()*10),y+(int)(this.getRadius()*10)-10,(int)(this.getRadius()*20),20);
		g.setColor(new Color(0,240,0));
		g.fillRect(x-(int)(this.getRadius()*10),y+(int)(this.getRadius()*10)-10,(int)((long)(this.getRadius()*20)*this.getHealth()/this.getMaxHealth()),20);
	}
}