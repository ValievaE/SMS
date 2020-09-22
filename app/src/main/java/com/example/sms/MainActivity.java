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
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private static final int CALL_PHONE_PERMISSION = 10;
    private static final int SMS_PERMISSION = 11;
    private String numberPhone = "";
    private EditText editTextPhone;
    private EditText editTextSms;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextPhone = findViewById(R.id.editTextPhone);
        editTextSms = findViewById(R.id.editTextSms);

        Button buttonCall = findViewById(R.id.buttonCall);
        buttonCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                call();

            }
        });

        Button buttonSend = findViewById(R.id.buttonSend);
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSMS();

            }
        });

    }



    private void sendSMS() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, SMS_PERMISSION);
        } else {
            String message = editTextSms.getText().toString();
            SmsManager smgr = SmsManager.getDefault();
            smgr.sendTextMessage(numberPhone, null, message, null, null);
        }

    }
    private void call() {
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE}, CALL_PHONE_PERMISSION);
        } else {
            numberPhone = "tel:" + editTextPhone.getText().toString();
            Uri uri = Uri.parse(numberPhone);
            Intent intent = new Intent(Intent.ACTION_CALL, uri);
            startActivity(intent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permission, int[] grandResults) {
        switch (requestCode) {
            case SMS_PERMISSION: {
                if (grandResults.length > 0 && grandResults[0] == PackageManager.PERMISSION_GRANTED) {
                    sendSMS();
                } else {
                    Toast.makeText(MainActivity.this, "Вы не дали разрешения на отправку SMS", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            }
            case CALL_PHONE_PERMISSION: {
                if (grandResults.length > 0 && grandResults[0] == PackageManager.PERMISSION_GRANTED) {
                    call();
                } else {
                    Toast.makeText(MainActivity.this, "Вы не дали разрешения на доступ к звонкам", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            }

        }
    }

}