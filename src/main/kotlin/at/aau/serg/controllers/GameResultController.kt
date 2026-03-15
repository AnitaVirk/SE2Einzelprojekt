package at.aau.serg.controllers

import at.aau.serg.models.GameResult
import at.aau.serg.services.GameResultService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/game-results")
class GameResultController(
    private val gameResultService: GameResultService
) {

    @GetMapping("/{gameResultId}") // gibt ein bestimmtes Ergebnis zurück
    fun getGameResult(@PathVariable gameResultId: Long): GameResult? {
        return gameResultService.getGameResult(gameResultId);
    }

    @GetMapping // gibt alle Ergebnisse zurück
    fun getAllGameResults(): List<GameResult> {
        return gameResultService.getGameResults();
    }

    @PostMapping //fügt ein neues Ergebnis hinzu
    fun addGameResult(@RequestBody gameResult: GameResult) {
        gameResultService.addGameResult(gameResult)
    }

    @DeleteMapping("/{gameResultId}") //löscht ein Ergebnis
    fun deleteGameResult(@PathVariable gameResultId: Long) {
        gameResultService.deleteGameResult(gameResultId)
    }
    
}