package com.dean.spaceclone.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Invader extends Sprite {

	private Texture spriteTexture;
	private float movementPerTick = 5;
	private boolean didJustMoveDown = false;

	public Invader(Texture spriteTexture, int x, int y) {
		super(spriteTexture);
		setX(x);
		setY(y);
	}

	public Texture getSpriteTexture() {
		return spriteTexture;
	}
	
	public void increaseSpeed() {
		movementPerTick += 0.5f;
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
}
