package com.sea.scene;
import java.awt.*;
import java.awt.image.BufferedImage;
import com.sea.event.*;
import com.sea.main.SeaBoatWindow;
public class WinScene extends BaseScene
{
	private BufferedImage image;
	private Color returnButton;
	private int screenW,screenH;
	private double ratioX,ratioY;
	public WinScene(BufferedImage b,int w,int h)
	{
		this.image=b;
		this.screenW=w;
		this.screenH=h;
		this.ratioX=(double)this.image.getWidth()/this.screenW;
		this.ratioY=(double)this.image.getHeight()/this.screenH;
		this.returnButton=new Color(0,255,255);
	}
	public void render(Graphics2D g,MouseInput m,KeyInput k)
	{
		int mouseX=(int)(m.getMouseX()*this.ratioX);
		int mouseY=(int)(m.getMouseY()*this.ratioY);
		g.drawImage(this.image,0,0,(a,b,c,d,e,f)->
		{
			return true;
		});
		g.setColor(new Color(255,32,32,64));
		g.fillRect(0,0,this.image.getWidth(),this.image.getHeight());
		g.setColor(new Color(128,255,255));
		g.setFont(new Font("Tahoma",0,80));
		SeaBoatWindow.drawString(g,960,50,"YOU WON");
		g.setColor(this.returnButton);
		g.fillRect(860,520,200,40);
		g.setColor(new Color(255-this.returnButton.getRed(),255-this.returnButton.getGreen(),255-this.returnButton.getBlue()));
		g.setFont(new Font("Tahoma",0,32));
		SeaBoatWindow.drawString(g,960,540,"Back to Title");
		if(mouseX>=860&&mouseX<1060&&mouseY>=520&&mouseY<560)
		{
			this.setCursor(12);
			this.returnButton=new Color(64,255,255);
			if(m.isMousePressed())
			{
				this.setScene(new TitleScreen(this.screenW,this.screenH));
				this.setRequireSwitch(true);
			}
		}
		else
		{
			this.setCursor(0);
		}
	}
}