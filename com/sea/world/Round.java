package com.sea.world;
import java.util.Random;
import com.sea.entity.Entity;
import com.sea.entity.enemies.*;
public final class Round
{
	public final int red;
	public final int orange;
	public final int yellow;
	public final int green;
	public final int blue;
	public final int violet;
	public final long seed;
	public final Random random;
	public Round(int red,int orange,int yellow,int green,int blue,int violet,long seed)
	{
		this.red=red;
		this.orange=orange;
		this.yellow=yellow;
		this.green=green;
		this.blue=blue;
		this.violet=violet;
		this.seed=seed;
		Random rand=new Random(this.seed);
		this.random=new Random((~seed)*(seed^rand.nextLong())+Double.doubleToRawLongBits(rand.nextGaussian()*12));
	}
	public final long getNextSeed()
	{
		return this.random.nextLong()^Double.doubleToRawLongBits(this.random.nextDouble()*Math.sin(this.random.nextGaussian()));
	}
	public int getTotalExp()
	{
		return this.red+this.orange*10+this.yellow*30+this.green*70+this.blue*300+this.violet*1300;
	}
	public final Entity[]spawn(double radius)
	{
		Entity[]enemies=new Entity[this.red+this.orange+this.yellow+this.green+this.blue+this.violet];
		double theta=0,r=0;
		for(int i=0;i<this.red;i++)
		{
			theta+=Math.PI/6;
			r=this.random.nextDouble()*(987.2);
			enemies[i]=new RedBoat(Math.sin(theta)*r,Math.cos(theta)*r);
		}
		for(int i=0;i<this.orange;i++)
		{
			theta+=Math.PI/12;
			r=this.random.nextDouble()*(987.2);
			enemies[i+this.red]=new OrangeBoat(Math.sin(theta)*r,Math.cos(theta)*r);
		}
		for(int i=0;i<this.yellow;i++)
		{
			theta+=Math.PI/12;
			r=this.random.nextDouble()*(987.2);
			enemies[i+this.red+this.orange]=new YellowBoat(Math.sin(theta)*r,Math.cos(theta)*r);
		}
		for(int i=0;i<this.green;i++)
		{
			theta+=Math.PI/12;
			r=this.random.nextDouble()*(987.2);
			enemies[i+this.red+this.orange+this.yellow]=new GreenBoat(Math.sin(theta)*r,Math.cos(theta)*r);
		}
		for(int i=0;i<this.blue;i++)
		{
			theta+=Math.PI/12;
			r=this.random.nextDouble()*(987.2);
			enemies[i+this.red+this.orange+this.yellow+this.green]=new BlueBoat(Math.sin(theta)*r,Math.cos(theta)*r);
		}
		for(int i=0;i<this.violet;i++)
		{
			theta+=Math.PI/12;
			r=this.random.nextDouble()*(987.2);
			enemies[i+this.red+this.orange+this.yellow+this.green+this.blue]=new VioletBoat(Math.sin(theta)*r,Math.cos(theta)*r);
		}
		return enemies;
	}
}