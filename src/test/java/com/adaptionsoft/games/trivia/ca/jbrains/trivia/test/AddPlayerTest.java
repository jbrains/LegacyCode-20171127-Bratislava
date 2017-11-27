package com.adaptionsoft.games.trivia.ca.jbrains.trivia.test;

import com.adaptionsoft.games.uglytrivia.Game;
import io.vavr.collection.List;
import org.junit.Assert;
import org.junit.Test;

public class AddPlayerTest {
    @Test
    public void firstPlayer() throws Exception {
        final Game game = new Game() {
            @Override
            protected void reportMessage(final String message) {
                // Intentionally do nothing
            }
        };
        game.addPlayerNamed("Player 1");
        Assert.assertEquals(1, game.howManyPlayers());
        Assert.assertFalse(game.isPlayable());
    }

    @Test
    public void emptyName() throws Exception {
        final Game game = new Game() {
            @Override
            protected void reportMessage(final String message) {
                // Intentionally do nothing
            }
        };
        game.addPlayerNamed("");

        // Surprisingly, this is OK! No exception.
    }

    @Test
    public void twoPlayersWithTheSameName() throws Exception {
        // REFACTOR I'd like to create a Game with some players
        // already "playing".
        final Game game = new Game() {
            {
                addPlayerNamed("player with the same name");
            }

            @Override
            protected void reportMessage(final String message) {
                // Intentionally do nothing
            }
        };
        game.addPlayerNamed("player with the same name");

        // Surprisingly, this is OK! No exception.
        Assert.assertEquals(2, game.howManyPlayers());
    }

    @Test
    public void addSixthPlayer() throws Exception {
        class TestableGame extends Game {
            public TestableGame() {
                List.of(1, 2, 3, 4, 5).map(n -> String.format("Player %d", n)).forEach(
                        this::addPlayerNamed
                );
            }
            @Override
            protected void reportMessage(final String message) {
                // Intentionally do nothing
            }
        }

        final TestableGame game = new TestableGame();

        try {
            game.addPlayerNamed("the player that exposes the bug");
            Assert.fail("Yay! We fixed bug JIRA-1721!");
        }
        catch (ArrayIndexOutOfBoundsException currentBehavior) {}
    }
}
