#include <jni.h>
#include <string>
#include <android/log.h>
#include <GLES2/gl2.h>
#include <vector>
#include <mutex>

#define LOG_TAG "StrongholdRuntimeNative"
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)

static std::string g_gamePath = "";
static std::string g_executableName = "";
static std::string g_savePath = "";
static bool g_isRunning = false;
static int g_viewportWidth = 1920;
static int g_viewportHeight = 1080;

struct InputEvent {
    int type; // 1: MouseMove, 2: MouseDown, 3: MouseUp, 4: MouseWheel
    int x;
    int y;
    int button; // 1: Left, 2: Right, 3: Middle
    int delta;
};

static std::vector<InputEvent> g_eventQueue;
static std::mutex g_eventMutex;

extern "C" JNIEXPORT jboolean JNICALL
Java_com_mineswordteam_strongholdcrusader_GameActivity_nativeInit(
        JNIEnv* env,
        jobject /* this */,
        jstring gamePath,
        jstring executableName,
        jstring savePath) {

    const char* nativeGamePath = env->GetStringUTFChars(gamePath, nullptr);
    const char* nativeExeName = env->GetStringUTFChars(executableName, nullptr);
    const char* nativeSavePath = env->GetStringUTFChars(savePath, nullptr);

    g_gamePath = nativeGamePath ? nativeGamePath : "";
    g_executableName = nativeExeName ? nativeExeName : "";
    g_savePath = nativeSavePath ? nativeSavePath : "";

    env->ReleaseStringUTFChars(gamePath, nativeGamePath);
    env->ReleaseStringUTFChars(executableName, nativeExeName);
    env->ReleaseStringUTFChars(savePath, nativeSavePath);

    LOGI("Native runtime initialized with GamePath: %s, Exe: %s, SavePath: %s",
         g_gamePath.c_str(), g_executableName.c_str(), g_savePath.c_str());

    g_isRunning = true;
    return JNI_TRUE;
}

extern "C" JNIEXPORT void JNICALL
Java_com_mineswordteam_strongholdcrusader_GameActivity_nativeResize(
        JNIEnv* env,
        jobject /* this */,
        jint width,
        jint height) {
    g_viewportWidth = width;
    g_viewportHeight = height;
    glViewport(0, 0, width, height);
    LOGI("Native viewport resized: %dx%d", width, height);
}

extern "C" JNIEXPORT void JNICALL
Java_com_mineswordteam_strongholdcrusader_GameActivity_nativeRenderFrame(
        JNIEnv* env,
        jobject /* this */) {
    if (!g_isRunning) return;

    // Clear background to medieval dark brown
    glClearColor(0.12f, 0.08f, 0.05f, 1.0f);
    glClear(GL_COLOR_BUFFER_BIT);

    // Process input event queue
    std::lock_guard<std::mutex> lock(g_eventMutex);
    if (!g_eventQueue.empty()) {
        g_eventQueue.clear();
    }
}

extern "C" JNIEXPORT void JNICALL
Java_com_mineswordteam_strongholdcrusader_GameActivity_nativeSendTouchEvent(
        JNIEnv* env,
        jobject /* this */,
        jint type,
        jint x,
        jint y,
        jint button,
        jint delta) {
    std::lock_guard<std::mutex> lock(g_eventMutex);
    InputEvent ev;
    ev.type = type;
    ev.x = x;
    ev.y = y;
    ev.button = button;
    ev.delta = delta;
    g_eventQueue.push_back(ev);
}

extern "C" JNIEXPORT void JNICALL
Java_com_mineswordteam_strongholdcrusader_GameActivity_nativeStop(
        JNIEnv* env,
        jobject /* this */) {
    LOGI("Native runtime stopping...");
    g_isRunning = false;
}
