package com.dean.spaceclone.gameobjects.test;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;
import com.dean.spaceclone.CollisionHandler;
import com.dean.spaceclone.ICollisionHandler;
import com.dean.spaceclone.gameobjects.Bullet;
import com.dean.spaceclone.gameobjects.Invader;

public class CollisionHandlerTest {

	ICollisionHandler collisionHandler;
	
	@Mock Texture mockedTexture;
	
	@Before
	public void setupTests() {
		MockitoAnnotations.initMocks(this);
		collisionHandler = new CollisionHandler();
	}
	
	@Test
	public void shouldDetectSpritesThatOverlap() {
		float overlappedXCoordinates = 923;
		float overlappedYCoordinates = 738;
		
		Bullet bullet = new Bullet(mockedTexture, overlappedXCoordinates, overlappedYCoordinates, 1);
		Array<Sprite> invaders = new Array<>();
		int x = 0;
		int y = 0;
		// Populate the list with a bunch of invaders so that the test includes a large number of variables
		for (int i = 0; i < 54; i++) {
			Invader invader = new Invader(mockedTexture, ++x, ++y);
			invaders.add(invader);
		}
		Invader overlappedInvader = new Invader(mockedTexture, overlappedXCoordinates, overlappedYCoordinates);
		invaders.add(overlappedInvader);
		
		int indexOfOverlappedInvader = collisionHandler.indexOfSpriteThatCollided(invaders, bullet);
		assertThat(invaders.get(indexOfOverlappedInvader), is(overlappedInvader));
	}
	
}
