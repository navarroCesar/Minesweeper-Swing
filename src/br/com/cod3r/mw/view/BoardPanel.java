package br.com.cod3r.mw.view;

import java.awt.GridLayout;

import javax.swing.JPanel;

import br.com.cod3r.mw.model.Board;

@SuppressWarnings("serial")
public class BoardPanel extends JPanel {

	public BoardPanel(Board board) {
		setLayout(new GridLayout(board.getRows(), board.getColumns()));

		board.forEachSquare(s -> add(new SquareButton(s)));

		board.addObserver(e -> {
			// TODO show result to user

			board.restartGame();
		});
	}

}
