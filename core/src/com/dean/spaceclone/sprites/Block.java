package com.dean.spaceclone.sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public class Block {

	Array<BlockPart> blockParts;
	
	public Block(Array<BlockPart> blockParts) {
		this.blockParts = blockParts;
	}
	
	public boolean destroyPartAt(int x, int y) {
		for (int i = 0; i < blockParts.size; i++) {
			BlockPart part = blockParts.get(i);
			if (part.getX() == x && part.getY() == y) {
				blockParts.removeIndex(i);
				return true;
			}
		}
		return false;
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
