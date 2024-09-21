import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:storage/storage.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({super.key});

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _platformVersion = 'Unknown';
  final _storagePlugin = Storage();
  int storageTotalSpace = 0;
  int storageFreeSpace = 0;
  int storageUsableSpace = 0;
  int externalStorageTotalSpace = 0;
  int externalStorageFreeSpace = 0;
  int externalStorageUsableSpace = 0;
  int rootTotalSpace = 0;
  int rootFreeSpace = 0;
  int rootUsableSpace = 0;

  @override
  void initState() {
    super.initState();
    initPlatformState();
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> initPlatformState() async {
    String platformVersion;
    // Platform messages may fail, so we use a try/catch PlatformException.
    // We also handle the message potentially returning null.
    try {
      platformVersion = await _storagePlugin.getPlatformVersion() ??
          'Unknown platform version';
      storageTotalSpace = await _storagePlugin.getStorageTotalSpace() ?? 0;
      storageFreeSpace = await _storagePlugin.getStorageFreeSpace() ?? 0;
      storageUsableSpace = await _storagePlugin.getStorageUsableSpace() ?? 0;
      externalStorageTotalSpace =
          await _storagePlugin.getExternalStorageTotalSpace() ?? 0;
      externalStorageFreeSpace =
          await _storagePlugin.getExternalStorageFreeSpace() ?? 0;
      externalStorageUsableSpace =
          await _storagePlugin.getExternalStorageUsableSpace() ?? 0;
      rootTotalSpace = await _storagePlugin.getRootTotalSpace() ?? 0;
      rootFreeSpace = await _storagePlugin.getRootFreeSpace() ?? 0;
      rootUsableSpace = await _storagePlugin.getRootUsableSpace() ?? 0;
    } on PlatformException {
      platformVersion = 'Failed to get platform version.';
    }

    // If the widget was removed from the tree while the asynchronous platform
    // message was in flight, we want to discard the reply rather than calling
    // setState to update our non-existent appearance.
    if (!mounted) return;

    setState(() {
      _platformVersion = platformVersion;
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: Column(
            children: [
              Text('Running on: $_platformVersion\n'),
              Text(
                  'Total Space: $storageTotalSpace ** ${storageTotalSpace / 1000000}\n'),
              Text(
                  'Free Space: $storageFreeSpace ** ${storageFreeSpace / 1000000}\n'),
              Text(
                  'Usable Space: $storageUsableSpace ** ${storageUsableSpace / 1000000}\n'),
              Text(
                  'External total: $externalStorageTotalSpace ** ${externalStorageTotalSpace / 1000000}\n'),
              Text(
                  'External free: $externalStorageFreeSpace ** ${externalStorageFreeSpace / 1000000}\n'),
              Text(
                  'External usable: $externalStorageUsableSpace ** ${externalStorageUsableSpace / 1000000}\n'),
              Text(
                  'Root tOTAL: $rootTotalSpace ** ${rootTotalSpace / 1000000}\n'),
              Text('Root free: $rootFreeSpace ** ${rootFreeSpace / 1000000}\n'),
              Text(
                  'Root usable: $rootUsableSpace ** ${rootUsableSpace / 1000000}\n'),
            ],
          ),
        ),
      ),
    );
  }
}
