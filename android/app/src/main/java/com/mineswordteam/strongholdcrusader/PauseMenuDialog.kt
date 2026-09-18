package com.mineswordteam.strongholdcrusader

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import com.mineswordteam.strongholdcrusader.databinding.DialogPauseMenuBinding

class PauseMenuDialog(
    context: Context,
    private val onResume: () -> Unit,
    private val onExit: () -> Unit
) : Dialog(context) {

    private lateinit var binding: DialogPauseMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = DialogPauseMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setCancelable(true)

        binding.btnResume.setOnClickListener {
            dismiss()
            onResume()
        }

        binding.btnExitToLauncher.setOnClickListener {
            dismiss()
            onExit()
        }
    }
}
