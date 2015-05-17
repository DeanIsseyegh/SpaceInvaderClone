package com.dean.spaceclone.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Invader extends Sprite {

	private float movementPerTick = 5;
	private boolean didJustMoveDown = false;

	public Invader(Texture spriteTexture, float x, float y) {
		super(spriteTexture);
		setX(x);
		setY(y);
	}
	
	public void moveRight() {
		setX(getX() + movementPerTick);
		setDidJustMoveDown(false);
	}
	
	public void moveLeft() {
		setX(getX() - movementPerTick);
		setDidJustMoveDown(false);
	}
	
	public void moveDown() {
		setY(getY() - 3);
		setDidJustMoveDown(true);
	}
	
	public boolean didJustMoveDown() {
		return didJustMoveDown;
	}

	public void setDidJustMoveDown(boolean didJustMoveDown) {
		this.didJustMoveDown = didJustMoveDown;
	}
	
	public float getMovementPerTick() {
		return movementPerTick;
	}

}
