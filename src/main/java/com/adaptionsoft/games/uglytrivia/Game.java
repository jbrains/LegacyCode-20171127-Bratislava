package com.adaptionsoft.games.uglytrivia;

import io.vavr.collection.HashMap;
import io.vavr.collection.Map;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.function.Supplier;

public class Game {
    ArrayList players = new ArrayList();
    int[] places = new int[6];
    int[] purses = new int[6];
    boolean[] inPenaltyBox = new boolean[6];

    LinkedList popQuestions = new LinkedList();
    LinkedList scienceQuestions = new LinkedList();
    LinkedList sportsQuestions = new LinkedList();
    LinkedList rockQuestions = new LinkedList();

    int currentPlayer = 0;
    boolean isGettingOutOfPenaltyBox;

    public Game() {
        for (int i = 0; i < 50; i++) {
            popQuestions.addLast("Pop Question " + i);
            scienceQuestions.addLast(("Science Question " + i));
            sportsQuestions.addLast(("Sports Question " + i));
            rockQuestions.addLast(createRockQuestion(i));
        }
    }

    // SMELL Generic, but still somehow strongly related to just this use.
    // I'm not convinced that it belongs in a generic library.
    private static <T> T chooseThenConsumeTheFirstItem(LinkedList<T> ts) {
        return ts.removeFirst();
    }

    public static String chooseNextQuestionInCategory(final String categoryName, final Map<String, LinkedList<String>> questionDecksByCategory) {
        return questionDecksByCategory.get(categoryName)
                .map(Game::chooseThenConsumeTheFirstItem)
                .getOrElseThrow(unrecognizedCategoryNamed(categoryName));
    }

    private static Supplier<RuntimeException> unrecognizedCategoryNamed(final String currentCategory) {
        return () -> new IllegalStateException(String.format(
                "I don't know how to ask a question in category '%s', because I don't recognize that category name.",
                currentCategory
        ));
    }

    public String createRockQuestion(int index) {
        return "Rock Question " + index;
    }

    public boolean isPlayable() {
        return (howManyPlayers() >= 2);
    }

    /**
     * @deprecated Scheduled to disappear on 2018-04-30
     */
    public boolean add(String playerName) {
        return addPlayerNamed(playerName);
    }

    public boolean addPlayerNamed(final String playerName) {
        final int positionOfNewPlayer = initializePlayerNamed(playerName);
        reportPlayerAddedInPosition(playerName, positionOfNewPlayer);
        return true;
    }

    private void reportPlayerAddedInPosition(final String playerName, final int position) {
        reportPlayerAdded(playerName);
        reportPlayerNumber(position);
    }

    private void reportPlayerNumber(final int playerNumber) {
        reportMessage("They are player number " + playerNumber);
    }

    private void reportPlayerAdded(final String playerName) {
        reportMessage(playerName + " was added");
    }

    public int initializePlayerNamed(final String playerName) {
        // REFACTOR Introduce a Player struct here
        players.add(playerName);
        places[howManyPlayers()] = 0;
        purses[howManyPlayers()] = 0;
        inPenaltyBox[howManyPlayers()] = false;
        return players.size();
    }

    protected void reportMessage(final String message) {
        System.out.println(message);
    }

    public int howManyPlayers() {
        return players.size();
    }

    public void roll(int roll) {
        System.out.println(players.get(currentPlayer) + " is the current player");
        System.out.println("They have rolled a " + roll);

        if (inPenaltyBox[currentPlayer]) {
            if (roll % 2 != 0) {
                isGettingOutOfPenaltyBox = true;

                System.out.println(players.get(currentPlayer) + " is getting out of the penalty box");
                places[currentPlayer] = places[currentPlayer] + roll;
                if (places[currentPlayer] > 11) places[currentPlayer] = places[currentPlayer] - 12;

                System.out.println(players.get(currentPlayer)
                        + "'s new location is "
                        + places[currentPlayer]);
                System.out.println("The category is " + currentCategory());
                askQuestion();
            } else {
                System.out.println(players.get(currentPlayer) + " is not getting out of the penalty box");
                isGettingOutOfPenaltyBox = false;
            }

        } else {

            places[currentPlayer] = places[currentPlayer] + roll;
            if (places[currentPlayer] > 11) places[currentPlayer] = places[currentPlayer] - 12;

            System.out.println(players.get(currentPlayer)
                    + "'s new location is "
                    + places[currentPlayer]);
            System.out.println("The category is " + currentCategory());
            askQuestion();
        }

    }

    private void askQuestion() {
        try {
            System.out.println(
                    chooseNextQuestionInCategory(
                            currentCategory(),
                            HashMap.of("Pop", popQuestions,
                                    "Science", scienceQuestions,
                                    "Sports", sportsQuestions,
                                    "Rock", rockQuestions
                            )));
        } catch (IllegalStateException intentionallyDoNothingJustAsWeDidBefore) {
        }
    }

    private String currentCategory() {
        return findCategoryByPlace(places[currentPlayer]);
    }

    // REFACTOR This relates only to the Board, so maybe a Board class?
    public String findCategoryByPlace(int place) {
        if (place == 0) return "Pop";
        if (place == 4) return "Pop";
        if (place == 8) return "Pop";
        if (place == 1) return "Science";
        if (place == 5) return "Science";
        if (place == 9) return "Science";
        if (place == 2) return "Sports";
        if (place == 6) return "Sports";
        if (place == 10) return "Sports";
        return "Rock";
    }

    public boolean wasCorrectlyAnswered() {
        if (inPenaltyBox[currentPlayer]) {
            if (isGettingOutOfPenaltyBox) {
                System.out.println("Answer was correct!!!!");
                purses[currentPlayer]++;
                System.out.println(players.get(currentPlayer)
                        + " now has "
                        + purses[currentPlayer]
                        + " Gold Coins.");

                boolean winner = didPlayerWin();
                currentPlayer++;
                if (currentPlayer == players.size()) currentPlayer = 0;

                return winner;
            } else {
                currentPlayer++;
                if (currentPlayer == players.size()) currentPlayer = 0;
                return true;
            }


        } else {

            System.out.println("Answer was corrent!!!!");
            purses[currentPlayer]++;
            System.out.println(players.get(currentPlayer)
                    + " now has "
                    + purses[currentPlayer]
                    + " Gold Coins.");

            boolean winner = didPlayerWin();
            currentPlayer++;
            if (currentPlayer == players.size()) currentPlayer = 0;

            return winner;
        }
    }

    public boolean wrongAnswer() {
        System.out.println("Question was incorrectly answered");
        System.out.println(players.get(currentPlayer) + " was sent to the penalty box");
        inPenaltyBox[currentPlayer] = true;

        currentPlayer++;
        if (currentPlayer == players.size()) currentPlayer = 0;
        return true;
    }


    private boolean didPlayerWin() {
        return !(purses[currentPlayer] == 6);
    }
}
