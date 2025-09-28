package br.com.cod3r.mw.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Board implements SquareObserver {

	private final int rows;
	private final int columns;
	private final int mines;

	private final List<Square> squares = new ArrayList<>();
	private final List<Consumer<EventResult>> observers = new ArrayList<>();

	public Board(int rows, int columns, int mines) {
		this.rows = rows;
		this.columns = columns;
		this.mines = mines;

		generateSquares();
		linkNeighborhood();
		randomizeMines();
	}

	public void forEachSquare(Consumer<Square> action) {
		squares.forEach(action);
	}

	public void addObserver(Consumer<EventResult> observer) {
		observers.add(observer);
	}

	private void notifyObservers(Boolean result) {
		observers.stream().forEach(o -> o.accept(new EventResult(result)));
	}

	public void open(int row, int column) {
		squares.parallelStream().filter(s -> s.getRow() == row && s.getColumn() == column).findFirst()
				.ifPresent(s -> s.openSquare());
	}

	public void changeMarking(int row, int column) {
		squares.parallelStream().filter(s -> s.getRow() == row && s.getColumn() == column).findFirst()
				.ifPresent(s -> s.changeMarking());
	}

	private void generateSquares() {
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < columns; c++) {
				Square square = new Square(r, c);
				square.addObserver(this);
				squares.add(square);
			}
		}
	}

	private void linkNeighborhood() {
		for (Square s1 : squares) {
			for (Square s2 : squares) {
				s1.addAdjacentSquare(s2);
			}
		}

	}

	private void randomizeMines() {
		long minesInBoard = 0;
		Predicate<Square> hasAMine = s -> s.isAMine();

		do {
			int random = (int) (Math.random() * squares.size());
			squares.get(random).markAsMine();
			minesInBoard = squares.stream().filter(hasAMine).count();
		} while (minesInBoard < mines);
	}

	public boolean goalAchieved() {
		return squares.stream().allMatch(s -> s.goalAchieved());
	}

	public void restartGame() {
		squares.stream().forEach(s -> s.restart());
		randomizeMines();
	}

	@Override
	public void onSquareEvent(Square square, SquareEvent event) {
		if (event == SquareEvent.EXPLODE) {
			revealMines();
			notifyObservers(false);
		} else if (goalAchieved()) {
			notifyObservers(true);
		}
	}

	private void revealMines() {
		squares.stream().filter(s -> s.isAMine()).filter(s -> !s.isFlagged()).forEach(s -> s.setOpen(true));
	}

	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}

}