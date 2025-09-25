package br.com.cod3r.mw.model;

import java.util.ArrayList;
import java.util.List;

public class Square {

	private final int row;
	private final int column;

	private boolean open = false;
	private boolean mine = false;
	private boolean flagged = false;

	private List<Square> adjacentSquares = new ArrayList<>();
	private List<SquareObserver> observers = new ArrayList<>();

	Square(int row, int column) {
		this.row = row;
		this.column = column;
	}

	public void addObserver(SquareObserver observer) {
		observers.add(observer);
	}

	private void notifyObservers(SquareEvent event) {
		observers.stream().forEach(o -> o.onSquareEvent(this, event));
	}

	boolean addAdjacentSquare(Square adjacent) {
		boolean differentRow = row != adjacent.row;
		boolean differentColumn = column != adjacent.column;
		boolean diagonal = differentRow && differentColumn;

		int deltaRow = Math.abs(row - adjacent.row);
		int deltaColumn = Math.abs(column - adjacent.column);
		int finalDelta = deltaRow + deltaColumn;

		if (finalDelta == 1 && !diagonal) {
			adjacentSquares.add(adjacent);
			return true;
		} else if (finalDelta == 2 && diagonal) {
			adjacentSquares.add(adjacent);
			return true;
		} else {
			return false;
		}

	}

	void changeMarking() {
		if (!open) {
			flagged = !flagged;

			if (flagged) {
				notifyObservers(SquareEvent.MARK);
			} else {
				notifyObservers(SquareEvent.UNMARK);
			}
		}
	}

	boolean openSquare() {

		if (!open && !flagged) {
			if (mine) {
				notifyObservers(SquareEvent.EXPLODE);
				return true;
			}
			setOpen(true);

			if (safeNeighborhood()) {
				adjacentSquares.forEach(a -> a.openSquare());
			}

			return true;
		}

		return false;
	}

	boolean safeNeighborhood() {
		return adjacentSquares.stream().noneMatch(a -> a.mine);
	}

	void markAsMine() {
		if (!mine) {
			mine = true;
		}
	}

	boolean goalAchieved() {
		boolean unraveled = !mine && open;
		boolean protectedd = mine && flagged;
		return unraveled || protectedd;
	}

	long minesInNeighborhood() {
		return adjacentSquares.stream().filter(a -> a.mine).count();
	}

	void restart() {
		open = false;
		mine = false;
		flagged = false;
	}

	public boolean isAMine() {
		return mine;
	}

	public boolean isFlagged() {
		return flagged;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
		if (open) {
			notifyObservers(SquareEvent.OPEN);
		}
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

}
