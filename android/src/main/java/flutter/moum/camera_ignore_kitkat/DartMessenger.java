package flutter.moum.camera_ignore_kitkat;

import android.os.Build;
import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.HashMap;
import java.util.Map;

import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.EventChannel;
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
class DartMessenger {
    @Nullable private EventChannel.EventSink eventSink;

    enum EventType {
        ERROR,
        CAMERA_CLOSING,
    }

    DartMessenger(BinaryMessenger messenger, long eventChannelId) {
        new EventChannel(messenger, "flutter.io/cameraPlugin/cameraEvents" + eventChannelId)
                .setStreamHandler(
                        new EventChannel.StreamHandler() {
                            @Override
                            public void onListen(Object arguments, EventChannel.EventSink sink) {
                                eventSink = sink;
                            }

                            @Override
                            public void onCancel(Object arguments) {
                                eventSink = null;
                            }
                        });
    }

    void sendCameraClosingEvent() {
        send(EventType.CAMERA_CLOSING, null);
    }

    void send(EventType eventType, @Nullable String description) {
        if (eventSink == null) {
            return;
        }

        Map<String, String> event = new HashMap<>();
        event.put("eventType", eventType.toString().toLowerCase());
        // Only errors have a description.
        if (eventType == EventType.ERROR && !TextUtils.isEmpty(description)) {
            event.put("errorDescription", description);
        }
        eventSink.success(event);
    }
}