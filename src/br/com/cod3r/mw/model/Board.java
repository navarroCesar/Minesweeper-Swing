package br.com.cod3r.mw.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import br.com.cod3r.mw.exception.ExplosionException;

public class Board {

	private int rows;
	private int columns;
	private int mines;

	private final List<Square> squares = new ArrayList<>();

	public Board(int rows, int columns, int mines) {
		this.rows = rows;
		this.columns = columns;
		this.mines = mines;

		generateSquares();
		linkNeighborhood();
		randomizeMines();
	}

	public void open(int row, int column) {
		try {
			squares.parallelStream().filter(s -> s.getRow() == row && s.getColumn() == column).findFirst()
					.ifPresent(s -> s.openSquare());
		} catch (ExplosionException e) {
			squares.forEach(s -> s.setOpen(true));

			throw e;
		}
	}

	public void changeMarking(int row, int column) {
		squares.parallelStream().filter(s -> s.getRow() == row && s.getColumn() == column).findFirst()
				.ifPresent(s -> s.changeMarking());
	}

	private void generateSquares() {
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < columns; c++) {
				squares.add(new Square(r, c));
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

	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("  ");
		for (int c = 0; c < columns; c++) {
			sb.append(" ");
			sb.append(c);
			sb.append(" ");
		}

		sb.append("\n");

		int i = 0;
		for (int r = 0; r < rows; r++) {
			sb.append(r);
			sb.append(" ");
			for (int c = 0; c < columns; c++) {
				sb.append(" ");
				sb.append(squares.get(i));
				sb.append(" ");
				i++;
			}
			sb.append("\n");
		}

		return sb.toString();

	}
}