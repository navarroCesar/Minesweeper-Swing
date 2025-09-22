package br.com.cod3r.mw;

import br.com.cod3r.mw.model.Board;
import br.com.cod3r.mw.view.ConsoleBoard;

public class Program {

	public static void main(String[] args) {

		Board board = new Board(6, 6, 6);
		new ConsoleBoard(board);

	}

}
