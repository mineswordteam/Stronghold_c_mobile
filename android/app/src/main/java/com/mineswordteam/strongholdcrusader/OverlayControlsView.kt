package com.mineswordteam.strongholdcrusader

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.mineswordteam.strongholdcrusader.databinding.OverlayControlsBinding

class OverlayControlsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    val binding: OverlayControlsBinding =
        OverlayControlsBinding.inflate(LayoutInflater.from(context), this, true)

    fun setOverlayListener(
        onLeftClick: () -> Unit,
        onRightClick: () -> Unit,
        onBoxSelect: () -> Unit,
        onPause: () -> Unit
    ) {
        binding.btnLeftClick.setOnClickListener { onLeftClick() }
        binding.btnRightClick.setOnClickListener { onRightClick() }
        binding.btnBoxSelect.setOnClickListener { onBoxSelect() }
        binding.btnPauseMenu.setOnClickListener { onPause() }
    }
}
