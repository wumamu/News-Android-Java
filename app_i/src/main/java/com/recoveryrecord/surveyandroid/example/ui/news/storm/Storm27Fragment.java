package com.recoveryrecord.surveyandroid.example.ui.news.storm;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.recoveryrecord.surveyandroid.example.adapter.NewsRecycleViewAdapter;
import com.recoveryrecord.surveyandroid.example.R;
import com.recoveryrecord.surveyandroid.example.model.NewsModel;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.recoveryrecord.surveyandroid.example.config.Constants.NEWS_LIMIT_PER_PAGE;

public class Storm27Fragment extends Fragment {
    private ArrayList<NewsModel> dataModalArrayList;
    private NewsRecycleViewAdapter dataRVAdapter;
    private FirebaseFirestore db;
    public static Storm27Fragment newInstance(int position) {
        Storm27Fragment fragment = new Storm27Fragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        fragment.setArguments(args);

        return fragment;
    }

    public Storm27Fragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* mPosition = getArguments().getInt("position");*/

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.nested_layer2_category, container, false);
        // initializing our variables.
        RecyclerView courseRV = view.findViewById(R.id.idRVItems);

        // initializing our variable for firebase
        // firestore and getting its instance.
        db = FirebaseFirestore.getInstance();

        // creating our new array list
        dataModalArrayList = new ArrayList<>();
        courseRV.setHasFixedSize(true);

        // adding horizontal layout manager for our recycler view.
//        courseRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        courseRV.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        // adding our array list to our recycler view adapter class.
        dataRVAdapter = new NewsRecycleViewAdapter(dataModalArrayList, getActivity());

        // setting adapter to our recycler view.
        courseRV.setAdapter(dataRVAdapter);

        loadrecyclerViewData();

        return view;
    }
    private void loadrecyclerViewData() {
//.orderBy("name").limit(3)//                db.collectionGroup("news") //
        db.collection("news")
                .whereEqualTo("media", "storm")
                .whereArrayContains("category", "人物")
                .orderBy("pubdate", Query.Direction.DESCENDING)
                .limit(NEWS_LIMIT_PER_PAGE)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    Log.d("logpager", "sss");
                    // after getting the data we are calling on success method
                    // and inside this method we are checking if the received
                    // query snapshot is empty or not.
                    if (!queryDocumentSnapshots.isEmpty()) {
                        // if the snapshot is not empty we are hiding our
                        // progress bar and adding our data in a list.
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot d : list) {
                            // after getting this list we are passing that
                            // list to our object class.
//                                NewsModel dataModal = d.toObject(NewsModel.class);
                            NewsModel dataModal = new NewsModel(d.getString("title"), d.getString("media"), d.getString("id"), d.getTimestamp("pubdate"), d.getString("image"));

                            // and we will pass this object class
                            // inside our arraylist which we have
                            // created for recycler view.
                            dataModalArrayList.add(dataModal);
                        }
                        // after adding the data to recycler view.
                        // we are calling recycler view notifyDataSetChanged
                        // method to notify that data has been changed in recycler view.
                        dataRVAdapter.notifyDataSetChanged();
                    }  // if the snapshot is empty we are
                    // displaying a toast message.
                    //                            Toast.makeText(TestNewsOneActivity.this, "No data found in Database", Toast.LENGTH_SHORT).show();

                }).addOnFailureListener(e -> {
                    Log.d("logpager", "Fail to get the data." + e);
                    // if we do not get any data or any error we are displaying
                    // a toast message that we do not get any data
    //                Toast.makeText(TestNewsOneActivity.this, "Fail to get the data.", Toast.LENGTH_SHORT).show();
                });
    }

}