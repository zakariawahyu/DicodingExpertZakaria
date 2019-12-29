package com.zakariawahyu.submissionexpert;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.os.Bundle;
import android.widget.CompoundButton;

import com.zakariawahyu.submissionexpert.reminder.AlarmReceiver;
import com.zakariawahyu.submissionexpert.reminder.AppPreference;

public class ReminderSetting extends AppCompatActivity {

    private AppPreference appPreference;
    private AlarmReceiver alarmReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_setting);

        alarmReceiver = new AlarmReceiver();

        SwitchCompat switchDailyReminder = findViewById(R.id.switch_daily_reminder);
        SwitchCompat switchReleaseTodayReminder = findViewById(R.id.switch_release_today_reminder);

        appPreference = new AppPreference(getApplicationContext());

        boolean isDailyReminderActivated = appPreference.getAppDailyReminder();
        boolean isReleaseTodayReminderActivated = appPreference.getAppReleaseTodayReminder();

        if (isDailyReminderActivated)
            switchDailyReminder.setChecked(true);

        if (isReleaseTodayReminderActivated)
            switchReleaseTodayReminder.setChecked(true);

        switchDailyReminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    appPreference.setAppDailyReminder(true);
                    alarmReceiver.setRepeatingAlarm(getApplicationContext(), AlarmReceiver.DAILY_REMINDER, "07:00");
                } else {
                    appPreference.setAppDailyReminder(false);
                    alarmReceiver.cancelAlarm(getApplicationContext(), AlarmReceiver.DAILY_REMINDER);
                }
            }
        });

        switchReleaseTodayReminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    appPreference.setAppReleaseTodayReminder(true);
                    alarmReceiver.setRepeatingAlarm(getApplicationContext(), AlarmReceiver.RELEASE_REMINDER, "08:00");
                } else {
                    appPreference.setAppReleaseTodayReminder(false);
                    alarmReceiver.cancelAlarm(getApplicationContext(), AlarmReceiver.RELEASE_REMINDER);
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
