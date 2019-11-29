package com.benlefevre.endometriosismonitoring.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.navigation.NavDeepLinkBuilder;
import androidx.work.Data;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.benlefevre.endometriosismonitoring.R;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import static com.benlefevre.endometriosismonitoring.utils.Constants.CONDI;
import static com.benlefevre.endometriosismonitoring.utils.Constants.DOSAGE;
import static com.benlefevre.endometriosismonitoring.utils.Constants.PILL;
import static com.benlefevre.endometriosismonitoring.utils.Constants.TREATMENT;
import static com.benlefevre.endometriosismonitoring.utils.Constants.TREATMENT_NAME;

public class NotificationWorker extends Worker {

    private Context mContext;

    public NotificationWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Data data = getInputData();
        String treatment = data.getString(TREATMENT);
        mContext = getApplicationContext();
        if (treatment != null && treatment.equals(PILL))
            sendPillNotification();
        else
            sendTreatmentNotification(data);
        return Result.success();
    }

    /**
     * Sends a notification with the right treatment information.
     * @param data the data that contains values needed to setup the notification fields
     */
    private void sendTreatmentNotification(Data data) {
        PendingIntent pendingIntent = new NavDeepLinkBuilder(mContext)
                .setGraph(R.navigation.nav_graph)
                .setDestination(R.id.treatmentFragment)
                .createPendingIntent();

        String channelId = "Treatment notification";
        String channelName = "Treatment Channel";

        NotificationManager manager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
            if (manager != null)
                manager.createNotificationChannel(notificationChannel);
        }

        NotificationCompat.Builder notification = new NotificationCompat.Builder(mContext, channelId)
                .setContentTitle(mContext.getString(R.string.forget_treatment,data.getString(TREATMENT_NAME)))
                .setContentText(mContext.getString(R.string.you_must_take,data.getString(DOSAGE),data.getString(CONDI)))
                .setSmallIcon(R.drawable.girl2)
                .setAutoCancel(false)
                .setContentIntent(pendingIntent);
        if (manager != null)
            manager.notify(2, notification.build());
    }

    /**
     * Sends a notification with pill information.
     */
    private void sendPillNotification() {
        PendingIntent pendingIntent = new NavDeepLinkBuilder(mContext)
                .setGraph(R.navigation.nav_graph)
                .setDestination(R.id.treatmentFragment)
                .createPendingIntent();

        String channelId = "Pill notification";
        String channelName = "Pill Channel";

        NotificationManager manager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
            if (manager != null)
                manager.createNotificationChannel(notificationChannel);
        }

        NotificationCompat.Builder notification = new NotificationCompat.Builder(mContext, channelId)
                .setContentTitle(mContext.getString(R.string.forget_pill))
                .setContentText(mContext.getString(R.string.check_pill))
                .setSmallIcon(R.drawable.girl2)
                .setAutoCancel(false)
                .setContentIntent(pendingIntent);
        if (manager != null)
            manager.notify(1, notification.build());
    }

    /**
     * Setups a PeriodicWorkRequest for send pill notification
     * @param context the context of the call
     * @param data the needed value to set notification's fields
     * @param hour the user's time choice
     */
    public static void configurePillNotification(Context context, Data data, String hour){
        PeriodicWorkRequest pillWorker = new PeriodicWorkRequest.Builder(NotificationWorker.class, 1, TimeUnit.DAYS)
                .setInputData(data)
                .setInitialDelay(setDelayDuration(hour), TimeUnit.MILLISECONDS)
                .addTag(PILL)
                .build();
        WorkManager manager = WorkManager.getInstance(context);
        manager.enqueue(pillWorker);
    }

    /**
     * Setups a PeriodicWorkRequest for send treatment notification
     * @param context the context of the call
     * @param data the needed value to set notification's fields
     * @param hour the user's time choice
     * @param tag the work tag to allows to cancel a work by its tag
     */
    public static void configureTreatmentNotification(Context context, Data data, String hour,String tag){
        PeriodicWorkRequest treatmentWorker = new PeriodicWorkRequest.Builder(NotificationWorker.class, 1, TimeUnit.DAYS)
                .setInputData(data)
                .setInitialDelay(setDelayDuration(hour), TimeUnit.MILLISECONDS)
                .addTag(tag)
                .build();
        WorkManager manager = WorkManager.getInstance(context);
        manager.enqueue(treatmentWorker);
    }


    /**
     * Computes the remaining time before the user's time choice to set the initial delay of PeriodicWorkRequest
     * @param hour the user's time choice
     * @return a long that is the difference between now and the selected time
     */
    private static long setDelayDuration(String hour) {
        long duration;
        Calendar now = Calendar.getInstance();
        Calendar sendHour = Calendar.getInstance();
        if (hour != null) {
            sendHour.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hour.substring(0, 2)));
            sendHour.set(Calendar.MINUTE, Integer.parseInt(hour.substring(3)));
        }
        if (now.after(sendHour)) {
            sendHour.add(Calendar.DAY_OF_YEAR, 1);
            duration = sendHour.getTimeInMillis() - System.currentTimeMillis();
            Log.i("worker", "setDelayDuration: " + duration);
        } else {
            duration = sendHour.getTimeInMillis() - System.currentTimeMillis();
            Log.i("worker", "setDelayDuration: " + duration);
        }
        return duration;
    }
}
