package com.kumpali.storage

//import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Environment
//import android.os.StatFs
import android.os.storage.StorageManager
import android.os.storage.StorageVolume
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import androidx.core.content.ContextCompat
import io.flutter.plugin.common.MethodChannel.Result
import java.io.File


/** StoragePlugin */
class StoragePlugin : FlutterPlugin, MethodCallHandler {
    /// The MethodChannel that will the communication between Flutter and native Android
    ///
    /// This local reference serves to register the plugin with the Flutter Engine and unregister it
    /// when the Flutter Engine is detached from the Activity
    private lateinit var channel: MethodChannel
    private lateinit var context: Context
    override fun onAttachedToEngine(flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        context = flutterPluginBinding.applicationContext
        channel = MethodChannel(flutterPluginBinding.binaryMessenger, "com.kumpali.storage")
        channel.setMethodCallHandler(this)
    }

    override fun onMethodCall(call: MethodCall, result: Result) {
        when (call.method) {
            "gPV" -> {
                result.success("Android ${Build.VERSION.RELEASE}")
            }

            "gSDKV" -> {
                result.success(Build.VERSION.SDK_INT)
            }

            "gSTS" -> {
                result.success(getStorageTotalSpace())
            }

            "gSFS" -> {
                result.success(getStorageFreeSpace())
            }

//            "gSUS" -> {
//                result.success(getStorageUsableSpace())
//            }
//
//            "gESTS" -> {
//                result.success(getExternalStorageTotalSpace())
//            }
//
//            "gESFS" -> {
//                result.success(getExternalStorageFreeSpace())
//            }
//
//            "gESUS" -> {
//                result.success(getExternalStorageUsableSpace())
//            }
//
//            "gRTS" -> {
//                result.success(getRootTotalSpace())
//            }
//
//            "gRFS" -> {
//                result.success(getRootFreeSpace())
//            }
//
//            "gRUS" -> {
//                result.success(getRootUsableSpace())
//            }

            "gSDCP" -> {
                result.success(getSdCardPaths(context))
            }
             "iSDP"->{
                 result.success(isSdCardPresent(context))
             }
//            "gSDTS"->{
//                result.success(getSdCardTotalSpace(call.argument<String>("path").toString()))
//            }
//            "gSDFS"->{
//                result.success(getSdCardFreeSpace(call.argument<String>("path").toString()))
//            }
            else -> {
                result.notImplemented()
            }
        }
    }

    override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
    }

    private fun getStorageTotalSpace(): Long {
        return Environment.getDataDirectory().totalSpace
    }

    private fun getStorageFreeSpace(): Long {
        return Environment.getDataDirectory().freeSpace
    }

//    @SuppressLint("UsableSpace")
//    private fun getStorageUsableSpace(): Long {
//        return Environment.getDataDirectory().usableSpace
//    }
//
//    private fun getExternalStorageTotalSpace(): Long {
//        return Environment.getExternalStorageDirectory().totalSpace
//    }
//
//    private fun getExternalStorageFreeSpace(): Long {
//        return Environment.getRootDirectory().freeSpace
//    }
//
//    @SuppressLint("UsableSpace")
//    private fun getExternalStorageUsableSpace(): Long {
//        return Environment.getRootDirectory().usableSpace
//    }
//
//    private fun getRootTotalSpace(): Long {
//        return Environment.getRootDirectory().totalSpace
//    }
//
//    private fun getRootFreeSpace(): Long {
//        return Environment.getRootDirectory().freeSpace
//    }
//
//    @SuppressLint("UsableSpace")
//    private fun getRootUsableSpace(): Long {
//        return Environment.getRootDirectory().usableSpace
//    }

    fun isSdCardPresent(context: Context): Boolean {
        val storageManager = context.getSystemService(Context.STORAGE_SERVICE) as StorageManager
        val storageVolumes: List<StorageVolume> = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            storageManager.storageVolumes
        } else {
            return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
        }
        for (storageVolume in storageVolumes) {
            if (storageVolume.state == Environment.MEDIA_MOUNTED && storageVolume.isRemovable) {
                return true
            }
        }
        return false
    }

