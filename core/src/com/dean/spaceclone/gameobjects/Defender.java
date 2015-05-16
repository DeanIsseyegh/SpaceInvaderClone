package com.dean.spaceclone.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Defender extends Sprite {

	private float moveSpeed = 1;
	private Bullet bullet;

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
	
	public Bullet getBullet() {
		return bullet;
	}

	/**
	 * Takes the texture in as a parameter so we don't tightly couple the graphics
	 * of the bullet to the Defender class, making it easy to update later on and
	 * potentially allow for multiple different bullets to be shot.
	 * 
	 * A defender can only shoot one bullet at a time, so if a bullet already exists onscreen,
	 * nothing will happen.
	 * 
	 * @param texture
	 * @param speed - A positive float will mean it travels upwards.
	 */
	public void shoot(Texture texture, float speed) {
		if (this.bullet == null) {
			float topCenter = getOriginY() + getHeight()/2;
			this.bullet = new Bullet(texture, getOriginX(), topCenter, speed);			
		}
	}
	
	/**
	 * Destroys bullet and its texture. Important for performance.
	 */
	public void popBullet() {
		if (this.bullet != null) {
			this.bullet.getTexture().dispose();
			this.bullet = null;
		}
	}
	
}
