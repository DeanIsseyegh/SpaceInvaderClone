package com.dean.spaceclone.controls;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

public class StandardControls implements InputProcessor {
	private static final Logger logger = LoggerFactory.getLogger(StandardControls.class);
	
	private boolean isLeftKeyPressed = false;
	private boolean isRightKeyPressed = false;
	private boolean isSpaceBarPressed;
	
	@Override
	public boolean keyDown(int keycode) {
		switch(keycode) {
		case Input.Keys.SPACE:
			isSpaceBarPressed = true;
			return true;
		case Input.Keys.LEFT:
			isLeftKeyPressed = true;
			logger.debug("Left key press detected");
			return true;
		case Input.Keys.RIGHT:
			isRightKeyPressed = true;
			logger.debug("Right key press detected");
			return true;
		default:
			logger.debug("Unhandled key press detected");
			break;
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		switch(keycode) {
		case Input.Keys.SPACE:
			isSpaceBarPressed = false;
			return true;
		case Input.Keys.LEFT:
			isLeftKeyPressed = false;
			logger.debug("Left key no longer being pressed");
			return true;
		case Input.Keys.RIGHT:
			isRightKeyPressed = false;
			logger.debug("Right key no longer being pressed");
			return true;
		default:
			logger.debug("Unhandled key no longer being pressed");
			break;
		}
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public boolean isLeftKeyPressed() {
		return isLeftKeyPressed;
	}

	public boolean isRightKeyPressed() {
		return isRightKeyPressed;
	}

	public boolean isSpaceBarPressed() {
		return isSpaceBarPressed;
	}
}
