package com.novinsadr.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

/* loaded from: classes.dex */
public class Settings extends Activity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    private SettingsData mSettingsData = null;
    private Spinner spinner2;

    @Override // android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        SelectLanguage.ChangeLanguage(getBaseContext(), this.mSettingsData.LangCode, null);
    }

    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.mSettingsData = new SettingsData(this);
        this.mSettingsData.LoadFromFile();
        setContentView(R.layout.settings);
        Button button = (Button) findViewById(R.id.Button01);
        button.setOnClickListener(this);
        Spinner spinner = (Spinner) findViewById(R.id.Spinner01);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.GroundScan_ScanModeOptions, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter((SpinnerAdapter) adapter);
        spinner.setOnItemSelectedListener(this);
        if (this.mSettingsData.ScanMode_ZigZag) {
            spinner.setSelection(0);
        } else {
            spinner.setSelection(1);
        }
        this.spinner2 = (Spinner) findViewById(R.id.Spinner02);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.GroundScan_ImpulseModeOptions, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.spinner2.setAdapter((SpinnerAdapter) adapter2);
        this.spinner2.setOnItemSelectedListener(this);
        if (this.mSettingsData.ImpulseMode_Automatic) {
            this.spinner2.setSelection(0);
        } else {
            this.spinner2.setSelection(1);
        }
        ArrayAdapter<CharSequence> adapter3a = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        adapter3a.clear();
        for (int i = 1; i <= 25; i++) {
            adapter3a.add(Integer.toString(i));
        }
        adapter3a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner3 = (Spinner) findViewById(R.id.Spinner03);
        spinner3.setAdapter((SpinnerAdapter) adapter3a);
        spinner3.setOnItemSelectedListener(this);
        spinner3.setSelection(((this.mSettingsData.Impulses - 1) / 6) - 1);
    }

    @Override // android.app.Activity
    public void onBackPressed() {
        Intent startIntent = new Intent(this,  MainMenu.class);
        startIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startIntent.putExtra("Lcheck", false);
        startActivity(startIntent);
        finish();
    }

    @Override // android.app.Activity
    protected void onStart() {
        super.onStart();
        this.mSettingsData.LoadFromFile();
    }

    @Override // android.app.Activity
    protected void onStop() {
        super.onStop();
        this.mSettingsData.SaveToFile();
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (adapterView.getId() == R.id.Spinner01) {
            this.mSettingsData.ScanMode_ZigZag = i == 0;
            this.mSettingsData.SaveToFile();
        } else if (adapterView.getId() == R.id.Spinner02) {
            this.mSettingsData.ImpulseMode_Automatic = i == 0;
            this.mSettingsData.SaveToFile();
        } else if (adapterView.getId() == R.id.Spinner03) {
            this.mSettingsData.Impulses = ((i + 1) * 6) + 1;
            this.mSettingsData.SaveToFile();
        }
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public void onNothingSelected(AdapterView<?> parent) {
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        Intent startIntent = new Intent(this, (Class<?>) HelloAndroid.class);
        Bundle bundle = new Bundle();
        bundle.putByte("opmode", (byte) 1);
        bundle.putBoolean("scanmode_zigzag", this.mSettingsData.ScanMode_ZigZag);
        bundle.putBoolean("impulsemode_automatic", this.mSettingsData.ImpulseMode_Automatic);
        bundle.putInt("impulses", this.mSettingsData.Impulses);
        bundle.putBoolean("bluetooth", true);
        startIntent.putExtras(bundle);
        startActivity(startIntent);
        finish();
    }
}
