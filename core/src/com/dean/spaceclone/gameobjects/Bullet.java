package com.dean.spaceclone.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Bullet extends Sprite {

	private float speed;
	
	public Bullet(Texture texture, float x, float y, float speed) {
		super(texture);
		setX(x);
		setY(y);
		this.speed = speed;
	}
	
	/**
	 * Note that bullets can only travel vertically, so there is no need to update and change
	 * its Y axis.
	 */
	public void update() {
		setX(getX() + speed);
	}
	
}
