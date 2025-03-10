package com.novinsadr.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/* loaded from: classes.dex */
public final class Globals {
    static final String BASE64_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnjWSkWJqcjCdLKibJtxc4kX3FBHUJnmDEgW8+d+CS7C3+kraEvtxZTWQMh6+j0sytcsd0vNT3fq6l42axexsGlRK2lt5AlZUxsEwyQu+FaxEmU5f5aHvhDlYrN0DFmVN3Sfsy1XgzAJXK4HVJMW7IzrINM+vwC68XYdCTaA4ScNFgRW0F0vCmFnae7QM5gQicS8/VQufRhjPlGxekWpMmkwfWLVtkc5/K7ZISE94aNYFSlvqfTod7gZqq1cIjkA6uFrXaP6YIexYb8IXzMVbtC7jIR2J+DfC3u97dBcnfUNA1Tn/nH9pQfZXSbamDviWLLdSjJZYgk7weCZASxpP6QIDAQAB";
    static final String BLUETOOTH_NAME = "OKM Rover UC";
    static final byte CALLER_FILEEXPLORER = 1;
    static final byte CALLER_GROUNDSCANMENU = 2;
    static final byte CALLER_MAINMENU = 0;
    static final byte CALLER_RENDERING = 4;
    static final byte CALLER_SETTINGS = 3;
    static final long DISC_TIME = 30000000;
    static final String FIRMWARE_VERSION = "2.6";
    static final boolean IsDemo = false;
    static final int MAX_METER = 25;
    static final int STEPS = 6;
    static final String URL_ANDROID_MARKET = "http://market.android.com/details?id=%s";
    static final String URL_OKM_API = "http://www.okm-technologies.com/api/index.php?ac=013-%s-0";
    static final long WAIT_TIME = 250000000;
    static final byte[] SALT = {-67, 35, 106, 18, -33, -97, 34, -84, 51, 98, -125, -39, 37, 117, 36, -103, 61, -32, 62, 39};
    static final char[] ENC_BYTES = {'&', '/', 'r', 'h', '?', '*', '3', 'z', '-', 'f', 'H', '.'};
    static final char[] LICENSE_DEALER = new char[0];

    public static void ShowMessageBox(Context context, boolean isInfo, String Title, String Message, DialogInterface.OnClickListener OnClick) {
        int IconID = android.R.drawable.ic_dialog_info;
        if (!isInfo) {
            IconID = android.R.drawable.ic_dialog_alert;
        }
        new AlertDialog.Builder(context).setIcon(IconID).setTitle(Title).setMessage(Message).setCancelable(false).setPositiveButton(R.string.Button_OK, OnClick).show();
    }

    public static void ShowMessageBox(Context context, boolean isInfo, int TitleResId, int MessageResId, DialogInterface.OnClickListener OnClick) {
        ShowMessageBox(context, isInfo, context.getString(TitleResId), context.getString(MessageResId), OnClick);
    }

    public static String FormatSerialNumber(String MySerialNumber) {
        String tmp = "";
        for (int i = 0; i < MySerialNumber.length(); i++) {
            tmp = String.valueOf(tmp) + MySerialNumber.charAt(i);
            if (i == 5) {
                tmp = String.valueOf(tmp) + "/";
            }
        }
        return tmp;
    }
}
