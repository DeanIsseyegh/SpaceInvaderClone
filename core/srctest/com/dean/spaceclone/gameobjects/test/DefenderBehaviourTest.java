package com.dean.spaceclone.gameobjects.test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.badlogic.gdx.graphics.Texture;
import com.dean.spaceclone.gameobjects.Defender;

public class DefenderBehaviourTest {

	private Defender defender;
	
	@Mock Texture mockedTexture;
	
	@Before
	public void setupTests() {
		MockitoAnnotations.initMocks(this);
		defender = new Defender(mockedTexture, 1, 1);
	}
	
	@Test
	public void shouldMoveLeftCorrectly() {
		float originalXPos = defender.getX();
		float originalYPos = defender.getY();
		
		defender.moveLeft();
		
		assertThat(defender.getX(), is(lessThan(originalXPos)));
		assertThat(defender.getY(), is(originalYPos));
	}
	
	@Test
	public void shouldMoveRightCorrectly() {
		float originalXPos = defender.getX();
		float originalYPos = defender.getY();
		
		defender.moveRight();
		
		assertThat(defender.getX(), is(greaterThan(originalXPos)));
		assertThat(defender.getY(), is(originalYPos));
	}
	
	@Test
	public void shouldCreateBulletWhenShooting() {
		defender.shoot(mockedTexture, 1);
		assertThat(defender.getBullet(), is(not(nullValue())));
	}
	
	@Test
	public void shouldNotHaveBulletWhenHasNotShot() {
		assertThat(defender.getBullet(), is(nullValue()));
	}
}
