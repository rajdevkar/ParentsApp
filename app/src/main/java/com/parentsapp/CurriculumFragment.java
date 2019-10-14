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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.parentsapp.adapter.CurriculumAdapter;
import com.parentsapp.adapter.ListAdapter;
import com.parentsapp.models.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CurriculumFragment extends Fragment {
    private FirebaseAuth auth;
    FirebaseFirestore db;
    FirebaseUser curUser;

    java.util.List<Item> List = new ArrayList<>();
    private RecyclerView recyclerView;
    private CurriculumAdapter mAdapter;

    String uid;

    public CurriculumFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_curriculum, container, false);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        curUser = auth.getCurrentUser();

        if (curUser != null)
            uid = curUser.getUid();

        recyclerView = v.findViewById(R.id.recyclerAttendance);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);

        mAdapter = new CurriculumAdapter(List);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        getList();

        return v;
    }

    private void getList() {
        List.clear();
        mAdapter.notifyDataSetChanged();
        db.collection("Curriculum")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Item item = new Item(document.get("Title").toString(), document.get("Content").toString());
                                List.add(item);
                            }
                        } else {
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }
}
