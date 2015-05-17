package com.dean.spaceclone.gameobjects.test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;
import com.dean.spaceclone.handlers.CollisionHandler;
import com.dean.spaceclone.handlers.ICollisionHandler;

public class CollisionHandlerTest {

	ICollisionHandler collisionHandler;
	
	@Mock Texture mockedTexture;
	final public static int TEXTURE_HEIGHT_WIDTH = 5;
	
	@Before
	public void setupTests() {
		MockitoAnnotations.initMocks(this);
		collisionHandler = new CollisionHandler();
	}
	
	@Test
	public void shouldDetectSpritesThatOverlap() {
		float overlappedXCoordinates = 923;
		float overlappedYCoordinates = 738;
		
		Sprite sprite = new Sprite(mockedTexture);
		sprite.setBounds(overlappedXCoordinates, overlappedYCoordinates, TEXTURE_HEIGHT_WIDTH, TEXTURE_HEIGHT_WIDTH);
		Array<Sprite> spriteArray = new Array<>();
		int x = 0;
		int y = 0;
		// Populate the list with a bunch of invaders so that the test includes a large number of variables
		for (int i = 0; i < 54; i++) {
			Sprite spriteX = new Sprite(mockedTexture);
			spriteX.setBounds(++x, ++y, TEXTURE_HEIGHT_WIDTH, TEXTURE_HEIGHT_WIDTH);
			spriteArray.add(spriteX);
		}
		Sprite overlappedSprite = new Sprite(mockedTexture);
		overlappedSprite.setBounds(overlappedXCoordinates, overlappedYCoordinates, TEXTURE_HEIGHT_WIDTH, TEXTURE_HEIGHT_WIDTH);
		spriteArray.add(overlappedSprite);
		
		int indexOfOverlappedSprite = collisionHandler.indexOfSpriteThatCollided(spriteArray, sprite);
		assertThat(spriteArray.get(indexOfOverlappedSprite), is(overlappedSprite));
	}
	
	@Test
	public void shouldCorrectlyDetectSpritesDidntOverlap() {
		Sprite sprite = new Sprite(mockedTexture);
		// Set sprite to coordinates -100, -100 so it won't overlap with anything
		sprite.setBounds(-100, -100, TEXTURE_HEIGHT_WIDTH, TEXTURE_HEIGHT_WIDTH);
		Array<Sprite> spriteArray = new Array<>();
		int x = 0;
		int y = 0;
		// Populate the list with a bunch of invaders so that the test includes a large number of variables
		for (int i = 0; i < 54; i++) {
			Sprite spriteX = new Sprite(mockedTexture);
			spriteX.setBounds(++x, ++y, TEXTURE_HEIGHT_WIDTH, TEXTURE_HEIGHT_WIDTH);
			spriteArray.add(spriteX);
		}
		int indexOfOverlappedSprite = collisionHandler.indexOfSpriteThatCollided(spriteArray, sprite);
		assertThat(indexOfOverlappedSprite, is(lessThan(0)));
	}
}
