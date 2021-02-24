package fr.rtsbasics.game;

import java.util.ArrayList;
import java.util.List;

import fr.rtsbasics.game.entity.Building;
import fr.rtsbasics.game.entity.Unit;

public class Gamestate {
	public static List<Unit> selectedUnit = new ArrayList<Unit>();
	public static List<Unit> Units = new ArrayList<Unit>();
	public static List<Building> buildings = new ArrayList<Building>();
	public static Controller GameController = null;
	public static void addSelectedUnit(Unit u) {
		if(selectedUnit.size() == 0)
		{
			selectedUnit.add(u);
		} else {
			boolean exist = false;
			for(Unit e: selectedUnit)
			{
				if(e.equals(u))
					exist = true;
			}
			if(!exist)
				selectedUnit.add(u);
		}
	}
	public static void clearSelectUnitList()
	{
		selectedUnit.clear();
	}
}
