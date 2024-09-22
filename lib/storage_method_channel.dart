import 'package:flutter/services.dart';

import 'model.dart';
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
  Future<int?> getSDKIntVersion() async {
    return await methodChannel.invokeMethod<int>('gSDKV');
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
  Future<SDCard> getSDCard() async {
    final sdCard = await methodChannel.invokeMethod('gSDC', []);
    return SDCard.fromJson(sdCard);
  }
}
