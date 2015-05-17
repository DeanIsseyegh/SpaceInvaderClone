package com.dean.spaceclone;

import java.io.File;
import java.util.Iterator;
import java.util.Random;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.dean.spaceclone.controls.ControlsEnum;
import com.dean.spaceclone.controls.StandardControls;
import com.dean.spaceclone.gameobjects.Bullet;
import com.dean.spaceclone.gameobjects.Defender;
import com.dean.spaceclone.gameobjects.Invader;
import com.dean.spaceclone.handlers.CollisionHandler;
import com.dean.spaceclone.handlers.ICollisionHandler;
import com.dean.spaceclone.settings.ISettings;
import com.dean.spaceclone.settings.SettingsWrapperXML;

public class SpaceClone extends ApplicationAdapter {
	static final Logger logger = LoggerFactory.getLogger(SpaceClone.class);

	private SpriteBatch batch;
	private Array<Invader> invaderList;
	private Array<Bullet> invaderBullets = new Array<>();
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
	float invaderFloorBoundary;
	float floorBoundary;

	// Settings that should be configurable //
	public final static String SETTINGS_LOCATION = "resources/GameSettings.xml";
	private float defenderBulletSpeed;
	private float defenderSpeed;
	private float invaderMovesPerSec;
	private float probablityInvaderShooting;
	private float invaderBulletSpeed;
	// End Of Settings that should be configurable //

	private ICollisionHandler collisionHandler = new CollisionHandler();
	private PositionCalculator positionCalc = new PositionCalculator();
	private Random rand = new Random();

	/**
	 * This is the first method thats called. We want to setup the initial
	 * variables and game objects.
	 */
	@Override
	public void create() {
		logger.info("Game has been started.");

		batch = new SpriteBatch();

		loadSettings();
		setupControls();
		setupCamera();
		setupBoundaries();
		setupDefender();
		setupInvaders();
	}

	public void loadSettings() {
		File file = new File(SETTINGS_LOCATION);
		try {
			JAXBContext context = JAXBContext.newInstance(SettingsWrapperXML.class);
			Unmarshaller um = context.createUnmarshaller();
			ISettings settings = (ISettings) um.unmarshal(file);
			this.defenderBulletSpeed = settings.getDefenderBulletSpeed();
			this.defenderSpeed = settings.getDefenderSpeed();
			this.invaderMovesPerSec = settings.getInvaderMovesPerSec();
			this.probablityInvaderShooting = settings.getProbablityInvaderShooting();
			this.invaderBulletSpeed = settings.getInvaderBulletSpeed();
			logger.debug("Loaded settings successfully!" + settings.toString());
		} catch (JAXBException e) {
			logger.warn("Warning, problem loading the settings! Setting game settings to default.");
			logger.warn(e.toString());
			loadDefautSettings();
		} finally {
			if (defenderBulletSpeed <= 0 || defenderSpeed <= 0 || invaderMovesPerSec <= 0 || invaderBulletSpeed <= 0) {
				logger.warn("Some game settings detected as invalid.\n defenderBulletSpeed was : " + defenderBulletSpeed +
						"\ndefenderSpeed was : " + defenderSpeed +
						"\ninvaderMovesPerSec was : " + invaderMovesPerSec +
						"\nprobablityInvaderShooting was : " + probablityInvaderShooting +
						"\ninvaderBulletSpeed was : " + invaderBulletSpeed +
						"\nResetting to default values.");
				loadDefautSettings();
			}
		}
	}

	public void loadDefautSettings() {
		defenderBulletSpeed = 4f;
		defenderSpeed = 2f;
		invaderMovesPerSec = 0.25f;
		probablityInvaderShooting = 0.0005f;
		invaderBulletSpeed = -2;
		logger.debug("Default settings loaded.\ndefenderBulletSpeed : " + defenderBulletSpeed +
						"\ndefenderSpeed : " + defenderSpeed +
						"\ninvaderMovesPerSec : " + invaderMovesPerSec +
						"\nprobablityInvaderShooting : " + probablityInvaderShooting +
						"\ninvaderBulletSpeed : " + invaderBulletSpeed);
	}
	
