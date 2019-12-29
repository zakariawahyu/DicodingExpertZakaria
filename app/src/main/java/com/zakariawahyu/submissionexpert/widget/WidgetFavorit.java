package com.zakariawahyu.submissionexpert.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.zakariawahyu.submissionexpert.R;

/**
 * Implementation of App Widget functionality.
 */
public class WidgetFavorit extends AppWidgetProvider {

    private static final String TOAST_ACTION = "com.zakariawahyu.submissionexpert.TOAST_ACTION";
    public static final String EXTRA_NAME = "com.zakariawahyu.submissionexpert.EXTRA_ITEM";
    public static final String UPDATE_WIDGET = "com.zakariawahyu.submissionexpert.UPDATE_WIDGET";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        Intent intent = new Intent(context, StackWidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_favorit);
        views.setRemoteAdapter(R.id.stack_view, intent);
        views.setEmptyView(R.id.stack_view, R.id.empty_favorites);

        Intent toastIntent = new Intent(context, WidgetFavorit.class);
        toastIntent.setAction(WidgetFavorit.TOAST_ACTION);
        toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        PendingIntent toastPendingIntent = PendingIntent.getBroadcast(context, 0, toastIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.stack_view, toastPendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        Log.d(this.getClass().getSimpleName(), "onUpdate: EXEC");
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (intent.getAction() != null) {
            if (intent.getAction().equals(TOAST_ACTION)) {
                String viewIndex = intent.getStringExtra(EXTRA_NAME);
                Toast.makeText(context, viewIndex, Toast.LENGTH_SHORT).show();
            } else if (intent.getAction().equals(UPDATE_WIDGET)){
                AppWidgetManager gm = AppWidgetManager.getInstance(context);
                int[] ids = gm.getAppWidgetIds(new ComponentName(context, WidgetFavorit.class));

                gm.notifyAppWidgetViewDataChanged(ids ,R.id.stack_view);
            }
        }
    }
}

