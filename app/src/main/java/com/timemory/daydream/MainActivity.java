package com.timemory.daydream;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.timemory.daydream.bean.entity.Sister;
import com.timemory.daydream.imageloader.PictureLoader;
import com.timemory.daydream.network.SisterApi;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button showBtn;
    private Button refreshBtn;
    private ImageView showImg;

    private ArrayList<Sister> data;
    /**当前的页数*/
    private int page = 1;
    /**当前显示的是哪一张*/
    private int curPos = 0;
    private PictureLoader loader;
    private SisterApi sisterApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loader = new PictureLoader();
        sisterApi = new SisterApi();
        initData();
        initUI();
    }

    private void initData(){
        data = new ArrayList<>();
        new SisterTask(page).execute();
    }

    private void initUI(){
        showImg = (ImageView)findViewById(R.id.img_show);
        showBtn = (Button)findViewById(R.id.btn_show);
        refreshBtn = (Button)findViewById(R.id.btn_reflesh);

        showBtn.setOnClickListener(this);
        refreshBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_show:
                if (data != null && !data.isEmpty()){
                    if (curPos > 9){
                        curPos = 0;
                    }
                    loader.load(showImg, data.get(curPos).getUrl());
                    curPos++;
                }
                break;
            case R.id.btn_reflesh:
                page++;
                new SisterTask(page).execute();
                curPos = 0;
                break;
            default:
                break;
        }

    }

    private class SisterTask extends AsyncTask<Void, Void, ArrayList<Sister>>{
        private int page;

        public SisterTask(int page){
            this.page = page;
        }

        @Override
        protected ArrayList<Sister> doInBackground(Void ... params){
            return sisterApi.fetchSister(10, page);
        }

        @Override
        protected void onPostExecute(ArrayList<Sister> sisters){
            super.onPostExecute(sisters);
            data.clear();
            data.addAll(sisters);
        }
    }
}
