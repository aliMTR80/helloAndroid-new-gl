package com.novinsadr.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

/* loaded from: classes.dex */
public class MainMenu extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {
    private GridView grid_main;
    SettingsData mSettingsData = null;
    BluetoothAdapter mBluetoothAdapter = null;

    @SuppressLint({"MissingPermission", "WrongConstant"})
    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSettingsData = new SettingsData(this);
        this.mSettingsData.LoadFromFile();
        setContentView(R.layout.mainmenu);
        this.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (this.mBluetoothAdapter == null) {
            finish();
            return;
        }
        this.mBluetoothAdapter.setName("OKM MOBYLE 3D");
        SelectLanguage.ChangeLanguage(getBaseContext(), this.mSettingsData.LangCode, null);

        grid_main = (GridView) findViewById(R.id.GridView01);
        grid_main.setAdapter((ListAdapter) new ImageAdapter(this));
        grid_main.setOnItemClickListener(this);
        grid_main.setSelected(false);
        CheckActivation(false);
    }

    @Override // android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        SelectLanguage.ChangeLanguage(getBaseContext(), this.mSettingsData.LangCode, null);
    }

    @Override // android.app.Activity
    protected Dialog onCreateDialog(int id) {
        return new AlertDialog.Builder(this).setTitle(getString(R.string.MessageBox_Information)).setMessage(getString(R.string.AndroidLicense_MessageText)).setCancelable(false).setPositiveButton(getString(R.string.AndroidLicense_ButtonBuy), new DialogInterface.OnClickListener() { // from class: com.okm.roveruc.MainMenu.1
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                Intent marketIntent = new Intent("android.intent.action.VIEW", Uri.parse(String.format("http://market.android.com/details?id=%s", MainMenu.this.getPackageName())));
                MainMenu.this.startActivity(marketIntent);
                MainMenu.this.finish();
            }
        }).setNegativeButton(getString(R.string.AndroidLicense_ButtonExit), new DialogInterface.OnClickListener() { // from class: com.okm.roveruc.MainMenu.2
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                MainMenu.this.finish();
            }
        }).create();
    }

