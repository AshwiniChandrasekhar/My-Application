package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AlertActivity extends AppCompatActivity {

    EditText e1, e2,e3,e4;
    Button b1;
    private final static int SEND_SMS_PERMISSION_REQ=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert);
        e1=findViewById(R.id.editText);
        e2=findViewById(R.id.editText2);
        e3=findViewById(R.id.editText1);
        e4=findViewById(R.id.editText11);
        b1=findViewById(R.id.button);
        b1.setEnabled(false);


        if(checkPermission(Manifest.permission.SEND_SMS))
        {
            b1.setEnabled(true);
        }
        else
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, SEND_SMS_PERMISSION_REQ);
        }
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1=e1.getText().toString();
                String s2=e3.getText().toString();
                String s3=e4.getText().toString();
                String s4=e2.getText().toString();
                if(!TextUtils.isEmpty(s1)&&!TextUtils.isEmpty(s2))
                {

                    if(checkPermission(Manifest.permission.SEND_SMS))
                    {
                        SmsManager smsManager=SmsManager.getDefault();
                        smsManager.sendTextMessage(s1,null,s4,null,null);
                        smsManager.sendTextMessage(s2,null,s4,null,null);
                        smsManager.sendTextMessage(s3,null,s4,null,null);
                    }
                    else {
                        Toast.makeText(AlertActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(AlertActivity.this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private boolean checkPermission(String sendSms) {

        int checkpermission= ContextCompat.checkSelfPermission(this,sendSms);
        return checkpermission== PackageManager.PERMISSION_GRANTED;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case SEND_SMS_PERMISSION_REQ:
                if (grantResults.length > 0 && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    b1.setEnabled(true);
                }
                break;
        }
    }

}