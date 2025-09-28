package br.com.cod3r.mw.view;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import br.com.cod3r.mw.model.Square;
import br.com.cod3r.mw.model.SquareEvent;
import br.com.cod3r.mw.model.SquareObserver;

@SuppressWarnings("serial")
public class SquareButton extends JButton implements SquareObserver, MouseListener {

	private final Color DEFAULT_BG = new Color(184, 184, 184);
	private final Color MARK_BG = new Color(8, 179, 247);
	private final Color EXPLODE_BG = new Color(189, 66, 68);
	private final Color GREEN_TEXT = new Color(0, 100, 0);

	private Square square;

	public SquareButton(Square square) {
		this.square = square;
		setBackground(DEFAULT_BG);
		setOpaque(true);
		setBorder(BorderFactory.createBevelBorder(0));

		addMouseListener(this);
		square.addObserver(this);
	}

	@Override
	public void onSquareEvent(Square square, SquareEvent event) {
		switch (event) {
		case OPEN:
			applyOpenStyle();
			break;
		case MARK:
			applyMarkStyle();
			break;
		case EXPLODE:
			applyExplodeStyle();
			break;
		default:
			applyDefaultStyle();
		}
	}

	private void applyDefaultStyle() {
		setBackground(DEFAULT_BG);
		setBorder(BorderFactory.createBevelBorder(0));
		setText("");
	}

	private void applyExplodeStyle() {
		setBackground(EXPLODE_BG);
		setForeground(Color.WHITE);
		setText("X");
	}

	private void applyMarkStyle() {
		setBackground(MARK_BG);
		setForeground(Color.BLACK);
		setText("M");
	}

	private void applyOpenStyle() {
		
		if(square.isAMine()) {
			setBackground(EXPLODE_BG);
			return;
		}
		setBackground(DEFAULT_BG);
		setBorder(BorderFactory.createLineBorder(Color.GRAY));

		switch (square.minesInNeighborhood()) {
		case 1:
			setForeground(GREEN_TEXT);
			break;
		case 2:
			setForeground(Color.BLUE);
			break;
		case 3:
			setForeground(Color.YELLOW);
			break;
		case 4:
			setForeground(Color.RED);
			break;
		case 5:
			setForeground(Color.RED);
			break;
		case 6:
			setForeground(Color.RED);
			break;
		default:
			setForeground(Color.PINK);
			break;
		}

		String value = !square.safeNeighborhood() ? square.minesInNeighborhood() + "" : "";

		setText(value);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == 1) {
			square.openSquare();
		} else {
			square.changeMarking();
		}

	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

}
