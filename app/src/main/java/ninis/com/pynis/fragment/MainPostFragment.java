package ninis.com.pynis.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import ninis.com.pynis.CustomAdapter;
import ninis.com.pynis.Defines;
import ninis.com.pynis.ItemClickSupport;
import ninis.com.pynis.R;
import ninis.com.pynis.SimpleDividerItemDecoration;
import ninis.com.pynis.VolleyMgr;
import ninis.com.pynis.activity.DetailActivity;
import ninis.com.pynis.activity.MainActivity;
import ninis.com.pynis.data.ClienPostData;
import ninis.com.pynis.data.ClienTabData;

/**
 * Created by gypark on 2016. 1. 5..
 */
public class MainPostFragment extends Fragment {

//    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView listview;
    private CustomAdapter listAdapter;

    private int visibleItemCount, totalItemCount, firstVisibleItem;
    private int previousTotal = 0;
    private boolean loading = true;

    private ClienTabData.TabItem mTabData;
    private int loadedPage = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main_post, null);

        listview = (RecyclerView) root.findViewById(R.id.rv_post);

        return root;
    }

    public void setData(ClienTabData.TabItem data) {
        mTabData = data;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setListView();
    }

    private void setListView() {
        linearLayoutManager = new LinearLayoutManager(getActivity());
        listview.setLayoutManager(linearLayoutManager);
        listview.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));

        listAdapter = new CustomAdapter();
        listview.setAdapter(listAdapter);
        listview.setItemAnimator(new DefaultItemAnimator());

        listview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                visibleItemCount = listview.getChildCount();
                totalItemCount = linearLayoutManager.getItemCount();
                firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();

                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false;
                        previousTotal = totalItemCount;
                    }
                }
                if (!loading && (totalItemCount - visibleItemCount)
                        <= (firstVisibleItem)) {
                    getMoreData();

                    loading = true;
                }
            }
        });


        ItemClickSupport.addTo(listview).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                try {
                    String url = (String) v.getTag();

                    Intent intent = new Intent(getActivity(), DetailActivity.class);
                    intent.putExtra("URL", url);
                    startActivity(intent);
                } catch (Exception e) {
                }
            }
        });

        getMainData();
    }

    private void getMainData() {
        String apiUrl = getMainApi();
        StringRequest stringRequest = new StringRequest(apiUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if( response == null )
                            return;

                        try {
                            Toast.makeText(getActivity(), "Loaded Data.", Toast.LENGTH_SHORT).show();

                            ClienPostData clienPostData = new Gson().fromJson(response, ClienPostData.class);

                            if( listAdapter != null )
                                listAdapter.setData(clienPostData);

//                            swipeRefreshLayout.setRefreshing(false);

                            loadedPage = 1;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        VolleyMgr.getInstance(getActivity()).getRequestQueue().add(stringRequest);
    }

    private void getMoreData() {
        String apiUrl = getNextPageApi();
        StringRequest stringRequest = new StringRequest(apiUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if( response == null )
                            return;

                        try {
                            ClienPostData clienPostData = new Gson().fromJson(response, ClienPostData.class);

                            if( listAdapter != null )
                                listAdapter.addData(clienPostData);

                            Toast.makeText(getActivity(), "Added More Data.", Toast.LENGTH_SHORT).show();

                            loadedPage++;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        VolleyMgr.getInstance(getActivity()).getRequestQueue().add(stringRequest);
    }

    private String getMainApi() {
        Uri.Builder builder = Uri.parse(Defines.URL_CLIEN_PARK_POST).buildUpon();
        try {
            builder.appendQueryParameter("url", URLEncoder.encode(mTabData.getLink(), "utf-8"));
            return builder.build().toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }

    private String getNextPageApi() {
        Uri.Builder builder = Uri.parse(Defines.URL_CLIEN_PARK_POST_NEXT + "/" + (loadedPage+1)).buildUpon();
        try {
            builder.appendQueryParameter("url", URLEncoder.encode(mTabData.getLink(), "utf-8"));
            return builder.build().toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }
}
