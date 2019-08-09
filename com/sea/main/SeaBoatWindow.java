package com.sea.main;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.*;
import com.sea.entity.*;
import com.sea.entity.enemies.*;
import com.sea.event.*;
import com.sea.scene.*;
import com.sea.world.*;
public class SeaBoatWindow extends JFrame
{
	private static final long serialVersionUID=-24553295867663L;
	public static final long starttime=System.currentTimeMillis();
	private BufferedImage screen;
	//private int[]pixels;
	private boolean running;
	private Thread thread;
	private Thread command;
	private Scene scene;
	private BaseWorld world;
	private boolean playing;
	private boolean debugMenuOn;
	private boolean ctrlppressed;
	private boolean lastctrlppressed;
	private boolean ctrlzpressed;
	private boolean lastctrlzpressed;
	private boolean epressed;
	private boolean lastepressed;
	private boolean ctrlmpressed;
	private boolean lastctrlmpressed;
	private boolean autofire;
	private long debugPrecision;
	private boolean highQuality;
	private boolean sandbox;
	public SeaBoatWindow()
	{
		super("Sea Boat Fight");
		Dimension dimension=Toolkit.getDefaultToolkit().getScreenSize();
		double ratio=(double)dimension.width/dimension.height;
		int width=ratio>16d/9+0.02?(dimension.height<<4)/9:dimension.width;
		int height=width*9>>>4;
		this.setSize(width,height);
		this.screen=new BufferedImage(1920,1080,2);
		this.debugPrecision=1;
		this.setUndecorated(true);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(3);
		this.addKeyListener(new KeyInput());
		this.addMouseListener(new MouseInput());
		this.setScene(new TitleScreen(this.getWidth(),this.getHeight()));
		try
		{
			this.setIconImage(ImageIO.read(this.getClass().getResource("/assets/textures/low_quality/player/boat.png")));
		}
		catch(Exception e)
		{
			e.printStackTrace(System.err);
		}
		this.setVisible(true);
	}
	public void addMouseListener(MouseInput l)
	{
		super.addMouseListener(l);
		super.addMouseMotionListener(l);
		super.addMouseWheelListener(l);
	}
	public void setScene(Scene scene)
	{
		this.scene=scene;
	}
	public void cmd()
	{
		BufferedReader reader=new BufferedReader(new InputStreamReader(System.in));
		String s="";
		String[]args;
		try
		{
			Entity en=null;
			double x=0;
			double y=0;
			double size=0;
			while((s=reader.readLine())!=null&&this.running)
			{
				if(this.world!=null)
				{
					args=s.split(" ");
					if(args.length>0)
					{
						if("exit".equals(args[0]))
						{
							this.running=false;
						}
						else if("spawn".equals(args[0]))
						{
							x=0;
							y=0;
							if(args.length>=4)
							{
								x=Integer.parseInt(args[2]);
								y=Integer.parseInt(args[3]);
							}
							if("redboat".equals(args[1]))
							{
								en=new RedBoat(x,y);
							}
							else if("orangeboat".equals(args[1]))
							{
								en=new OrangeBoat(x,y);
							}
							else if("yellowboat".equals(args[1]))
							{
								en=new YellowBoat(x,y);
							}
							else if("greenboat".equals(args[1]))
							{
								en=new GreenBoat(x,y);
							}
							else if("blueboat".equals(args[1]))
							{
								en=new BlueBoat(x,y);
							}
							else if("violetboat".equals(args[1]))
							{
								en=new VioletBoat(x,y);
							}
							else if("island".equals(args[1]))
							{
								size=16;
								if(args.length>=5)
								{
									size=Integer.parseInt(args[4]);
								}
								en=new Island(x,y,size);
							}
							this.world.addEntity(en);
						}
						else if("kill".equals(args[0]))
						{
							if("all".equals(args[1]))
							{
								if(args.length==2)
								{
									for(int i=0;i<this.world.getEntityCount();i++)
									{
										en=this.world.getEntity(i);
										en.takeDamage(Long.MAX_VALUE);
									}
								}
								else
								{
									if("redboat".equals(args[2]))
									{
										for(int i=0;i<this.world.getEntityCount();i++)
										{
											en=this.world.getEntity(i);
											if(en instanceof RedBoat)
											{
												en.takeDamage(Long.MAX_VALUE);
											}
										}
									}
									else if("orangeboat".equals(args[2]))
									{
										for(int i=0;i<this.world.getEntityCount();i++)
										{
											en=this.world.getEntity(i);
											if(en instanceof OrangeBoat)
											{
												en.takeDamage(Long.MAX_VALUE);
											}
										}
									}
									else if("yellowboat".equals(args[2]))
									{
										for(int i=0;i<this.world.getEntityCount();i++)
										{
											en=this.world.getEntity(i);
											if(en instanceof YellowBoat)
											{
												en.takeDamage(Long.MAX_VALUE);
											}
										}
									}
									else if("greenboat".equals(args[2]))
									{
										for(int i=0;i<this.world.getEntityCount();i++)
										{
											en=this.world.getEntity(i);
											if(en instanceof GreenBoat)
											{
												en.takeDamage(Long.MAX_VALUE);
											}
										}
									}
									else if("blueboat".equals(args[2]))
									{
										for(int i=0;i<this.world.getEntityCount();i++)
										{
											en=this.world.getEntity(i);
											if(en instanceof BlueBoat)
											{
												en.takeDamage(Long.MAX_VALUE);
											}
										}
									}
									else if("violetboat".equals(args[2]))
									{
										for(int i=0;i<this.world.getEntityCount();i++)
										{
											en=this.world.getEntity(i);
											if(en instanceof VioletBoat)
											{
												en.takeDamage(Long.MAX_VALUE);
											}
										}
									}
									else if("island".equals(args[2]))
									{
										for(int i=0;i<this.world.getEntityCount();i++)
										{
											en=this.world.getEntity(i);
											if(en instanceof Island)
											{
												en.takeDamage(Long.MAX_VALUE);
											}
										}
									}
									else if("player".equals(args[2]))
									{
										for(int i=0;i<this.world.getEntityCount();i++)
										{
											en=this.world.getEntity(i);
											if(en instanceof Player)
											{
												en.takeDamage(Long.MAX_VALUE);
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	public void playScene(Graphics2D g,MouseInput m,KeyInput k)
	{
		if(this.world.getEntity(0).getID()!=0)
		{
			this.setScene(new DeathScene(this.screen,this.getWidth(),this.getHeight()));
		}
		else
		{
			if(this.world.getRound()<=this.world.getRoundTotal())
			{
				this.world.render(this.screen);
				Player player=(Player)this.world.getEntity(0);
				int mx=(int)(m.getMouseX()*this.screen.getWidth()/this.getWidth())-(this.screen.getWidth()>>>1);
				int my=(int)(m.getMouseY()*this.screen.getHeight()/this.getHeight())-(this.screen.getHeight()>>>1);
				double mtheta=Math.atan2(my,mx);
				double mscale=Math.sqrt(mx*mx/4+my*my/4);
				g.setColor(new Color(48,48,192));
				g.fillRect(1760,920,160,160);
				g.setColor(new Color(32,32,128));
				g.fillOval(1760,920,160,160);
				double ratio=this.world.getRadius()/80;
				g.setColor(new Color(0,0,0));
				g.fillRect(1836+(int)(player.getX()/ratio),996+(int)(player.getY()/ratio),4,4);
				for(int i=1;i<this.world.getEntityCount();i++)
				{
					if(this.world.getEntity(i).getID()==1)
					{
						g.setColor(new Color(48,192,48));
						g.fillRect(1836+(int)(this.world.getEntity(i).getX()/ratio),996+(int)(this.world.getEntity(i).getY()/ratio),4,4);
					}
					else if(this.world.getEntity(i).getID()==3)
					{
						g.setColor(new Color(240,0,0));
						g.fillRect(1836+(int)(this.world.getEntity(i).getX()/ratio),996+(int)(this.world.getEntity(i).getY()/ratio),4,4);
					}
					else if(this.world.getEntity(i).getID()==4)
					{
						g.setColor(new Color(240,120,0));
						g.fillRect(1836+(int)(this.world.getEntity(i).getX()/ratio),996+(int)(this.world.getEntity(i).getY()/ratio),4,4);
					}
					else if(this.world.getEntity(i).getID()==5)
					{
						g.setColor(new Color(240,240,0));
						g.fillRect(1836+(int)(this.world.getEntity(i).getX()/ratio),996+(int)(this.world.getEntity(i).getY()/ratio),4,4);
					}
					else if(this.world.getEntity(i).getID()==6)
					{
						g.setColor(new Color(0,240,0));
						g.fillRect(1836+(int)(this.world.getEntity(i).getX()/ratio),996+(int)(this.world.getEntity(i).getY()/ratio),4,4);
					}
					else if(this.world.getEntity(i).getID()==7)
					{
						g.setColor(new Color(0,240,240));
						g.fillRect(1836+(int)(this.world.getEntity(i).getX()/ratio),996+(int)(this.world.getEntity(i).getY()/ratio),4,4);
					}
					else if(this.world.getEntity(i).getID()==8)
					{
						g.setColor(new Color(0,0,240));
						g.fillRect(1836+(int)(this.world.getEntity(i).getX()/ratio),996+(int)(this.world.getEntity(i).getY()/ratio),4,4);
					}
				}
				g.setColor(new Color(32,32,32));
				g.fillRect(200,1000,1520,40);
				g.setColor(new Color(240,240,0));
				g.fillRect(200,1000,1520*(player.getExperience()-player.levelsToExperience(player.getLevels()))/((player.getLevels()+1)*10),40);
				g.setColor(new Color(240,240,240));
				g.setFont(new Font("Tahoma",0,32));
				SeaBoatWindow.drawString(g,960,1020,"Level "+Integer.toString(player.getLevels()));
				if(player.getSkills()>0||mx<-806&&my>446)
				{
					g.setColor(new Color(255,128,0));
					g.fillRect(0,900,200,180);
					g.setColor(new Color(255,255,0));
					g.fillRect(20,905,player.getBulletMass()*40,20);
					g.fillRect(20,935,player.getFirePower()*40,20);
					g.fillRect(20,965,player.getCannonLoad()*40,20);
					g.fillRect(20,995,player.getMovementSpeed()*40,20);
					g.fillRect(20,1025,player.getMaxhp()*40,20);
					g.fillRect(20,1055,player.getRegen()*40,20);
					g.setColor(new Color(0,255,0));
					g.setFont(new Font("Tahoma",0,20));
					SeaBoatWindow.drawString(g,100,915,"Bullet Mass");
					SeaBoatWindow.drawString(g,100,945,"Fire Power");
					SeaBoatWindow.drawString(g,100,975,"Cannon Reload");
					SeaBoatWindow.drawString(g,100,1005,"Movement Speed");
					SeaBoatWindow.drawString(g,100,1035,"Maximum Health");
					SeaBoatWindow.drawString(g,100,1065,"Health Regeneration");
					if(player.getSkills()>0)
					{
						if(k.isThisKeyPressed(49))
						{
							player.addBulletMass(1);
							player.setSkills(player.getSkills()-1);
						}
						else if(k.isThisKeyPressed(50))
						{
							player.addFirePower(1);
							player.setSkills(player.getSkills()-1);
						}
						else if(k.isThisKeyPressed(51))
						{
							player.addCannonLoad(1);
							player.setSkills(player.getSkills()-1);
						}
						else if(k.isThisKeyPressed(52))
						{
							player.addMovementSpeed(1);
							player.setSkills(player.getSkills()-1);
						}
						else if(k.isThisKeyPressed(53))
						{
							player.addMaxhp(1);
							player.setSkills(player.getSkills()-1);
						}
						else if(k.isThisKeyPressed(54))
						{
							player.addRegen(1);
							player.setSkills(player.getSkills()-1);
						}
					}
				}
				if(this.debugMenuOn)
				{
					g.setFont(new Font("Consolas",0,20));
					FontMetrics fm=g.getFontMetrics();
					String playerpos="Coordinates ["+Double.toString((double)Math.round(player.getX()*this.debugPrecision)/this.debugPrecision)+","+Double.toString((double)Math.round(player.getY()*this.debugPrecision)/this.debugPrecision)+"]";
					String playerpolarpos="Polar Coordinates ["+Double.toString((double)Math.round(Math.atan2(player.getY(),player.getX())*this.debugPrecision)/this.debugPrecision)+","+Double.toString((double)Math.round(Math.sqrt(player.getX()*player.getX()+player.getY()*player.getY())*this.debugPrecision)/this.debugPrecision)+"]";
					String playerspeed="Speed "+Double.toString((double)Math.round(player.getVelocity()*this.debugPrecision)/this.debugPrecision);
					String playervelvector="Velocity Vector ["+Double.toString((double)Math.round(player.getVelocityX()*this.debugPrecision)/this.debugPrecision)+","+Double.toString((double)Math.round(player.getVelocityY()*this.debugPrecision)/this.debugPrecision)+"]";
					String playerdirection="Direction "+Double.toString((double)Math.round(player.getDirection()*this.debugPrecision)/this.debugPrecision);
					String entityCount="Entity Count "+Integer.toString(this.world.getEntityCount());
					String mousePos="Pointing to ["+Integer.toString(mx)+","+Integer.toString(my)+"]";
					String mousePolarPos="Pointing to (polar) ["+Double.toString((double)Math.round(mtheta*this.debugPrecision)/this.debugPrecision)+","+Double.toString((double)Math.round(mscale*this.debugPrecision)/this.debugPrecision)+"]";
					String worldDataMemory="World Data RAM Usage "+Long.toString(this.world.getMemoryUsage())+"B";
					String currentMemoryUsage="Total RAM Usage "+Long.toString(Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory())+"B";
					String worldRadiusArea="WR WA "+Double.toString((double)Math.round(this.world.getRadius()*this.debugPrecision)/this.debugPrecision)+"m,"+Double.toString((double)Math.round(this.world.getArea()*this.debugPrecision)/this.debugPrecision)+"m^2";
					String playerexp="Exp "+Integer.toString(player.getExperience());
					String enemies="Enemies Remaining "+Integer.toString(this.world.getEnemyCount());
					String round="Round: "+Integer.toString(this.world.getRound());
					g.setColor(new Color(255,255,0));
					g.drawString(mousePos,0,25);
					g.drawString(mousePolarPos,0,50);
					g.drawString(worldDataMemory,0,75);
					g.drawString(currentMemoryUsage,0,100);
					g.drawString(worldRadiusArea,0,125);
					g.drawString(enemies,0,150);
					g.drawString(playerpos,1920-fm.stringWidth(playerpos),25);
					g.drawString(playerpolarpos,1920-fm.stringWidth(playerpolarpos),50);
					g.drawString(playerspeed,1920-fm.stringWidth(playerspeed),75);
					g.drawString(playervelvector,1920-fm.stringWidth(playervelvector),100);
					g.drawString(playerdirection,1920-fm.stringWidth(playerdirection),125);
					g.drawString(entityCount,1920-fm.stringWidth(entityCount),150);
					g.drawString(playerexp,1920-fm.stringWidth(playerexp),175);
					g.drawString(round,1920-fm.stringWidth(round),200);
				}
			}
			else
			{
				System.out.println("com.sea.main.SeaBoatWindow.playScene(Graphics2D,MouseInput,KeyInput)"+Integer.toString(this.world.getRound()));
				BufferedImage b=new BufferedImage(this.screen.getWidth(),this.screen.getHeight(),2);
				b.createGraphics().drawImage(this.screen,0,0,(a,d,c,e,h,j)->
				{
					return true;
				});
				this.setScene(new WinScene(b,this.getWidth(),this.getHeight()));
			}
		}
	}
	public void worldGeneration(long seed)
	{
		Random r=new Random(seed);
		Random random=new Random((~seed)*(seed^r.nextLong())+Double.doubleToRawLongBits(r.nextGaussian()*12));
		Island island=null;
		double size=0;
		double theta=0,distance=0;
		for(int i=0;i<=(random.nextInt()&0xFF);i++)
		{
			size=(random.nextDouble()+2)*30;
			theta=(random.nextDouble()-0.5)*2*Math.PI;
			distance=random.nextDouble()*(this.world.getRadius()-size);
			island=new Island(Math.sin(theta)*distance,Math.cos(theta)*distance,size);
			this.world.addEntity(island);
		}
	}
	public void tick()
	{
		KeyInput k=(KeyInput)this.getKeyListeners()[0];
		MouseInput m=(MouseInput)this.getMouseListeners()[0];
		this.ctrlmpressed=k.isThisKeyPressed(17)&&k.isThisKeyPressed(77);
		if(!this.ctrlmpressed&&this.lastctrlmpressed)
		{
			this.highQuality=!this.highQuality;
		}
		if(this.scene instanceof BaseScene)
		{
			if(((BaseScene)this.scene).isSwitchRequired())
			{
				Scene s=((BaseScene)this.scene).getScene()==null?this::playScene:((BaseScene)this.scene).getScene();
				if(((BaseScene)this.scene).getScene()==null)
				{
					this.sandbox=((com.sea.scene.Menu)this.scene).isSandboxRequested();
					this.world=new BaseWorld(1000,this.highQuality);
					this.world.addEntity(new Player(0,0));
					if(!this.sandbox)
					{
						this.worldGeneration(SeaBoatWindow.starttime);
					}
					this.playing=true;
				}
				else
				{
					this.playing=false;
				}
				this.scene=s;
			}
		}
		if(this.playing&&this.world.getEntity(0).getID()==0)
		{
			Player player=(Player)this.world.getEntity(0);
			this.ctrlzpressed=k.isThisKeyPressed(17)&&k.isThisKeyPressed(90);
			this.ctrlppressed=k.isThisKeyPressed(17)&&k.isThisKeyPressed(80);
			this.epressed=k.isThisKeyPressed(69);
			if(Math.abs(player.getVelocity())<=player.getMaxVelocity())
			{
				double d=Math.sqrt(0.5);
				if(k.isThisKeyPressed(87))
				{
					if(k.isThisKeyPressed(65))
					{
						player.setVelocityX(Math.abs(player.getVelocityX())+d<player.getMaxVelocity()*d?player.getVelocityX()-d:player.getMaxVelocity()*-1*d);
						player.setVelocityY(Math.abs(player.getVelocityY())+d<player.getMaxVelocity()*d?player.getVelocityY()-d:player.getMaxVelocity()*-1*d);
					}
					else if(k.isThisKeyPressed(68))
					{
						player.setVelocityX(Math.abs(player.getVelocityX())+d<player.getMaxVelocity()*d?player.getVelocityX()+d:player.getMaxVelocity()*d);
						player.setVelocityY(Math.abs(player.getVelocityY())+d<player.getMaxVelocity()*d?player.getVelocityY()-d:player.getMaxVelocity()*-1*d);
					}
					else
					{
						player.setVelocityY(Math.abs(player.getVelocityY())+1<player.getMaxVelocity()?player.getVelocityY()-1:player.getMaxVelocity()*-1);
						player.setVelocityX(0);
					}
				}
				else if(k.isThisKeyPressed(83))
				{
					if(k.isThisKeyPressed(65))
					{
						player.setVelocityX(Math.abs(player.getVelocityX())+d<player.getMaxVelocity()*d?player.getVelocityX()-d:player.getMaxVelocity()*-1*d);
						player.setVelocityY(Math.abs(player.getVelocityY())+d<player.getMaxVelocity()*d?player.getVelocityY()+d:player.getMaxVelocity()*d);
					}
					else if(k.isThisKeyPressed(68))
					{
						player.setVelocityX(Math.abs(player.getVelocityX())+d<player.getMaxVelocity()*d?player.getVelocityX()+d:player.getMaxVelocity()*d);
						player.setVelocityY(Math.abs(player.getVelocityY())+d<player.getMaxVelocity()*d?player.getVelocityY()+d:player.getMaxVelocity()*d);
					}
					else
					{
						player.setVelocityY(Math.abs(player.getVelocityY())+1<player.getMaxVelocity()?player.getVelocityY()+1:player.getMaxVelocity());
						player.setVelocityX(0);
					}
				}
				else
				{
					player.setVelocityY(0);
					if(k.isThisKeyPressed(65))
					{
						player.setVelocityX(Math.abs(player.getVelocityX())+1<player.getMaxVelocity()?player.getVelocityX()-1:player.getMaxVelocity()*-1);
					}
					else if(k.isThisKeyPressed(68))
					{
						player.setVelocityX(Math.abs(player.getVelocityX())+1<player.getMaxVelocity()?player.getVelocityX()+1:player.getMaxVelocity());
					}
					else
					{
						player.setVelocityX(0);
					}
				}
			}
			else
			{
				player.setVelocityY(0);
			}
			if(this.lastctrlzpressed&&!this.ctrlzpressed)
			{
				this.debugMenuOn=!this.debugMenuOn;
			}
			if(this.lastctrlppressed&&!this.ctrlppressed)
			{
				if(this.debugPrecision>=4294967296L)
				{
					this.debugPrecision=1;
				}
				else
				{
					this.debugPrecision*=16;
				}
			}
			if(this.lastepressed&&!this.epressed)
			{
				this.autofire=!this.autofire;
			}
			if(m.isMousePressed()||this.autofire)
			{
				Bullet[]bullets=player.shoot();
				for(int i=0;i<bullets.length;i++)
				{
					if(bullets[i]!=null)
					{
						bullets[i].setOwner(0);
						this.world.addEntity(bullets[i]);
					}
				}
			}
			if(m.getMouseX()!=this.getWidth()>>>1||m.getMouseY()!=this.getHeight()>>>1)
			{
				int mw=this.getWidth()>>>1,mh=this.getHeight()>>>1;
				double d=Math.atan2(m.getMouseY()-mh,m.getMouseX()-mw);
				player.setDirection((d>Math.PI/-2?d-Math.PI/2:d+3*Math.PI/2)*-1);
			}
			else
			{
				player.setDirection(0);
			}
			this.world.tick();
			this.lastctrlzpressed=k.isThisKeyPressed(17)&&k.isThisKeyPressed(90);
			this.lastctrlppressed=k.isThisKeyPressed(17)&&k.isThisKeyPressed(80);
			this.lastepressed=k.isThisKeyPressed(69);
		}
		this.lastctrlmpressed=k.isThisKeyPressed(17)&&k.isThisKeyPressed(77);
	}
	public void paint(Graphics2D g)
	{
		KeyInput k=(KeyInput)this.getKeyListeners()[0];
		MouseInput m=(MouseInput)this.getMouseListeners()[0];
		this.scene.render(g,m,k);
		if(this.scene instanceof BaseScene)
		{
			this.setCursor(((BaseScene)this.scene).getCursor());
		}
		if(k.isThisKeyPressed(88)&&k.isThisKeyPressed(27))
		{
			this.running=false;
		}
	}
	public void render()
	{
		BufferStrategy bs=this.getBufferStrategy();
		if(null==bs)
		{
			this.createBufferStrategy(3);
		}
		else
		{
			Graphics g=bs.getDrawGraphics();
			Graphics2D graphics=this.screen.createGraphics();
			this.paint(graphics);
			g.drawImage(this.screen,0,0,this.getWidth(),this.getHeight(),this::imageUpdate);
			graphics.dispose();
			g.dispose();
			bs.show();
		}
	}
	public synchronized void start()
	{
		this.thread=new Thread(this::run);
		this.command=new Thread(this::cmd);
		this.running=true;
		this.thread.start();
		this.command.start();
	}
	public synchronized void stop()
	{
		SeaBoatWindow.exit(0);
	}
	public void run()
	{
		long then=System.nanoTime();
		long now;
		long timepassed=0;
		long timer=0;
		int fps=60;
		long updateTime=1000000000/fps;
		int frames=0;
		while(this.running)
		{
			now=System.nanoTime();
			timepassed+=now-then;
			timer+=now-then;
			then=now;
			if(timepassed>=updateTime)
			{
				this.tick();
				this.render();
				frames++;
				timepassed=0;
			}
			if(timer>1000000000)
			{
				if(frames>fps+10)
				{
					System.out.println("Rendering at "+frames+" frames per second, too frequent, should not be happening.");
				}
				else if(frames<=fps-10)
				{
					System.out.println("Rendering at "+frames+" frames per second, too infrequent, processor too slow.");
				}
				frames=0;
				timer=0;
			}
		}
		this.stop();
	}
	public static final void drawString(Graphics2D g,int x,int y,String s)
	{
		FontMetrics fm=g.getFontMetrics();
		int nx=x-(fm.stringWidth(s)>>>1);
		int ny=y+((fm.getAscent()+fm.getDescent()>>>1)-fm.getDescent());
		g.drawString(s,nx,ny);
	}
	public static final synchronized void exit(int status)
	{
		System.exit(status);
	}
	public static void main(String...args)
	{
		SeaBoatWindow window=new SeaBoatWindow();
		window.start();
	}
}