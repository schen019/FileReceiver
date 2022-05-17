package com.suyi.filereceiver.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.suyi.filereceiver.File;
import com.suyi.filereceiver.FileAdapter;
import com.suyi.filereceiver.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryFragment extends Fragment
{

    public static final String TAG = "FilesFragment";
    RecyclerView rvFiles;
    List<File> allFiles;
    FileAdapter adapter;
    SwipeRefreshLayout swipeContainer;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HistoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HistoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HistoryFragment newInstance(String param1, String param2)
    {
        HistoryFragment fragment = new HistoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        rvFiles = view.findViewById(R.id.rvFiles);
        allFiles = new ArrayList<>();
        adapter = new FileAdapter(getContext(), allFiles);
        rvFiles.setAdapter(adapter);
        rvFiles.setLayoutManager(new LinearLayoutManager(getContext()));
        queryPosts();

        swipeContainer = view.findViewById(R.id.swipeContainer);
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.i(TAG, "fetching new data");
                queryPosts();
            }
        });

    }


    private void queryPosts()
    {
        String currentUser = ParseUser.getCurrentUser().getUsername();
        ParseQuery<File> query = ParseQuery.getQuery(File.class);
        query.include(File.KEY_FILE);
        query.whereEqualTo(File.KEY_RECEIVER,currentUser);
        query.setLimit(20);
        query.orderByDescending(File.KEY_CREATED_AT);
        query.findInBackground(new FindCallback<File>()
        {
            @Override
            public void done(List<File> files, ParseException e)
            {
                if (e != null)
                {
                    Log.e(TAG, "error when querying the database", e);
                    return;
                }
                allFiles.addAll(files);
                adapter.notifyDataSetChanged();
            }
        });

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false);
    }
}