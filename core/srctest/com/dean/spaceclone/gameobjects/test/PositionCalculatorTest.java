package com.dean.spaceclone.gameobjects.test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;
import com.dean.spaceclone.PositionCalculator;

public class PositionCalculatorTest {

	PositionCalculator positionCalculator;
	
	@Mock Texture mockedTexture;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		positionCalculator = new PositionCalculator();
	}
	
	@Test
	public void shouldCalculateSpriteMostSpritePosX() {
		float rightMostPos = 999;
		Array<Sprite> spriteList = new Array<>();
		for (int i = 0; i < (rightMostPos - 100); i++) {
			Sprite sprite = new Sprite(mockedTexture);
			sprite.setX(i);
			sprite.setY(i);
			spriteList.add(sprite);
		}
		Sprite rightMostSprite = new Sprite(mockedTexture);
		rightMostSprite.setX(rightMostPos);
		rightMostSprite.setY(232); // Don't care about Y value - set it to arbitrary value!
		spriteList.add(rightMostSprite);
		float calculatedRightMostPos = positionCalculator.calcRightMostSpritePos(spriteList);
		
		assertThat(calculatedRightMostPos, is(rightMostPos));
	}
	
	@Test
	public void shouldCalculateLeftMostSpritePosX() {
		float leftMostPos = -10;
		Array<Sprite> spriteList = new Array<>();
		for (int i = 0; i < 999; i++) {
			Sprite sprite = new Sprite(mockedTexture);
			sprite.setX(i);
			sprite.setY(i);
			spriteList.add(sprite);
		}
		Sprite rightMostSprite = new Sprite(mockedTexture);
		rightMostSprite.setX(leftMostPos);
		rightMostSprite.setY(232); // Don't care about Y value - set it to arbitrary value!
		spriteList.add(rightMostSprite);
		float calculatedLeftMostPos = positionCalculator.calcLeftMostSpritePos(spriteList);
		
		assertThat(calculatedLeftMostPos, is(leftMostPos));
	}
	
	@Test
	public void shouldCalculateLowestSpritePosY() {
		float lowestPos = -10;
		Array<Sprite> spriteList = new Array<>();
		for (int i = 0; i < 999; i++) {
			Sprite sprite = new Sprite(mockedTexture);
			sprite.setX(i);
			sprite.setY(i);
			spriteList.add(sprite);
		}
		Sprite rightMostSprite = new Sprite(mockedTexture);
		rightMostSprite.setX(232); // Don't care about X value - set it to arbitrary value!
		rightMostSprite.setY(lowestPos);
		spriteList.add(rightMostSprite);
		float calculatedLowestPos = positionCalculator.calcLowestSpritePos(spriteList);
		
		assertThat(calculatedLowestPos, is(lowestPos));
	}
	
}
