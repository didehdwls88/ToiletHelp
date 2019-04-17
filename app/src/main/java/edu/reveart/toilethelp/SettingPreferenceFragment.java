package edu.reveart.toilethelp;



import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;

import static android.widget.Toast.*;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingPreferenceFragment extends PreferenceFragment {

    SharedPreferences Prefs;
    SwitchPreference sPush, sSound, sVibe, sDistance;
    ListPreference lDistance;
    SharedPreferences.Editor editor;

    @Override
    public void onStart() {

        super.onStart();
        Prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editor = Prefs.edit();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.setting_preference);

        sPush = (SwitchPreference) findPreference("switchPush");

        if (sPush != null) {

            sPush.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference arg0, Object newValue) {

                    boolean alarmPushOn = ((Boolean)newValue).booleanValue();

                    if (alarmPushOn)
                        FirebaseMessaging.getInstance().subscribeToTopic("push").addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                            }
                        });
                    else
                        FirebaseMessaging.getInstance().unsubscribeFromTopic("push");

                    editor.putBoolean("switchPush", alarmPushOn);
                    editor.apply();

                    return true;
                }
            });
        }

        sSound = (SwitchPreference) findPreference("switchSound");

        if (sSound != null) {

            sSound.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference arg0, Object newValue) {

                    boolean soundPushOn = ((Boolean)newValue).booleanValue();

                    editor.putBoolean("switchSound", soundPushOn);
                    editor.apply();

                    return true;
                }
            });
        }

        sVibe = (SwitchPreference) findPreference("switchVibe");

        if (sVibe != null) {

            sVibe.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {

                    boolean vibePushOn = ((Boolean)newValue).booleanValue();

                    editor.putBoolean("switchVibe", vibePushOn);
                    editor.apply();

                    return true;
                }
            });

        }

        sDistance = (SwitchPreference) findPreference("switchDistance");

        if (sDistance != null) {

            sDistance.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {

                    boolean distancePushOn = ((Boolean)newValue).booleanValue();

                    if (distancePushOn)
                        ((MainActivity)MainActivity.mContext).requestLocationPermission();
                    else
                        FirebaseMessaging.getInstance().unsubscribeFromTopic("push");

                    editor.putBoolean("switchDistance", distancePushOn);
                    editor.apply();

                    return true;
                }
            });

        }

        lDistance = (ListPreference) findPreference("listDistance");

        if (lDistance != null) {
            lDistance.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {

                    lDistance.setSummary(newValue.toString());

                    editor.putString("listDistance", newValue.toString());
                    editor.apply();

                    ((MainActivity)MainActivity.mContext).distanceSelect();

                    return true;
                }
            });
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (Prefs.contains("listDistance")) {
            String distance = Prefs.getString("listDistance", "");
            lDistance.setSummary(distance);
        }
    }
}




















