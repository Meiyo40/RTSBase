package fr.rtsbasics.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import fr.rtsbasics.game.Gamestate;

public class Building extends Entity{
	
	@SuppressWarnings("unused")
	private static int buildingCount = 0;

	public Building(Vector2 pPos, String pName, int pWidth, int pHeight) {
		super(pPos, pName, pWidth, pHeight);
		Building.buildingCount++;
		this.setMovable(false);
		this.setMAX_HEALTH(1000);
		this.setCurrentHealth(this.getMAX_HEALTH());
	}
	
	public static void createBuilding()
	{
		Vector2 buildingPosition = new Vector2(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
		Building newBuilding = new Building(buildingPosition, "RandomBuilding", 40, 40);
		Gamestate.buildings.add(newBuilding);
	}
	
	public void updateSelf()
	{
		
	}
}
