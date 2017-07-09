package com.example.bianqian.activity;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bianqian.R;
import com.example.bianqian.db.User;
import com.example.bianqian.db.UserNote;
import com.example.bianqian.util.GetFindData;
import com.example.bianqian.util.UpdateUserNote;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

public class EditingTextActivity extends BasicActivity implements View.OnClickListener {

    public static final String CREATNOTE = "creatNote";
    public static final String CHANGENOTE = "changeNote";

    public static final String MOOD = "mood";
    public static final String DATE = "date";
    public static final String TEXT = "text";
    public static final String TYPE = "type";
    public static final String NOTEID = "noteId";

    private boolean isComplete;
    private Button completeButton, redButton, purpleButton, pinkButton, yellowButton, greenButton, blueButton, grayButton;

    private ImageView backButton;
    private TextView textDate;

    private EditText editText;

    private LinearLayout backgroundLayout;

    private String moodColor = "red";
    private String text = null;
    private String date = null;
    private String editType;
    private User user;
    private String noteId;

    private GetFindData<UserNote> getResult;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_editing_text);
    }

    @Override
    public void initViews() {
        backButton = (ImageView) findViewById(R.id.editing_backbutton);
        completeButton = (Button) findViewById(R.id.editing_complete);
        redButton = (Button) findViewById(R.id.editing_red_button);
        purpleButton = (Button) findViewById(R.id.editing_purple_button);
        pinkButton = (Button) findViewById(R.id.editing_pink_button);
        yellowButton = (Button) findViewById(R.id.editing_yellow_button);
        greenButton = (Button) findViewById(R.id.editing_green_button);
        blueButton = (Button) findViewById(R.id.editing_blue_button);
        grayButton = (Button) findViewById(R.id.editing_gray_button);
        textDate = (TextView) findViewById(R.id.editing_textdate);
        editText = (EditText) findViewById(R.id.editing_edittext);
        backgroundLayout = (LinearLayout) findViewById(R.id.editing_background);
    }

    @Override
    public void initListeners() {
        backButton.setOnClickListener(this);
        completeButton.setOnClickListener(this);
        redButton.setOnClickListener(this);
        purpleButton.setOnClickListener(this);
        pinkButton.setOnClickListener(this);
        yellowButton.setOnClickListener(this);
        greenButton.setOnClickListener(this);
        blueButton.setOnClickListener(this);
        grayButton.setOnClickListener(this);
    }

    @Override
    public void initData() {
        user = BmobUser.getCurrentUser(User.class);
        Intent intent = getIntent();
        editType = intent.getStringExtra(TYPE);
        date = intent.getStringExtra(DATE);
        text = intent.getStringExtra(TEXT);
        moodColor = intent.getStringExtra(MOOD);
        noteId = intent.getStringExtra(NOTEID);

        isComplete = false;

        getResult = new GetFindData<UserNote>() {
            @Override
            public void returnFindData(List<UserNote> findData,Boolean isSuccess) {    }

            @Override
            public void deletDataResult(Boolean isSuccess) {     }

            @Override
            public void creatDataResult(Boolean isSuccess) {
                finish();
            }

            @Override
            public void upDataResult(Boolean isSuccess) {
                finish();
            }
        };

        if(editType.equals(CREATNOTE)){
            intilizeCreatNote(moodColor);
        }

        if(editType.equals(CHANGENOTE)){
            intilizeChangeNote(moodColor);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.editing_backbutton:
                finish();
                break;
            case R.id.editing_red_button:
                moodColor = "red";
                intilizeColor(moodColor);
                break;
            case R.id.editing_purple_button:
                moodColor = "purple";
                intilizeColor(moodColor);
                break;
            case R.id.editing_pink_button:
                moodColor = "pink";
                intilizeColor(moodColor);
                break;
            case R.id.editing_yellow_button:
                moodColor = "yellow";
                intilizeColor(moodColor);
                break;
            case R.id.editing_green_button:
                moodColor = "green";
                intilizeColor(moodColor);
                break;
            case R.id.editing_blue_button:
                moodColor = "blue";
                intilizeColor(moodColor);
                break;
            case R.id.editing_gray_button:
                moodColor = "gray";
                intilizeColor(moodColor);
                break;
            case R.id.editing_complete:
                if (!isComplete) {
                    isComplete = true;
                    UserNote userNote = new UserNote();
                    userNote.setUser(user);
                    userNote.setMoodColor(moodColor);
                    userNote.setNote(editText.getText().toString());

                    if (editType.equals(CREATNOTE)) {
                        //存储新建的
                        UpdateUserNote.creatNewNote(userNote, this, getResult);
                    }

                    if (editType.equals(CHANGENOTE)) {
                        //更新后台数据
                        UpdateUserNote.updateNote(userNote, noteId, this, getResult);
                    }
                    break;
                }
        }
    }

    private void intilizeColor(String color){
        switch (color){
            case "red" :
                backgroundLayout.setBackgroundResource(R.color.text_background_red);
                textDate.setTextColor(ContextCompat.getColor(this,R.color.text_red));
                editText.setTextColor(ContextCompat.getColor(this,R.color.text_red));
                backButton.setColorFilter(ContextCompat.getColor(this,R.color.text_red));
                completeButton.setTextColor(ContextCompat.getColor(this,R.color.text_red));
                break;
            case "purple" :
                backgroundLayout.setBackgroundResource(R.color.text_background_purple);
                textDate.setTextColor(ContextCompat.getColor(this,R.color.text_purple));
                editText.setTextColor(ContextCompat.getColor(this,R.color.text_purple));
                backButton.setColorFilter(ContextCompat.getColor(this,R.color.text_purple));
                completeButton.setTextColor(ContextCompat.getColor(this,R.color.text_purple));
                break;
            case "pink" :
                backgroundLayout.setBackgroundResource(R.color.text_background_pink);
                textDate.setTextColor(ContextCompat.getColor(this,R.color.text_pink));
                editText.setTextColor(ContextCompat.getColor(this,R.color.text_pink));
                backButton.setColorFilter(ContextCompat.getColor(this,R.color.text_pink));
                completeButton.setTextColor(ContextCompat.getColor(this,R.color.text_pink));
                break;
            case "yellow" :
                backgroundLayout.setBackgroundResource(R.color.text_background_yellow);
                textDate.setTextColor(ContextCompat.getColor(this,R.color.text_yellow));
                editText.setTextColor(ContextCompat.getColor(this,R.color.text_yellow));
                backButton.setColorFilter(ContextCompat.getColor(this,R.color.text_yellow));
                completeButton.setTextColor(ContextCompat.getColor(this,R.color.text_yellow));
                break;
            case "green" :
                backgroundLayout.setBackgroundResource(R.color.text_background_green);
                textDate.setTextColor(ContextCompat.getColor(this,R.color.text_green));
                editText.setTextColor(ContextCompat.getColor(this,R.color.text_green));
                backButton.setColorFilter(ContextCompat.getColor(this,R.color.text_green));
                completeButton.setTextColor(ContextCompat.getColor(this,R.color.text_green));
                break;
            case "blue" :
                backgroundLayout.setBackgroundResource(R.color.text_background_blue);
                textDate.setTextColor(ContextCompat.getColor(this,R.color.text_blue));
                editText.setTextColor(ContextCompat.getColor(this,R.color.text_blue));
                backButton.setColorFilter(ContextCompat.getColor(this,R.color.text_blue));
                completeButton.setTextColor(ContextCompat.getColor(this,R.color.text_blue));
                break;
            case "gray" :
                backgroundLayout.setBackgroundResource(R.color.text_background_gray);
                textDate.setTextColor(ContextCompat.getColor(this,R.color.text_gray));
                editText.setTextColor(ContextCompat.getColor(this,R.color.text_gray));
                backButton.setColorFilter(ContextCompat.getColor(this,R.color.text_gray));
                completeButton.setTextColor(ContextCompat.getColor(this,R.color.text_gray));
                break;
        }
    }

    private void intilizeCreatNote(String color){
        Bmob.getServerTime(new QueryListener<Long>() {
            @Override
            public void done(Long aLong, BmobException e) {
                if(e == null){
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    String times = format.format(new Date(aLong * 1000L));
                    textDate.setText(times);
                }else {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    String times = format.format(new Date(System.currentTimeMillis()));
                    textDate.setText(times);
                }
            }
        });

        editText.setText(text);
        intilizeColor(color);
    }

    private void intilizeChangeNote(String color){
        textDate.setText(date);
        editText.setText(text);
        intilizeColor(color);
    }

}
