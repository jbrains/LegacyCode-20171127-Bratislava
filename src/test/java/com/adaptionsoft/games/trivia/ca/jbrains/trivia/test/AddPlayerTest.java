package com.adaptionsoft.games.trivia.ca.jbrains.trivia.test;

import com.adaptionsoft.games.uglytrivia.Game;
import org.junit.Assert;
import org.junit.Test;

public class AddPlayerTest {
    @Test
    public void firstPlayer() throws Exception {
        final Game game = new Game();
        game.add("Player 1");
        Assert.assertEquals(1, game.howManyPlayers());
        Assert.assertFalse(game.isPlayable());
    }
}
