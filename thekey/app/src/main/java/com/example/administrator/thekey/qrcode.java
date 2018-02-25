package com.example.administrator.thekey;

import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ImageView;
import java.util.Random;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import android.os.Handler;
import android.os.Message;
import com.example.administrator.thekey.MySqlHelp;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import android.graphics.Bitmap;
import java.util.HashMap;

import java.util.Map;

import static com.example.administrator.thekey.MySqlHelp.*;


/**
 * Created by Administrator on 2018/2/24 0024.
 */

public class qrcode extends AppCompatActivity{
    ImageView imageView;
    String url="jdbc:mysql://150.95.216.176:3306/test";
    String user="root";
    String pass="baobei1181213";
    String qrstr;
    String qr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qrcode);
        imageView=(ImageView) findViewById(R.id.imageView);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Connection conn = openConnection(url,user,pass);


                String sql = "select * from key_value";


                Statement statement = null;
                ResultSet result = null;

                try {
                    statement = conn.createStatement();
                    result = statement.executeQuery(sql);

                    while(result.next()){
                    qrstr=result.getString("key");
                    }







                } catch (SQLException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable(){

                    @Override
                    public void run() {
                        //更新UI
                        Bitmap qrcode= generateBitmap(qrstr,300, 300);
                        imageView.setImageBitmap(qrcode);
                    }

                });




            }
        }).start();


    }

    public void shareclick(View view) {
    }

    public void getclick(View view) {
        Random rand = new Random();
        int i = rand.nextInt(10000);
        qr=String.valueOf(i);


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Connection conn = openConnection(url,user,pass);


                String sql = "UPDATE key_value SET `key`="+"'"+qr+"'"+"WHERE id='one'";


                if(execSQL(conn,sql)){

                }
                    runOnUiThread(new Runnable(){

                        @Override
                        public void run() {
                            //更新UI
                            Bitmap qrcode= generateBitmap(qr,300, 300);
                            imageView.setImageBitmap(qrcode);
                        }

                    });



            }
        }).start();


    }


    private Bitmap generateBitmap(String content,int width, int height) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType, String> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        try {
            BitMatrix encode = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hints);
            int[] pixels = new int[width * height];
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (encode.get(j, i)) {
                        pixels[i * width + j] = 0x00000000;
                    } else {
                        pixels[i * width + j] = 0xffffffff;
                    }
                }
            }
            return Bitmap.createBitmap(pixels, 0, width, width, height, Bitmap.Config.RGB_565);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }
}
