package com.dean.spaceclone.collision;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;

public interface ICollisionDetection {

	/**
	 * Returns the index of the sprite in an array that overlaps with the individual Sprite
	 * passed in.
	 * 
	 * @param sprites
	 * @param sprite
	 * @return int - Index of sprite in Array that has overlapped. Returns -1 if there is no overlap.
	 */
	int indexOfSpriteThatCollided(Array<? extends Sprite> sprites, Sprite sprite);

}
