import 'package:flutter/material.dart';
import 'package:flutter_native_integration/controller/controller.dart';
import 'package:get/get.dart';

class HomeScreen extends StatelessWidget {
  HomeScreen({super.key});

  final IntegrationController controller = Get.put(IntegrationController());

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Flutter Native Integration'),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            ElevatedButton(
              onPressed: () => controller.getDeviceInfo(),
              child: const Text('Get Device Info'),
            ),
            const SizedBox(height: 16),
            Obx(
              () => Text('Device Info: ${controller.deviceInfo.value}'),
            ),
            const SizedBox(height: 32),
            ElevatedButton(
              onPressed: () => controller.getVideoInfo(),
              child: const Text('Get Video Info'),
            ),
            const SizedBox(height: 16),
            Obx(
              () => Text('Video Info: ${controller.videoInfo.value}'),
            ),
          ],
        ),
      ),
    );
  }
}