	private void setupBoundaries() {
		boundaryOffset = effectiveViewportWidth / 10;
		leftBoundary = (cam.position.x - effectiveViewportWidth / 2) + boundaryOffset;
		rightBoundary = (cam.position.x + effectiveViewportWidth / 2) - boundaryOffset;
		ceilingBoundary = (cam.position.y + effectiveViewportHeight / 2);
		invaderFloorBoundary = (cam.position.y - effectiveViewportHeight / 5);
		floorBoundary = (cam.position.y - effectiveViewportHeight);
		logger.debug("Left boundary set to X : " + leftBoundary);
		logger.debug("Right boundary set to X : " + rightBoundary);
		logger.debug("Ceiling boundary set to Y : " + ceilingBoundary);
		logger.debug("Invader Floor boundary set to Y : " + invaderFloorBoundary);
		logger.debug("Floor boundary set to Y : " + floorBoundary);
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
		checkCollisions();
		checkGameOver();

		batch.end();
	}

	private boolean checkGameOver() {
		if (positionCalc.calcLowestSpritePos(invaderList) <= invaderFloorBoundary) {
			// TO DO: Implement Gameover Screen
			logger.debug("Invader passed lower boundary of " + positionCalc.calcLowestSpritePos(invaderList) + ". Game Over!");
			System.exit(1);
		}
		return false;
	}

	private void checkCollisions() {
		// Check for defender bullet hitting invader
		if (this.invaderList.size > 0 && this.defenderBullet != null) {
			int indexOfHitInvader = collisionHandler.indexOfSpriteThatCollided(invaderList, defenderBullet);
			if (indexOfHitInvader > 0) {
				Invader hitInvader = invaderList.get(indexOfHitInvader);
				logger.debug("Invader hit at x,y : " + hitInvader.getX() + ", " + hitInvader.getY());
				invaderList.removeIndex(indexOfHitInvader);
				defenderBullet = null;
			}
		}
		
		// Check for invader bullets hitting defender
		if (this.invaderBullets.size > 0) {
			int indexOfBulletThatHit = collisionHandler.indexOfSpriteThatCollided(invaderBullets, defender);
				if (indexOfBulletThatHit > 0) {
					// TO DO: Implement Gameover Screen
					logger.debug("Defender was shot! Game Over!");
					System.exit(1);
				}
			}
		}

	private void updateBullets() {
		if (this.defenderBullet != null) {
			defenderBullet.draw(batch);
			defenderBullet.update();
			if (defenderBullet.getY() >= ceilingBoundary) {
				defenderBullet = null;
			}
		}
		
		Iterator<Bullet> iterator = invaderBullets.iterator();
		while(iterator.hasNext()) {
			Bullet invBullet = iterator.next();
			invBullet.draw(batch);
			invBullet.update();
			if (invBullet.getY() <= floorBoundary) {
				invaderBullets.removeValue(invBullet, true);
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
		boolean isBeyondRightLimit = positionCalc.calcRightMostSpritePos(invaderList) >= rightBoundary;
		boolean isBeyondLeftLimit = positionCalc.calcLeftMostSpritePos(invaderList) <= leftBoundary;

		if (isBeyondRightLimit) {
			shouldInvadersMoveRight = false;
		} else if (isBeyondLeftLimit) {
			shouldInvadersMoveRight = true;
		}

		for (Invader invader : invaderList) {
			invader.draw(batch);
			double randomDouble = rand.nextDouble();
			if (randomDouble <= probablityInvaderShooting) {
				Bullet invBullet = invader.shoot(GameTextures.INVADER_BULLET_TEXTURE, invaderBulletSpeed);
				invaderBullets.add(invBullet);
			}
			if (timeSinceInvadersMoved >= invaderMovesPerSec) {
				if ((isBeyondRightLimit || isBeyondLeftLimit) && !invader.didJustMoveDown()) {
					invader.moveDown();
				} else if (shouldInvadersMoveRight) {
					invader.moveRight();
				} else {
					invader.moveLeft();
				}
			}
		}

		// Per tick
		if (timeSinceInvadersMoved >= invaderMovesPerSec) {
			timeSinceInvadersMoved = 0;
			if (invaderList.get(0).didJustMoveDown()) {
				invaderMovesPerSec += 0 - invaderMovesPerSec/12;
				logger.debug("Invaders just moved down. Increasing speed per sec from " + (invaderMovesPerSec + invaderMovesPerSec/12) + " to " + invaderMovesPerSec
						+ " per second.");
			}
		}
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
		float startXPos = cam.position.x - GameTextures.DEFENDER_TEXTURE.getWidth() / 2;
		float startYPos = cam.position.y - effectiveViewportHeight / 2 + GameTextures.DEFENDER_TEXTURE.getHeight() / 2;
		defender = new Defender(GameTextures.DEFENDER_TEXTURE, startXPos, startYPos, defenderSpeed);
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
