package com.example.bianqian.activity;

import android.content.Intent;

import com.example.bianqian.R;

public class MainActivity extends BasicActivity {

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    public void initViews() {

    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {
        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
        MainActivity.this.startActivity(intent);
    }
}
