package com.dean.spaceclone.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Defender extends Sprite {

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
}
