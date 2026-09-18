package com.mineswordteam.strongholdcrusader

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mineswordteam.strongholdcrusader.databinding.ActivityLauncherBinding

class LauncherActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLauncherBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLauncherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupLauncherUI()
    }

    private fun setupLauncherUI() {
        val storageManager = StorageManager(this)
        val gameDetector = GameDetector(storageManager.getGameDir())

        val isHdAvailable = gameDetector.isHdAvailable()
        val isExtremeAvailable = gameDetector.isExtremeAvailable()

        // Configure HD Option
        if (isHdAvailable) {
            binding.tvCrusaderHdStatus.text = "Executable Found: Stronghold Crusader.exe"
            binding.tvCrusaderHdStatus.setTextColor(0xFF32CD32.toInt())
            binding.btnLaunchCrusaderHd.isEnabled = true
        } else {
            binding.tvCrusaderHdStatus.text = "Executable Missing (Stronghold Crusader.exe)"
            binding.tvCrusaderHdStatus.setTextColor(0xFFFF6347.toInt())
            binding.btnLaunchCrusaderHd.isEnabled = false
        }

        // Configure Extreme Option
        if (isExtremeAvailable) {
            binding.tvCrusaderExtremeStatus.text = "Executable Found: Stronghold_Crusader_Extreme.exe"
            binding.tvCrusaderExtremeStatus.setTextColor(0xFF32CD32.toInt())
            binding.btnLaunchCrusaderExtreme.isEnabled = true
        } else {
            binding.tvCrusaderExtremeStatus.text = "Executable Missing (Stronghold_Crusader_Extreme.exe)"
            binding.tvCrusaderExtremeStatus.setTextColor(0xFFFF6347.toInt())
            binding.btnLaunchCrusaderExtreme.isEnabled = false
        }

        binding.tvStorageInfo.text = "Storage Path: ${storageManager.getGameDir().absolutePath}"

        // Click listeners
        binding.btnLaunchCrusaderHd.setOnClickListener {
            launchGame("Stronghold Crusader.exe")
        }

        binding.btnLaunchCrusaderExtreme.setOnClickListener {
            launchGame("Stronghold_Crusader_Extreme.exe")
        }

        binding.btnImportGame.setOnClickListener {
            Toast.makeText(this, "Place game files in ${storageManager.getGameDir().absolutePath}", Toast.LENGTH_LONG).show()
        }
    }

    private fun launchGame(exeName: String) {
        val intent = Intent(this, GameActivity::class.java).apply {
            putExtra("EXTRA_EXE_NAME", exeName)
        }
        startActivity(intent)
    }
}
