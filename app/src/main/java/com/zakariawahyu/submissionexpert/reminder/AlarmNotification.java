package com.zakariawahyu.submissionexpert.reminder;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;


import com.zakariawahyu.submissionexpert.MainActivity;
import com.zakariawahyu.submissionexpert.R;
import com.zakariawahyu.submissionexpert.film.ItemFilm;

import java.util.ArrayList;

import static com.zakariawahyu.submissionexpert.reminder.AlarmReceiver.ID_DAILY_REMINDER;
import static com.zakariawahyu.submissionexpert.reminder.AlarmReceiver.ID_RELEASE_REMINDER;
import static com.zakariawahyu.submissionexpert.reminder.AlarmReceiver.ID_RELEASE_REMINDER_DIFF;

public class AlarmNotification {

	static void showDailyReminderNotification(Context context) {
		String CHANNEL_ID = "Channel_1";
		String CHANNEL_NAME = "Daily Reminder Channel";

		String title = context.getString(R.string.daily_reminder);
		String message = context.getString(R.string.daily_notif_message);

		Intent intent = new Intent(context, MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
		PendingIntent pendingIntent = PendingIntent.getActivity(context, ID_RELEASE_REMINDER, intent, PendingIntent.FLAG_UPDATE_CURRENT);

		NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
				.setSmallIcon(R.drawable.ic_local_movies_black_24dp)
				.setContentTitle(title)
				.setContentText(message)
				.setColor(ContextCompat.getColor(context, android.R.color.black))
				.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
				.setSound(alarmSound)
				.setContentIntent(pendingIntent)
				.setAutoCancel(true);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);

			channel.enableVibration(true);
			channel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});

			builder.setChannelId(CHANNEL_ID);

			if (notificationManagerCompat != null)
				notificationManagerCompat.createNotificationChannel(channel);
		}

		android.app.Notification notification = builder.build();

		if (notificationManagerCompat != null)
			notificationManagerCompat.notify(ID_DAILY_REMINDER, notification);
	}

	public static void onReceiveReleaseToday(Context context, ArrayList<ItemFilm> items) {
		for (int i = 0; i < items.size(); ++i) {
			String title = items.get(i).getJudul();
			String message = String.format(context.getString(R.string.has_been_release), title);
			showReleaseReminderNotification(context, title, message);
		}

		// Reset ulang
		ID_RELEASE_REMINDER -= ID_RELEASE_REMINDER_DIFF;
		ID_RELEASE_REMINDER_DIFF = 0;
	}

	private static void showReleaseReminderNotification(Context context, String title, String message) {
		String CHANNEL_ID = "Channel_2";
		String CHANNEL_NAME = "Release Today Reminder Channel";

		NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
				.setSmallIcon(R.drawable.ic_local_movies_black_24dp)
				.setContentTitle(title)
				.setContentText(message)
				.setColor(ContextCompat.getColor(context, android.R.color.black))
				.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
				.setSound(alarmSound)
				.setAutoCancel(true);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);

			channel.enableVibration(true);
			channel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});

			builder.setChannelId(CHANNEL_ID);

			if (notificationManagerCompat != null)
				notificationManagerCompat.createNotificationChannel(channel);
		}

		android.app.Notification notification = builder.build();

		if (notificationManagerCompat != null) {
			notificationManagerCompat.notify(ID_RELEASE_REMINDER++, notification);
			++ID_RELEASE_REMINDER_DIFF;
		}
	}
}
