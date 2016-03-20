package com.greysonparrelli.classqueue.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.greysonparrelli.classqueue.R;
import com.greysonparrelli.classqueue.models.Question;

import java.util.ArrayList;
import java.util.List;


public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> {

    private List<Question> mData;
    private final Context mAppContext;

    public QuestionAdapter(Context context) {
        mData = new ArrayList<>(0);
        mAppContext = context.getApplicationContext();
    }

    public void setData(List<Question> questions) {
        mData = questions;
        notifyDataSetChanged();
    }

    @Override
    public QuestionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_question, parent, false);
        return new QuestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(QuestionViewHolder holder, int position) {
        Question question = mData.get(position);
        holder.computerNumber.setText(String.format(mAppContext.getString(R.string.computer_number), question.getComputerNumber()));
        holder.time.setText(generateTimeString(question.getTime()));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    private String generateTimeString(long questionTime) {
        long millisecondsAgo = System.currentTimeMillis() - questionTime;
        long secondsAgo = millisecondsAgo / 1000;
        if (secondsAgo < 10) {
            return "Just now";
        } else if (secondsAgo < 60) {
            return "Less than a minute ago";
        } else if (secondsAgo < 120) {
            return "1 minute ago";
        } else {
            return (secondsAgo / 60) + " minutes ago";
        }
    }



    public static class QuestionViewHolder extends RecyclerView.ViewHolder {

        public final TextView computerNumber;
        public final TextView time;

        public QuestionViewHolder(View itemView) {
            super(itemView);
            computerNumber = (TextView) itemView.findViewById(R.id.computer_number);
            time = (TextView) itemView.findViewById(R.id.time);
        }
    }
}
