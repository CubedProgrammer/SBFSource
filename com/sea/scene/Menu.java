package com.sea.scene;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import javax.imageio.ImageIO;
import com.sea.event.*;
import com.sea.main.SeaBoatWindow;
public class Menu extends BaseScene
{
	private BufferedImage title;
	private int screenW,screenH;
	private int width,height;
	private float ratioX,ratioY;
	private Color playButton;
	private Color controlsButton;
	private boolean sandbox;
	public Menu(int w,int h)
	{
		this.screenW=w;
		this.screenH=h;
		this.width=1920;
		this.height=1080;
		this.ratioX=(float)this.width/this.screenW;
		this.ratioY=(float)this.height/this.screenH;
		this.playButton=new Color(255,0,255);
		this.controlsButton=new Color(255,0,255);
		try
		{
			this.title=ImageIO.read(this.getClass().getResource("/assets/textures/title_screen.png"));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void render(Graphics2D g,MouseInput m,KeyInput k)
	{
		int mouseX=(int)(m.getMouseX()*this.ratioX);
		int mouseY=(int)(m.getMouseY()*this.ratioY);
		AffineTransform old=g.getTransform();
		g.scale(-1,1);
		g.drawImage(this.title,this.width*-1,0,null);
		g.setTransform(old);
		g.setColor(this.playButton);
		g.fillRect(this.width>>>4,this.height>>>3,this.width/3,this.height>>>2);
		g.setColor(new Color(255-this.playButton.getRed(),255-this.playButton.getGreen(),255-this.playButton.getBlue()));
		g.setFont(new Font("Tahoma",0,this.height/5));
		SeaBoatWindow.drawString(g,(this.width>>>4)+(this.width>>>1)/3,this.height>>>2,"Play");
		g.setColor(this.controlsButton);
		g.fillRect(this.width>>>4,this.height*5>>>3,this.width/3,this.height>>>2);
		g.setColor(new Color(255-this.controlsButton.getRed(),255-this.controlsButton.getGreen(),255-this.controlsButton.getBlue()));
		g.setFont(new Font("Tahoma",0,this.height*10>>>6));
		SeaBoatWindow.drawString(g,(this.width>>>4)+(this.width>>>1)/3,this.height*3>>>2,"Controls");
		if(mouseX>=this.width>>>4&&mouseX<(this.width>>>4)+this.width/3&&mouseY>=this.height>>>3&&mouseY<(this.height>>>3)+(this.height>>>2))
		{
			this.setCursor(12);
			this.playButton=new Color(0,255,0);
			if(m.isMousePressed())
			{
				this.sandbox=m.isThisButtonPressed(1);
				//System.out.println(this.sandbox);
				this.setCursor(0);
				this.setScene(null);
				this.setRequireSwitch(true);
			}
		}
		else if(mouseX>=this.width>>>4&&mouseX<(this.width>>>4)+this.width/3&&mouseY>=this.height*5>>>3&&mouseY<(this.height*7>>>3))
		{
			this.setCursor(12);
			this.controlsButton=new Color(0,255,0);
			if(m.isMousePressed())
			{
				g.setColor(new Color(16,16,16));
				g.fillRect(this.width>>>1,this.height>>>3,this.width*7>>>4,this.height*3>>>2);
				g.setColor(new Color(240,240,240));
				g.setFont(new Font("Tahoma",0,32));
				g.drawString("WASD to move. E to auto fire. X+Esc to exit.",(this.width>>>1)+10,(this.height>>>3)+40);
				g.drawString("Ctrl+Z to open debug menu. Ctrl+P to toggle precision.",(this.width>>>1)+10,(this.height>>>3)+80);
				g.drawString("Use mouse button to shoot as an alternative to auto fire.",(this.width>>>1)+10,(this.height>>>3)+120);
				g.drawString("Use your number keys 1 through 6 to upgrade.",(this.width>>>1)+10,(this.height>>>3)+160);
				//g.drawString("Press Ctrl+M to toggle graphics quality.",(this.width>>>1)+10,(this.height>>>3)+200);
			}
		}
		else
		{
			this.setCursor(0);
			this.playButton=new Color(255,0,255);
			this.controlsButton=new Color(255,0,255);
		}
	}
	public boolean isSandboxRequested()
	{
		return this.sandbox;
	}
}