package com.sea.entity;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import com.sea.main.Cannon;
import com.sea.world.BaseWorld;
public class Player extends Entity
{
	private ArrayList<Cannon>cannons;
	private BufferedImage texture;
	private int experience;
	private int skills;
	private int bmass,firepower,cannonLoad,movementSpeed,maxhp,regen;
	public Player(double x,double y)
	{
		super(x,y,12.8,0,65536000,40);
		try
		{
			this.texture=ImageIO.read(this.getClass().getResource(BaseWorld.texturepath+"player/boat.png"));
		}
		catch(Exception e)
		{
			System.err.println(e);
		}
		if(this.texture==null)
		{
			this.texture=new BufferedImage(4,4,2);
			Graphics2D g=this.texture.createGraphics();
			g.setColor(new Color(0,0,0));
			g.fillRect(0,0,4,4);
		}
		this.cannons=new ArrayList<Cannon>();
		this.cannons.add(new Cannon(0,12.8,8,100,60,this.getDirection()));
	}
	public int getBulletMass()
	{
		return this.bmass;
	}
	public int getFirePower()
	{
		return this.firepower;
	}
	public int getCannonLoad()
	{
		return this.cannonLoad;
	}
	public int getMovementSpeed()
	{
		return this.movementSpeed;
	}
	public int getMaxhp()
	{
		return this.maxhp;
	}
	public int getRegen()
	{
		return this.regen;
	}
	public int getExperience()
	{
		return this.experience;
	}
	public int getLevels()
	{
		return(int)((-5+Math.sqrt(25+20*this.experience))/10);
	}
	public int levelsToExperience(int levels)
	{
		return(levels*levels+levels)*5;
	}
	public int getSkills()
	{
		return this.skills;
	}
	public Bullet[]shoot()
	{
		Bullet[]bullets=new Bullet[this.cannons.size()];
		for(int i=0;i<bullets.length;i++)
		{
			bullets[i]=this.cannons.get(i).fire(this.getX()+this.cannons.get(i).getX(),this.getY()+this.cannons.get(i).getY());
		}
		return bullets;
	}
	public long getMemorySize()
	{
		return super.getMemorySize()+this.cannons.size()*48+this.texture.getWidth()*this.texture.getHeight();
	}
	public void setBulletMass(int bmass)
	{
		this.bmass=bmass;
		for(int i=0;i<this.cannons.size();i++)
		{
			this.cannons.get(i).setSize(this.bmass*8+8);
		}
	}
	public void setFirePower(int firepower)
	{
		this.firepower=firepower;
		for(int i=0;i<this.cannons.size();i++)
		{
			this.cannons.get(i).setFirepower(100+this.firepower*25);
		}
	}
	public void setCannonLoad(int cannonLoad)
	{
		this.cannonLoad=cannonLoad;
		for(int i=0;i<this.cannons.size();i++)
		{
			this.cannons.get(i).setInitialLoad(60/(this.cannonLoad+1));
		}
	}
	public void setMovementSpeed(int movementSpeed)
	{
		this.movementSpeed=movementSpeed;
		this.setMaxVelocity(40+this.movementSpeed*10);
	}
	public void setMaxhp(int maxhp)
	{
		this.maxhp=maxhp;
		this.setMaxHealth(65536000*(this.maxhp+1));
	}
	public void setRegen(int regen)
	{
		this.regen=regen;
	}
	public void addBulletMass(int bmass)
	{
		this.setBulletMass(Math.min(this.bmass+bmass,4));
	}
	public void addFirePower(int firepower)
	{
		this.setFirePower(Math.min(this.firepower+firepower,4));
	}
	public void addCannonLoad(int cannonLoad)
	{
		this.setCannonLoad(Math.min(this.cannonLoad+cannonLoad,4));
	}
	public void addMovementSpeed(int movementSpeed)
	{
		this.setMovementSpeed(Math.min(this.movementSpeed+movementSpeed,4));
	}
	public void addMaxhp(int maxhp)
	{
		this.setMaxhp(Math.min(this.maxhp+maxhp,4));
	}
	public void addRegen(int regen)
	{
		this.setRegen(Math.min(this.regen+regen,4));
	}
	public void setSkills(int skills)
	{
		this.skills=skills;
	}
	public void addSkills(int skills)
	{
		this.skills+=skills;
	}
	public void setExperience(int experience)
	{
		this.experience=experience;
	}
	public void addExperience(int experience)
	{
		this.experience+=experience;
		int oldlv=(int)((-5+Math.sqrt(25+20*(this.experience-experience)))/10);
		if(oldlv<this.getLevels())
		{
			this.addSkills(this.getLevels()-oldlv);
			this.takeDamage(this.getHealth()-this.getMaxHealth());
		}
	}
	public void tick()
	{
		this.takeDamage(this.regen*-16000);
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