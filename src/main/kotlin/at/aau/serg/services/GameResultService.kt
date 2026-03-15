package at.aau.serg.services

import at.aau.serg.models.GameResult
import org.springframework.stereotype.Service
import java.util.concurrent.atomic.AtomicLong

@Service
class GameResultService { //open erlaubt Vererbungen

    private val gameResults = mutableListOf<GameResult>() //veränderbare Liste, in der alle Spielergebnisse gespeichert werden.
    private var nextId = AtomicLong(1) //Zähler für IDs; jedes Ergebnis bekommt eine eigene ID

    fun addGameResult(gameResult: GameResult) { //
        gameResult.id = nextId.getAndIncrement() //ID wird gesetzt
        gameResults.add(gameResult) // Ergebnis wird gespeichert
    }

    fun getGameResult(id: Long): GameResult? = gameResults.find { it.id == id } // ? allows null


    fun getGameResults(): List<GameResult> = gameResults.sortedWith(
        compareByDescending<GameResult> { it.score } //sotiert den Score absteigend
            .thenBy { it.timeInSeconds} // bei gleichem Score - kürzere Zeit zuerst
    ) // returns immutable list copy

    /**
     * Kotlin-idiomatic for:
     * fun deleteGameResult(gameResultId: Long) {
     *     gameResults.removeIf({ gameResult -> gameResult.id == gameResultId })
     * }
     */
    fun deleteGameResult(id: Long) = gameResults.removeIf { it.id == id } //ein Ergebnis wird gelöscht

}