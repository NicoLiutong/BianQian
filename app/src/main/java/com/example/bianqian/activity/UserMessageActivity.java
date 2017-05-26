package com.example.bianqian.activity;

import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.bianqian.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserMessageActivity extends BasicActivity implements View.OnClickListener {

    private LinearLayout titleLayout;

    private Button backButton;

    private TextView titleText;

    private RelativeLayout userPictureLayout;

    private CircleImageView userPicture;

    private RelativeLayout userNameLayout;

    private TextView userNmae;

    private RelativeLayout userSexLayout;

    private TextView userSex;

    private RelativeLayout userIndividualityLayout;

    private TextView userIndividuality;

    private Button changePasswordButton;

    private Button exitButton;
    @Override
    public void setContentView() {
        setContentView(R.layout.activity_user_message);
    }

    @Override
    public void initViews() {
        titleLayout = (LinearLayout) findViewById(R.id.basic_title_layout);
        backButton = (Button) findViewById(R.id.basic_backbutton);
        titleText = (TextView) findViewById(R.id.basic_texttitle);
    }

    @Override
    public void initListeners() {
    backButton.setOnClickListener(this);
    }

    @Override
    public void initData() {
        titleLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
        titleText.setText("个人资料");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.basic_backbutton:
                finish();
                break;
        }
    }
}
