package com.example.administrator.thekey;

import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import android.os.Handler;
import android.os.Message;
import com.example.administrator.thekey.MySqlHelp;
import android.content.Intent;

import static com.example.administrator.thekey.MySqlHelp.*;


public class MainActivity extends AppCompatActivity {
    EditText username;
    EditText password;
    String url="jdbc:mysql://150.95.216.176:3306/test";
    String user="root";
    String pass="baobei1181213";
    String name;
    String passwd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username=(EditText)findViewById(R.id.username);
        password=(EditText)findViewById(R.id.password);

    }
    public void loginclick(View view) {


        Toast.makeText(getApplicationContext(),"login1",Toast.LENGTH_SHORT).show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                name = username.getText().toString();
                passwd = password.getText().toString();

                Connection conn = openConnection(url,user,pass);


                String sql = "select * from users where username=" + "'" + name + "'" + "and password=" + "'" + passwd + "'";

                if (execSQL(conn, sql)) {
                   /* Looper.prepare();
                    Toast.makeText(getApplicationContext(), "sucess"+name, Toast.LENGTH_SHORT).show();
                    Looper.loop();
                    */
                    Intent intent =new Intent(MainActivity.this, qrcode.class);
                    startActivity(intent);
                    finish();
                }

            }


        }).start();




    }

}
