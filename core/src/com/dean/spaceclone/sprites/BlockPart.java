package com.dean.spaceclone.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class BlockPart extends Sprite {
	
	public BlockPart(Texture texture, float x, float y) {
		super(texture);
		setX(x);
		setY(y);
	}
	
}
