package com.ahmedelouha.telfaza.matches.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.ahmedelouha.telfaza.R;
import com.ahmedelouha.telfaza.matchdetail.MatchDetailActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

/**
 * Created by raaja on 29-12-2017.
 */

public class MatchNotificationService extends FirebaseMessagingService {
    public static final String TYPE_NOTIF_BROWSER ="browser";
    public static final String TYPE_NOTIF_MARKET ="market";
    public static final String TYPE_NOTIF_MATCH ="match";
    public static final String TYPE_NOTIF_NORMAL="normal";



    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if(remoteMessage.getData().size()>0){
            for(Map.Entry<String,String> entry:remoteMessage.getData().entrySet()){
                String key = entry.getKey();
                String value = entry.getValue();
                String title = "Telfaza";
                String message = "Open to watch live matches";
                if(remoteMessage.getNotification() !=null){
                    RemoteMessage.Notification notification = remoteMessage.getNotification();
                    if(notification.getTitle()!=null){
                        title = notification.getTitle();
                    }
                    if(notification.getBody()!=null){
                        message = notification.getBody();
                    }
                }
                sendNotification(key,value,title,message);
            }
        }
    }

    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();
    }

    void sendNotification(String key,String value,String title,String message){
        Intent notifIntent = getIntent(key,value);
        if(notifIntent==null){
            return;
        }
        PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(),23,notifIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder bldr = new NotificationCompat.Builder(getBaseContext());
        bldr.setContentTitle(title);
        bldr.setContentText(message)
                .setOngoing(false)
                .setOnlyAlertOnce(true)
                .setAutoCancel(true)
                .setLargeIcon(getLauncherIcon())
                .setContentIntent(pendingIntent)
                .setColor(ContextCompat.getColor(getBaseContext(),R.color.colorPrimary));
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            bldr.setSmallIcon(R.mipmap.ic_launcher_round);
        }else{
            bldr.setSmallIcon(R.mipmap.ic_launcher);
        }

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(54236589,bldr.build());
    }

    Bitmap getLauncherIcon(){
        return BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
    }

    Intent getIntent(String key,String value){
        Intent notifIntent;
        if(key.equals(TYPE_NOTIF_BROWSER)){
            notifIntent = new Intent(Intent.ACTION_VIEW);
            try {
                notifIntent.setData(Uri.parse(URLDecoder.decode(value, "UTF-8")));
                List<ResolveInfo> queryList = queryIntent(notifIntent);
                if (queryList.isEmpty()) {
                    notifIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://search?q=browser"));
                    List<ResolveInfo> querMarketList = queryIntent(notifIntent);
                    if (querMarketList.isEmpty()) {
                        return null;
                    }
                }
            }catch (UnsupportedEncodingException e){
                e.printStackTrace();
            }
            return notifIntent;
        }else
        if(key.equals(TYPE_NOTIF_MARKET)){
            notifIntent = new Intent(Intent.ACTION_VIEW,Uri.parse("market://search?q="+value));
            List<ResolveInfo> queryList = queryIntent(notifIntent);
            if(queryList.isEmpty()){
                notifIntent = new Intent(Intent.ACTION_VIEW,Uri.parse("http://play.google.com/store/search?q="+value));
                List<ResolveInfo> queryBrowserlist = queryIntent(notifIntent);
                if(queryBrowserlist.isEmpty()){
                    return null;
                }
            }
            return notifIntent;
        }else
        if(key.equals(TYPE_NOTIF_MATCH)){
            notifIntent = new Intent(getBaseContext(), MatchDetailActivity.class);
            notifIntent.putExtra(MatchDetailActivity.MATCH_ID,value);
            notifIntent.putExtra(MatchDetailActivity.INTENT_TYPE,MatchDetailActivity.INTENT_TYPE_NOTIFICATION);
            notifIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            return notifIntent;
        }else{
            notifIntent = getPackageManager().getLaunchIntentForPackage("com.ahmedelouha.telfaza");
            notifIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            return notifIntent;
        }

    }

    List<ResolveInfo> queryIntent(Intent intent){
        return getPackageManager().queryIntentActivities(intent, PackageManager.GET_META_DATA);
    }
}
