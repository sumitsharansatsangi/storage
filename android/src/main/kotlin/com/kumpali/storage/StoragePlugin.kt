package com.kumpali.storage

import android.annotation.SuppressLint
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import android.os.Environment
import android.os.StatFs

/** StoragePlugin */
class StoragePlugin: FlutterPlugin, MethodCallHandler {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private lateinit var channel : MethodChannel

  override fun onAttachedToEngine(flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    channel = MethodChannel(flutterPluginBinding.binaryMessenger, "storage")
    channel.setMethodCallHandler(this)
  }

  override fun onMethodCall(call: MethodCall, result: Result) {
      when (call.method) {
          "gPV" -> {
              result.success("Android ${android.os.Build.VERSION.RELEASE}")
          }
          "gSTS" -> {
              result.success(getStorageTotalSpace())
          }
          "gSFS" -> {
              result.success(getStorageFreeSpace())
          }
          "gSUS" -> {
              result.success(getStorageUsableSpace())
          }
          "gESTS" -> {
              result.success(getExternalStorageTotalSpace())
          }
          "gESFS" -> {
              result.success(getExternalStorageFreeSpace())
          }
          "gESUS" -> {
              result.success(getExternalStorageUsableSpace())
          }
          "gRTS" -> {
              result.success(getRootTotalSpace())
          }
          "gRFS" -> {
              result.success(getRootFreeSpace())
          }
          "gRUS" -> {
              result.success(getRootUsableSpace())
          }
          else -> {
              result.notImplemented()
          }
      }
  }

  override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
    channel.setMethodCallHandler(null)
  }

    private fun getStorageTotalSpace(): Long{
        return Environment.getDataDirectory().totalSpace;
    }

    private fun getStorageFreeSpace(): Long{
        return Environment.getDataDirectory().freeSpace;
    }

    @SuppressLint("UsableSpace")
    private fun getStorageUsableSpace(): Long{
        return Environment.getDataDirectory().usableSpace;
    }

    private fun getExternalStorageTotalSpace(): Long{
      return  Environment.getExternalStorageDirectory().totalSpace;
    }

    private fun getExternalStorageFreeSpace(): Long{
      return Environment.getRootDirectory().freeSpace;
    }

    @SuppressLint("UsableSpace")
    private fun getExternalStorageUsableSpace(): Long{
        return Environment.getRootDirectory().usableSpace;
    }

    private fun getRootTotalSpace(): Long{
        return  Environment.getRootDirectory().totalSpace;
    }

    private fun getRootFreeSpace(): Long{
        return Environment.getRootDirectory().freeSpace;
    }

    @SuppressLint("UsableSpace")
    private fun getRootUsableSpace(): Long{
        return Environment.getRootDirectory().usableSpace;
    }

    private fun getExternalStoragePublicDownloadDirectoryTotalSpace(): Long{
        return  Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_DOWNLOADS).totalSpace;
    }

    private fun getExternalStoragePublicDownloadDirectoryFreeSpace(): Long{
        return  Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_DOWNLOADS).freeSpace;
    }

    private fun getFreeDiskSpaceForPath(path: String): Double {
        val stat = StatFs(path)
        val bytesAvailable: Long = stat.blockSizeLong * stat.availableBlocksLong
        return (bytesAvailable / (1024f * 1024f)).toDouble()
    }
}
