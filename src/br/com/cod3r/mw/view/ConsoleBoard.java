package br.com.cod3r.mw.view;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

import br.com.cod3r.mw.exception.ExplosionException;
import br.com.cod3r.mw.exception.QuitGameException;
import br.com.cod3r.mw.model.Board;

public class ConsoleBoard {

	private Board board;
	private Scanner sc = new Scanner(System.in);

	public ConsoleBoard(Board board) {
		this.board = board;

		runGame();
	}

	private void runGame() {
		try {
			boolean continueGame = true;

			while (continueGame) {
				gameCycle();

				System.out.println("Do you want to play again? (S/n) ");
				String answer = sc.nextLine();

				if ("n".equalsIgnoreCase(answer)) {
					continueGame = false;
				} else {
					board.restartGame();
				}
			}

		} catch (QuitGameException e) {
			System.out.println("You quit the game.");
		} finally {
			sc.close();
		}

	}

	private void gameCycle() {
		try {

			while (!board.goalAchieved()) {
				System.out.println(board);

				String value = captureEnteredValue("Insert the position (row,col): ");

				Iterator<Integer> xy = Arrays.stream(value.split(",")).map(e -> Integer.parseInt(e.trim())).iterator();

				value = captureEnteredValue("[1] Open square   [2] Mark/Unmark square ");

				if ("1".equals(value)) {
					board.open(xy.next(), xy.next());
				} else if ("2".equals(value)) {
					board.changeMarking(xy.next(), xy.next());
				}
			}

			System.out.println("Congratulations! You cleared the board.");
		} catch (ExplosionException e) {
			System.out.println(board);
			System.out.println("Game Over!!");
		}

	}

	private String captureEnteredValue(String text) {
		System.out.print(text);
		String value = sc.nextLine();

		if ("quit".equalsIgnoreCase(value)) {
			throw new QuitGameException();
		}
		return value;
	}
}