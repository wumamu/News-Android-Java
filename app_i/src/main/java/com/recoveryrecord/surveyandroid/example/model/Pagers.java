package com.recoveryrecord.surveyandroid.example.model;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.recoveryrecord.surveyandroid.example.NewsRecycleViewAdapter;
import com.recoveryrecord.surveyandroid.example.R;
import com.recoveryrecord.surveyandroid.example.model.NewsModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class Pagers extends RelativeLayout {
    private RecyclerView courseRV;
    private ArrayList<NewsModel> dataModalArrayList;
    private NewsRecycleViewAdapter dataRVAdapter;
    private FirebaseFirestore db;
    public Pagers(final Context context, int pageNumber, String pageTag, String media_name) {
        super(context);

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_pagers, null);
//        TextView textView = view.findViewById(R.id.textView);
//        textView.setText("第"+pageNumber+"頁");


        // initializing our variables.
        courseRV = view.findViewById(R.id.idRVItems);

        // initializing our variable for firebase
        // firestore and getting its instance.
        db = FirebaseFirestore.getInstance();

        // creating our new array list
        dataModalArrayList = new ArrayList<>();
        courseRV.setHasFixedSize(true);

        // adding horizontal layout manager for our recycler view.
//        courseRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        courseRV.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        // adding our array list to our recycler view adapter class.
        dataRVAdapter = new NewsRecycleViewAdapter(dataModalArrayList, context);

        // setting adapter to our recycler view.
        courseRV.setAdapter(dataRVAdapter);

        loadrecyclerViewData(media_name, context);
//        Log.d("log: pager", "news id");

        //remember!!!!!!!!!!!!!!!!!
        addView(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

    }
    private void loadrecyclerViewData(final String media_nn, final Context cc) {
        Log.d("log: pager", media_nn);
        db.collection("medias")
                .document(media_nn)
                .collection("news")
                .orderBy("pubdate", Query.Direction.DESCENDING)
                .limit(50)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        // after getting the data we are calling on success method
                        // and inside this method we are checking if the received
                        // query snapshot is empty or not.
//                        Log.d("log: pager", "onSuccess");
                        if (!queryDocumentSnapshots.isEmpty()) {
                            // if the snapshot is not empty we are hiding our
                            // progress bar and adding our data in a list.
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                // after getting this list we are passing that
                                // list to our object class.
                                NewsModel dataModal = d.toObject(NewsModel.class);

                                // and we will pass this object class
                                // inside our arraylist which we have
                                // created for recycler view.
                                dataModalArrayList.add(dataModal);
                            }
                            // after adding the data to recycler view.
                            // we are calling recycler view notifyDataSetChanged
                            // method to notify that data has been changed in recycler view.
                            dataRVAdapter.notifyDataSetChanged();
                        } else {
                            // if the snapshot is empty we are
                            // displaying a toast message.
//                            TextView myTextViewsEmpty = new TextView(cc);
//                            myTextViewsEmpty.setText("no result");
                            Log.d("log: pager", "No data found " + media_nn);
//                            vv.addView(myTextViewsEmpty);
//                            Toast.makeText(cc, "No data found in Database", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // if we do not get any data or any error we are displaying
                // a toast message that we do not get any data
                Log.d("log: pager", "Fail to get the data." + media_nn);
//                Toast.makeText(cc, "Fail to get the data." + media_nn, Toast.LENGTH_SHORT).show();
            }
        });
    }
}