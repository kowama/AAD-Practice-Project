package com.kowama.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.kowama.R;
import com.kowama.adapters.StudentAdapter;
import com.kowama.models.Student;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LearningLeaderFragment extends Fragment {
    private StudentAdapter mStudentAdapter;
    private List<Student> mLearningLeader = new ArrayList<>();

    public static LearningLeaderFragment newInstance() {
        return new LearningLeaderFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.learning_leader_fragment,
                container, false);
        RecyclerView learningLeaderRecyclerView = view.findViewById(R.id.learning_leaders_recycler_view);
        mStudentAdapter = new StudentAdapter(getContext(), mLearningLeader);
        learningLeaderRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        learningLeaderRecyclerView.setAdapter(mStudentAdapter);
        learningLeaderRecyclerView.setItemAnimator(new DefaultItemAnimator());
        learningLeaderRecyclerView.hasFixedSize();
        fetchLearningLeaders();
        return view;
    }

    private void fetchLearningLeaders() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        String URL = "https://gadsapi.herokuapp.com/api/hours";
        final JsonArrayRequest jsonArrayRequest =
                new JsonArrayRequest(URL,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                for (int i = 0; i < response.length(); i++) {
                                    try {
                                        JSONObject jsonObject = response.getJSONObject(i);
                                        Student student = new Student(
                                                jsonObject.getString("name"),
                                                0,
                                                jsonObject.getInt("hours"),
                                                jsonObject.getString("country"),
                                                jsonObject.getString("badgeUrl"));
                                        mLearningLeader.add(student);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        progressDialog.dismiss();
                                    }
                                }
                                Collections.sort(mLearningLeader, new Comparator<Student>() {
                                    @Override
                                    public int compare(Student s1, Student s2) {
                                        return -Integer.compare(s1.getHours(), s2.getHours());
                                    }
                                });
                                mStudentAdapter.notifyDataSetChanged();
                                progressDialog.dismiss();
                                Log.println(Log.DEBUG, "DEB", String.valueOf(mLearningLeader.size()) + "?????????????????//");
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", error.toString());
                        progressDialog.dismiss();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonArrayRequest);
    }
}