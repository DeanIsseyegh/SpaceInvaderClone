package com.dean.spaceclone.gameobjects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Defender extends Sprite {
	static final Logger logger = LoggerFactory.getLogger(Defender.class);
	
	private float moveSpeed = 1;

	public Defender(Texture texture, float x, float y) {
		super(texture);
		setX(x);
		setY(y);
	}

	public void moveRight() {
		setX(getX() + moveSpeed);
	}

	public void moveLeft() {
		setX(getX() - moveSpeed);
	}

	/**
	 * Takes the texture in as a parameter so we don't tightly couple the graphics
	 * of the bullet to the Defender class, making it easy to update later on and
	 * potentially allow for multiple different bullets to be shot.
	 * 
	 * @param texture
	 * @param speed - A positive float will mean it travels upwards.
	 */
	public Bullet shoot(Texture texture, float speed) {
		float topCenterY = getY() + getHeight()/2;
		float topCenterX = getX() + getWidth()/2;
		Bullet bullet = new Bullet(texture, topCenterX, topCenterY, speed);
		return bullet;
	}
	
}