//    private class MyLicenseCheckerCallback implements LicenseCheckerCallback {
//        private MyLicenseCheckerCallback() {
//        }
//
//        /* synthetic */ MyLicenseCheckerCallback(MainMenu mainMenu, MyLicenseCheckerCallback myLicenseCheckerCallback) {
//            this();
//        }
//
//        @Override // com.android.vending.licensing.LicenseCheckerCallback
//        public void allow() {
//            if (!MainMenu.this.isFinishing()) {
//                MainMenu.this.mSettingsData.isLicenseAndroidMarket = true;
//                MainMenu.this.mSettingsData.SaveToFile();
//                MainMenu.this.CheckActivation(false);
//            }
//        }
//
//        @Override // com.android.vending.licensing.LicenseCheckerCallback
//        public void dontAllow() {
//            if (!MainMenu.this.isFinishing()) {
//                MainMenu.this.mSettingsData.isLicenseAndroidMarket = false;
//                MainMenu.this.mSettingsData.SaveToFile();
//                if (Globals.LICENSE_DEALER.length == 0) {
//                    MainMenu.this.showDialog(0);
//                }
//                MainMenu.this.CheckActivation(false);
//            }
//        }
//
//        @Override // com.android.vending.licensing.LicenseCheckerCallback
//        public void applicationError(LicenseCheckerCallback.ApplicationErrorCode errorCode) {
//            if (MainMenu.this.isFinishing()) {
//                return;
//            }
//            MainMenu.this.CheckActivation(false);
//        }
//    }

    @Override // android.app.Activity
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_activation, menu);
        menu.getItem(0).setIcon(R.drawable.ic_menu_account_list);
        menu.getItem(0).setTitle(R.string.Activation_ReNew);
        return true;
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() != R.id.item01) {
            return false;
        }
        CheckActivation(true);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void CheckActivation(boolean ForceReactivation) {
        if (ForceReactivation || this.mSettingsData.ActivationCode == "" || !BluetoothAdapter.checkBluetoothAddress(this.mSettingsData.BluetoothAddress)) {
          //  Intent startIntent = new Intent(this, (Class<?>) Activation.class);
         //   startActivity(startIntent);
          //  finish();
        }
    }

    public class ImageAdapter extends BaseAdapter {
        public static final int ACTIVITY_CREATE = 10;
        Context mContext;

        public ImageAdapter(Context c) {
            this.mContext = c;
        }

        @Override // android.widget.Adapter
        public int getCount() {
            return 6;
        }

        @Override // android.widget.Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater li = MainMenu.this.getLayoutInflater();
                View v = li.inflate(R.layout.mainmenu_item, (ViewGroup) null);
                TextView tv = (TextView) v.findViewById(R.id.icon_text);
                ImageView iv = (ImageView) v.findViewById(R.id.icon_image);
                switch (position) {
                    case 0:
                        tv.setText(R.string.MainMenu_Magnetometer);
                        iv.setImageResource(R.drawable.menu_magnetometer);
                        break;
                    case 1:
                        tv.setText(R.string.MainMenu_GroundScan);
                        iv.setImageResource(R.drawable.menu_groundscan);
                        break;
                    case 2:
                        tv.setText(R.string.MainMenu_Discrimination);
                        iv.setImageResource(R.drawable.menu_discrimination);
                        break;
                    case 3:
                        tv.setText(R.string.MainMenu_BrowseScans);
                        iv.setImageResource(R.drawable.v3d);
                        break;
                    case 4:
                        tv.setText(R.string.MainMenu_Language);
                        iv.setImageResource(R.drawable.menu_language);
                        break;
                    case 5:
                        tv.setText(R.string.MainMenu_Info);
                        iv.setImageResource(R.drawable.menu_info);
                        break;
                }
                return v;
            }
            return convertView;
        }

        @Override // android.widget.Adapter
        public Object getItem(int arg0) {
            return null;
        }

        @Override // android.widget.Adapter
        public long getItemId(int position) {
            return 0L;
        }
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public void onNothingSelected(AdapterView<?> parent) {
    }

    @Override // android.app.Activity
    public void onBackPressed() {
        finish();
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        String LicenseStr;
        Bundle startBundle = new Bundle();
        switch (arg2) {
            case 0:
                Intent startIntent = new Intent(this, HelloAndroid.class);
                startBundle.putByte("caller", (byte) 0);
                startBundle.putByte("opmode", (byte) 0);
                startBundle.putBoolean("bluetooth", true);
                startIntent.putExtras(startBundle);
                startActivity(startIntent);
                finish();
                break;
            case 1:
                Intent startIntent2 = new Intent(this,  Settings.class);
                startBundle.putByte("caller", (byte) 2);
                startIntent2.putExtras(startBundle);
                startActivity(startIntent2);
                finish();
                break;
            case 2:
                Intent startIntent3 = new Intent(this,  HelloAndroid.class);
                startBundle.putByte("caller", (byte) 0);
                startBundle.putByte("opmode", (byte) 2);
                startBundle.putBoolean("bluetooth", true);
                startIntent3.putExtras(startBundle);
                startActivity(startIntent3);
                finish();
                break;
            case 3:
                Intent startIntent4 = new Intent(this,FileExplorer.class);
                startBundle.putByte("caller", (byte) 0);
                startIntent4.putExtras(startBundle);
                startActivity(startIntent4);
                finish();
                break;
            case 4:
                startActivity(new Intent(this, (Class<?>) SelectLanguage.class));
                finish();
                break;
            case 5:
                String VersionStr = "?";
                try {
                    VersionStr = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
                } catch (PackageManager.NameNotFoundException e) {
                }
                if (this.mSettingsData.ProbeVersion.length() > 0) {
                    VersionStr = String.valueOf(VersionStr) + " / " + this.mSettingsData.ProbeVersion;
                }
                if (this.mSettingsData.isLicenseOKM) {
                    LicenseStr = String.valueOf("") + " OKM";
                } else {
                    LicenseStr = this.mSettingsData.isLicenseAndroidMarket ? String.valueOf("") + " Android Market" : Globals.LICENSE_DEALER.length > 0 ? String.valueOf("") + String.valueOf(Globals.LICENSE_DEALER) : String.valueOf("") + " ?";
                }
                Globals.ShowMessageBox((Context) this, true, getString(R.string.app_name), String.valueOf(String.format("%s: %s\n%s: %s\n%s: %s\n\n", getString(R.string.app_version), VersionStr, getString(R.string.Activation_SerialNumber), Globals.FormatSerialNumber(this.mSettingsData.SerialNumber), getString(R.string.GeneralLicense), LicenseStr)) + getString(R.string.app_copyright), (DialogInterface.OnClickListener) null);
                break;
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
    }
}
