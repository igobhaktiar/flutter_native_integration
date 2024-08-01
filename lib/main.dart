import 'package:flutter/material.dart';
import 'package:flutter_native_integration/page/HomeScreen.dart';
import 'package:get/get.dart';

void main() {
  runApp(const MainApp());
}

class MainApp extends StatelessWidget {
  const MainApp({super.key});

  @override
  Widget build(BuildContext context) {
    return GetMaterialApp(
      title: 'Flutter Native Integration',
      home: HomeScreen(),
    );
  }
}
