package com.sea.scene;
import java.awt.*;
import java.awt.image.BufferedImage;
import com.sea.event.*;
import com.sea.main.SeaBoatWindow;
public class DeathScene extends BaseScene
{
	private BufferedImage image;
	private Color returnButton;
	private int screenW,screenH;
	private int width,height;
	private float ratioX,ratioY;
	public DeathScene(BufferedImage image,int w,int h)
	{
		Graphics2D g=image.createGraphics();
		g.setColor(new Color(255,0,0,64));
		g.fillRect(0,0,image.getWidth(),image.getHeight());
		this.image=image;
		this.returnButton=new Color(0,0,255);
		this.screenW=w;
		this.screenH=h;
		this.width=1920;
		this.height=1080;
		this.ratioX=(float)this.width/this.screenW;
		this.ratioY=(float)this.height/this.screenH;
	}
	public void render(Graphics2D g,MouseInput m,KeyInput k)
	{
		int mouseX=(int)(m.getMouseX()*this.ratioX);
		int mouseY=(int)(m.getMouseY()*this.ratioY);
		g.drawImage(this.image,0,0,this.image.getWidth(),this.image.getHeight(),null);
		g.setColor(new Color(0,0,0));
		g.setFont(new Font("Tahoma",0,50));
		SeaBoatWindow.drawString(g,960,30,"You died");
		g.setColor(this.returnButton);
		g.fillRect(860,520,200,40);
		g.setColor(new Color(255-this.returnButton.getRed(),255-this.returnButton.getGreen(),255-this.returnButton.getBlue()));
		g.setFont(new Font("Tahoma",0,30));
		SeaBoatWindow.drawString(g,960,540,"Back to Title");
		if(mouseX>=860&&mouseX<1060&&mouseY>=520&&mouseY<560)
		{
			this.returnButton=new Color(64,64,255);
			this.setCursor(12);
			if(m.isMousePressed())
			{
				this.returnButton=new Color(0,0,255);
				this.setCursor(0);
				this.setScene(new TitleScreen(this.screenW,this.screenH));
				this.setRequireSwitch(true);
			}
		}
		else
		{
			this.returnButton=new Color(0,0,255);
			this.setCursor(0);
		}
	}
}