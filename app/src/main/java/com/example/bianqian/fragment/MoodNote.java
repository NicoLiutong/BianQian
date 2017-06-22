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
import android.widget.LinearLayout;

import com.example.bianqian.R;
import com.example.bianqian.adapter.MultiItemTypeSupport;
import com.example.bianqian.adapter.MyAdapter;
import com.example.bianqian.adapter.MyViewHolder;
import com.example.bianqian.db.UserNote;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 刘通 on 2017/6/15.
 */

public class MoodNote extends Fragment {

    public static final int TITLE = 1;
    public static final int NOTEITEM = 2;

    private RecyclerView mainRecyclerView;

    private Button cancleButton,deletButton;

    private LinearLayout cancleDeletLayout;

    private List<UserNote> data;

    private MyAdapter<UserNote> adapter;

    private MultiItemTypeSupport<UserNote> multiItemTypeSupport;

    private Integer date;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.main_activity_recycler,container,false);
        mainRecyclerView = (RecyclerView) view.findViewById(R.id.main_recyclerview);
        cancleDeletLayout = (LinearLayout) view.findViewById(R.id.cancle_delet_layout);
        cancleDeletLayout.setVisibility(View.GONE);
        cancleButton = (Button) view.findViewById(R.id.cancle_button);
        deletButton = (Button) view.findViewById(R.id.delet_button);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        multiItemTypeSupport = new MultiItemTypeSupport<UserNote>() {
            @Override
            public int getLayoutId(int itenType) {
                if(itenType == TITLE){
                    return R.layout.date_item;
                }else {
                    return R.layout.item_note;
                }
            }

            @Override
            public int getItemViewType(int position, UserNote userNote) {
                if(userNote.getObjectId().equals("1")){
                    return TITLE;
                }else {
                    return NOTEITEM;
                }
            }
        };


        intialize();
        adapter = new MyAdapter<UserNote>(getContext(),data,multiItemTypeSupport) {
            @Override
            public void convert(final MyViewHolder holder, int viewType, UserNote userNote, boolean isClickLong) {
                if(viewType == TITLE){
                    holder.setText(R.id.item_date_title,userNote.getNote());
                    holder.setCircleColor(R.id.note_circle,R.drawable.red_circle);
                    holder.setInvisiblity(R.id.note_top_line);
                    holder.setBackGround(R.id.note_card_color,R.color.text_background_red);
                    holder.setText(R.id.note_text,userNote.getNote());
                    holder.setTextColor(R.id.note_text,R.color.text_red);
                    holder.setOnLongClickListener(R.id.note_card_color, new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            holder.setVisiblity(R.id.note_checkbox);
                            //holder.setChecked(R.id.note_checkbox,adapter.isSelectItems(holder.getAdapterPosition()));
                            return true;
                        }
                    });

                }else {
                    holder.setCircleColor(R.id.note_circle,R.drawable.green_circle);
                    holder.setBackGround(R.id.note_card_color,R.color.text_background_green);
                    holder.setText(R.id.note_text,userNote.getNote());
                    holder.setTextColor(R.id.note_text,R.color.text_green);
                    holder.setOnLongClickListener(R.id.note_card_color, new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            holder.setVisiblity(R.id.note_checkbox);
                            //holder.setChecked(R.id.note_checkbox,adapter.isSelectItems(holder.getAdapterPosition()));
                            return true;
                        }
                    });
                }
            }
        };

        mainRecyclerView.setLayoutManager(layoutManager);
        mainRecyclerView.setAdapter(adapter);
        return view;
    }

    private List<UserNote> intialize(){
        data = new ArrayList<>();
        UserNote user1 = new UserNote();
        user1.setMoodColor("red");
        user1.setNote("我爱你");
        user1.setObjectId("1");
        data.add(user1);
        UserNote user2 = new UserNote();
        user2.setMoodColor("green");
        user2.setNote("mamapi");
        user2.setObjectId("2");
        data.add(user2);
        UserNote user3 = new UserNote();
        user3.setMoodColor("blue");
        user3.setNote("mamapi");
        user3.setObjectId("3");
        data.add(user3);
        return data;
    }
}
