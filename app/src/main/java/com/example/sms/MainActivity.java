package com.example.sms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private static final int CALL_PHONE_PERMISSION = 10;
    private static final int SMS_PERMISSION = 11;
    

    private Button buttonCall;
    private Button buttonSend;
    private EditText editTextPhone;
    private EditText editTextSms;
    private String numberPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonCall = findViewById(R.id.buttonCall);
        buttonCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                call();
            }
        });

        buttonSend = findViewById(R.id.buttonSend);
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSMS();
            }
        });

    }

    private void call() {
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE}, CALL_PHONE_PERMISSION);
        } else {
            editTextPhone = findViewById(R.id.editTextPhone);
            numberPhone = "tel:" + editTextPhone.getText().toString();
            Uri uri = Uri.parse(numberPhone);
            Intent intent = new Intent(Intent.ACTION_CALL, uri);
            startActivity(intent);
        }
    }

    private void sendSMS() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, SMS_PERMISSION);
        } else {
            editTextSms = findViewById(R.id.editTextSms);
            String message = editTextSms.getText().toString();
            SmsManager smgr = SmsManager.getDefault();
            smgr.sendTextMessage(numberPhone, null, message, null, null);
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permission, int[] grandResults) {
        switch (requestCode) {
            case CALL_PHONE_PERMISSION: {
                if (grandResults.length > 0 && grandResults[0] == PackageManager.PERMISSION_GRANTED) {
                    call();
                } else {
                    finish();
                }
                break;
            }

            case SMS_PERMISSION: {
                if (grandResults.length > 0 && grandResults[0] == PackageManager.PERMISSION_GRANTED) {
                    sendSMS();
                } else {
                    finish();
                }
                break;
            }
        }
    }

}