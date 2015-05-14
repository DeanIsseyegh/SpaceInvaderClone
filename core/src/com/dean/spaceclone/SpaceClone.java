package com.dean.spaceclone;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.dean.spaceclone.gameobjects.Defender;
import com.dean.spaceclone.gameobjects.Invader;

public class SpaceClone extends ApplicationAdapter {
	private SpriteBatch batch;
	private Array<Invader> invaderList;
	private Defender defender;
	private OrthographicCamera cam;
	
	public final static int NUM_OF_INVADERS = 55;
	public final static int ROWS_OF_INVADERS = 11;
	float effectiveViewportWidth;
	float effectiveViewportHeight;
	
	/**
	 * This is the first method thats called. We want to setup the initial variables and game objects.
	 */
	@Override
	public void create () {
		batch = new SpriteBatch();
		
		setupCamera();
        
        setupDefender();
        
		setupInvaders();
	}

	private float time;
	private boolean shouldMoveRight = true;
	@Override
	public void render () {
		float deltaTime = Gdx.graphics.getDeltaTime();
		time += deltaTime;

		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		cam.update();
		batch.setProjectionMatrix(cam.combined);
		
		batch.begin();
		float boundaryOffset = effectiveViewportWidth/10;
		boolean isBeyondRightLimit = calcLeftMostInvaderPos(invaderList) <= ((cam.position.x - effectiveViewportWidth/2) + boundaryOffset);
		boolean isBeyondLeftLimit = calcRightMostInvaderPos(invaderList) >= ((cam.position.x + effectiveViewportWidth/2) - boundaryOffset);
		
		if (isBeyondRightLimit) {
			shouldMoveRight = true;
		} else if (isBeyondLeftLimit) {
			shouldMoveRight = false;
		}
		for (Invader invader : invaderList) {
			invader.draw(batch);
			if (time >= 0.25f) {
				if ((isBeyondRightLimit || isBeyondLeftLimit) && !invader.didJustMoveDown()) {
					invader.moveDown();
					invader.increaseSpeed();
				} else if (shouldMoveRight) {
					invader.moveRight();
				} else {
					invader.moveLeft();
				}
				
			}
		}
		if (time >= 0.25f) {
			time = 0;
		}
		
		batch.end();
	}
	
	/**
	 * Will find the Invader in the array which is at the left most position.
	 * 
	 * @param invaderList
	 * @return
	 */
	private float calcLeftMostInvaderPos(Array<Invader> invaderList) {
		boolean isFirstLoop = true;
		float leftMostPos = 0;
		for (Invader invader : invaderList) {
			if (isFirstLoop) {
				leftMostPos = invader.getX();
				isFirstLoop = false;
			}
			float currentPos = invader.getX();
			if (currentPos < leftMostPos) {
				leftMostPos = currentPos;
			}
			
		}
		return leftMostPos;
	}
	
	/**
	 * Will find the Invader in the array which is at the right most position.
	 * 
	 * @param invaderList
	 * @return
	 */
	private float calcRightMostInvaderPos(Array<Invader> invaderList) {
		boolean isFirstLoop = true;
		float rightMostPos = 0;
		for (Invader invader : invaderList) {
			if (isFirstLoop) {
				rightMostPos = invader.getX();
				isFirstLoop = false;
			}
			float currentPos = invader.getX();
			if (currentPos > rightMostPos) {
				rightMostPos = currentPos;
			}
			
		}
		return rightMostPos + invaderList.get(0).getWidth();
	}
	
	/**
	 * Creates all the invaders at their starting positions.
	 */
	private void setupInvaders() {
		invaderList = new Array<Invader>();
		
		final int xGapBetweenInvaders = 3;
		final int yGapBetweenInvaders = 3;
		int xOffset = (int) (cam.position.x - 
				(ROWS_OF_INVADERS * GameTextures.INVADER_TEXTURE.getWidth()/2) - 
				(ROWS_OF_INVADERS * xGapBetweenInvaders/2 ));
		int yOffset = (int) (cam.position.y);
		int x = xOffset;
		int y = yOffset;
		for (int i = 0; i < NUM_OF_INVADERS; i++) {
			Invader invader = new Invader(GameTextures.INVADER_TEXTURE, x, y);
			if ((i + 1) % 11 == 0) {
				y += invader.getHeight() + yGapBetweenInvaders;
				x = 0 + xOffset;
			} else {
				x += invader.getWidth() + xGapBetweenInvaders;
			}
			invaderList.add(invader);
		}
	}
	
	private void setupDefender() {
        defender = new Defender(GameTextures.DEFENDER_TEXTURE, cam.position.x, 1);
	}
	
	private void setupCamera() {
		cam = new OrthographicCamera();
		cam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.zoom += -0.5;
		cam.update();
		
		effectiveViewportWidth = cam.viewportWidth * cam.zoom;
        effectiveViewportHeight = cam.viewportHeight * cam.zoom;
	}
}
