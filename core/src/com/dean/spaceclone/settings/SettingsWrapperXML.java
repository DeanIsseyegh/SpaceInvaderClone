package com.dean.spaceclone.settings;

import javax.xml.bind.annotation.XmlRootElement;

import com.sun.xml.internal.txw2.annotation.XmlElement;

/**
 * Responsible for extracting Settings from an XML file.
 * 
 * @author Dean
 *
 */
@XmlRootElement(name = "settings")
public class SettingsWrapperXML implements ISettings {

	private float defenderBulletSpeed = 4f;
	private float defenderSpeed = 5f;
	private float invaderMovesPerSec = 0.25f;
	
	@Override
	@XmlElement
	public float getDefenderBulletSpeed() {
		return defenderBulletSpeed;
	}

	@Override
	@XmlElement
	public float getDefenderSpeed() {
		return defenderSpeed;
	}

	@Override
	@XmlElement
	public float getInvaderMovesPerSec() {
		return invaderMovesPerSec;
	}
	
	@Override
	public void setDefenderBulletSpeed(float defenderBulletSpeed) {
		this.defenderBulletSpeed = defenderBulletSpeed;
	}
	
	@Override
	public void setDefenderSpeed(float defenderSpeed) {
		this.defenderSpeed = defenderSpeed;
	}
	
	@Override
	public void setInvaderMovesPerSec(float invaderMovesPerSec) {
		this.invaderMovesPerSec = invaderMovesPerSec;
	}

}
