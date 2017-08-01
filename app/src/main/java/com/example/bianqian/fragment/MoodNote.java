package com.example.bianqian.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.example.bianqian.R;
import com.example.bianqian.activity.EditingTextActivity;
import com.example.bianqian.adapter.AdapterDateList;
import com.example.bianqian.adapter.MultiItemTypeSupport;
import com.example.bianqian.adapter.MyAdapter;
import com.example.bianqian.adapter.MyViewHolder;
import com.example.bianqian.bmobbasic.User;
import com.example.bianqian.bmobbasic.UserNote;
import com.example.bianqian.impl.GetFindData;
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

    private static final String ALL = "all";
    private static final String RED = "red";
    private static final String BLUE = "blue";
    private static final String YELLOW = "yellow";
    private static final String GREEN = "green";
    private static final String PURPLE = "purple";
    private static final String PINK = "pink";
    private static final String GRAY = "gray";

    private String mood = ALL;

    private User user;

    private ProgressDialog dialog ;

    private RecyclerView mainRecyclerView;

    private Button cancleButton,deletButton;

    private FloatingActionButton addNewNoteButton;

    private LinearLayout cancleDeletLayout;

    private SwipeRefreshLayout swipeRefreshNote;

    private List<AdapterDateList> data = new ArrayList<>();

    private List<UserNote> allData;

    private MyAdapter<AdapterDateList> adapter;

    private MultiItemTypeSupport<AdapterDateList> multiItemTypeSupport;

    private GetFindData<UserNote> changeData;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.main_activity_recycler,container,false);
        user = BmobUser.getCurrentUser(User.class);
        mainRecyclerView = (RecyclerView) view.findViewById(R.id.main_recyclerview);
        cancleDeletLayout = (LinearLayout) view.findViewById(R.id.cancle_delet_layout);
        addNewNoteButton = (FloatingActionButton) view.findViewById(R.id.floating_newitem_button);
        swipeRefreshNote = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_note);
        swipeRefreshNote.setColorSchemeResources(R.color.text_background_purple,R.color.colorAccent,R.color.text_background_pink,R.color.text_background_red);
        dialog = new ProgressDialog(getActivity());
        swipeRefreshNote.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                intialize();
            }
        });

        cancleDeletLayout.setVisibility(View.GONE);
        cancleButton = (Button) view.findViewById(R.id.cancle_button);
        deletButton = (Button) view.findViewById(R.id.delet_button);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        multiItemTypeSupport = new MultiItemTypeSupport<AdapterDateList>() {
            @Override
            public int getLayoutId(int itenType) {
                switch (itenType){
                    case TITLE : return R.layout.date_item;
                    case NOTEITEM : return R.layout.item_note;
                    default: return R.layout.item_nothing;
                }
            }


            @Override
            public int getItemViewType(int position, AdapterDateList dataList) {
                if(data.size() != 0){
                    if(dataList.isDataTile()){
                        return TITLE;
                    }else {
                        return NOTEITEM;
                    }
                }
                    return NOLAYOUT;

                }
            };
        changeData = new GetFindData<UserNote>() {
            @Override
            public void returnFindData(List<UserNote> findData,Boolean isSuccess) {
                if(isSuccess){
                allData = findData;
                //Log.d("1","3");
                notifyDataSetChanged(mood,allData);
                }else {
                    swipeRefreshNote.setRefreshing(false);
                }
            }

            @Override
            public void deletDataResult(Boolean isSuccess) {
                //删除完数据后，获取删除成功的项目并更新
                intialize();

            }

            @Override
            public void creatDataResult(Boolean isSuccess) {     }

            @Override
            public void upDataResult(Boolean isSuccess) {        }
        };

        //intialize();
        adapter = new MyAdapter<AdapterDateList>(getContext(),data,multiItemTypeSupport) {
            @Override
            public void convert(final MyViewHolder holder, int viewType, AdapterDateList adapterDate, boolean isClickLong, int positoin) {
                switch (viewType){
                    case TITLE :
                        holder.setText(R.id.item_date_title,adapterDate.getDate().split("-")[0] + "年" + adapterDate.getDate().split("-")[1] + "月");
                        holder.setInvisiblity(R.id.note_top_line);
                        intializeRecycler(holder,adapterDate,isClickLong,positoin);
                        break;
                    case NOTEITEM :
                        intializeRecycler(holder,adapterDate,isClickLong,positoin);
                        break;
                    default:
                        break;
                }
            }
        };
        //Log.d("1","start");
        mainRecyclerView.setLayoutManager(layoutManager);
        mainRecyclerView.setAdapter(adapter);

        addNewNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), EditingTextActivity.class);
                intent.putExtra(EditingTextActivity.TYPE,EditingTextActivity.CREATNOTE);
                intent.putExtra(EditingTextActivity.NOTEID,"");
                intent.putExtra(EditingTextActivity.MOOD,"red");
                intent.putExtra(EditingTextActivity.DATE,"");
                intent.putExtra(EditingTextActivity.TEXT,"");
                getContext().startActivity(intent);
            }
        });


        cancleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.clearSelectItems();
                adapter.setClickLong(false);
                notifyDataSetChanged(mood,allData);
                cancleDeletLayout.setVisibility(View.GONE);
                addNewNoteButton.setVisibility(View.VISIBLE);
            }
        });

        deletButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setMessage("删除中…");
                dialog.show();
                //dialog = ProgressDialog.show(getActivity(), null, "删除中…", true, false);
                List<String> noteIds = new ArrayList<String>();
                List<Integer> position = adapter.getSelectItems();
                for(int i : position){
                    noteIds.add(data.get(i).getUserNote().getObjectId());
                }

                UpdateUserNote.deletNote(noteIds,getContext(),changeData);
                adapter.clearSelectItems();
                adapter.setClickLong(false);
                cancleDeletLayout.setVisibility(View.GONE);
                addNewNoteButton.setVisibility(View.VISIBLE);
            }
        });
        return view;
    }

   @Override
    public void onResume() {
       //Log.d("1","1");
       //data.clear();
       //adapter.notifyDataSetChanged();
       swipeRefreshNote.setRefreshing(true);
       intialize();
        super.onResume();
    }

    private void intialize(){
        //Log.d("1","2");
        UpdateUserNote.getAuthorNote(user,getContext(),changeData);
    }

    public void intializeRecycler(final MyViewHolder holder, final AdapterDateList adapterDate, final boolean isClickLong, int positoin){
        int circleColor,backgroundColor,textColor;
        switch(adapterDate.getUserNote().getMoodColor()){
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

        holder.setCircleColor(R.id.note_circle,circleColor);
        //holder.setCardBackGround(R.id.note_card_color,backgroundColor);
        holder.setBackGround(R.id.note_text,backgroundColor);
        holder.setTextColor(R.id.note_text,textColor);
        holder.setText(R.id.note_text,adapterDate.getUserNote().getNote());
        holder.setText(R.id.note_item_date,adapterDate.getUserNote().getUpdatedAt());

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
                        Intent intent = new Intent(getContext(), EditingTextActivity.class);
                        intent.putExtra(EditingTextActivity.TYPE,EditingTextActivity.CHANGENOTE);
                        intent.putExtra(EditingTextActivity.NOTEID,adapterDate.getUserNote().getObjectId());
                        intent.putExtra(EditingTextActivity.MOOD,adapterDate.getUserNote().getMoodColor());
                        intent.putExtra(EditingTextActivity.DATE,adapterDate.getUserNote().getUpdatedAt());
                        intent.putExtra(EditingTextActivity.TEXT,adapterDate.getUserNote().getNote());
                        getContext().startActivity(intent);
                    }
                }
            });
            holder.setOnLongClickListener(R.id.note_card_color, new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    adapter.setClickLong(true);
                    cancleDeletLayout.setVisibility(View.VISIBLE);
                    addNewNoteButton.setVisibility(View.GONE);
                    notifyDataSetChanged(mood,allData);
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

    private void notifyDataSetChanged(String mood,List<UserNote> alldata){
        //Log.d("1","4");
        data.clear();
        String date = "2050-12-12 24:59:59";
        for(AdapterDateList i:getListWithMood(mood,alldata)){
            if(i.getUserNote().getUpdatedAt().split("-")[0].equals(date.split("-")[0])&&i.getUserNote().getUpdatedAt().split("-")[1].equals(date.split("-")[1])){
               i.setDataTile(false);
                i.setDate(i.getUserNote().getUpdatedAt());
                data.add(i);
            }else {
                date = i.getUserNote().getUpdatedAt();
                i.setDataTile(true);
                i.setDate(i.getUserNote().getUpdatedAt());
                data.add(i);
            }
        }
        adapter.notifyDataSetChanged();
        swipeRefreshNote.setRefreshing(false);
        dialog.dismiss();
    }

    private List<AdapterDateList> getListWithMood(String mood,List<UserNote> alldata){
        List<AdapterDateList> adapterDateLists = new ArrayList<>();
        switch (mood){
            case RED: for (UserNote i:alldata){
                if(i.getMoodColor().equals("red")){
                    AdapterDateList adapterDateList = new AdapterDateList();
                    adapterDateList.setUserNote(i);
                    adapterDateLists.add(adapterDateList);
                }
            }
                return adapterDateLists;

            case GREEN : for (UserNote i:alldata){
                if(i.getMoodColor().equals("green")){
                    AdapterDateList adapterDateList = new AdapterDateList();
                    adapterDateList.setUserNote(i);
                    adapterDateLists.add(adapterDateList);
                }
            }
                return adapterDateLists;

            case BLUE : for (UserNote i:alldata){
                if(i.getMoodColor().equals("blue")){
                    AdapterDateList adapterDateList = new AdapterDateList();
                    adapterDateList.setUserNote(i);
                    adapterDateLists.add(adapterDateList);
                }
            }
                return adapterDateLists;

            case YELLOW : for (UserNote i:alldata){
                if(i.getMoodColor().equals("yellow")){
                    AdapterDateList adapterDateList = new AdapterDateList();
                    adapterDateList.setUserNote(i);
                    adapterDateLists.add(adapterDateList);
                }
            }
                return adapterDateLists;

            case PURPLE :  for (UserNote i:alldata){
                if(i.getMoodColor().equals("purple")){
                    AdapterDateList adapterDateList = new AdapterDateList();
                    adapterDateList.setUserNote(i);
                    adapterDateLists.add(adapterDateList);
                }
            }
                return adapterDateLists;

            case PINK : for (UserNote i:alldata){
                if(i.getMoodColor().equals("pink")){
                    AdapterDateList adapterDateList = new AdapterDateList();
                    adapterDateList.setUserNote(i);
                    adapterDateLists.add(adapterDateList);
                }
            }
                return adapterDateLists;

            case GRAY : for (UserNote i:alldata){
                if(i.getMoodColor().equals("gray")){
                    AdapterDateList adapterDateList = new AdapterDateList();
                    adapterDateList.setUserNote(i);
                    adapterDateLists.add(adapterDateList);
                }
            }
                return adapterDateLists;

            default:
                for(UserNote i:alldata){
                    AdapterDateList adapterDateList = new AdapterDateList();
                    adapterDateList.setUserNote(i);
                    adapterDateLists.add(adapterDateList);
                }
                return adapterDateLists;
        }
    }

    public void updateWithMood(String mood){
        this.mood = mood;
        notifyDataSetChanged(this.mood,allData);
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
