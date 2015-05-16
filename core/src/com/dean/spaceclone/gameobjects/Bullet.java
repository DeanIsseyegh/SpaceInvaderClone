package com.dean.spaceclone.gameobjects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Bullet extends Sprite {

	static Logger logger = LoggerFactory.getLogger(Bullet.class);
	
	private float speed;
	
	public Bullet(Texture texture, float x, float y, float speed) {
		super(texture);
		setX(x);
		setY(y);
		this.speed = speed;
		logger.debug("Bullet created at x,y : " + x + "," + y + ". Speed is : " + speed);
	}
	
	/**
	 * Note that bullets can only travel vertically, so there is no need to update and change
	 * its X axis.
	 */
	public void update() {
		setY(getY() + speed);
	}
	
}
