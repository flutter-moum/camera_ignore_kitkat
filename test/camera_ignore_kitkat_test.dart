import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:camera_ignore_kitkat/camera_ignore_kitkat.dart';

void main() {
  const MethodChannel channel = MethodChannel('camera_ignore_kitkat');

  TestWidgetsFlutterBinding.ensureInitialized();

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    expect(await CameraIgnoreKitkat.platformVersion, '42');
  });
}
