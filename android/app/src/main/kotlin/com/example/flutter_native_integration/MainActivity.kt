package com.example.flutter_native_integration


import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaCodec
import android.media.MediaExtractor
import android.media.MediaFormat
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel

class MainActivity : FlutterActivity() {
    private val CHANNEL = "com.example.flutter_native_integration/device_info"
    private val REQUEST_CODE_PERMISSIONS = 1001

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                REQUEST_CODE_PERMISSIONS
            )
        }
    }


    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        MethodChannel(
            flutterEngine.dartExecutor.binaryMessenger,
            CHANNEL
        ).setMethodCallHandler { call, result ->
            when (call.method) {
                "getDeviceInfo" -> {
                    val deviceInfo = getDeviceInfo()
                    result.success(deviceInfo)
                }

                "getVideoInfo" -> {
                    val videoInfo = getVideoInfo()
                    result.success(videoInfo)
                }

                else -> {
                    result.notImplemented()
                }
            }
        }
    }

    private fun getDeviceInfo(): String {
        // Get the device information
        return "OS Version: ${Build.VERSION.RELEASE}, Device: ${Build.MODEL}"
    }

    private fun getVideoInfo(): String {
        // Get the video information
        val path =
            "/storage/emulated/0/DCIM/Camera/VID_20240701_052543.mp4" // Path to the video file
        val extractor = MediaExtractor()
        val width: Int?
        val height: Int?
        try {
            extractor.setDataSource(path)
            val format = extractor.getTrackFormat(0)
            val duration = format.getLong(MediaFormat.KEY_DURATION)
            width = format.getInteger(MediaFormat.KEY_WIDTH)
            height = format.getInteger(MediaFormat.KEY_HEIGHT)
            val mime = format.getString(MediaFormat.KEY_MIME)

            val codec = MediaCodec.createDecoderByType(mime!!)
            codec.configure(format, null, null, 0)
            codec.start()


            return "Duration: ${duration / 1000000}s, Resolution: ${width}x${height}, Codec: $mime"
        } catch (e: Exception) {
            e.printStackTrace()
            return "Failed to get video info: ${e.message}"
        } finally {
            extractor.release()
        }
    }


    private fun allPermissionsGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            this, Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
            this, Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

}

