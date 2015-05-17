package com.dean.spaceclone;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;

/**
 * Util class where only purely functional methods should go.
 * 
 * @author Dean
 */
public class PositionCalculator {

	/**
	 * Will find the Sprite in the array which at the lowest position.
	 * 
	 * @param spriteList
	 * @return Y coordinate of the lowest part of the lowest Sprite.
	 */
	public float calcLowestSpritePos(Array<? extends Sprite> spriteList) {
		boolean isFirstLoop = true;
		float lowestPos = 0;
		for (Sprite sprite : spriteList) {
			if (isFirstLoop) {
				lowestPos = sprite.getY();
				isFirstLoop = false;
			}
			float currentPos = sprite.getY();
			if (currentPos < lowestPos) {
				lowestPos = currentPos;
			}

		}
		return lowestPos;
	}
	
	/**
	 * Will find the Sprite in the array which is at the left most position.
	 * 
	 * @param spriteList
	 * @return x coordinate of leftmost part of leftmost Sprite.
	 */
	public float calcLeftMostSpritePos(Array<? extends Sprite> spriteList) {
		boolean isFirstLoop = true;
		float leftMostPos = 0;
		for (Sprite sprite : spriteList) {
			if (isFirstLoop) {
				leftMostPos = sprite.getX();
				isFirstLoop = false;
			}
			float currentPos = sprite.getX();
			if (currentPos < leftMostPos) {
				leftMostPos = currentPos;
			}

		}
		return leftMostPos;
	}

	/**
	 * Will find the Sprite in the array which is at the right most position, and
	 * return that position
	 * 
	 * @param spriteList
	 * @return x coordinate of rightmost part of rightmost Sprite.
	 */
	public float calcRightMostSpritePos(Array<? extends Sprite> spriteList) {
		boolean isFirstLoop = true;
		float rightMostPos = 0;
		for (Sprite sprite : spriteList) {
			if (isFirstLoop) {
				rightMostPos = sprite.getX();
				isFirstLoop = false;
			}
			float currentPos = sprite.getX();
			if (currentPos > rightMostPos) {
				rightMostPos = currentPos;
			}

		}
		return rightMostPos + spriteList.get(0).getWidth();
	}
	
}
