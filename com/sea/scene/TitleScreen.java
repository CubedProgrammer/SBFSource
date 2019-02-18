package com.sea.scene;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import com.sea.event.*;
import com.sea.main.*;
public class TitleScreen extends BaseScene
{
	private BufferedImage title;
	private int screenW,screenH;
	public TitleScreen(int w,int h)
	{
		this.screenW=w;
		this.screenH=h;
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
		int x=(int)((double)m.getMouseX()*1920d/(double)this.screenW);
		int y=(int)((double)m.getMouseY()*1080d/(double)this.screenH);
		g.drawImage(this.title,0,0,null);
		if(x>=880&&x<1040&&y>=520&&y<560)
		{
			g.setColor(new Color(255,255,0));
			g.fillRect(870,517,180,45);
			this.setCursor(12);
			if(m.isMousePressed())
			{
				this.setCursor(0);
				this.scene=new Menu(this.screenW,this.screenH);
				this.requireSwitch=true;
			}
		}
		else
		{
			this.setCursor(0);
		}
		g.setColor(new Color(255,255,0));
		g.fillRect(880,520,160,40);
		g.setColor(new Color(0,0,255));
		g.setFont(new Font("Tahoma",0,30));
		SeaBoatWindow.drawString(g,960,540,"Play Game");
	}
}