package br.com.cod3r.mw.view;

import br.com.cod3r.mw.model.Board;

public class Temp {

	public static void main(String[] args) {

		Board board = new Board(3, 3, 9);

		board.addObserver(e -> {
			if (e.isVictory()) {
				System.out.println("Congratulations! You cleared the board.");
			} else {
				System.out.println("Game Over!");
			}
		});

		board.changeMarking(0, 0);
		board.changeMarking(0, 1);
		board.changeMarking(0, 2);
		board.changeMarking(1, 0);
		board.changeMarking(1, 1);
		board.changeMarking(1, 2);
		board.changeMarking(2, 0);
		board.changeMarking(2, 1);
		board.open(2, 2);
		// board.changeMarking(2, 2);

	}

}
