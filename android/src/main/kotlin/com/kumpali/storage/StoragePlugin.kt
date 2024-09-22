package com.kumpali.storage

import android.annotation.SuppressLint
import android.content.Context
import android.os.Environment
import android.os.StatFs
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import java.io.File


/** StoragePlugin */
class StoragePlugin: FlutterPlugin, MethodCallHandler {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private lateinit var channel : MethodChannel
  private lateinit var context : Context
  override fun onAttachedToEngine(flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    context = flutterPluginBinding.applicationContext
    channel = MethodChannel(flutterPluginBinding.binaryMessenger, "storage")
    channel.setMethodCallHandler(this)
  }

  override fun onMethodCall(call: MethodCall, result: Result) {
      when (call.method) {
          "gPV" -> {
              result.success("Android ${android.os.Build.VERSION.RELEASE}")
          }
          "gSTS" -> {
              result.success(getStorageTotalSpace(context))
          }
          "gSFS" -> {
              result.success(getStorageFreeSpace(context))
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
          "gESAD" -> {
              result.success(getExternalStorageAvailableData(context))
          }
          else -> {
              result.notImplemented()
          }
      }
  }

  override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
    channel.setMethodCallHandler(null)
  }

    private fun getStorageTotalSpace(context: Context): Long{
        val dirs: Array<File> = context.getExternalFilesDirs(null)
        val stat = StatFs(dirs[1].path.split("Android")[0])
        return stat.totalBytes
//        return Environment.getDataDirectory().totalSpace
    }

    private fun getStorageFreeSpace(context: Context): Long{
        val dirs: Array<File> = context.getExternalFilesDirs(null)
        print(dirs);
        val stat = StatFs(dirs[1].path.split("Android")[0])
        return stat.availableBytes
//        return Environment.getDataDirectory().freeSpace
    }

    @SuppressLint("UsableSpace")
    private fun getStorageUsableSpace(): Long{
        return Environment.getDataDirectory().usableSpace
    }

    private fun getExternalStorageTotalSpace(): Long{
      return  Environment.getExternalStorageDirectory().totalSpace
    }

    private fun getExternalStorageFreeSpace(): Long{
      return Environment.getRootDirectory().freeSpace
    }

    @SuppressLint("UsableSpace")
    private fun getExternalStorageUsableSpace(): Long{
        return Environment.getRootDirectory().usableSpace
    }

    private fun getRootTotalSpace(): Long{
        return  Environment.getRootDirectory().totalSpace
    }

    private fun getRootFreeSpace(): Long{
        return Environment.getRootDirectory().freeSpace
    }

    @SuppressLint("UsableSpace")
    private fun getRootUsableSpace(): Long{
        return Environment.getRootDirectory().usableSpace
    }

    private fun getExternalStoragePublicDownloadDirectoryTotalSpace(): Long{
        return  Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_DOWNLOADS).totalSpace
    }

    private fun getExternalStoragePublicDownloadDirectoryFreeSpace(): Long{
        return  Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_DOWNLOADS).freeSpace
    }

    private fun getFreeDiskSpaceForPath(path: String): Double {
        val stat = StatFs(path)
        val bytesAvailable: Long = stat.blockSizeLong * stat.availableBlocksLong
        return (bytesAvailable / (1024f * 1024f)).toDouble()
    }

    private fun getExternalStorageAvailableData(context: Context): ArrayList<HashMap<*, *>> {
        val appsDir = context.getExternalFilesDirs(Environment.MEDIA_UNMOUNTED)
        val sharedAppsDir = context.getExternalFilesDirs(Environment.MEDIA_SHARED)
        val extRootPaths = ArrayList<HashMap<*, *>>()
        for (file in appsDir) {
            if (file != null) {
                val path = file.absolutePath
                val statFs = StatFs(path)
                val availableBytes = statFs.availableBlocksLong * statFs.blockSizeLong
                val storageData = HashMap<String, Any>()
                try {
                    val rootPath =
                        "" //file.getParentFile().getParentFile().getParentFile().getParentFile().getAbsolutePath();
                    storageData["rootPath"] = rootPath
                } catch (_: Exception) {
                }
                storageData["path"] = path
                storageData["availableBytes"] = availableBytes
                extRootPaths.add(storageData)
            }
        }
            for (fil in sharedAppsDir) {
                if (fil != null) {
                    val path = fil.absolutePath
                    val statFs = StatFs(path)
                    val availableBytes = statFs.availableBlocksLong * statFs.blockSizeLong
                    val storageData = HashMap<String, Any>()
                    try {
                        val rootPath =
                            "" //file.getParentFile().getParentFile().getParentFile().getParentFile().getAbsolutePath();
                        storageData["rootPath"] = rootPath
                    } catch (_: Exception) {
                    }
                    storageData["path"] = path
                    storageData["availableBytes"] = availableBytes
                    extRootPaths.add(storageData)
                }
            }
            return extRootPaths
        }
}


