package _04_Maze_Maker;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;


public class MazeMaker{
	
	private static int width;
	private static int height;
	
	private static Maze maze;
	
	private static Random randGen = new Random();
	private static Stack<Cell> uncheckedCells = new Stack<Cell>();
	
	
	public static Maze generateMaze(int w, int h){
		width = w;
		height = h;
		maze = new Maze(width, height);
		
		//4. select a random cell to start
		Random rand = new Random();
		
		int r = rand.nextInt(w);
		int c = rand.nextInt(h);	
		Cell cell = maze.getCell(r,c);
		//5. call selectNextPath method with the randomly selected cell
		selectNextPath(cell);
		
		return maze;
	}

	//6. Complete the selectNextPathMethod
	private static void selectNextPath(Cell currentCell) {
		//A. mark cell as visited
		currentCell.setBeenVisited(true);
		//B. Get an ArrayList of unvisited neighbors using the current cell and the method below
		
		//C. if has unvisited neighbors,
		if (getUnvisitedNeighbors(currentCell).size() > 0) {
			//C1. select one at random.
			Random rand = new Random();
			int n = rand.nextInt(getUnvisitedNeighbors(currentCell).size());
			Cell neighbor = getUnvisitedNeighbors(currentCell).get(n);
			//C2. push it to the stack
			uncheckedCells.push(neighbor);
			//C3. remove the wall between the two cells
			removeWalls(neighbor, currentCell);
			//C4. make the new cell the current cell and mark it as visited
			currentCell = neighbor;
			currentCell.setBeenVisited(true);
			//C5. call the selectNextPath method with the current cell
			selectNextPath(currentCell);
		}
			
		//D. if all neighbors are visited
		if (getUnvisitedNeighbors(currentCell).size() == 0) {

			//D1. if the stack is not empty
			if (uncheckedCells.size() != 0) {
				// D1a. pop a cell from the stack
				Cell popped = uncheckedCells.pop();
				// D1b. make that the current cell
				currentCell = popped;
				// D1c. call the selectNextPath method with the current cell
				selectNextPath(currentCell);
			}
		}	
		
	}

	//7. Complete the remove walls method.
	//   This method will check if c1 and c2 are adjacent.
	//   If they are, the walls between them are removed.
	private static void removeWalls(Cell c1, Cell c2) {
		if (c1.getX() == c2.getX()) {
			if (c1.getY() > c2.getY()) {
				c1.setNorthWall(false);
				c2.setSouthWall(false);

			}
			if (c1.getY() < c2.getY()) {
				c1.setSouthWall(false);
				c2.setNorthWall(false);
			}
		}
		if (c1.getY() == c2.getY()) {
			if (c1.getX() < c2.getX()) {
				c1.setEastWall(false);
				c2.setWestWall(false);

			}
			if (c1.getX() > c2.getX()) {
				c1.setWestWall(false);
				c2.setEastWall(false);
			}
		}

	}
	
	//8. Complete the getUnvisitedNeighbors method
	//   Any unvisited neighbor of the passed in cell gets added
	//   to the ArrayList
	private static ArrayList<Cell> getUnvisitedNeighbors(Cell c) {
		ArrayList<Cell> UncheckedCells = new ArrayList<Cell>();

		if (inBounds(c.getX(), c.getY())) {

			if (inBounds(c.getX() - 1, c.getY())) {
				Cell c3 = maze.getCell(c.getX() - 1, c.getY());
				if (!(c3.hasBeenVisited())) {
					UncheckedCells.add(c3);
				}
			}
			if (inBounds(c.getX() + 1, c.getY())) {
				Cell c2 = maze.getCell(c.getX() + 1, c.getY());
				if (!(c2.hasBeenVisited())) {
					UncheckedCells.add(c2);
				}
			}

			if (inBounds(c.getX(), c.getY() + 1)) {
				Cell c4 = maze.getCell(c.getX(), c.getY() + 1);
				if (!(c4.hasBeenVisited())) {
					UncheckedCells.add(c4);
				}
			}
			if (inBounds(c.getX(), c.getY() - 1)) {
				Cell c5 = maze.getCell(c.getX(), c.getY() - 1);
				if (!(c5.hasBeenVisited())) {
					UncheckedCells.add(c5);
				}
			}
		}

		System.out.println(UncheckedCells.size());
		return UncheckedCells;
	}

	private static boolean inBounds(int x, int y) {
		return (x >= 0 && x < width && y >= 0 && y < height);

	
	}
}
