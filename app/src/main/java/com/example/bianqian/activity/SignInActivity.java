package com.example.bianqian.activity;

import android.app.ProgressDialog;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.bianqian.R;
import com.example.bianqian.bmobbasic.User;
import com.example.bianqian.util.LevelUtils;
import com.example.bianqian.util.ShowError;
import com.example.bianqian.view.CalendarView;
import com.example.bianqian.view.MyProgressBar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import de.hdodenhof.circleimageview.CircleImageView;

public class SignInActivity extends BasicActivity {

    private User user;

    //已被选择的日期
    private List<String> mSelectDays;

    //可被选择的日期
    private List<String> mOptionDays;

    //是否为当月
    private boolean isCurrentMonth;

    //当月月份
    private Integer currentMonth;

    //用户总经验
    private Integer mEmpircalValue;

    //当天获得的经验
    private int mEveryDayEmpircalValue;

    //本月签到天数
    private int dateNumber;

    //已选择的日期
    private List<String> signInDays = new ArrayList<>();

    private ImageButton backButton;

    private CircleImageView userImage;

    private CalendarView calendarView;

    private TextView levelText;

    private MyProgressBar levelProgressBar;

    private TextView signInAllMonthDays;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_sign_in);
    }

    @Override
    public void initViews() {
        backButton = (ImageButton) findViewById(R.id.sign_in_backbutton);
        userImage = (CircleImageView) findViewById(R.id.sign_in_circle_image);
        calendarView = (CalendarView) findViewById(R.id.sign_in_calendar);
        levelText = (TextView) findViewById(R.id.level_text);
        levelProgressBar = (MyProgressBar) findViewById(R.id.level_progress_bar);
        signInAllMonthDays = (TextView) findViewById(R.id.sign_in_month_all_day);
    }

    @Override
    public void initListeners() {
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        calendarView.setOnClickDate(new CalendarView.OnClickListener() {
            @Override
            public void onClickDateListener(final String selectDate) {
                final ProgressDialog dialog = ProgressDialog.show(SignInActivity.this, null, null, true, false);
                /*for (String i:signInDays){
                    Log.d("date1",i );
                }*/
                //Log.d("select",selectDate);
                    if (isCurrentMonth) {
                        //如果是当月，将签到日期累加
                        signInDays.add(selectDate);
                       // Log.d("12","12");
                    } else {
                        //如果不是当月，初始化新的日期
                        signInDays.clear();
                        signInDays.add(selectDate);
                       // Log.d("12","23");
                    }
               /* Log.d("selectDate",selectDate);
                Log.d("1","1");
                for (String i:signInDays){
                    Log.d("date2",i );
                }

                for (String i:mSelectDays){
                    Log.d("date3",i );
                }*/
                    //更新经验
                    mEveryDayEmpircalValue = getEveryDayEmpircalValue();
                    mEmpircalValue = mEmpircalValue + mEveryDayEmpircalValue;

                    User upUser = new User();
                    upUser.setCurrentMonth(currentMonth);
                    upUser.setSignInDays(signInDays);
                    upUser.setEmpircalValue(mEmpircalValue);
                    upUser.update(user.getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                ShowToast("签到成功 经验+" + mEveryDayEmpircalValue);
                                dateNumber = signInDays.size();
                                setLeve(mEmpircalValue);
                                dialog.dismiss();
                            } else {
                                ShowToast(ShowError.showError(e));
                                //失败需要重新绘制Calendar
                                mSelectDays.remove(selectDate);
                                calendarView.setSelectedDates(mSelectDays);
                                calendarView.invalidate();
                                mEmpircalValue = mEmpircalValue - mEveryDayEmpircalValue;
                                dialog.dismiss();
                           }
                        }
                    });

            }
        });

    }

    @Override
    public void initData() {
        //获取用户
        user = BmobUser.getCurrentUser(User.class);
        mOptionDays = new ArrayList<>();
        mSelectDays = new ArrayList<>();
        mEmpircalValue = user.getEmpircalValue();
        //加载用户图片
        Glide.with(this).load(user.getUesrImage().getUrl()).into(userImage);
        initCurrentDay();
        initSelectDays();
        calendarView.setOptionalDate(mOptionDays);
        calendarView.setSelectedDates(mSelectDays);
        setLeve(mEmpircalValue);
    }

    //设置等级相关的显示
    private void setLeve(Integer empircalValue){
        levelText.setText("Lv " + LevelUtils.getLevel(empircalValue)[0]);
        //设置Progress
        levelProgressBar.setMax(LevelUtils.getLevel(empircalValue)[2]);
        levelProgressBar.setProgress(LevelUtils.getLevel(empircalValue)[1]);
        signInAllMonthDays.setText("本月签到" + dateNumber + "天");
    }

    //获取签到获得的经验
    private int getEveryDayEmpircalValue(){
        Random random = new Random();
        return random.nextInt(6) + 4;

    }
    //初始化可选择的日期
    private void initCurrentDay(){
        //获取当天日期
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH) + 1;
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        //判断当天是否是正在签到的月份
        if(user.getCurrentMonth() == currentMonth){
            isCurrentMonth = true;
            dateNumber = user.getSignInDays().size();
        }else {
            isCurrentMonth = false;
            dateNumber = 0;
        }
        //设置签到月份
        this.currentMonth = currentMonth;
        Log.d("currentMonth",currentMonth + "");
        //设置可被点击的日期
        String currentYears = String.valueOf(currentYear);
        String currentMonths;
        String currentDays;
        //判断当月是否小于10，小于10在前边加0
        if(currentMonth < 10){
            currentMonths = "0" + currentMonth;
        }else {
            currentMonths = String.valueOf(currentMonth);
        }
        //判断当天是否小于10，小于10在前边加0
        if(currentDay < 10){
            currentDays = "0" + currentDay;
        }else {
            currentDays = String.valueOf(currentDay);
        }
        Log.d("optionDays",currentYears + currentMonths + currentDays);
        mOptionDays.add(currentYears + currentMonths + currentDays);

    }

    //初始化已选择的日期
    private void initSelectDays(){
        signInDays = user.getSignInDays();
        for(String s:signInDays){
            mSelectDays.add(s);
           // Log.d("selectDays",s);
        }
    }
}
