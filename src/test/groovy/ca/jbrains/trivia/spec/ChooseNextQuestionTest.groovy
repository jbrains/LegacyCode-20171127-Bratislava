package ca.jbrains.trivia.spec

import com.adaptionsoft.games.uglytrivia.Game
import io.vavr.collection.HashMap
import spock.lang.Specification
import spock.lang.Unroll


class ChooseNextQuestionTest extends Specification {
    private def fakeNextQuestionIn(categoryName) {
        String.format("::the next %s question::", categoryName)
    }

    @Unroll
    def "choose the next question in the deck for category #category"() {
        given:
        def popQuestions = new LinkedList([fakeNextQuestionIn("Pop")])
        def scienceQuestions = new LinkedList([fakeNextQuestionIn("Science")])
        def sportsQuestions = new LinkedList([fakeNextQuestionIn("Sports")])
        def rockQuestions = new LinkedList([fakeNextQuestionIn("Rock")])

        expect:
        Game.chooseNextQuestionInCategory(category, HashMap.of(
                "Pop", popQuestions,
                "Science", scienceQuestions,
                "Sports", sportsQuestions,
                "Rock", rockQuestions
        )) == expectedNextQuestion

        where:
        category  | expectedNextQuestion
        "Pop"     | fakeNextQuestionIn("Pop")
        "Science" | fakeNextQuestionIn("Science")
        "Sports"  | fakeNextQuestionIn("Sports")
        "Rock"    | fakeNextQuestionIn("Rock")
    }

    def "reject unknown category"() {
        expect:
        try {
            Game.chooseNextQuestionInCategory("::unknown category::", HashMap.empty())
            fail("How did you ask a question in an unknown category?!");
        }
        catch (IllegalStateException currentBehavior) {
        }
    }
}