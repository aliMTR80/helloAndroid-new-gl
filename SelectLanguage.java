package com.novinsadr.myapplication;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/* loaded from: classes.dex */
public class SelectLanguage extends ListActivity {
    private List<String> item = null;
    private String[] MyLocales = {"en", "de", "fr", "es", "it", "nl", "el", "tr", "ru", "zh", "ja", "fa", "ar"};
    SettingsData mSettingsData = null;

    public static void ChangeLanguage(Context context, String MyLanguage, SettingsData MySettingsData) {
        if (MyLanguage.length() > 0) {
            Locale MyLocale = new Locale(MyLanguage);
            Locale.setDefault(MyLocale);
            Configuration config = new Configuration();
            config.locale = MyLocale;
            context.getResources().updateConfiguration(config, null);
            if (MySettingsData != null) {
                MySettingsData.LangCode = MyLanguage;
                MySettingsData.SaveToFile();
            }
        }
    }

    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mSettingsData = new SettingsData(this);
        this.mSettingsData.LoadFromFile();
        this.item = new ArrayList();
        for (int i = 0; i < this.MyLocales.length; i++) {
            Locale MyLocale = new Locale(this.MyLocales[i]);
            this.item.add(MyLocale.getDisplayLanguage(MyLocale));
        }
        setListAdapter(new ArrayAdapter(this, R.layout.language_row, R.id.TextView01, this.item));
        ListView lv = getListView();
        lv.setTextFilterEnabled(true);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.okm.roveruc.SelectLanguage.1
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SelectLanguage.ChangeLanguage(SelectLanguage.this.getBaseContext(), SelectLanguage.this.MyLocales[position], SelectLanguage.this.mSettingsData);
                SelectLanguage.this.onBackPressed();
            }
        });
    }

    @Override // android.app.Activity
    public void onBackPressed() {
        super.onBackPressed();
        Intent startIntent = new Intent(this, (Class<?>) MainMenu.class);
        startIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(startIntent);
        finish();
    }
}
