package com.dean.spaceclone.gameobjects.test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.badlogic.gdx.graphics.Texture;
import com.dean.spaceclone.gameobjects.Bullet;

public class BulletTest {
	
	private Bullet bullet;
	
	@Mock Texture mockedTexture;
	
	@Before
	public void setupTests() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void bulletShouldMoveCorrectly() {
		float startPosX = 1;
		float startPosY = 1;
		float speed = 2;
		bullet = new Bullet(mockedTexture, startPosX, startPosY, speed);
		
		int numberOfRenders = 50;
		for (int i = 0; i < numberOfRenders; i++) {
			bullet.update();
		}
		
		float newExpectedYPos = startPosY + (numberOfRenders * speed);
		// Should only move vertically...
		float newExpectedXPos = startPosX;
		assertThat(bullet.getY(), is(newExpectedYPos));
		assertThat(bullet.getX(), is(newExpectedXPos));
	}
}
