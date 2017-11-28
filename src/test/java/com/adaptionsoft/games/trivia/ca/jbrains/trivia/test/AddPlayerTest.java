package com.adaptionsoft.games.trivia.ca.jbrains.trivia.test;

import com.adaptionsoft.games.uglytrivia.Game;
import io.vavr.collection.List;
import net.ttsui.junit.rules.pending.PendingImplementation;
import net.ttsui.junit.rules.pending.PendingRule;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.MethodRule;

public class AddPlayerTest {
    @Rule
    public MethodRule pendingRule = new PendingRule();

    @Test
    public void firstPlayer() throws Exception {
        final SilentGame game = new SilentGame();
        game.addPlayerNamed("Player 1");
        Assert.assertEquals(1, game.howManyPlayers());
        Assert.assertFalse(game.isPlayable());
    }

    @Test
    public void emptyName() throws Exception {
        final SilentGame game = new SilentGame();
        game.addPlayerNamed("");

        // Surprisingly, this is OK! No exception.
    }

    @Test
    public void twoPlayersWithTheSameName() throws Exception {
        // REFACTOR I'd like to create a Game with some players
        // already "playing".
        final SilentGame game = new SilentGame() {
            {
                addPlayerNamed("player with the same name");
            }
        };
        game.addPlayerNamed("player with the same name");

        // Surprisingly, this is OK! No exception.
        Assert.assertEquals(2, game.howManyPlayers());
    }

    @Test
    @PendingImplementation("Waiting to fix bug JIRA-1721.")
    public void addSixthPlayer() throws Exception {
        class SilentGameWithFivePlayers extends SilentGame {
            public SilentGameWithFivePlayers() {
                List.of(1, 2, 3, 4, 5).map(n -> String.format("Player %d", n)).forEach(
                        this::addPlayerNamed
                );
            }
        }

        final SilentGameWithFivePlayers game = new SilentGameWithFivePlayers();
        game.addPlayerNamed("the player that exposes the bug");
    }

    public static class SilentGame extends Game {
        @Override
        protected void reportMessage(final String message) {
            // Intentionally do nothing
        }
    }
}
