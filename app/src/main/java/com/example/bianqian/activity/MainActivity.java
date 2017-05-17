package com.example.bianqian.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.bianqian.R;

import cn.bmob.v3.Bmob;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bmob.initialize(this,"3da138f19a8d8a32a5a64ac1c4e740df");
        setContentView(R.layout.activity_main);
    }
}
