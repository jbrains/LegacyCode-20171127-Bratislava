package ca.jbrains.trivia.spec

import com.adaptionsoft.games.uglytrivia.Game
import spock.lang.Specification
import spock.lang.Unroll


class FindCategoryByPlaceTest extends Specification {
    @Unroll
    def "place #place corresponds to category #category"() {
        def game = new Game()

        expect:
        game.findCategoryByPlace(place) == category

        where:
        place | category
        0     | "Pop"
        1     | "Science"
        2     | "Sports"
        3     | "Rock"
        4     | "Pop"
        5     | "Science"
        6     | "Sports"
        7     | "Rock"
        8     | "Pop"
        9     | "Science"
        10    | "Sports"
        11    | "Rock"
    }

    @Unroll
    def "place #place is not on the board, but it has a category"() {
        def game = new Game()

        expect:
        // I don't EXPECT this result, but this is just the current behavior
        game.findCategoryByPlace(place) == "Rock"

        where:
        place << [12, 13, 14, 56, 12938, -1, -2, -3, -283746]
    }
}