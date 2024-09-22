import 'dart:math';

import 'package:flutter/services.dart';

import 'storage_platform_interface.dart';

/// An implementation of [StoragePlatform] that uses method channels.
class MethodChannelStorage extends StoragePlatform {
  /// The method channel used to interact with the native platform.
  // @visibleForTesting
  final methodChannel = const MethodChannel('com.kumpali.storage');

  @override
  Future<String?> getPlatformVersion() async {
    return await methodChannel.invokeMethod<String>('gPV');
  }

  @override
  Future<int?> getStorageTotalSpace() async {
    return await methodChannel.invokeMethod<int>('gSTS');
  }

  @override
  Future<int?> getStorageFreeSpace() async {
    return await methodChannel.invokeMethod<int>('gSFS');
  }

  @override
  Future<int?> getStorageUsableSpace() async {
    return await methodChannel.invokeMethod<int>('gSUS');
  }

  @override
  Future<int?> getExternalStorageTotalSpace() async {
    return await methodChannel.invokeMethod<int>('gESTS');
  }

  @override
  Future<int?> getExternalStorageFreeSpace() async {
    return await methodChannel.invokeMethod<int>('gESFS');
  }

  @override
  Future<int?> getExternalStorageUsableSpace() async {
    return await methodChannel.invokeMethod<int>('gESUS');
  }

  @override
  Future<int?> getRootTotalSpace() async {
    return await methodChannel.invokeMethod<int>('gRTS');
  }

  @override
  Future<int?> getRootFreeSpace() async {
    return await methodChannel.invokeMethod<int>('gRFS');
  }

  @override
  Future<int?> getRootUsableSpace() async {
    return await methodChannel.invokeMethod<int>('gRUS');
  }

  @override
  Future<String?> getSDCard() async {
    return await methodChannel.invokeMethod<String>('gSDC');
  }

  @override
  Future<List<StorageInfo>> getStorageInfo() async {
    List reply = await methodChannel.invokeMethod('gESAD', []);
    return reply
        .map((storageInfoMap) => StorageInfo.fromJson(storageInfoMap))
        .toList();
  }
}

class StorageInfo {
  final String appFilesDir;
  final int availableBytes;
  int get availableGB => availableBytes ~/ pow(2, 30);
  int get availableMB => availableBytes ~/ pow(2, 20);

  String get rootDir => appFilesDir
      .split("/")
      .sublist(0, appFilesDir.split("/").length - 4)
      .join("/");

  StorageInfo(this.appFilesDir, this.availableBytes);

  factory StorageInfo.fromJson(Map json) {
    return StorageInfo(json["path"], json["availableBytes"]);
  }
  //get root with: reply[0]["path"].split("/").sublist(0,reply[0]["path"].split("/").length-4).join("/");
}