//    private fun getSDCard(): HashMap<String, Any> {
//        val sdCard = HashMap<String, Any>()
//        val file = File("/storage")
//        val files = file.listFiles()
//        if (files != null) {
//            for (f in files) {
//                if (f != null)
//                    if (f.isDirectory && f.name.contains("-")) {
//                        if(f.absolutePath != null) {
//                            sdCard["path"] = f.absolutePath
//                            sdCard["total"] = f.totalSpace
//                            sdCard["free"] = f.freeSpace
//                            break
//                        }
//                    }
//            }
//        }
//        if(!sdCard.containsKey("path")){
//            sdCard["path"] = ""
//            sdCard["total"] =0
//            sdCard["free"] = 0
//        }
//        return sdCard
//    }


    fun getSdCardPaths(context: Context): List<HashMap<String, Any>> {
        val sdCardList = mutableListOf<HashMap<String, Any>>()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // For SDK 30 and above
            val storageManager = context.getSystemService(Context.STORAGE_SERVICE) as StorageManager
            val storageVolumes = storageManager.storageVolumes
            for (storageVolume in storageVolumes) {
                val sdCard = HashMap<String, Any>()
                if (storageVolume.state == Environment.MEDIA_MOUNTED && storageVolume.isRemovable) {
                    var path  =storageVolume.directory?.path ?: ""
                    if(path.isNotEmpty()) {
                        sdCard["path"]=path
                        sdCard["total"] = File(path).totalSpace
                        sdCard["free"] = File(path).freeSpace
                        sdCardList.add(sdCard)
                    }
                }
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                // For SDK 24 and above, use StorageManager and StorageVolume
                val storageManager = context.getSystemService(Context.STORAGE_SERVICE) as StorageManager
                val storageVolumes = storageManager.storageVolumes

                for (storageVolume in storageVolumes) {
                    val path = storageVolume.javaClass.getMethod("getPath").invoke(storageVolume) as? String
                    if (storageVolume.state == Environment.MEDIA_MOUNTED && storageVolume.isRemovable) {
                        val sdCard = HashMap<String, Any>()
                        if(path != null && path.isNotEmpty()) {
                            sdCard["path"] = path
                            sdCard["total"] = File(path).totalSpace
                            sdCard["free"] = File(path).freeSpace
                            sdCardList.add(sdCard)
                        }
                    }
                }
            }
            else {
            // For SDK 21 to 23
            val externalDirs: Array<File> = ContextCompat.getExternalFilesDirs(context, null)
            for (file in externalDirs) {
                if (Environment.isExternalStorageRemovable(file)) {
                   val path = file.path
                    val sdCard = HashMap<String, Any>()
                    if(path != null && path.isNotEmpty()) {
                        sdCard["path"] = path
                        sdCard["total"] = File(path).totalSpace
                        sdCard["free"] = File(path).freeSpace
                        sdCardList.add(sdCard)
                    }
                }
            }
        }

        return sdCardList
    }

//    fun getSdCardFreeSpace(sdCardPath: String): Long {
//        val file = File(sdCardPath)
//        return file.freeSpace
//    }
//
//    fun getSdCardTotalSpace(sdCardPath: String): Long {
//        val file = File(sdCardPath)
//        return file.totalSpace
//    }



//    private fun getTotalDiskSpaceForPath(path: String): Double {
//        val stat = StatFs(path)
//        val bytesAvailable: Long = stat.blockSizeLong * stat.availableBlocksLong
//        return (bytesAvailable / (1024f * 1024f)).toDouble()
//    }
//
//    private fun getFreeDiskSpaceForPath(path: String): Double {
//        val stat = StatFs(path)
//        val bytesAvailable: Long = stat.blockSizeLong * stat.availableBlocksLong
//        return (bytesAvailable / (1024f * 1024f)).toDouble()
//    }

}