package se702.mobileorientation;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.vision.barcode.Barcode;

public class MainActivity extends AppCompatActivity {

    public static final int PERMISSION_REQUEST = 200;
    public static final int REQUEST_CODE = 100;

    Button scanButton;
    TextView qrCodeResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        qrCodeResult = (TextView) findViewById(R.id.result_view);
        scanButton = (Button) findViewById(R.id.scan_Btn);

        //Ask for Camera access
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST);
        }

        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ScanQrActivity.class);
                startActivityForResult(intent, REQUEST_CODE);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == REQUEST_CODE){

            if (resultCode == RESULT_OK){
                if (data!= null){
                    final Barcode barcode = data.getParcelableExtra("barcode");
//                    qrCodeResult.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            qrCodeResult.setText(barcode.displayValue);
//                        }
//                    });
                    qrCodeResult.setText(barcode.displayValue);
                }else{
                    qrCodeResult.setText("No QR code found");
                }
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data);

        }


    }
}
