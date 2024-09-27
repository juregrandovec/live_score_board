import org.example.LiveScoreBoard
import org.example.LiveScoreBoard.Match
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class LiveScoreBoardTest {

    private lateinit var scoreboard: LiveScoreBoard

    @BeforeEach
    fun setUp() {
        scoreboard = LiveScoreBoard()
    }

    @Test
    fun createMatch() {
        scoreboard.createMatch("A", "B")

        val matches: List<Match> = scoreboard.getMatches()
        assertEquals(1, matches.size)
        val firstMatch = matches[0]
        assertEquals("A", firstMatch.homeTeamName)
        assertEquals("B", firstMatch.awayTeamName)
        assertEquals(0, firstMatch.homeTeamScore)
        assertEquals(0, firstMatch.awayTeamScore)
    }

    @Test
    fun updateMatch() {
        scoreboard.createMatch("A", "B")
        scoreboard.updateMatch(0, 1, 2)

        val matches: List<Match> = scoreboard.getMatches()
        assertEquals(1, matches.size)
        val firstMatch = matches[0]
        assertEquals(1, firstMatch.homeTeamScore)
        assertEquals(2, firstMatch.awayTeamScore)
    }

    @Test
    fun finish() {
        scoreboard.createMatch("A", "B")

        val matchesAfterCreate: List<Match> = scoreboard.getMatches()
        assertEquals(1, matchesAfterCreate.size)

        scoreboard.finish(0)

        val matchesAfterFinish: List<Match> = scoreboard.getMatches()
        assertEquals(0, matchesAfterFinish.size)
    }

    @Test
    fun summary() {
        scoreboard.createMatch("Mexico", "Canada")
        scoreboard.updateMatch(0, 0,5)
        scoreboard.createMatch("Spain", "Brazil")
        scoreboard.updateMatch(1, 10,2)
        scoreboard.createMatch("Germany", "France")
        scoreboard.updateMatch(2, 2,2)
        scoreboard.createMatch("Uruguay", "Italy")
        scoreboard.updateMatch(3, 6,6)
        scoreboard.createMatch("Argentina", "Australia")
        scoreboard.updateMatch(4, 3,1)

        val summary = scoreboard.summary()
        assertEquals(5, summary.size)

        val expectedIds = arrayOf(4, 2, 1, 5, 3)
        val actualIds = summary.map { it.id }.toTypedArray()
        assertArrayEquals(expectedIds, actualIds)

    }
}