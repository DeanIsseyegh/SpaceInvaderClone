package com.dean.spaceclone.collision;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;

public class CollisionDetection implements ICollisionDetection {

	@Override
	public int indexOfSpriteThatCollided(Array<? extends Sprite> sprites, Sprite sprite) {
		for (int i = 0; i < sprites.size; i++) {
			if (sprite.getBoundingRectangle().overlaps(sprites.get(i).getBoundingRectangle())) {
				return i;
			}
		}
		return -1;
	}
	
}
