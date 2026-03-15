package at.aau.serg.controllers

import at.aau.serg.models.GameResult
import at.aau.serg.services.GameResultService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class GameResultControllerTests {
    private lateinit var service: GameResultService
    private lateinit var controller: GameResultController

    @BeforeEach
    fun setup() {
        service = GameResultService()
        controller = GameResultController(service)
    }

    @Test //wenn eine ID existiert, wird das Ergebnis zurückgegeben
    fun test_getGameResult_existingId_returnsGameResult() {
        val gameResult = GameResult(0, "player1", 17, 15.3) //Spieler wird erstellt
        service.addGameResult(gameResult) //service speichert Ergebnis

        val result = controller.getGameResult(1)

        assertEquals(gameResult, result)
    }

    @Test // ID existiert nicht
    fun test_getGameResult_nonExistingId_returnsNull() {
        val result = controller.getGameResult(999)

        assertNull(result) //Ergebnis muss null sein
    }

    @Test //Alle Ergebnisse holen
    fun test_getAllGameResults_returnsAllResultsSorted() {
        val gameResult1 = GameResult(0, "player1", 17, 15.3)
        val gameResult2 = GameResult(0, "player2", 25, 16.0)

        service.addGameResult(gameResult1)
        service.addGameResult(gameResult2)

        val result = controller.getAllGameResults()

        assertEquals(2, result.size) //Liste muss 2 Ergebnisse enthalten
        assertEquals(gameResult2, result[0])
        assertEquals(gameResult1, result[1])
    }

    @Test //Ergebnis hinzufügen
    fun test_addGameResult_addsNewEntry() {
        val gameResult = GameResult(0, "player1", 17, 15.3)

        controller.addGameResult(gameResult)

        val result = service.getGameResults()

        assertEquals(1, result.size)
        assertEquals(gameResult, result[0])
    }

    @Test //Ergebnis löschen
    fun test_deleteGameResult_existingId_removesEntry() {
        val gameResult = GameResult(0, "player1", 17, 15.3)
        service.addGameResult(gameResult) //Ergebniss speichern

        controller.deleteGameResult(1) //controller löschen

        val result = service.getGameResults()

        assertEquals(0, result.size) // Liste muss leer sein
    }

    @Test //löschen mit falscher ID
    fun test_deleteGameResult_nonExistingId_keepsListUnchanged() {
        val gameResult = GameResult(0, "player1", 17, 15.3)
        service.addGameResult(gameResult)

        controller.deleteGameResult(999) //falsche ID löschen

        val result = service.getGameResults()

        assertEquals(1, result.size) //Das Ergebnis muss noch existieren
        assertEquals(gameResult, result[0])
    }
}