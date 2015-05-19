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
public class GameSettingsWrapperXML implements IGameSettings {

	private float defenderBulletSpeed;
	private float defenderSpeed;
	private float invaderMovesPerSec;
	private float probablityInvaderShooting;
	private float invaderBulletSpeed;

	@Override
	@XmlElement
	public float getInvaderBulletSpeed() {
		return invaderBulletSpeed;
	}

	@Override
	@XmlElement
	public float getProbablityInvaderShooting() {
		return probablityInvaderShooting;
	}

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
	
	@Override
	public void setProbablityInvaderShooting(float probablityInvaderShooting) {
		this.probablityInvaderShooting = probablityInvaderShooting;
	}

	@Override
	public void setInvaderBulletSpeed(float invaderBulletSpeed) {
		this.invaderBulletSpeed = invaderBulletSpeed;
	}
	
	@Override
	public String toString() {
		String defenderBulletSpeedStr = "\n\nDefender Bullet Speed : " + defenderBulletSpeed + "\n";
		String defenderSpeedStr = "Defender Speed : " + defenderSpeed + "\n";
		String invaderMovesPerSecStr = "Invader Moves Per Sec : " + invaderMovesPerSec + "\n";
		String probablityInvaderShootingStr = "Probability of Invader Shooting : " + probablityInvaderShooting +"\n";
		String invaderBullSpeedStr = "Invader Bullet Speed : " + invaderBulletSpeed +"\n";
		String str = defenderBulletSpeedStr + defenderSpeedStr + invaderMovesPerSecStr + probablityInvaderShootingStr + invaderBullSpeedStr;
		return str;
	}
}
