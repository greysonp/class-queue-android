package com.greysonparrelli.classqueue;

import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.greysonparrelli.classqueue.models.Question;

import java.util.ArrayList;
import java.util.List;

public class FirebaseManager {

    public static void registerForQuestionUpdates(final IOnQuestionsChangedListener listener) {
        getClient().child("questions").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Question> questions = new ArrayList<>((int) dataSnapshot.getChildrenCount());
                for (DataSnapshot questionSnapshot : dataSnapshot.getChildren()) {
                    questions.add(new Question(questionSnapshot));
                }
                listener.onQuestionsChanged(questions);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.e("REMOVE", firebaseError.getMessage());
            }
        });
    }

    private static Firebase getClient() {
        return new Firebase("https://class-queue.firebaseio.com/");
    }


    public interface IOnQuestionsChangedListener {
        void onQuestionsChanged(List<Question> questions);
    }
}
