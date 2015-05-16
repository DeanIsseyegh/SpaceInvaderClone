package com.dean.spaceclone;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;

public class CollisionHandler implements ICollisionHandler {

	@Override
	public int indexOfSpriteThatCollided(Array<Sprite> sprites, Sprite sprite) {
		for (int i = 0; i < sprites.size; i++) {
			if (sprite.getBoundingRectangle().overlaps(sprites.get(i).getBoundingRectangle())) {
				return i;
			}
		}
		return -1;
	}

}
