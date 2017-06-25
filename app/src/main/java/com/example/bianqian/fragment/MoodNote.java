package com.example.bianqian.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.bianqian.R;
import com.example.bianqian.adapter.MultiItemTypeSupport;
import com.example.bianqian.adapter.MyAdapter;
import com.example.bianqian.adapter.MyViewHolder;
import com.example.bianqian.db.User;
import com.example.bianqian.db.UserNote;
import com.example.bianqian.util.GetFindData;
import com.example.bianqian.util.UpdateUserNote;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;

/**
 * Created by 刘通 on 2017/6/15.
 */

public class MoodNote extends Fragment {

    public static final int TITLE = 1;
    public static final int NOTEITEM = 2;
    public static final int NOLAYOUT = 3;

    private User user;
    private RecyclerView mainRecyclerView;

    private Button cancleButton,deletButton;

    private LinearLayout cancleDeletLayout;

    private List<UserNote> data = new ArrayList<>();

    private MyAdapter<UserNote> adapter;

    private MultiItemTypeSupport<UserNote> multiItemTypeSupport;

    private GetFindData<UserNote> changeData;

    private String[] upDateAt = {"2050","12","12 24:59:59"};


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.main_activity_recycler,container,false);
        user = BmobUser.getCurrentUser(User.class);
        mainRecyclerView = (RecyclerView) view.findViewById(R.id.main_recyclerview);
        cancleDeletLayout = (LinearLayout) view.findViewById(R.id.cancle_delet_layout);
        cancleDeletLayout.setVisibility(View.GONE);
        cancleButton = (Button) view.findViewById(R.id.cancle_button);
        deletButton = (Button) view.findViewById(R.id.delet_button);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        multiItemTypeSupport = new MultiItemTypeSupport<UserNote>() {
            @Override
            public int getLayoutId(int itenType) {
                switch (itenType){
                    case TITLE : return R.layout.date_item;
                    case NOTEITEM : return R.layout.item_note;
                    default: return R.layout.item_nothing;
                }
            }

            @Override
            public int getItemViewType(int position, UserNote userNote) {
                if(data.size() != 0){
                    /*if(upDateAt == null){
                    upDateAt = userNote.getUpdatedAt().split("-");
                    }*/
                    if(userNote.getUpdatedAt().split("-")[0].equals(upDateAt[0]) && userNote.getUpdatedAt().split("-")[1].equals(upDateAt[1])){
                        return NOTEITEM;
                    }else {
                        upDateAt = userNote.getUpdatedAt().split("-");
                        return TITLE;
                    }
                }else {
                    return NOLAYOUT;
                }
                }
            };
        changeData = new GetFindData<UserNote>() {
            @Override
            public void returnFindData(List<UserNote> findData) {
                data.clear();
                //data = findData;
                for(UserNote i:findData){
                    data.add(i);
                    //Log.d("22",i.getObjectId());
                }
                notifyDataSetChanged();
            }

            @Override
            public void deletDataResout(Boolean bool) {
                UpdateUserNote.getAuthorNote(user,getContext(),changeData);
            }
        };

        intialize();
        adapter = new MyAdapter<UserNote>(getContext(),data,multiItemTypeSupport) {
            @Override
            public void convert(final MyViewHolder holder, int viewType, UserNote userNote, boolean isClickLong, int positoin) {
                switch (viewType){
                    case TITLE :
                        holder.setText(R.id.item_date_title,userNote.getUpdatedAt().split("-")[0] + "年" + userNote.getUpdatedAt().split("-")[1] + "月");
                        holder.setInvisiblity(R.id.note_top_line);
                        intializeRecycler(holder,userNote,isClickLong,positoin);
                        break;
                    case NOTEITEM :
                        intializeRecycler(holder,userNote,isClickLong,positoin);
                        break;
                    default:
                        break;
                }
            }
        };

        mainRecyclerView.setLayoutManager(layoutManager);
        mainRecyclerView.setAdapter(adapter);

        cancleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.clearSelectItems();
                adapter.setClickLong(false);
                notifyDataSetChanged();
                cancleDeletLayout.setVisibility(View.GONE);
            }
        });

        deletButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> noteIds = new ArrayList<String>();
                List<Integer> position = adapter.getSelectItems();
                for(int i : position){
                    noteIds.add(data.get(i).getObjectId());
                }

                UpdateUserNote.deletNote(noteIds,getContext(),changeData);
                adapter.clearSelectItems();
                adapter.setClickLong(false);
                cancleDeletLayout.setVisibility(View.GONE);
            }
        });
        return view;
    }

    private List<UserNote> intialize(){
        UpdateUserNote.getAuthorNote(user,getContext(),changeData);
        return data;
    }

    public void intializeRecycler(final MyViewHolder holder, UserNote userNote,final boolean isClickLong,int positoin){
        int circleColor,backgroundColor,textColor;
        switch(userNote.getMoodColor()){
            case  "red" : circleColor = R.drawable.red_circle;
                backgroundColor = R.color.text_background_red;
                textColor = R.color.text_red;
                break;

            case "green" : circleColor = R.drawable.green_circle;
                backgroundColor = R.color.text_background_green;
                textColor = R.color.text_green;
                break;

            case "blue" : circleColor = R.drawable.blue_circle;
                backgroundColor = R.color.text_background_blue;
                textColor = R.color.text_blue;
                break;

            case "pink" : circleColor = R.drawable.pink_circle;
                backgroundColor = R.color.text_background_pink;
                textColor = R.color.text_pink;
                break;

            case "purple" : circleColor = R.drawable.purple_circle;
                backgroundColor = R.color.text_background_purple;
                textColor = R.color.text_purple;
                break;

            case "yellow" : circleColor = R.drawable.yellow_circle;
                backgroundColor = R.color.text_background_yellow;
                textColor = R.color.text_yellow;
                break;

            case "gray" : circleColor = R.drawable.gray_circle;
                backgroundColor = R.color.text_background_gray;
                textColor = R.color.text_gray;
                break;
            default: circleColor = R.drawable.red_circle;
                backgroundColor = R.color.text_background_red;
                textColor = R.color.text_red;
                break;
        }

        /*if(data.get(positoin + 1) != -1){
            if(!data.get(positoin + 1).getUpdatedAt().split("-")[1].equals(upDateAt[1])){
                holder.setInvisiblity(R.id.note_bottom_line);
            }
        }*/

        holder.setCircleColor(R.id.note_circle,circleColor);
        holder.setBackGround(R.id.note_card_color,backgroundColor);
        holder.setTextColor(R.id.note_text,textColor);
        holder.setText(R.id.note_text,userNote.getNote());
        holder.setText(R.id.note_item_date,userNote.getUpdatedAt());

        if(isClickLong){
            holder.setVisiblity(R.id.note_checkbox);
            holder.setChecked(R.id.note_checkbox,adapter.isSelectItems(holder.getAdapterPosition()));
        }else {
            holder.setInvisiblity(R.id.note_checkbox);
        }


            holder.setOnClickListener(R.id.note_card_color,new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    if(!isClickLong){
                    //傳入數據，進入編輯頁面
                    Toast.makeText(getContext(),"点击",Toast.LENGTH_SHORT).show();
                    }
                }
            });
            holder.setOnLongClickListener(R.id.note_card_color, new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    adapter.setClickLong(true);
                    cancleDeletLayout.setVisibility(View.VISIBLE);
                    notifyDataSetChanged();
                    return true;
                }
            });

        //checkbox的點擊事件
        holder.setOnCheckedChangeLister(R.id.note_checkbox, new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
              if(isChecked){
                  adapter.addCheck(holder.getAdapterPosition());
              } else {
                  adapter.removeCheck(holder.getAdapterPosition());
              }
            }
        });

    }

    private void notifyDataSetChanged(){
        upDateAt = new String[]{"2050","12","12 24:59:59"};
        adapter.notifyDataSetChanged();
    }
}
