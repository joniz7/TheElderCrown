package model.villager.intentions_Reloaded;

import org.newdawn.slick.util.pathfinding.Path;

import model.TestWorld;
import model.villager.Villager;

public class MoveAction extends Action{

	private Path path;
	
	public MoveAction(Villager villager, Path path) {
		super(villager);
		this.path = path;
	}

	@Override
	public void tick(TestWorld world) {
		
	}

}
