package com.example.nasmanage.aa.ui.main;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.nasmanage.Adapter.ListAdapter;
import com.example.nasmanage.GloabValue;
import com.example.nasmanage.R;
import com.example.nasmanage.aa.MainActivity22;
import com.example.nasmanage.bean.ApiFile;
import com.example.nasmanage.bean.ApiPage;
import com.example.nasmanage.databinding.FragmentMainBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Stack;

public class MainFragment extends Fragment {

    private MainViewModel mViewModel;
    public static int count = 0;
    public static Stack<String> pathStack = new Stack<>();
    private String basePath;
    private ListAdapter listAdapter;
    private Toolbar toolbar;

    public static MainFragment newInstance(String path) {
        return new MainFragment(path);
    }

    private FragmentMainBinding binding;
    private Fragment that;

    private void get_list() {
        new GloabValue().init(getContext());
        RequestQueue queue = Volley.newRequestQueue(this.getContext());
        String url = GloabValue.base_api_url + "/quickGetFiles?basePath=" + basePath;
        Log.d("url", url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gs = new Gson();
                        ApiPage apiPage = gs.fromJson(response.toString(), new TypeToken<ApiPage>() {
                        }.getType());
                        List<ApiFile> list = apiPage.getFiles();
                        listAdapter.set_list(list);
                        listAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("err", error.toString());
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    public MainFragment(String path) {
        super();
        this.basePath = path;
        pathStack.push(basePath);
        Log.d("", "new:" + path);
    }

    public MainFragment() {
        super();
        String path = GloabValue.base_api_path;
        this.basePath = path;
        pathStack.push(basePath);
        Log.d("", "new:" + path);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        Log.d("QWER", hidden + basePath);
        if (!hidden) {
            if (toolbar != null) {
                String s = basePath.replace("\\", "/").replace(GloabValue.replace.replace("\\", "/"), "");
                String sl[] = s.split("/");
                String b = sl == null || sl.length == 0 ? "/" : sl[sl.length - 1];
                toolbar.setTitle("".equals(b) ? "/" : b);
                toolbar.setSubtitle(s);
            }
        } else {
            MainActivity22 mainActivity = (MainActivity22) getActivity();
            toolbar = (Toolbar) mainActivity.findViewById(R.id.toolbar);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater, container, false);
        binding.actionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestQueue queue = Volley.newRequestQueue(getContext());
                String url = GloabValue.base_api_url + "/initAll?path=" + basePath;
                Log.d("url", url);
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("err", error.toString());
                    }
                });
                // Add the request to the RequestQueue.
                queue.add(stringRequest);
            }
        });
        binding.actionB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.rl.smoothScrollToPosition(0);
            }
        });
        binding.actionC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearImageMemoryCache(getContext());
                get_list();
            }
        });
        if (toolbar != null) {
            String s = basePath.replace("\\", "/").replace(GloabValue.replace.replace("\\", "/"), "");
            String sl[] = s.split("/");
            String b = sl == null || sl.length == 0 ? "/" : sl[sl.length - 1];
            toolbar.setTitle("".equals(b) ? "/" : b);
            toolbar.setSubtitle(s);
        }
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void clearImageMemoryCache(Context context) {
        try {
            Glide.get(context).clearMemory();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        // TODO: Use the ViewModel
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        binding.rl.setLayoutManager(layoutManager);
        listAdapter = new ListAdapter(this.getContext(), getParentFragmentManager(), this);
        binding.rl.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();
        that = this;
        get_list();
    }

    @Override
    public void onAttach(Context activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        if (activity instanceof MainActivity22) {
            MainActivity22 mainActivity = (MainActivity22) activity;
            toolbar = (Toolbar) mainActivity.findViewById(R.id.toolbar);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        if (activity instanceof MainActivity22) {
            MainActivity22 mainActivity = (MainActivity22) activity;
            toolbar = (Toolbar) mainActivity.findViewById(R.id.toolbar);
        }
    }
}