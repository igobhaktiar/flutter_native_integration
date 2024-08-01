import 'package:flutter/services.dart';
import 'package:get/get.dart';

class IntegrationController extends GetxController {
  static const platform =
      MethodChannel('com.example.flutter_native_integration/device_info');
  var deviceInfo = 'Unknown'.obs;
  var videoInfo = 'Unknown'.obs;

  Future<void> getDeviceInfo() async {
    try {
      final String result = await platform.invokeMethod('getDeviceInfo');
      deviceInfo.value = result;
    } on PlatformException catch (e) {
      deviceInfo.value = "Failed to get device info: '${e.message}'.";
    }
  }

  Future<void> getVideoInfo() async {
    try {
      final String result = await platform.invokeMethod('getVideoInfo');
      videoInfo.value = result;
    } on PlatformException catch (e) {
      videoInfo.value = "Failed to get video info: '${e.message}'.";
    }
  }
}
