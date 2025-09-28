package br.com.cod3r.mw.view;

import javax.swing.JFrame;

import br.com.cod3r.mw.model.Board;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {

	public MainWindow() {
		Board board = new Board(16, 30, 50);

		add(new BoardPanel(board));


		setTitle("Minesweeper");
		setSize(690, 438);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	public static void main(String[] args) {
		new MainWindow();
	}

}
