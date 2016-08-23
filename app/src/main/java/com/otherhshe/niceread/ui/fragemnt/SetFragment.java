package com.otherhshe.niceread.ui.fragemnt;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import com.otherhshe.niceread.R;
import com.otherhshe.niceread.utils.FileUtil;

import java.io.File;

/**
 * Author: Othershe
 * Time: 2016/8/23 11:00
 */
public class SetFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener, Preference.OnPreferenceChangeListener {
    private static final String CLEAR_CACHE = "clear_cache";
    public static final String SPLASH = "original_splash";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPreferenceManager().setSharedPreferencesName("niceread");//设置参数与值保存文件
        addPreferencesFromResource(R.xml.set_preference);

        init();
    }

    private void init() {
        Preference clearCache = findPreference(CLEAR_CACHE);
        clearCache.setSummary(clearCache.getSummary() + getCacheSize());
        clearCache.setOnPreferenceClickListener(this);

        Preference splash = findPreference(SPLASH);
        splash.setOnPreferenceChangeListener(this);
    }

    private String getCacheSize() {
        File file = getActivity().getApplicationContext().getCacheDir();
        return FileUtil.getFileSize(file);
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        switch (preference.getKey()) {
            case CLEAR_CACHE:
                openAppInfo(getActivity());
                break;
        }
        return true;
    }

    public static void openAppInfo(Context context) {
        Intent i = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.setData(Uri.parse("package:" + context.getApplicationContext().getPackageName()));
        context.startActivity(i);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object o) {
        return true;
    }
}
