package fr.rtsbasics.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

import fr.rtsbasics.game.entity.Building;
import fr.rtsbasics.game.entity.Unit;

public class Controller {
	private Vector2 baseMousePosition;
	private ShapeRenderer shapeRenderer;
	/**
	 *  Contain coordinate of selection rectangle: X1,Y1,X2,Y2
	 */
	private float[] rectCoord = null;
	private Vector2 lastPosClick = Vector2.Zero;

	public Controller(ShapeRenderer pShaperenderer)
	{
		this.baseMousePosition = null;
		this.shapeRenderer = pShaperenderer;
	}

	public Vector2 getBaseMousePosition() {
		return baseMousePosition;
	}

	public void setBaseMousePosition(Vector2 basePosition) {
		this.baseMousePosition = basePosition;
	}

	public float[] getRectCoord() {
		return rectCoord;
	}
	
	public void Update()
	{
		this.mouseHandler();
		this.keyboardHandler();
	}
	
	private void keyboardHandler() {
		if(Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_0))
			Building.createBuilding();
	}

	/*
	 * Handle the mouse controller (Selection/move)
	 * */
	private void mouseHandler()
	{
		if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT))
		{
			this.baseMousePosition = new Vector2(Gdx.input.getX(), Gdx.input.getY());
			if(Gamestate.selectedUnit.size() > 0)
				Gamestate.clearSelectUnitList();
		}
		if(!Gdx.input.isButtonPressed(Input.Buttons.LEFT))
		{
			this.baseMousePosition = null;
			this.rectCoord = null;
		}		
		if(this.baseMousePosition != null)
			drawSelected();
		if(Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT) && Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT))
		{
			if(Gamestate.selectedUnit.size() > 0)
			{
				for(Unit u: Gamestate.selectedUnit)
				{
					System.out.println("Add new WP (" + u.getPath().size() + ")");
					u.addWP(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
				}
			}
		} else if(Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT) && !Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT))
		{
			if(Gamestate.selectedUnit.size() > 0)
			{
				for(Unit u: Gamestate.selectedUnit)
				{
					u.deletePath();
					u.addWP(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
				}
			}
		}
	}
	
	public Vector2 getLastPosClick() {
		return lastPosClick;
	}

	/*
	 * Create the rectangle of the selection from mouse position
	 * */
	public void drawSelected()
	{
		float mouseOldX = this.baseMousePosition.x;
		float mouseOldY = Gdx.graphics.getHeight() - this.baseMousePosition.y;
		float sizeX = Gdx.input.getX() - mouseOldX;
		float sizeY = this.baseMousePosition.y - Gdx.input.getY();
		
		this.rectCoord = new float[4];
		this.rectCoord[0] = mouseOldX;
		this.rectCoord[1] = mouseOldY;
		this.rectCoord[2] = mouseOldX + sizeX;
		this.rectCoord[3] = mouseOldY + sizeY;
		
		//System.out.println("X1:" + this.rectCoord[0] + " Y1:" + this.rectCoord[1] + " X2:" + this.rectCoord[2] + " Y2:" + this.rectCoord[3]);		
		
		this.shapeRenderer.begin(ShapeType.Line);
		this.shapeRenderer.setColor(Color.GREEN);
		this.shapeRenderer.rect(mouseOldX, mouseOldY, sizeX, sizeY);		
		this.shapeRenderer.end();
	}
}
