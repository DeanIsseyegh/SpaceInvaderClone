package com.dean.spaceclone;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;

public class GameTextures {

	public static Texture DEFENDER_TEXTURE = new Texture(Gdx.files.internal("Defender.png"));
	
	public static Texture INVADER_TEXTURE = new Texture(Gdx.files.internal("Invader.png"));
	
	public static Texture DEFENDER_BULLET_TEXTURE = new Texture(Gdx.files.internal("DefenderBullet.png"));
	
	public static Texture INVADER_BULLET_TEXTURE = new Texture(Gdx.files.internal("InvaderBullet.png"));
	
	public static Texture BLOCK_PART_TEXTURE = new Texture(Gdx.files.internal("BlockPart.png"));
	
	public static FileHandle BLOCK_IMAGE = Gdx.files.internal("Block.png");
}
