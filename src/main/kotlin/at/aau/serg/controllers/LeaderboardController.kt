package at.aau.serg.controllers

import at.aau.serg.models.GameResult
import at.aau.serg.services.GameResultService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/leaderboard")
class LeaderboardController(
    private val gameResultService: GameResultService
) {

    @GetMapping
    fun getLeaderboard(
        @RequestParam(required = false) rank: Int? //Parameter ist nicht Pflicht und Int? kann null sein
        ): List<GameResult>{
            val results = gameResultService.getGameResults()
                .sortedWith(compareByDescending<GameResult> { it.score }.thenBy { it.timeInSeconds })


        if(rank == null){ // wenn jemand nur aufruft /leaderboard
            return results //bekommt er die komplette Liste
                }
        if (rank < 1 || rank > results.size){ //Rank prüfen, ob er gültig ist
            throw ResponseStatusException( //Fehler zurückgeben
                HttpStatus.BAD_REQUEST,
                "Invalid rank"
            )
        }
        val index = rank -1 //Rank in Listenindex umwandeln, weil wir starten bei 0
        val start = maxOf(0, index -3) //Bereich berechnen
        val end = minOf(results.size, index + 4) //Endpunkt- 4+ weil subList Ende exclusiv ist.

        return results.subList(start, end) //Teil der Liste zurückgeben
    }
}