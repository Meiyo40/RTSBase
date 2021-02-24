package fr.rtsbasics.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

import fr.rtsbasics.game.entity.Building;
import fr.rtsbasics.game.entity.Unit;
import fr.rtsbasics.game.tools.GenericTools;

import java.util.Arrays;

public class RtsBase extends ApplicationAdapter {
	SpriteBatch batch;
	ShapeRenderer shapeRenderer;
	Texture img;
	Controller controller;
	BitmapFont font;
	private int gameWindowHeight;
	private int gameWindowWidth;
	private int UiWidth;
	private int UiHeight;
	
	@Override
	public void create () {		
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		controller = new Controller(shapeRenderer);
		Gamestate.GameController = controller;
		font = new BitmapFont();

		this.UiHeight = 200;
		this.UiWidth = Gdx.graphics.getWidth();
		this.gameWindowHeight = Gdx.graphics.getHeight() - this.UiHeight;
		this.gameWindowWidth = Gdx.graphics.getWidth();
		
		for(int i = 0; i < 5; i++)
		{
			Unit unit = Unit.CreateUnit();
			Gamestate.Units.add(unit);
		}
	}

	@Override
	public void render () {
		//ScreenUtils.clear(.2f, .55f, .7f, 1);
		ScreenUtils.clear(0, 0, 0, 1);
		
		controller.Update();
		updateUnit();
		updateBuilding();
		updateUI();

		batch.begin();
		
		//batch.draw(img, 0, 0);
		renderDebug();
		
		batch.end();
	}

	private void updateUI() {
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.line(0, this.UiHeight, this.UiWidth, this.UiHeight);
		shapeRenderer.end();
	}

	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
	
	private void renderDebug()
	{
		for(Unit u: Gamestate.Units)
		{			
			if(u.getTargetPoint() != Vector2.Zero)
			{
				font.setColor(Color.WHITE);
				font.draw(batch, "S:" + u.isSelected(), u.getPosition().x, u.getPosition().y - 2);
				//font.draw(batch, "Dist: " + u.targetDist, u.getPosition().x, u.getPosition().y - 14);
				font.draw(batch, "ID: " + u.getUnitID(), u.getPosition().x, u.getPosition().y - 14);
				font.draw(batch, "V:" + u.getVelocity(), u.getPosition().x, u.getPosition().y - 26);
				
			}
		}
		
		if(Gamestate.buildings.size() > 0)
		{
			for (Building b: Gamestate.buildings)
			{
				font.setColor(Color.BLACK);
				font.draw(batch, "BUILDING", b.getPosition().x - b.getWidth() + 8, b.getPosition().y + 3);
			}
		}
	}
	
	private void updateUnit()
	{
		shapeRenderer.begin(ShapeType.Filled);
		for(Unit u: Gamestate.Units)
		{
			u.Update();
			
			if(u.isSelected())
				Gamestate.addSelectedUnit(u);

			if(Gamestate.selectedUnit.contains(u))
			{
				shapeRenderer.setColor(Color.GREEN);
			} else {
				shapeRenderer.setColor(Color.WHITE);
			}

			renderUnit(u);
		}
		shapeRenderer.end();
	}

	private void renderUnit(Unit u) {
		if(u.getTargetPoint() != Vector2.Zero)
		{
			shapeRenderer.line(u.getPosition().x + (u.getWidth()/2f), u.getPosition().y + (u.getHeight()/2f), u.getTargetPoint().x, Gdx.graphics.getHeight() - u.getTargetPoint().y);
			if(u.getPath().size() > 1)
			{
				Vector2 currentWP = u.getTargetPoint();
				for(Vector2 wp: u.getPath())
				{
					int indexCurrent = u.getPath().indexOf(currentWP);
					int indexWp = u.getPath().indexOf(wp);
					if(!currentWP.equals(wp) && indexCurrent < indexWp)
					{
						if(wp.y < this.UiHeight)
						{
							//float[] normalizedXY = GenericTools.CalculateLinePoints(currentWP.x,
							//		Gdx.graphics.getHeight() - currentWP.y, wp.x, Gdx.graphics.getHeight() - wp.y, );
							shapeRenderer.line(currentWP.x, Gdx.graphics.getHeight() - currentWP.y, wp.x, Gdx.graphics.getHeight() - wp.y);
						} else {
							shapeRenderer.line(currentWP.x, Gdx.graphics.getHeight() - currentWP.y, wp.x, Gdx.graphics.getHeight() - wp.y);
						}

						currentWP = wp;
					}
				}
			}
		}
		if(u.getPosition().y > this.UiHeight)
			shapeRenderer.rect(u.getPosition().x, u.getPosition().y, u.getWidth(), u.getHeight());
	}

	private void updateBuilding()
	{
		if(Gamestate.buildings.size() > 0)
		{
			for(Building b: Gamestate.buildings)
			{
				b.updateSelf();
				shapeRenderer.begin(ShapeType.Filled);
				shapeRenderer.setColor(Color.WHITE);
				shapeRenderer.circle(b.getPosition().x, b.getPosition().y, b.getWidth());
				
				shapeRenderer.end();
			}
		}
	}
}
