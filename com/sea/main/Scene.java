package com.sea.main;
import java.awt.*;
import com.sea.event.*;
public interface Scene
{
	public abstract void render(Graphics2D g,MouseInput m,KeyInput k);
}