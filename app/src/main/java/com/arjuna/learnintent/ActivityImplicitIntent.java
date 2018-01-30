package com.arjuna.learnintent;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ActivityImplicitIntent extends AppCompatActivity {

    Button btnPhone, btnEmail, btnSms, btnUrl;

    String noTelp = "089576152729";
    String addresses = "arjunaa1504@gmail.com";
    String subject = "Hello Email";

    //tambahkan permission
    private static final int PERMISION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_implicit_intent);

        btnPhone = (Button)findViewById(R.id.btnPhone);
        btnEmail = (Button)findViewById(R.id.btnEmail);
        btnSms = (Button)findViewById(R.id.btnSms);
        btnUrl = (Button)findViewById(R.id.btnUrl);

        btnPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //aksi ketika btnPhone diklik

                //intent Implicit ke no Telp
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + noTelp));
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });
        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String isiEmail = "Halo Apa Kabar ?";

                ///intent email
                Intent nEmail = new Intent(Intent.ACTION_SEND);
                nEmail.putExtra(Intent.EXTRA_EMAIL, new String[]{addresses});
                nEmail.putExtra(Intent.EXTRA_SUBJECT, subject);
                nEmail.putExtra(Intent.EXTRA_TEXT, isiEmail);

                //Format kode u perngiriman emial
                nEmail.setType("message/rfc822");
                startActivity(Intent.createChooser(nEmail, "Select your email"));


            }
        });

        btnSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dataIsiSMS = "Hallo, Ini adalah sms";

                //android permission SDK 6.0
                if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M){

                    if (checkSelfPermission(Manifest.permission.SEND_SMS)
                            == PackageManager.PERMISSION_DENIED) {
                        Log.d("Permision", "permision denided to SEND SMS - requesting it ");
                        String[] permisions = {Manifest.permission.SEND_SMS};

                        requestPermissions(permisions, PERMISION_REQUEST_CODE);
                    }
                }

                Intent intent = new Intent(getApplicationContext(),ActivityImplicitIntent.class);

                PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);

                //memanggil liblary sms ke SMSManager dan memanggil string dataIsiSMS
                SmsManager sms = SmsManager.getDefault();
                sms.sendTextMessage(noTelp, null, dataIsiSMS, pi, null);

                Toast.makeText(getApplicationContext(), "SMS Berhasil di KIRIM", Toast.LENGTH_LONG).show();


            }
        });
        btnUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //memanggil link url nya
                String urlWeb = "http://www.google.co.id";

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlWeb));
                startActivity(intent);
            }
        });
    }
}
