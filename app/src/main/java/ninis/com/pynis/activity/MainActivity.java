package ninis.com.pynis.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.gson.Gson;

import ninis.com.pynis.Defines;
import ninis.com.pynis.MainPageAdapter;
import ninis.com.pynis.R;
import ninis.com.pynis.VolleyMgr;
import ninis.com.pynis.data.ClienTabData;
import ninis.com.pynis.fragment.MainPostFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AppBarLayout.OnOffsetChangedListener {

    private AppBarLayout appBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        appBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
//        setListView();

        getTabData();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setFragment(ClienTabData data) {
        MainPageAdapter adapter = new MainPageAdapter(getSupportFragmentManager());

        for( ClienTabData.TabItem item : data.getItems() ) {
            MainPostFragment mainPostFragment = new MainPostFragment();
            mainPostFragment.setData(item);
            adapter.addFragment(mainPostFragment, item);
        }

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        findViewById(R.id.progressBar_main).setVisibility(View.GONE);
    }

    private void getTabData() {
        StringRequest stringRequest = new StringRequest(Defines.URL_CLIEN_BOARD_LIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if( response == null || response.length() < 1 )
                            return;

                        Log.d("NINIS", "getTabData\n" + response);

                        try {
                            ClienTabData tabData = new Gson().fromJson(response, ClienTabData.class);
                            if( tabData != null ) {
                                setFragment(tabData);
                            }
                        } catch (Exception e) {}
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Tab Data Load Failed!!", Toast.LENGTH_SHORT).show();
                        findViewById(R.id.progressBar_main).setVisibility(View.GONE);
                    }
                }
        );
        VolleyMgr.getInstance(getApplicationContext()).getRequestQueue().add(stringRequest);
    }

    /*
    private void setListView() {
        linearLayoutManager = new LinearLayoutManager(this);
        listview.setLayoutManager(linearLayoutManager);
        listview.addItemDecoration(new SimpleDividerItemDecoration(
                getApplicationContext()));

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

                    Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                    intent.putExtra("URL", url);
                    startActivity(intent);
                } catch (Exception e) {}
            }
        });

        getMainData();
    }

    private void getMainData() {
        StringRequest stringRequest = new StringRequest(Defines.URL_CLIEN_PARK_POST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if( response == null )
                            return;

                        try {
                            Toast.makeText(getApplicationContext(), "Loaded Data.", Toast.LENGTH_SHORT).show();

                            ClienPostData clienPostData = new Gson().fromJson(response, ClienPostData.class);

                            if( listAdapter != null )
                                listAdapter.setData(clienPostData);

                            swipeRefreshLayout.setRefreshing(false);
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
        VolleyMgr.getInstance(this.getApplicationContext()).getRequestQueue().add(stringRequest);
    }

    private void getMoreData() {
        StringRequest stringRequest = new StringRequest(Defines.URL_CLIEN_PARK_POST_NEXT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if( response == null )
                            return;

                        try {
                            ClienPostData clienPostData = new Gson().fromJson(response, ClienPostData.class);

                            if( listAdapter != null )
                                listAdapter.addData(clienPostData);

                            Toast.makeText(getApplicationContext(), "Added More Data.", Toast.LENGTH_SHORT).show();
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
        VolleyMgr.getInstance(this.getApplicationContext()).getRequestQueue().add(stringRequest);
    }
    */

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//        swipeRefreshLayout.setEnabled(verticalOffset == 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        appBarLayout.addOnOffsetChangedListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        appBarLayout.addOnOffsetChangedListener(this);
    }
}
