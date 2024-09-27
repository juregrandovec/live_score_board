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
    }

    fun updateMatch(id: Int, homeTeamScore: Int, awayTeamScore: Int) {
    }

    fun finish(id: Int) {
    }

    fun summary(): List<Match> {
        return matches
    }

    fun getMatches(): List<Match> {
        return matches
    }
}