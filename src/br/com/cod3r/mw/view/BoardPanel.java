package br.com.cod3r.mw.view;

import java.awt.GridLayout;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import br.com.cod3r.mw.model.Board;

@SuppressWarnings("serial")
public class BoardPanel extends JPanel {

	public BoardPanel(Board board) {
		setLayout(new GridLayout(board.getRows(), board.getColumns()));

		board.forEachSquare(s -> add(new SquareButton(s)));

		board.addObserver(e -> {
			SwingUtilities.invokeLater(() -> {
				if (e.isVictory()) {
					JOptionPane.showMessageDialog(this, "Congratulations! You cleared the board.");
				} else {
					JOptionPane.showMessageDialog(this, "Game Over.");
				}
				board.restartGame();
			});

		});
	}

}
