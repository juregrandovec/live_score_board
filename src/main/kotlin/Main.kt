package org.example

class LiveScoreBoard() {

    data class Match(
        val id: Int,
        val homeTeamName: String,
        val awayTeamName: String,
        var homeTeamScore: Int = 0,
        var awayTeamScore: Int = 0,
    )

    private val matches = mutableListOf<Match>()

    fun createMatch(homeTeamName: String, awayTeamName: String) {
        matches.add(Match(matches.size, homeTeamName, awayTeamName))
    }

    fun updateMatch(id: Int, homeTeamScore: Int, awayTeamScore: Int) {
        val match = matches.find { it.id == id }

        if (match != null) {
            match.homeTeamScore = homeTeamScore
            match.awayTeamScore = awayTeamScore
        } else {
            throw IllegalArgumentException("Match with ID $id not found")
        }
    }

    fun finishMatch(id: Int) {
        if (matches.any { it.id == id }) {
            matches.removeIf { it.id == id }
        } else {
            throw IllegalArgumentException("Match with ID $id not found")
        }
    }

    fun matchSummary(): List<Match> {
        return matches.sortedWith(
            compareByDescending { match: Match -> match.homeTeamScore + match.awayTeamScore }
                .thenByDescending { match: Match -> match.id }
        )
    }

    fun getMatches(): List<Match> {
        return matches.toList()
    }
}