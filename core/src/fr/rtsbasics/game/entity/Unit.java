package fr.rtsbasics.game.entity;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

import fr.rtsbasics.game.Gamestate;
import fr.rtsbasics.game.tools.GenericTools;

public class Unit extends Entity {
	
	private static int unitCount = 0;
	private int UnitID;
	private Vector2 targetPoints;
	private boolean isSelected;
	private Color UnitColor;
	private List<Vector2> path;
	private int currentWP = 0;
	private float velocity;
	public float targetDist;
	private float oldDist = 0f;
	private long time;

	public float getVelocity() {
		return velocity;
	}

	public void setVelocity(float velocity) {
		this.velocity = velocity;
	}

	public Unit(Vector2 pPos, String pName) {
		super(pPos, pName, 20, 20);
		Unit.unitCount++;
		this.setMovable(true);
		this.UnitID = Unit.unitCount;
		this.targetPoints = Vector2.Zero;
		this.setPath(new ArrayList<Vector2>());
		this.UnitColor = Color.WHITE;
		this.velocity = GenericTools.RandomNb(1, 3);
		this.setMAX_HEALTH(100);
		this.setCurrentHealth(this.getMAX_HEALTH());
	}
	
	public void Update()
	{
		this.unitIsSelected();
		this.executePath();
	}
	
	
	/*
	 * Move unit if waypoint exist
	 * */
	private void moveUnit()
	{
		if(this.getTargetPoint() != Vector2.Zero)
		{
			float reversedTY = Gdx.graphics.getHeight() - this.getTargetPoint().y;
			targetDist= this.getDistance(this.getPosition().x, this.getPosition().y, this.getTargetPoint().x, reversedTY);
			if(targetDist <= this.velocity)
			{
				this.setTargetPoint(Vector2.Zero);
			} else {				
				if(this.getPosition().x > this.getTargetPoint().x)
				{
					this.setPosition(new Vector2(this.getPosition().x - this.velocity, this.getPosition().y));
				}
				if(this.getPosition().y > reversedTY)
				{
					this.setPosition(new Vector2(this.getPosition().x, this.getPosition().y - this.velocity));
				}
				if(this.getPosition().x < this.getTargetPoint().x)
				{
					this.setPosition(new Vector2(this.getPosition().x + this.velocity, this.getPosition().y));
				}
				if(this.getPosition().y < reversedTY)
				{
					this.setPosition(new Vector2(this.getPosition().x, this.getPosition().y + this.velocity));
				}
			}
		}		
	}
	
	
	/*
	 * Select current waypoint if exist and execute the moveUnit method
	 * */
	private void executePath()
	{
		long elapsedTime = System.currentTimeMillis() - this.time;	
		
		if(this.path.size() > 0)
		{			
			for(int i = this.currentWP; i < this.path.size(); i++)
			{				
				Vector2 wp = this.path.get(i);
				if(wp != Vector2.Zero)
				{
					this.setTargetPoint(wp);
					moveUnit();
					this.time = System.currentTimeMillis();
					if(targetDist > this.velocity + 1f)
					{
						break;
					} else {
						this.currentWP++;
						if(this.currentWP == this.path.size())
						{
							this.path.clear();
							this.currentWP = 0;
							break;
						}
						wp = Vector2.Zero;
					}
				}
			}
		}
		if(this.oldDist == this.targetDist && elapsedTime > 1000 && this.path.size() > 0)
		{
			this.path.clear();
		}
		this.oldDist = this.targetDist;		
	}
	
	/*
	 * Reset the path content
	 * */
	public void deletePath()
	{
		this.path.clear();
		this.oldDist = 0f;
		this.targetDist = 0f;
		this.time = System.currentTimeMillis();
		this.currentWP = 0;
	}
	
	/*
	 * Simple collider to select the unit with the cursor
	 * */
	private void unitIsSelected()
	{
		if(Gamestate.GameController.getRectCoord() != null)
		{
			float unitX = this.getPosition().x;
			float unitY = this.getPosition().y;
			float selectX1 = Gamestate.GameController.getRectCoord()[0];
			float selectY1 = Gamestate.GameController.getRectCoord()[1];
			float selectX2 = Gamestate.GameController.getRectCoord()[2];
			float selectY2 = Gamestate.GameController.getRectCoord()[3];
			
			float minX1;
			float minY1;
			if(selectX1 > selectX2)
			{
				minX1 = selectX2;
				selectX2 = selectX1;
				selectX1 = minX1;
			}
			if(selectY1 > selectY2)
			{
				minY1 = selectY2;
				selectY2 = selectY1;
				selectY1 = minY1;
			}
				
			if((unitX >= selectX1 && unitX <= selectX2) &&
				(unitY >= selectY1 && unitY <= selectY2))
			{
				this.isSelected = true;
				this.UnitColor = Color.GREEN;
			} else {
				this.isSelected = false;
				this.UnitColor = Color.WHITE;
			}
		}
	}
	
	/**
	 * 
	 * @return the current unitColor
	 */
	public Color getUnitColor() {
		return UnitColor;
	}

	public static Unit CreateUnit()
	{
		float x = GenericTools.RandomNb(0 ,Gdx.graphics.getWidth());
		float y = GenericTools.RandomNb(0, Gdx.graphics.getHeight());
		return new Unit(new Vector2(x, y), "Unit");	
	}

	/**
	 * @return the unitID
	 */
	public int getUnitID() {
		return UnitID;
	}

	/**
	 * @return the targetPoints
	 */
	public Vector2 getTargetPoint() {
		return targetPoints;
	}

	/**
	 * @param targetPoints the targetPoints to set
	 */
	public void setTargetPoint(Vector2 targetPoints) {
		this.targetPoints = targetPoints;
	}

	/**
	 * @return the isSelected
	 */
	public boolean isSelected() {
		return isSelected;
	}

	/**
	 * @param isSelected the isSelected to set
	 */
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	/**
	 * @return the path
	 */
	public List<Vector2> getPath() {
		return path;
	}

	/**
	 * @param path the path to set
	 */
	public void setPath(List<Vector2> path) {
		this.path = path;
	}
	
	/**
	 * @param path the path to add
	 */
	public void addWP(Vector2 wp) {
		this.path.add(wp);
	}
}
