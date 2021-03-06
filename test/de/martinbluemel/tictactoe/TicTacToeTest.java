package de.martinbluemel.tictactoe;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.awt.Dimension;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.martinbluemel.tictactoe.domain.Move;
import de.martinbluemel.tictactoe.domain.TicTacToe;
import de.martinbluemel.tictactoe.domain.TicTacToe.GameState;
import de.martinbluemel.tictactoe.presentation.UserInterface;

public class TicTacToeTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testMatch01() {
		TicTacToe game = new TicTacToe(new UIMock());
		game.newMatch();
		assertNull(game.winner);
		game.moveEntered(new Move(game.getPlayer1(), 2, 2));
		game.moveEntered(new Move(game.getPlayer2(), 1, 2));
		game.moveEntered(new Move(game.getPlayer1(), 1, 1));
		game.moveEntered(new Move(game.getPlayer2(), 1, 3));
		assertNull(game.winner);
		game.moveEntered(new Move(game.getPlayer1(), 3, 3));
		assertSame(game.getPlayer1(), game.winner);
	}

	@Test
	public void testMatch02() {
		TicTacToe game = new TicTacToe(new UIMock());
		game.newMatch();
		assertNull(game.winner);
		game.moveEntered(new Move(game.getPlayer1(), 2, 2));
		game.moveEntered(new Move(game.getPlayer2(), 1, 2));
		game.moveEntered(new Move(game.getPlayer1(), 2, 3));
		game.moveEntered(new Move(game.getPlayer2(), 2, 1));
		game.moveEntered(new Move(game.getPlayer1(), 3, 3));
		game.moveEntered(new Move(game.getPlayer2(), 1, 1));
		game.moveEntered(new Move(game.getPlayer1(), 3, 2));
		assertNull(game.winner);
		game.moveEntered(new Move(game.getPlayer2(), 3, 1));
		assertSame(game.getPlayer2(), game.winner);
	}

	@Test
	public void testMatchStandOff() {
		TicTacToe game = new TicTacToe(new UIMock());
		game.setDim(new Dimension(3, 3));
		game.newMatch();
		assertNull(game.winner);
		assertFalse(game.getField().isSetAll());
		game.moveEntered(new Move(game.getPlayer1(), 2, 2));
		game.moveEntered(new Move(game.getPlayer2(), 1, 2));
		game.moveEntered(new Move(game.getPlayer1(), 2, 3));
		game.moveEntered(new Move(game.getPlayer2(), 2, 1));
		game.moveEntered(new Move(game.getPlayer1(), 3, 2));
		game.moveEntered(new Move(game.getPlayer2(), 3, 3));
		game.moveEntered(new Move(game.getPlayer1(), 3, 1));
		game.moveEntered(new Move(game.getPlayer2(), 1, 3));
		assertFalse(game.getField().isSetAll());
		game.moveEntered(new Move(game.getPlayer1(), 1, 1));
		assertTrue(game.getField().isSetAll());
//        new TicTacToeCmdline().updateUI(game);
		assertNull(game.winner);
	}

	public void testMoveIllegalTwice() {
		UIMock ui = new UIMock();
		TicTacToe game = new TicTacToe(ui);
		game.newMatch();
		game.moveEntered(new Move(game.getPlayer1(), 2, 2));
		assertEquals(0, ui.errorCount);
		game.moveEntered(new Move(game.getPlayer2(), 2, 2));
		assertEquals(1, ui.errorCount);
	}

	public void testMoveIllegalOutOfBounds() {
		UIMock ui = new UIMock();
		TicTacToe game = new TicTacToe(ui);
		game.moveEntered(new Move(game.getPlayer1(), 2, 2));
		assertEquals(0, ui.errorCount);
		game.moveEntered(new Move(game.getPlayer2(), 2, 20));
		assertEquals(1, ui.errorCount);
	}

	@Test
	public void testEnterMove() {
		TicTacToe game = new TicTacToe(new UIMock());
		game.newMatch();
		assertEquals(null, game.getField().get(2, 2).setBy);
		game.moveEntered(new Move(game.getPlayer1(), 2, 2));
		assertEquals(game.getPlayer1(), game.getField().get(2, 2).setBy);
	}

	private class UIMock implements UserInterface {

		public int errorCount = 0;

		@Override
		public void messageInfo(String message) {
		}

		@Override
		public void messageError(String message) {
			this.errorCount++;
		}

		@Override
		public void updateUI() {
		}

		@Override
		public void stateChanged(GameState state) {
		}

		@Override
		public boolean askYesNo(String message) {
			return true;
		}
	}
}
