package com.novinsadr.myapplication;

import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class FileExplorer extends ListActivity {
    private File[] files;
    private List<String> item = null;
    byte OperatingMode = -1;
    private byte MyCaller = -1;

    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent startIntent = getIntent();
        Bundle MyBundle = startIntent.getExtras();
        if (MyBundle != null) {
            this.MyCaller = MyBundle.getByte("caller");
            this.OperatingMode = MyBundle.getByte("opmode");
        }
        this.item = new ArrayList();
        String state = Environment.getExternalStorageState();
        if ("mounted".equals(state)) {
            File MyFile = Environment.getExternalStorageDirectory();
            if (MyFile.exists() && MyFile.canRead()) {
                File f = new File(MyFile, "/sd/OKM/");
                if (f.exists()) {
                    this.files = f.listFiles();
                    if (this.files.length > 0) {
                        Comparator<File> comperator = new Comparator<File>() { // from class: com.okm.roveruc.FileExplorer.1
                            @Override // java.util.Comparator
                            public int compare(File object1, File object2) {
                                return Long.valueOf(object2.lastModified()).compareTo(Long.valueOf(object1.lastModified()));
                            }
                        };
                        Arrays.sort(this.files, comperator);
                        for (int i = 0; i < this.files.length; i++) {
                            File file = this.files[i];
                            if (file.isFile() && file.getName().endsWith(".v3d")) {
                                this.item.add(file.getName());
                            }
                        }
                        setListAdapter(new ArrayAdapter(this, R.layout.file_explorer_row, R.id.TextView01, this.item));
                        ListView lv = getListView();
                        lv.setTextFilterEnabled(true);
                        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.okm.roveruc.FileExplorer.2
                            @Override // android.widget.AdapterView.OnItemClickListener
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Bundle startBundle = new Bundle();
                                Intent startIntent2 = new Intent(FileExplorer.this.getApplicationContext(), (Class<?>) HelloAndroid.class);
                                startBundle.putByte("opmode", (byte) 1);
                                startBundle.putBoolean("bluetooth", false);
                                startBundle.putString("filename", ((String) FileExplorer.this.item.get(position)).toString());
                                startIntent2.putExtras(startBundle);
                                FileExplorer.this.startActivity(startIntent2);
                                FileExplorer.this.finish();
                            }
                        });
                        return;
                    }
                    Globals.ShowMessageBox((Context) this, true, R.string.MessageBox_Information, R.string.FileExplorer_NoFiles, new DialogInterface.OnClickListener() { // from class: com.okm.roveruc.FileExplorer.3
                        @Override // android.content.DialogInterface.OnClickListener
                        public void onClick(DialogInterface dialog, int which) {
                            FileExplorer.this.onBackPressed();
                        }
                    });
                    return;
                }
                onBackPressed();
                return;
            }
            onBackPressed();
            return;
        }
        if ("shared".equals(state)) {
            Globals.ShowMessageBox((Context) this, true, R.string.MessageBox_Information, R.string.FileExplorer_MemShared, new DialogInterface.OnClickListener() { // from class: com.okm.roveruc.FileExplorer.4
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialog, int which) {
                    FileExplorer.this.onBackPressed();
                }
            });
        } else {
            Globals.ShowMessageBox((Context) this, true, R.string.MessageBox_Error, R.string.FileExplorer_NoMemory, new DialogInterface.OnClickListener() { // from class: com.okm.roveruc.FileExplorer.5
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialog, int which) {
                    FileExplorer.this.onBackPressed();
                }
            });
        }
    }

    @Override // android.app.Activity
    public void onBackPressed() {
        super.onBackPressed();
        switch (this.MyCaller) {
            case 0:
                Intent startIntent = new Intent(this, (Class<?>) MainMenu.class);
                startIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startIntent.putExtra("Lcheck", false);
                startActivity(startIntent);
                finish();
                break;
            case 4:
                Intent startIntent2 = new Intent(this, (Class<?>) HelloAndroid.class);
                startIntent2.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                Bundle bundle = new Bundle();
                bundle.putByte("opmode", (byte) 1);
                bundle.putBoolean("bluetooth", false);
                startIntent2.putExtras(bundle);
                startIntent2.putExtra("Lcheck", false);
                startActivity(startIntent2);
                finish();
                break;
            default:
                finish();
                break;
        }
    }
}
