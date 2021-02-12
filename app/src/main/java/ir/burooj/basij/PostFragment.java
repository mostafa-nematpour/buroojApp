package ir.burooj.basij;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static ir.burooj.basij.MainActivity.apiInterface;


public class PostFragment extends Fragment {


    View view;

    private RecyclerView recyclerView;
    private SwipeRefreshLayout pullToRefresh;
    List<Item> mItem = new ArrayList<>();
    ItemAdapter mAdapter;
    private ProgressBar progressBar;
    private AVLoadingIndicatorView progressBarB;
    Toolbar toolbar;


    int i = 2;
    private SharedPreferences shPref;
    private static final String MyPref = "BuroojPrefers1";
    private static final String tokenName = "tokenNameKey";
    private static final String userIdName = "userIdNameKey";
    private static final String haveAccountName = "haveAccount";
    private String token = "", userId = "", haveAccount = "";

    private String id = "-2";
    private String name = "بروج";
    // MainActivity activity;

    public PostFragment() {
        // Required empty public constructor
    }

    public PostFragment(String id, String name) {

        this.id = id;
        this.name = name;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_post, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        pullToRefresh = view.findViewById(R.id.pullToRefresh);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar2);
        progressBarB = (AVLoadingIndicatorView) view.findViewById(R.id.progressBar);
        toolbar = view.findViewById(R.id.toolbar);
        TextView textViewToolbar=view.findViewById(R.id.toolbar_text);
        textViewToolbar.setText(name);
        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);
        toolbar.inflateMenu(R.menu.main);

        progressBar.setVisibility(View.GONE);



        /*
        SharedPreferences
        */
        laodshar();
        /*
        SharedPreferences
        */

        /*
        اندازه گیری صفحه
         */
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        /*
        اندازه گیری صفحه
         */

        /*
         ریسایکل ویو
         */
        mAdapter = new ItemAdapter(mItem, view.getContext(), token, apiInterface, userId, getActivity());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        RecyclerView.ItemDecoration iDecoration = new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(iDecoration);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mAdapter);
        if (MainActivity.mItem != null && MainActivity.mItem.size() > 0 && groupP().isEmpty()) {
            mItem.removeAll(mItem);
            mItem.addAll(MainActivity.mItem);
            mAdapter.notifyDataSetChanged();

        }
        if (mItem != null && mItem.size() > 0) {
            progressBarB.setVisibility(View.INVISIBLE);
        }
        getTheFirstTenPosts();
        /*
        ریسایکل ویو
         */
//        this.onPause();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    progressBar.setVisibility(View.VISIBLE);
                    Call<List<Item>> posts = MainActivity.apiInterface.getPosts(userId, i, token, groupP());
                    posts.enqueue(new Callback<List<Item>>() {
                        @Override
                        public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                            try {
                                if (response.body().get(0).getResponse() == null) {

                                    if (response.body().size() != 0) {
                                        i++;
                                        List<Item> postsList = response.body();
                                        mItem.addAll(postsList);
                                        mAdapter.updateList(mItem);
                                    }
                                    pullToRefresh.setRefreshing(false);
                                    progressBar.setVisibility(View.GONE);
                                } else if (response.body().get(0).getResponse().equals("-1")) {
                                    logOut(view.getContext());
                                    getActivity().finish();
                                }
                            } catch (IndexOutOfBoundsException e) {

                                pullToRefresh.setRefreshing(false);
                                progressBar.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Item>> call, Throwable t) {
                            Snackbar.make(progressBar, "مشکلی در ارتباط با سرور پیش\u200Cآمده", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();

                            pullToRefresh.setRefreshing(false);
                            progressBar.setVisibility(View.GONE);
                        }
                    });
                }
            }
        });
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getTheFirstTenPosts();
            }
        });

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();


    }


    private void getTheFirstTenPosts() {

        Call<List<Item>> posts = MainActivity.apiInterface.getPosts(userId, 1, token, groupP());
        posts.enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                List<Item> postsList = response.body();
                if (response.body() != null && postsList.size() > 0) {
                    if (postsList.get(0).getResponse() == null) {
                        final TextView textView = view.findViewById(R.id.textView4);

                        mItem.clear();
                        mItem.addAll(postsList);
                        mAdapter.notifyDataSetChanged();
                        textView.setText("");
                        MainActivity.mItem.removeAll(mItem);
                        MainActivity.mItem.addAll(postsList);
                        i = 2;
                        if (response.body().size() == 0) {
                            textView.setText("لیست پر از خالی هست");
                        }
                        pullToRefresh.setRefreshing(false);
                        progressBarB.setVisibility(View.INVISIBLE);
                    } else if (postsList.get(0).getResponse().equals("-1")) {
                        logOut(view.getContext());
                        Objects.requireNonNull(getActivity()).finish();
                    }
                }
                pullToRefresh.setRefreshing(false);
                progressBarB.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                Snackbar.make(progressBar, "مشکلی در ارتباط پیش آمده", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                final TextView textView = view.findViewById(R.id.textView4);
                if (mItem != null) {
                    if (mItem.size() == 0)
                        textView.setText("مشکلی در ارتباط پیش\u200cآمده.\nبرای بارگزاری مجدد صفحه را به پایین بکشید.");
                }
                progressBarB.setVisibility(View.INVISIBLE);

                pullToRefresh.setRefreshing(false);
            }
        });
    }


    private void laodshar() {
        shPref = getActivity().getSharedPreferences(MyPref, Context.MODE_PRIVATE);

        if (shPref.contains(tokenName)) {
            token = shPref.getString(tokenName, null);
        } else {
            logOut(view.getContext());
            getActivity().finish();

        }
        if (shPref.contains(userIdName)) {
            userId = shPref.getString(userIdName, null);
        } else {
            logOut(view.getContext());
            getActivity().finish();
        }
        if (shPref.contains(haveAccountName)) {
            haveAccount = shPref.getString(haveAccountName, null);
        } else {
            logOut(view.getContext());
            getActivity().finish();
        }

    }

    void logOut(Context context) {
        Tools.sharedPreference(context, "", "", "");
        Intent intent = new Intent(context, SplashScreen.class);
        startActivity(intent);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action2:
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(MainActivity.appDetails[6]));
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.action3:
                try {
                    Tools.sendApplication(getActivity());
                } catch (Exception e) {
                    Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
                break;

        }
        return super.onOptionsItemSelected(item);
    }


    private String groupP() {
        String s = "";
        if (!id.equals("-2")) {
            s =(id);
        }
        return s;
    }
}
