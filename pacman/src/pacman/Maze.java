package pacman;

import java.util.Arrays;
import java.util.Random;

public class Maze {
	
	private Random random;
	private MazeMap map;
	private PacMan pacMan;
	private Ghost[] ghosts;
	private FoodItem[] fooditems;
	
	public MazeMap getMap() { return map; }
	
	public PacMan getPacMan() { return pacMan; }
	
	public Ghost[] getGhosts() { return ghosts.clone(); }
	
	public FoodItem[] getFoodItems() { return fooditems.clone(); }
	
	public Maze(Random random, MazeMap map, PacMan pacMan, Ghost[] ghosts, FoodItem[] fooditems) {
		this.random = random;
		this.map = map;
		this.pacMan = pacMan;
		this.ghosts = ghosts.clone();
		this.fooditems = fooditems.clone();
	}
	public void setGhosts(Ghost[] geesten) {ghosts = geesten.clone();}
	
	
	public boolean isCompleted() {
		return fooditems.length == 0;
	}
	
	private void checkPacManDamage() {
		for (Ghost ghost : ghosts)
			if (ghost.getSquare().equals(pacMan.getSquare()))
				ghost.hitBy(pacMan);
	}
	
	public void moveGhosts() {
		for (Ghost ghost : ghosts)
			ghost.move(random);
		checkPacManDamage();
	}
	
	private void removeFoodItemAtIndex(int index) {
		FoodItem[] newFoodItems = new FoodItem[fooditems.length - 1];
		System.arraycopy(fooditems, 0, newFoodItems, 0, index);
		System.arraycopy(fooditems, index + 1, newFoodItems, index, newFoodItems.length - index);
		fooditems = newFoodItems;
	}
	/*
	 * beide getters hier toegevoegd
	 */
	private FoodItem getFoodItemAtIndex(int index) {
		return fooditems[index];
	}
	
	private FoodItem getFoodItemAtSquare(Square square) {
		for (int i =0; i< fooditems.length;i++) {
			if (fooditems[i].getSquare().equals(square)){
				return getFoodItemAtIndex(i);
			}
		}
		return null;
	}
	
	private void removeFoodItemAtSquare(Square square) {
		for (int i = 0; i < fooditems.length; i++) {
			if (fooditems[i].getSquare().equals(square)) {
				removeFoodItemAtIndex(i);
				return;
			}
		}
	}
	/**
	 * tweede if statement toegevoegd dus daar is miss iets fout, deze != null miss nog aanpassa want is niet deftig voor 6/6
	 * @param direction
	 */
	public void movePacMan(Direction direction) {
		Square newSquare = pacMan.getSquare().getNeighbor(direction);
		if (newSquare.isPassable()) {
			pacMan.setSquare(newSquare);
			for (int i = 0; i < fooditems.length; i++) {
				if (fooditems[i].getSquare().equals(newSquare)){
					fooditems[i].eatenbypacman(this);
				}
			}
			
			/*
			if  ((getFoodItemAtSquare(newSquare) != null) && (getFoodItemAtSquare(newSquare).isPowerPellet()) ){
				for (Ghost g : ghosts) {
					g.pacManAtePowerPellet();
					
				}
			}*/
			removeFoodItemAtSquare(newSquare);
			checkPacManDamage();
		}
	}
	
}
