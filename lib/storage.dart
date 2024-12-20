import 'model.dart';
import 'storage_platform_interface.dart';

class Storage {
  Future<String?> getPlatformVersion() {
    return StoragePlatform.instance.getPlatformVersion();
  }

  Future<int?> getSDKIntVersion() {
    return StoragePlatform.instance.getSDKIntVersion();
  }


  Future<int?> getStorageTotalSpace() {
    return StoragePlatform.instance.getStorageTotalSpace();
  }

  Future<int?> getStorageFreeSpace() {
    return StoragePlatform.instance.getStorageFreeSpace();
  }

  // Future<int?> getStorageUsableSpace() {
  //   return StoragePlatform.instance.getStorageUsableSpace();
  // }

  // Future<int?> getExternalStorageTotalSpace() {
  //   return StoragePlatform.instance.getExternalStorageTotalSpace();
  // }

  // Future<int?> getExternalStorageFreeSpace() {
  //   return StoragePlatform.instance.getExternalStorageFreeSpace();
  // }

  // Future<int?> getExternalStorageUsableSpace() {
  //   return StoragePlatform.instance.getExternalStorageUsableSpace();
  // }

  // Future<int?> getRootTotalSpace() {
  //   return StoragePlatform.instance.getRootTotalSpace();
  // }

  // Future<int?> getRootFreeSpace() {
  //   return StoragePlatform.instance.getRootFreeSpace();
  // }

  // Future<int?> getRootUsableSpace() {
  //   return StoragePlatform.instance.getRootUsableSpace();
  // }
  
  Future<List<SDCard>?> getSDCard() {
    return StoragePlatform.instance.getSDCard();
  }

  Future<bool?> isSDCardPresent() {
    return StoragePlatform.instance.isSDCardPresent();
  }

  // Future<int?> getSdCardFreeSpace(String path){
  //   return StoragePlatform.instance.getSdCardFreeSpace(path);
  // }

  // Future<int?> getSdCardTotalSpace(String path){
  //   return StoragePlatform.instance.getSdCardTotalSpace(path);
  // }

}
