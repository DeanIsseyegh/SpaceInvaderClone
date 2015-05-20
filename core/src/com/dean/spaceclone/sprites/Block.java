package com.dean.spaceclone.sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public class Block {

	Array<BlockPart> blockParts;

	public Block(Array<BlockPart> blockParts) {
		this.blockParts = blockParts;
	}

	/**
	 * Will destroy the particle block part at the given x,y coordinates.
	 * Returns true if a block part actually exists at those coordinates, and
	 * false otherwise.
	 * 
	 * @param x
	 * @param y
	 * @return didBlockGetDestroyed
	 */
	public boolean destroyPartAt(float x, float y) {
		for (int i = 0; i < blockParts.size; i++) {
			BlockPart part = blockParts.get(i);
			if (part.getX() == x && part.getY() == y) {
				blockParts.removeIndex(i);
				return true;
			}
		}
		return false;
	}

	/**
	 * Similar to destoryPartAt(float x, float y) method, but will destroy
	 * multiple parts around the given radius.
	 * 
	 * @param x
	 * @param y
	 * @param radius
	 * @return didBlockGetDestroyed
	 */
	public boolean destroyPartsAround(int x, int y, int radius) {
		boolean didDestroyABlock = false;
		for (int i = 0; i < blockParts.size; i++) {
			BlockPart part = blockParts.get(i);
			for (int j = 0; j < radius; j++) {
				if (isWithinBounds(i)) {
					boolean shouldRemove = false;
					// North
					if ((part.getX()) == x && ((part.getY()) + j) == y) {
						shouldRemove = true;
					}

					// North-east
					if ((part.getX() + j) == x && ((part.getY()) + j) == y) {
						shouldRemove = true;
					}

					// East
					if ((part.getX() + j) == x && part.getY() == y) {
						shouldRemove = true;
					}

					// South-East
					if ((part.getX() + j) == x && (part.getY() - j) == y) {
						shouldRemove = true;
					}

					// South
					if ((part.getX()) == x && (part.getY() - j) == y) {
						shouldRemove = true;
					} 

					// South-West
					if ((part.getX() - j) == x && (part.getY() - j) == y) {
						shouldRemove = true;
					}

					// West
					if ((part.getX() - j) == x && (part.getY()) == y) {
						shouldRemove = true;
					}

					// North-West
					if ((part.getX() - j) == x && (part.getY() + j) == y) {
						shouldRemove = true;
					}
					if (shouldRemove) {
						blockParts.removeIndex(i);
						didDestroyABlock = true;
					}
				}
			}
		}
		return didDestroyABlock;
	}

	private boolean isWithinBounds(int i) {
		if (i < 0 || i >= blockParts.size) {
			return false;
		}
		return true;
	}

	public void render(SpriteBatch batch) {
		for (BlockPart part : blockParts) {
			part.draw(batch);
		}
	}

	public Array<BlockPart> getBlockParts() {
		return blockParts;
	}
}
