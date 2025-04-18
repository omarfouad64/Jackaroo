package engine.board;

import model.player.Marble;

public class Cell {
	private Marble marble;
	private CellType cellType;
	private boolean trap;
	
	public Cell(CellType cellType) {
		super();
		marble = null;
		this.cellType = cellType;
		trap = false;
	}

	public Marble getMarble() {
		return marble;
	}

	public void setMarble(Marble marble) {
		this.marble = marble;
	}

	public CellType getCellType() {
		return cellType;
	}

	public void setCellType(CellType cellType) {
		this.cellType = cellType;
	}

	public boolean isTrap() {
		return trap;
	}

	public void setTrap(boolean trap) {
		this.trap = trap;
	}
}
