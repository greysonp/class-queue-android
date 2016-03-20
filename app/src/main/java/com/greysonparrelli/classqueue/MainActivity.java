package com.greysonparrelli.classqueue;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.greysonparrelli.classqueue.adapter.QuestionAdapter;
import com.greysonparrelli.classqueue.models.Question;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mQuestionList;
    private QuestionAdapter mQuestionAdapter;
    private Timer mTimer;

    private BroadcastReceiver mTimeTickReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Intent.ACTION_TIME_TICK.equals(intent.getAction())) {
                mQuestionAdapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mQuestionList = (RecyclerView) findViewById(R.id.list);
        mQuestionList.setLayoutManager(new LinearLayoutManager(this));

        mQuestionAdapter = new QuestionAdapter(this);
        mQuestionList.setAdapter(mQuestionAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseManager.registerForQuestionUpdates(new FirebaseManager.IOnQuestionsChangedListener() {
            @Override
            public void onQuestionsChanged(List<Question> questions) {
                mQuestionAdapter.setData(questions);
            }
        });

        mTimer = new Timer();
        mTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mQuestionAdapter.notifyDataSetChanged();
                    }
                });
            }
        }, TimeUnit.SECONDS.toMillis(1), TimeUnit.SECONDS.toMillis(1));
//        registerReceiver(mTimeTickReceiver, new IntentFilter(Intent.ACTION_TIME_TICK));
    }

    @Override
    protected void onStop() {
        super.onStop();
//        unregisterReceiver(mTimeTickReceiver);
        mTimer.cancel();
    }
}
