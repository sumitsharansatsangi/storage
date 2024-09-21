import 'package:flutter_test/flutter_test.dart';
import 'package:storage/storage.dart';
import 'package:storage/storage_platform_interface.dart';
import 'package:storage/storage_method_channel.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

class MockStoragePlatform
    with MockPlatformInterfaceMixin
    implements StoragePlatform {

  @override
  Future<String?> getPlatformVersion() => Future.value('42');
  
  @override
  Future<int?> getExternalStorageFreeSpace() {
    // TODO: implement getExternalStorageFreeSpace
    throw UnimplementedError();
  }
  
  @override
  Future<int?> getExternalStorageTotalSpace() {
    // TODO: implement getExternalStorageTotalSpace
    throw UnimplementedError();
  }
  
  @override
  Future<int?> getStorageFreeSpace() {
    // TODO: implement getStorageFreeSpace
    throw UnimplementedError();
  }
  
  @override
  Future<int?> getStorageTotalSpace() {
    // TODO: implement getStorageTotalSpace
    throw UnimplementedError();
  }
  
  @override
  Future<int?> getExternalStorageUsableSpace() {
    // TODO: implement getExternalStorageUsableSpace
    throw UnimplementedError();
  }
  
  @override
  Future<int?> getRootFreeSpace() {
    // TODO: implement getRootFreeSpace
    throw UnimplementedError();
  }
  
  @override
  Future<int?> getRootTotalSpace() {
    // TODO: implement getRootTotalSpace
    throw UnimplementedError();
  }
  
  @override
  Future<int?> getRootUsableSpace() {
    // TODO: implement getRootUsableSpace
    throw UnimplementedError();
  }
  
  @override
  Future<int?> getStorageUsableSpace() {
    // TODO: implement getStorageUsableSpace
    throw UnimplementedError();
  }
  
  @override
  Future<List<StorageInfo>> getStorageInfo() {
    // TODO: implement getStorageInfo
    throw UnimplementedError();
  }
}

void main() {
  final StoragePlatform initialPlatform = StoragePlatform.instance;

  test('$MethodChannelStorage is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelStorage>());
  });

  test('getPlatformVersion', () async {
    Storage storagePlugin = Storage();
    MockStoragePlatform fakePlatform = MockStoragePlatform();
    StoragePlatform.instance = fakePlatform;

    expect(await storagePlugin.getPlatformVersion(), '42');
  });
}
