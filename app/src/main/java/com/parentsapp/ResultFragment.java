package com.parentsapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.parentsapp.adapter.ListAdapter;
import com.parentsapp.models.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ResultFragment  extends Fragment {
    private FirebaseAuth auth;
    FirebaseFirestore db;
    FirebaseUser curUser;

    java.util.List<Item> List = new ArrayList<>();
    private RecyclerView recyclerView;
    private ListAdapter mAdapter;

    String uid;

    public ResultFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_attendance, container, false);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        curUser = auth.getCurrentUser();

        if (curUser != null)
            uid = curUser.getUid();

        recyclerView = v.findViewById(R.id.recyclerAttendance);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);

        mAdapter = new ListAdapter(List);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        getList();

        return v;
    }

    private void getList() {
        List.clear();
        mAdapter.notifyDataSetChanged();
        DocumentReference docRef = db.collection("Results").document(uid);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    Map<String, Object> map = task.getResult().getData();
                    for (Map.Entry<String, Object> entry : map.entrySet()) {
                        Item item = new Item(entry.getKey(), entry.getValue().toString());
                        List.add(item);
                    }
                }
                mAdapter.notifyDataSetChanged();
            }
        });
    }
}