package com.dean.spaceclone.settings;

/**
 * Interface for configurable settings. We can abstract the settings so we can switch between XML, Database, etc. type of settings.
 * 
 * @author Dean
 */
public interface IGameSettings {
	
	float getDefenderBulletSpeed();
	void setDefenderBulletSpeed(float speed);
		
	float getDefenderSpeed();
	void setDefenderSpeed(float speed);
	
	float getInvaderMovesPerSec();
	void setInvaderMovesPerSec(float speed);
	
	float getProbablityInvaderShooting();
	void setProbablityInvaderShooting(float prob);
	
	float getInvaderBulletSpeed();
	void setInvaderBulletSpeed(float speed);
}
