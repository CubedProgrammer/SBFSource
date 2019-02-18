package com.sea.world;
import java.awt.*;
import java.awt.image.*;
import java.util.*;
import com.sea.entity.*;
import com.sea.main.SeaBoatWindow;
public class BaseWorld
{
	public static String texturepath="/assets/textures/low_quality/";
	private double radius;
	private ArrayList<Entity>entities;
	private boolean highQuality;
	private Round[]rounds;
	private int round;
	private String titleNotification;
	private int titleNotificationTime;
	private long memory;
	//private Random random;
	public BaseWorld(double radius,boolean highQuality)
	{
		this.radius=radius;
		this.highQuality=highQuality;
		this.entities=new ArrayList<Entity>();
		this.titleNotification="";
		this.rounds=new Round[21];
		this.round=0;
		this.rounds[0]=new Round(1,0,0,0,0,0,SeaBoatWindow.starttime);
		this.rounds[1]=new Round(4,0,0,0,0,0,this.rounds[0].getNextSeed());
		this.rounds[2]=new Round(0,1,0,0,0,0,this.rounds[1].getNextSeed());
		this.rounds[3]=new Round(5,1,0,0,0,0,this.rounds[2].getNextSeed());
		this.rounds[4]=new Round(10,4,0,0,0,0,this.rounds[3].getNextSeed());
		this.rounds[5]=new Round(0,0,1,0,0,0,this.rounds[4].getNextSeed());
		this.rounds[6]=new Round(0,10,1,0,0,0,this.rounds[5].getNextSeed());
		this.rounds[7]=new Round(0,5,2,0,0,0,this.rounds[6].getNextSeed());
		this.rounds[8]=new Round(0,1,5,0,0,0,this.rounds[7].getNextSeed());
		this.rounds[9]=new Round(0,0,0,1,0,0,this.rounds[8].getNextSeed());
		this.rounds[10]=new Round(0,0,2,1,0,0,this.rounds[9].getNextSeed());
		this.rounds[11]=new Round(0,0,1,2,0,0,this.rounds[10].getNextSeed());
		this.rounds[12]=new Round(0,0,1,3,0,0,this.rounds[11].getNextSeed());
		this.rounds[13]=new Round(0,0,1,4,0,0,this.rounds[12].getNextSeed());
		this.rounds[14]=new Round(0,0,0,0,1,0,this.rounds[13].getNextSeed());
		this.rounds[15]=new Round(30,20,12,6,2,0,this.rounds[14].getNextSeed());
		this.rounds[16]=new Round(0,0,0,0,3,0,this.rounds[15].getNextSeed());
		this.rounds[17]=new Round(0,0,0,10,4,0,this.rounds[16].getNextSeed());
		this.rounds[18]=new Round(0,0,0,0,5,0,this.rounds[17].getNextSeed());
		this.rounds[19]=new Round(0,0,0,0,10,0,this.rounds[18].getNextSeed());
		this.rounds[20]=new Round(0,0,0,0,0,1,this.rounds[19].getNextSeed());
		int exptotal=0;
		for(int i=0;i<this.rounds.length;i++)
		{
			exptotal+=this.rounds[i].getTotalExp();
			System.out.println("Round "+Integer.toString(i+1)+" Experience: "+Integer.toString(this.rounds[i].getTotalExp())+" Total: "+Integer.toString(exptotal));
		}
		BaseWorld.texturepath=this.highQuality?"/assets/textures/high_quality/":"/assets/textures/low_quality/";
	}
	public double getRadius()
	{
		return this.radius;
	}
	public double getArea()
	{
		return Math.PI*this.radius*this.radius;
	}
	public int getRound()
	{
		return this.round;
	}
	public int getRoundTotal()
	{
		return this.rounds.length;
	}
	public void addEntity(Entity e)
	{
		if(this.canAddEntity(e))
		{
			this.entities.add(e);
		}
	}
	public boolean canAddEntity(Entity e)
	{
		boolean canAdd=true;
		for(int i=0;i<this.entities.size();i++)
		{
			canAdd=canAdd&&this.entities.get(i).getDistanceBetween(e)>=e.getRadius()+this.entities.get(i).getRadius();
		}
		return e.canOverlap()||canAdd;
	}
	public void removeEntity(int index)
	{
		this.entities.remove(index);
	}
	public Entity getEntity(int index)
	{
		return this.entities.get(index);
	}
	public int getEntityCount()
	{
		return this.entities.size();
	}
	public int getEnemyCount()
	{
		int enemies=0;
		for(int i=0;i<this.entities.size();i++)
		{
			if(this.entities.get(i)instanceof EnemyBoat)
			{
				enemies++;
			}
		}
		return enemies;
	}
	public long getMemoryUsage()
	{
		return this.memory+this.rounds.length*24;
	}/*
	public void createDarkerWave(int[]pixels,int width,int height,int x,int y,int size)
	{
		double distance=0;
		double distanceX=0;
		double distanceY=0;
		double r=128d/size;
		for(int i=(x-size)-(y-size)*width;i<(x+size)+(y+size)*width;i++)
		{
			distanceX=(x-i+(int)(i/width*width));
			distanceY=(y-i/width+1);
			distance=Math.sqrt(distanceX*distanceX+distanceY*distanceY);
			if(i>=0&&i<pixels.length)
			{
				pixels[i]=128+Math.max(0,(int)(distance*r));
			}
		}
	}*/
	public void tick()
	{
		long memory=12+this.rounds.length*24;
		long damage=0;
		double vxd,vyd,sd2;
		double distanceX=0,distanceY=0;
		Player player=(Player)this.entities.get(0);
		ArrayList<Entity>enemySight;
		Entity[]enemyTargets;
		Entity[]shots;
		if(this.getEnemyCount()==0)
		{
			if(this.round<this.rounds.length)
			{
				Entity[]enemies=this.rounds[round].spawn(this.radius);
				boolean valid=true;
				int invalidone=-1;
				for(int i=0;i<enemies.length;i++)
				{
					valid=valid&&this.canAddEntity(enemies[i]);
					if(!valid)
					{
						invalidone=i;
						i=enemies.length;
					}
				}
				while(!valid)
				{
					enemies=this.rounds[round].spawn(this.radius);
					valid=true;
					invalidone=-1;
					for(int i=0;i<enemies.length;i++)
					{
						valid=valid&&this.canAddEntity(enemies[i]);
						if(!valid)
						{
							invalidone=i;
							i=enemies.length;
						}
					}
					System.out.println(valid?"Valid spawning.":"Invalid spawning at index "+Integer.toString(invalidone)+", ID "+enemies[invalidone].getID()+".");
				}
				for(int i=0;i<enemies.length;i++)
				{
					this.addEntity(enemies[i]);
				}
				this.round++;
			}
			else if(this.round==this.rounds.length)
			{
				this.round++;
			}
		}
		for(int i=0;i<this.entities.size();i++)
		{
			enemySight=new ArrayList<Entity>();
			if(this.entities.get(i).getID()==2)
			{
				if(((Bullet)this.entities.get(i)).getAccelerationTime()>0)
				{
					this.entities.get(i).setVelocityByDirection(this.entities.get(i).getVelocity()+this.entities.get(i).getMaxVelocity()/30);
				}
				for(int j=0;j<this.entities.size();j++)
				{
					if(j!=((Bullet)this.entities.get(i)).getOwner()&&j!=i)
					{
						if(this.entities.get(i).getDistanceBetween(this.entities.get(j))<=this.entities.get(i).getRadius()+this.entities.get(j).getRadius())
						{
							vxd=this.entities.get(j).getVelocityX()-this.entities.get(i).getVelocityX();
							vyd=this.entities.get(j).getVelocityY()-this.entities.get(i).getVelocityY();
							sd2=vxd*vxd+vyd*vyd;
							damage=(long)(this.entities.get(i).getRadius()*sd2/2);
							this.entities.get(j).takeDamage(damage*1000);
							this.entities.get(i).takeDamage(damage*1000);
							if(this.entities.get(j).getHealth()==0&&this.entities.get(j)instanceof EnemyBoat&&((Bullet)this.entities.get(i)).getOwner()==0)
							{
								player.addExperience(((EnemyBoat)this.entities.get(j)).reward);
								this.titleNotification="You killed a "+((EnemyBoat)this.entities.get(j)).getName();
								this.titleNotificationTime=255;
							}
						}
					}
				}
			}
			if(this.entities.get(i).isShotRequested())
			{
				shots=this.entities.get(i).shoot();
				if(shots!=null)
				{
					for(int j=0;j<shots.length;j++)
					{
						if(shots[j]!=null)
						{
							if(shots[j]instanceof Bullet)
							{
								((Bullet)shots[j]).setOwner(i);
							}
							this.entities.add(shots[j]);
						}
					}
				}
				this.entities.get(i).setShotRequested(false);
			}
			if(this.entities.get(i)instanceof EnemyBoat)
			{
				for(int j=0;j<this.entities.size();j++)
				{
					if(this.entities.get(j)instanceof Player)
					{
						if(Math.abs(this.entities.get(i).getX()-this.entities.get(j).getX())<96&&Math.abs(this.entities.get(i).getY()-this.entities.get(j).getY())<54)
						{
							enemySight.add(this.entities.get(j));
						}
					}
				}
				enemyTargets=new Entity[enemySight.size()];
				((EnemyBoat)this.entities.get(i)).getAutoDrive().drive(enemySight.toArray(enemyTargets));
			}
			memory+=this.entities.get(i).getMemorySize();
			for(int j=0;j<this.entities.size();j++)
			{
				if(i!=j)
				{
					if(!this.entities.get(i).canOverlap()&&!this.entities.get(j).canOverlap())
					{
						distanceX=this.entities.get(i).getX()+(double)Math.round(this.entities.get(i).getVelocityX()/15*16384)/65536-(this.entities.get(j).getX()+(double)Math.round(this.entities.get(j).getVelocityX()/15*16384)/65536);
						distanceY=this.entities.get(i).getY()+(double)Math.round(this.entities.get(i).getVelocityY()/15*16384)/65536-(this.entities.get(j).getY()+(double)Math.round(this.entities.get(j).getVelocityY()/15*16384)/65536);
						if(Math.sqrt(distanceX*distanceX+distanceY*distanceY)<this.entities.get(i).getRadius()+this.entities.get(j).getRadius())
						{
							this.entities.get(i).setVelocityX((this.entities.get(i).getDistanceBetween(this.entities.get(j))-this.entities.get(j).getVelocityX()-this.entities.get(i).getRadius()-this.entities.get(j).getRadius())*(this.entities.get(i).getVelocityX()==0?0:this.entities.get(i).getVelocityX()/Math.abs(this.entities.get(i).getVelocityX())));
							this.entities.get(i).setVelocityY((this.entities.get(i).getDistanceBetween(this.entities.get(j))-this.entities.get(j).getVelocityY()-this.entities.get(i).getRadius()-this.entities.get(j).getRadius())*(this.entities.get(i).getVelocityY()==0?0:this.entities.get(i).getVelocityY()/Math.abs(this.entities.get(i).getVelocityY())));
						}
					}
				}
			}
			this.entities.get(i).run();
			if(this.entities.get(i).getHealth()==0||Math.sqrt(this.entities.get(i).getX()*this.entities.get(i).getX()+this.entities.get(i).getY()*this.entities.get(i).getY())+this.entities.get(i).getRadius()>this.radius||this.entities.get(i).getID()==2&&this.entities.get(i).getVelocity()==0)
			{
				this.removeEntity(i);
			}
		}
		this.memory=memory;
	}
	public void render(BufferedImage b)
	{
		Graphics2D g=b.createGraphics();
		double ux=0;
		double uy=0;
		if(this.entities.get(0).getID()==0)
		{
			Player player=(Player)this.entities.get(0);
			ux=player.getX();
			uy=player.getY();
			boolean canSeeOutside=Math.sqrt((ux-96)*(ux-96)+(uy-54)*(uy-54))>this.radius||Math.sqrt((ux+96)*(ux+96)+(uy-54)*(uy-54))>this.radius||Math.sqrt((ux-96)*(ux-96)+(uy+54)*(uy+54))>this.radius||Math.sqrt((ux+96)*(ux+96)+(uy+54)*(uy+54))>this.radius;
			g.setColor(new Color(0,0,255));
			g.fillRect(0,0,b.getWidth(),b.getHeight());
			if(this.highQuality)
			{
				//do shit here
			}
			else
			{
				if(this.getEnemyCount()==0&&this.round<this.rounds.length)
				{
					this.titleNotification="Round: "+Integer.toString(this.round+1);
					this.titleNotificationTime=255;
				}
			}
			double x,y;
			for(int i=0;i<this.entities.size();i++)
			{
				x=this.entities.get(i).getX()-this.entities.get(0).getX();
				y=this.entities.get(i).getY()-this.entities.get(0).getY();
				if(x>=this.entities.get(i).getRadius()*-1-96&&x<this.entities.get(i).getRadius()+96&&y>=this.entities.get(i).getRadius()*-1-54&&y<this.entities.get(i).getRadius()+54)
				{
					this.entities.get(i).render(g,960+(int)(x*10),540+(int)(y*10));
				}
			}
			if(this.titleNotificationTime>0)
			{
				g.setColor(new Color(255,0,0,this.titleNotificationTime));
				g.setFont(new Font("Tahoma",0,64));
				SeaBoatWindow.drawString(g,960,270,this.titleNotification);
				this.titleNotificationTime--;
			}
			double widthy;
			int startpixel,endpixel;
			if(canSeeOutside)
			{
				for(int i=0;i<1080;i++)
				{
					widthy=2*Math.sqrt(Math.max(0,radius*radius-(uy+(double)i/10-54)*(uy+(double)i/10-54)));
					startpixel=(int)((widthy/-2-ux)*10)+960;
					endpixel=(int)((widthy/2-ux)*10)+960;
					if(startpixel>0)
					{
						g.setColor(new Color(0,0,0));
						g.fillRect(0,i,startpixel,1);
					}
					if(endpixel<1920)
					{
						g.setColor(new Color(0,0,0));
						g.fillRect(endpixel,i,1920-endpixel,1);
					}
				}
			}
		}
	}
}