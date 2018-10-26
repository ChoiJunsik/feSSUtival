package com.example.junsikchoi.ssu_festival;

import android.app.Notification;
import android.content.Context;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import android.os.Handler;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mFirebaseDatabaseReference;
    private String mUsername;
    private String storeName;

    @Override

    public void onMessageReceived(final RemoteMessage remoteMessage) {
        mFirebaseDatabaseReference= FirebaseDatabase.getInstance().getReference();
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mUsername = mFirebaseUser.getDisplayName();
        super.onMessageReceived(remoteMessage);
        if (remoteMessage.getNotification() != null) {

            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            long millisecond = 1500;
            vibrator.vibrate(millisecond);
            DatabaseReference userDB = mFirebaseDatabaseReference.child("Users").child(mUsername).child("store");
            userDB.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    storeName = (String) snapshot.getValue();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                        String body = remoteMessage.getNotification().getBody();
                        public void run() {
                            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext())
                                    .setSmallIcon(R.mipmap.ic_launcher) // 알림 영역에 노출 될 아이콘.
                                    .setContentTitle(storeName) // 알림 영역에 노출 될 타이틀
                                    .setSound(defaultSoundUri)
                                    .setPriority(Notification.PRIORITY_HIGH)
                                    .setContentText(body); // Firebase Console 에서 사용자가 전달한 메시지내용

                            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
                            notificationManagerCompat.notify(0x1001, notificationBuilder.build());

                            mFirebaseDatabaseReference.child("Users").child(mUsername).setValue(null);

                        }
                    }, 1000);

                }
                public void onCancelled (DatabaseError databaseError){
                }

            });

        }
    }

}
