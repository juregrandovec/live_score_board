import org.livescoreboard.LiveScoreBoard
import org.livescoreboard.LiveScoreBoard.Match
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertThrows

class LiveScoreBoardTest {

    private lateinit var scoreboard: LiveScoreBoard

    @BeforeEach
    fun setUp() {
        scoreboard = LiveScoreBoard()
    }

    @Test
    fun createMatch() {
        scoreboard.createMatch("Mexico", "Canada")

        var matches: List<Match> = scoreboard.getMatches()
        assertEquals(1, matches.size)
        val firstMatch = matches[0]
        assertEquals("Mexico", firstMatch.homeTeamName)
        assertEquals("Canada", firstMatch.awayTeamName)
        assertEquals(0, firstMatch.homeTeamScore)
        assertEquals(0, firstMatch.awayTeamScore)
        assertEquals(0, firstMatch.id)

        scoreboard.createMatch("Spain", "Brazil")
        matches = scoreboard.getMatches()
        assertEquals(2, matches.size)
        val secondMatch = matches[1]
        assertEquals("Spain", secondMatch.homeTeamName)
        assertEquals("Brazil", secondMatch.awayTeamName)
        assertEquals(0, secondMatch.homeTeamScore)
        assertEquals(0, secondMatch.awayTeamScore)
        assertEquals(1, secondMatch.id)
    }

    @Test
    fun updateMatch() {
        scoreboard.createMatch("Mexico", "Canada")
        scoreboard.updateMatch(0, 1, 2)

        val matches: List<Match> = scoreboard.getMatches()
        assertEquals(1, matches.size)
        val firstMatch = matches[0]
        assertEquals(1, firstMatch.homeTeamScore)
        assertEquals(2, firstMatch.awayTeamScore)

        assertThrows<IllegalArgumentException> {
            scoreboard.updateMatch(1, 3, 4)
        }
    }

    @Test
    fun finishMatch() {
        scoreboard.createMatch("Mexico", "Canada")
        assertEquals(1, scoreboard.getMatches().size)

        scoreboard.finishMatch(0)
        assertEquals(0, scoreboard.getMatches().size)

        assertThrows<IllegalArgumentException> {
            scoreboard.finishMatch(0)
        }

        assertTrue(scoreboard.matchSummary().isEmpty())
    }

    @Test
    fun matchSummary() {
        scoreboard.createMatch("Mexico", "Canada")
        scoreboard.updateMatch(0, 0, 5)
        scoreboard.createMatch("Spain", "Brazil")
        scoreboard.updateMatch(1, 10, 2)
        scoreboard.createMatch("Germany", "France")
        scoreboard.updateMatch(2, 2, 2)
        scoreboard.createMatch("Uruguay", "Italy")
        scoreboard.updateMatch(3, 6, 6)
        scoreboard.createMatch("Argentina", "Australia")
        scoreboard.updateMatch(4, 3, 1)

        val summary = scoreboard.matchSummary()
        assertEquals(5, summary.size)

        val expectedIds = arrayOf(3, 1, 0, 4, 2)
        val actualIds = summary.map { it.id }.toTypedArray()
        assertArrayEquals(
            expectedIds,
            actualIds,
            "Match Summary should be ordered by SUM of the match score desc, and secondly by the id desc"
        )

    }

    @Test
    fun sameTotalScoreOrdering() {
        scoreboard.createMatch("Mexico", "Canada")
        scoreboard.updateMatch(0, 3, 3)
        scoreboard.createMatch("Spain", "Brazil")
        scoreboard.updateMatch(1, 3, 3)

        val summary = scoreboard.matchSummary()
        assertEquals(2, summary.size)
        val expectedIds = arrayOf(1,0)
        val actualIds = summary.map { it.id }.toTypedArray()
        assertArrayEquals(
            expectedIds,
            actualIds,
            "Match Summary should be ordered by SUM of the match score desc, and secondly by the id desc"
        )
    }
}