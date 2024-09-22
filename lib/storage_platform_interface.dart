import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'storage_method_channel.dart';

abstract class StoragePlatform extends PlatformInterface {
  /// Constructs a StoragePlatform.
  StoragePlatform() : super(token: _token);

  static final Object _token = Object();

  static StoragePlatform _instance = MethodChannelStorage();

  /// The default instance of [StoragePlatform] to use.
  ///
  /// Defaults to [MethodChannelStorage].
  static StoragePlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [StoragePlatform] when
  /// they register themselves.
  static set instance(StoragePlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<String?> getPlatformVersion() {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }

  Future<int?> getStorageTotalSpace() {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }
  Future<int?> getStorageFreeSpace() {
    throw UnimplementedError('getStorageTotalSpace() has not been implemented.');
  }
    Future<int?> getStorageUsableSpace() {
    throw UnimplementedError('getStorageUsableSpace() has not been implemented.');
  }
  Future<int?> getExternalStorageTotalSpace() {
    throw UnimplementedError('getExternalStorageTotalSpace() has not been implemented.');
  }
  Future<int?> getExternalStorageFreeSpace() {
    throw UnimplementedError('getExternalStorageFreeSpace() has not been implemented.');
  }
  Future<int?> getExternalStorageUsableSpace() {
    throw UnimplementedError('getExternalStorageUsableSpace() has not been implemented.');
  }
   Future<int?> getRootTotalSpace() {
    throw UnimplementedError('getRootTotalSpace() has not been implemented.');
  }
  Future<int?> getRootFreeSpace() {
    throw UnimplementedError('getRootFreeSpace() has not been implemented.');
  }
   Future<String?> getSDCard() {
    throw UnimplementedError('getSDCard() has not been implemented.');
  }
  Future<int?> getRootUsableSpace() {
    throw UnimplementedError('getRootUsableSpace() has not been implemented.');
  }
}
