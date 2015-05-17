package com.dean.spaceclone.gameobjects.test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.*;
import static org.hamcrest.number.OrderingComparison.greaterThan;
import static org.hamcrest.number.OrderingComparison.lessThan;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.badlogic.gdx.graphics.Texture;
import com.dean.spaceclone.gameobjects.Invader;

public class InvaderTest {

	private Invader invader;
	
	@Mock Texture mockedTexture;
	
	@Before
	public void setupTests() {
		MockitoAnnotations.initMocks(this);
		invader = new Invader(mockedTexture, 1, 1);
	}
	
	@Test
	public void constructorShouldSetXCoordinates() {
		int xCoordinate = 3;
		invader = new Invader(mockedTexture, xCoordinate, 0);
		assertThat((int) invader.getX(), is(xCoordinate));
	}
	
	@Test
	public void constructorShouldSetYCoordinates() {
		int yCoordinate = 3;
		invader = new Invader(mockedTexture, 0, yCoordinate);
		assertThat((int) invader.getY(), is(yCoordinate));
	}
	
	@Test
	public void shouldMoveLeftCorretly() {
		float originalXPos = invader.getX();
		invader.moveLeft();
		float newXPos = invader.getX();
		assertThat(originalXPos, is(greaterThan(newXPos)));
	}
	
	@Test
	public void shouldMoveRightCorretly() {
		float originalXPos = invader.getX();
		invader.moveRight();
		float newXPos = invader.getX();
		assertThat(originalXPos, is(lessThan(newXPos)));
	}
	
	@Test
	public void shouldMoveDownCorrectly() {
		float originalYPos = invader.getY();
		invader.moveDown();
		float newYPos = invader.getY();
		assertThat(originalYPos, is(greaterThan(newYPos)));
	}
	
	@Test
	public void shouldCorrectlyFlagThatHasNotMovedDown() {
		assertThat(invader.didJustMoveDown(), is(false));
	}
	
	@Test
	public void shouldCorrectlyFlagThatHasMovedDown() {
		invader.moveDown();
		assertThat(invader.didJustMoveDown(), is(true));
	}

}
