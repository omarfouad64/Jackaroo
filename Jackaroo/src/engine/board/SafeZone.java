package engine.board;

import java.util.ArrayList;

import model.Colour;

public class SafeZone {
	private final Colour colour;
	private final ArrayList<Cell> cells;
	
	public SafeZone(Colour colour) {
		super();
		this.colour = colour;
		this.cells = new ArrayList<>(4);
	    for (int i = 0; i < 4; i++) {
	        cells.add(new Cell(CellType.SAFE)); 
	    }
	}

	public boolean isFull() {
		for (int i = 0 ; i < cells.size(); i++) {
			if (cells.get(i).getCellType() == null) {
				return false;
			}
		}
		return true;
	}
	
	public Colour getColour() {
		return colour;
	}

	public ArrayList<Cell> getCells() {
		return cells;
	}
}
