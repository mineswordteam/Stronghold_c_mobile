package com.mineswordteam.strongholdcrusader

import java.io.File

class GameDetector(private val baseDir: File) {

    companion object {
        const val EXE_STRONGHOLD_CRUSADER_HD = "Stronghold Crusader.exe"
        const val EXE_STRONGHOLD_CRUSADER_EXTREME_HD = "Stronghold_Crusader_Extreme.exe"
    }

    fun isHdAvailable(): Boolean {
        val exeFile = File(baseDir, EXE_STRONGHOLD_CRUSADER_HD)
        val exeFileLower = File(baseDir, "stronghold crusader.exe")
        return exeFile.exists() || exeFileLower.exists()
    }

    fun isExtremeAvailable(): Boolean {
        val exeFile = File(baseDir, EXE_STRONGHOLD_CRUSADER_EXTREME_HD)
        val exeFileLower = File(baseDir, "stronghold_crusader_extreme.exe")
        return exeFile.exists() || exeFileLower.exists()
    }

    fun findAvailableExecutables(): List<String> {
        val list = mutableListOf<String>()
        if (isHdAvailable()) list.add(EXE_STRONGHOLD_CRUSADER_HD)
        if (isExtremeAvailable()) list.add(EXE_STRONGHOLD_CRUSADER_EXTREME_HD)
        return list
    }
}
