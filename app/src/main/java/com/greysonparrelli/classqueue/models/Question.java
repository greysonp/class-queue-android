package com.greysonparrelli.classqueue.models;

import com.firebase.client.DataSnapshot;

public class Question {

    private final String mKey;
    private final int mComputerNumber;
    private final String mName;
    private final String mQuestion;
    private final long mTime;


    public Question(DataSnapshot snapshot) {
        mKey = snapshot.getKey();
        mComputerNumber = Integer.parseInt((String) snapshot.child("computer_number").getValue());
        mName = (String) snapshot.child("name").getValue();
        mQuestion = (String) snapshot.child("question").getValue();
        mTime = (long) snapshot.child("time").getValue();
    }

    public String getKey() {
        return mKey;
    }

    public int getComputerNumber() {
        return mComputerNumber;
    }

    public String getName() {
        return mName;
    }

    public String getQuestion() {
        return mQuestion;
    }

    public long getTime() {
        return mTime;
    }
}
