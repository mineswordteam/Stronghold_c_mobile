package com.mineswordteam.strongholdcrusader

import android.opengl.GLSurfaceView
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class GameActivity : AppCompatActivity(), TouchInputHandler.TouchInputListener {

    companion object {
        init {
            System.loadLibrary("stronghold_runtime")
        }
    }

    private lateinit var glSurfaceView: GLSurfaceView
    private lateinit var overlayControlsView: OverlayControlsView
    private var exeName: String = "Stronghold Crusader.exe"
    private var pauseDialog: PauseMenuDialog? = null

    // Native C++ prototypes
    private external fun nativeInit(gamePath: String, exeName: String, savePath: String): Boolean
    private external fun nativeResize(width: Int, height: Int)
    private external fun nativeRenderFrame()
    private external fun nativeSendTouchEvent(type: Int, x: Int, y: Int, button: Int, delta: Int)
    private external fun nativeStop()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        exeName = intent.getStringExtra("EXTRA_EXE_NAME") ?: "Stronghold Crusader.exe"
        val storageManager = StorageManager(this)

        val rootLayout = FrameLayout(this)

        // Initialize OpenGL Surface View
        glSurfaceView = GLSurfaceView(this)
        glSurfaceView.setEGLContextClientVersion(2)
        glSurfaceView.setRenderer(object : GLSurfaceView.Renderer {
            override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
                nativeInit(
                    storageManager.getGameDir().absolutePath,
                    exeName,
                    storageManager.getSavesDir().absolutePath
                )
            }

            override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
                nativeResize(width, height)
            }

            override fun onDrawFrame(gl: GL10?) {
                nativeRenderFrame()
            }
        })

        // Setup Touch Input
        val touchHandler = TouchInputHandler(this, this)
        glSurfaceView.setOnTouchListener(touchHandler)

        rootLayout.addView(glSurfaceView)

        // Add Overlay Controls
        overlayControlsView = OverlayControlsView(this)
        overlayControlsView.setOverlayListener(
            onLeftClick = { nativeSendTouchEvent(2, 0, 0, 1, 0) },
            onRightClick = { nativeSendTouchEvent(2, 0, 0, 2, 0) },
            onBoxSelect = { nativeSendTouchEvent(1, 100, 100, 1, 0) },
            onPause = { showPauseMenu() }
        )
        rootLayout.addView(overlayControlsView)

        setContentView(rootLayout)

        // Hardware Back Button Handler
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                showPauseMenu()
            }
        })
    }

    private fun showPauseMenu() {
        if (pauseDialog?.isShowing == true) return
        pauseDialog = PauseMenuDialog(
            this,
            onResume = { /* Resume gameplay */ },
            onExit = {
                nativeStop()
                finish()
            }
        )
        pauseDialog?.show()
    }

    override fun onCameraPan(deltaX: Float, deltaY: Float) {
        nativeSendTouchEvent(1, deltaX.toInt(), deltaY.toInt(), 0, 0)
    }

    override fun onPinchZoom(zoomDelta: Float) {
        nativeSendTouchEvent(4, 0, 0, 0, zoomDelta.toInt())
    }

    override fun onTap(x: Float, y: Float) {
        nativeSendTouchEvent(2, x.toInt(), y.toInt(), 1, 0)
    }

    override fun onPause() {
        super.onPause()
        glSurfaceView.onPause()
    }

    override fun onResume() {
        super.onResume()
        glSurfaceView.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        nativeStop()
    }
}
