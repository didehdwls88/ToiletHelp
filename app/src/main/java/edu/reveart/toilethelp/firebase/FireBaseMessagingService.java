package edu.reveart.toilethelp.firebase;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import edu.reveart.toilethelp.ListActivity;
import edu.reveart.toilethelp.MainActivity;
import edu.reveart.toilethelp.R;

public class FireBaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage.getData().size() > 0) {

            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

            if (true) {

            } else {

                handleNow();
            }
        }

        if (remoteMessage.getNotification() != null) {

            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());

            sendNotification(remoteMessage.getNotification().getBody());

        }
    }

    private void handleNow() {

        Log.d(TAG, "Short lived task is done.");
    }

    private void sendNotification(String messageBody) {

        NotificationManager notificationManager =

                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Intent intent = new Intent(this, ListActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,

                PendingIntent.FLAG_ONE_SHOT);



        String channelId = getString(R.string.default_notification_channel_id);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            String channelName = getString(R.string.default_notification_channel_name);

            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);

            notificationManager.createNotificationChannel(channel);

        }

        NotificationCompat.Builder notificationBuilder =

                new NotificationCompat.Builder(this, channelId)

                        .setSmallIcon(R.drawable.ic_wc_blue_24dp)

                        .setContentTitle("새로운 도움 요청이 있습니다!")

                        .setContentText("이동하기")

                        .setAutoCancel(true)

                        .setSound(defaultSoundUri)

                        .setColor(0x00b3ff)

                        .setContentIntent(pendingIntent);

        notificationManager.notify(0, notificationBuilder.build());

    }
}
