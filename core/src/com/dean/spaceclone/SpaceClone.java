package com.dean.spaceclone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.dean.spaceclone.controls.ControlsEnum;
import com.dean.spaceclone.controls.StandardControls;
import com.dean.spaceclone.gameobjects.Bullet;
import com.dean.spaceclone.gameobjects.Defender;
import com.dean.spaceclone.gameobjects.Invader;

public class SpaceClone extends ApplicationAdapter {
	static final Logger logger = LoggerFactory.getLogger(SpaceClone.class);

	private SpriteBatch batch;
	private Array<Invader> invaderList;
	private Defender defender;
	private Bullet defenderBullet;
	private OrthographicCamera cam;

	public final static int NUM_OF_INVADERS = 55;
	public final static int ROWS_OF_INVADERS = 11;
	float effectiveViewportWidth;
	float effectiveViewportHeight;
	// We don't want gameobjects going right to the edge of the screen.
	float boundaryOffset;
	float leftBoundary;
	float rightBoundary;
	float ceilingBoundary;

	// Settings that should be configurable
	private float defenderBulletSpeed = 2f;

	/**
	 * This is the first method thats called. We want to setup the initial
	 * variables and game objects.
	 */
	@Override
	public void create() {
		logger.info("Game has been started.");

		batch = new SpriteBatch();

		setupControls();

		setupCamera();

		setupBoundaries();

		setupDefender();

		setupInvaders();
	}

	private void setupBoundaries() {
		boundaryOffset = effectiveViewportWidth / 10;
		leftBoundary = (cam.position.x - effectiveViewportWidth / 2) + boundaryOffset;
		rightBoundary = (cam.position.x + effectiveViewportWidth / 2) - boundaryOffset;
		ceilingBoundary = (cam.position.y + effectiveViewportHeight / 2);
		logger.debug("Left boundary set to X : " + leftBoundary);
		logger.debug("Right boundary set to X : " + rightBoundary);
	}

	@Override
	public void render() {
		float deltaTime = Gdx.graphics.getDeltaTime();
		timeSinceInvadersMoved += deltaTime;

		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		cam.update();
		batch.setProjectionMatrix(cam.combined);

		batch.begin();

		updateInvaders();
		updateDefender();
		updateBullets();

		batch.end();
	}

	private void updateBullets() {
		if (this.defenderBullet != null) {
			defenderBullet.draw(batch);
			defenderBullet.update();
			if (defenderBullet.getY() >= ceilingBoundary) {
				defenderBullet = null;
			}
		}
	}

	private void updateDefender() {
		// Use boolean flags to abstract controls from wanted defender behaviour
		boolean shouldMoveLeft = false;
		boolean shouldMoveRight = false;
		boolean shouldShoot = false;

		// Handle controls
		if (Gdx.input.getInputProcessor() instanceof InputMultiplexer) {
			InputMultiplexer inputMultiplexer = (InputMultiplexer) Gdx.input.getInputProcessor();
			Array<InputProcessor> inputProcessors = inputMultiplexer.getProcessors();
			for (InputProcessor controls : inputProcessors) {
				if (controls instanceof StandardControls) {
					StandardControls input = (StandardControls) controls;
					if (input.isLeftKeyPressed()) {
						shouldMoveLeft = true;
					} else if (input.isRightKeyPressed()) {
						shouldMoveRight = true;
					}

					if (input.isSpaceBarPressed()) {
						shouldShoot = true;
					}
				}
			}
		}

		if (shouldMoveLeft && defender.getX() >= leftBoundary) {
			defender.moveLeft();
			logger.debug("Defender trying to move left");
		} else if (shouldMoveRight && (defender.getX() + defender.getWidth()) <= rightBoundary) {
			defender.moveRight();
			logger.debug("Defender trying to move right");
		}

		// Should only shoot if bullet is null
		if (shouldShoot && this.defenderBullet == null) {
			defenderBullet = defender.shoot(GameTextures.DEFENDER_BULLET_TEXTURE, defenderBulletSpeed);
			logger.debug("Defender trying to shoot");
		}

		defender.draw(batch);
	}

	private float timeSinceInvadersMoved;
	private boolean shouldInvadersMoveRight = true;

	private void updateInvaders() {
		boolean isBeyondRightLimit = calcRightMostInvaderPos(invaderList) >= rightBoundary;
		boolean isBeyondLeftLimit = calcLeftMostInvaderPos(invaderList) <= leftBoundary;

		if (isBeyondRightLimit) {
			shouldInvadersMoveRight = false;
		} else if (isBeyondLeftLimit) {
			shouldInvadersMoveRight = true;
		}

		for (Invader invader : invaderList) {
			invader.draw(batch);
			if (timeSinceInvadersMoved >= 0.25f) {
				if ((isBeyondRightLimit || isBeyondLeftLimit) && !invader.didJustMoveDown()) {
					invader.moveDown();
					invader.increaseSpeed();
				} else if (shouldInvadersMoveRight) {
					invader.moveRight();
				} else {
					invader.moveLeft();
				}

			}
		}
		if (timeSinceInvadersMoved >= 0.25f) {
			timeSinceInvadersMoved = 0;
		}
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
		int xOffset = (int) (cam.position.x - (ROWS_OF_INVADERS * GameTextures.INVADER_TEXTURE.getWidth() / 2) - (ROWS_OF_INVADERS
				* xGapBetweenInvaders / 2));
		int yOffset = (int) (cam.position.y);
		int x = xOffset;
		int y = yOffset;
		for (int i = 0; i < NUM_OF_INVADERS; i++) {
			Invader invader = new Invader(GameTextures.INVADER_TEXTURE, x, y);
			logger.debug("Invader created at x,y : " + x + "," + y);
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
		defender = new Defender(GameTextures.DEFENDER_TEXTURE, cam.position.x - GameTextures.DEFENDER_TEXTURE.getWidth() / 2, cam.position.y
				- effectiveViewportHeight / 2 + GameTextures.DEFENDER_TEXTURE.getHeight() / 2);
		logger.debug("Defender created at x,y : " + defender.getX() + "," + defender.getY());
	}

	private void setupCamera() {
		cam = new OrthographicCamera();
		cam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.zoom += -0.5;
		cam.update();

		effectiveViewportWidth = cam.viewportWidth * cam.zoom;
		effectiveViewportHeight = cam.viewportHeight * cam.zoom;

		logger.debug("Effective viewport width x height set to : " + effectiveViewportWidth + " x " + effectiveViewportHeight);
	}

	public ControlsEnum[] controls = new ControlsEnum[] { ControlsEnum.STANDARD };

	/**
	 * Note the use of the InputMultiplexer to support the potential for
	 * multiple types of controls.
	 */
	private void setupControls() {
		InputMultiplexer inputMultiplexer = new InputMultiplexer();
		for (ControlsEnum control : controls) {
			switch (control) {
			case STANDARD:
				inputMultiplexer.addProcessor(new StandardControls());
			}
		}
		Gdx.input.setInputProcessor(inputMultiplexer);
	}

}
