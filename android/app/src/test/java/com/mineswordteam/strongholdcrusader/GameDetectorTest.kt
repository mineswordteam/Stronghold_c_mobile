package com.mineswordteam.strongholdcrusader

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import java.io.File

class GameDetectorTest {

    @get:Rule
    val tempFolder = TemporaryFolder()

    @Test
    fun testHdExecutableDetection() {
        val baseDir = tempFolder.newFolder("game")
        val detector = GameDetector(baseDir)

        assertFalse(detector.isHdAvailable())
        assertFalse(detector.isExtremeAvailable())

        // Create HD Executable
        File(baseDir, "Stronghold Crusader.exe").createNewFile()
        assertTrue(detector.isHdAvailable())
        assertFalse(detector.isExtremeAvailable())
        assertEquals(1, detector.findAvailableExecutables().size)
    }

    @Test
    fun testExtremeExecutableDetection() {
        val baseDir = tempFolder.newFolder("game")
        val detector = GameDetector(baseDir)

        // Create Extreme Executable
        File(baseDir, "Stronghold_Crusader_Extreme.exe").createNewFile()
        assertFalse(detector.isHdAvailable())
        assertTrue(detector.isExtremeAvailable())
        assertEquals(1, detector.findAvailableExecutables().size)
    }
}
