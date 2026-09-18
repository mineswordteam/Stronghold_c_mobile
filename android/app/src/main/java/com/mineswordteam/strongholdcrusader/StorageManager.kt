package com.mineswordteam.strongholdcrusader

import android.content.Context
import java.io.File

class StorageManager(private val context: Context) {

    fun getGameDir(): File {
        val dir = File(context.filesDir, "game")
        if (!dir.exists()) {
            dir.mkdirs()
        }
        return dir
    }

    fun getSavesDir(): File {
        val dir = File(context.filesDir, "saves")
        if (!dir.exists()) {
            dir.mkdirs()
        }
        return dir
    }

    fun getLogsDir(): File {
        val dir = File(context.filesDir, "logs")
        if (!dir.exists()) {
            dir.mkdirs()
        }
        return dir
    }
}
