package com.example.myapplication.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;
import com.example.myapplication.R;
import com.example.myapplication.adapter.PostListAdapater;
import com.example.myapplication.databinding.ActivityMainBinding;
import com.example.myapplication.model.HitsItem;
import com.example.myapplication.model.PostListResponse;
import com.example.myapplication.utility.Utils;
import com.example.myapplication.viewmodel.MainActivityNavigator;
import com.example.myapplication.viewmodel.MainActivityViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements MainActivityNavigator {

    private ActivityMainBinding activityMainBinding;
    private MainActivityViewModel mainActivityViewModel;


    private LinearLayout llContainer;

    private String tags = "story";
    private int page = 1;

    private PostListAdapater postListAdapter;

    private List<HitsItem> postList;


    private int lastVisibleItem;

    private int visibleThreshold = 1;

    private int pageItemCount = 0;
    private int totalItemCount;

    private int numberOfPost = 0;


    private SkeletonScreen skeletonScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initComponents();
        setUpadapater(postList);
        setUpSkeletonView();

    }

    /**
     * intilize component
     */

    @Override
    public void initComponents() {

        postList = new ArrayList<>();

        activityMainBinding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);
        mainActivityViewModel = new MainActivityViewModel(this);
        activityMainBinding.setMainViewModel(mainActivityViewModel);

        llContainer = activityMainBinding.llcontainer;
    }


    /**
     * set up adapter is for intilize adapter and pass list of vale
     *
     * @param postList is post list
     */

    private void setUpadapater(List<HitsItem> postList) {

        activityMainBinding.rcvPostList.removeAllViews();
        activityMainBinding.rcvPostList.setHasFixedSize(true);
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(MainActivity.this);
        activityMainBinding.rcvPostList.setLayoutManager(mLayoutManager);

        postListAdapter = new PostListAdapater(MainActivity.this, postList);
        activityMainBinding.rcvPostList.setAdapter(postListAdapter);

        activityMainBinding.rcvPostList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                totalItemCount = mLayoutManager.getItemCount();
                lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();

                if (totalItemCount > 0) {

                    if (!postListAdapter.isLoading() && totalItemCount <= (lastVisibleItem + visibleThreshold)) {

                        if (pageItemCount > totalItemCount) {
                            postListAdapter.setLoading();
                            page++;
                            getPostList(true);
                        }
                    }
                }
            }
        });


    }


    /**
     * Setup skelton view
     */
    private void setUpSkeletonView() {

        skeletonScreen = Skeleton.bind(activityMainBinding.rcvPostList)
                .adapter(postListAdapter)
                .load(R.layout.row_loading_skelton)
                .shimmer(false)
                .show();

        getPostList(false);

    }


    /**
     *
     * get post list
     *
     * @param isLoadmore is first time false not require load more bottom dialog at first time
     */
    private void getPostList(final boolean isLoadmore) {
        if (Utils.isOnline(MainActivity.this, true)) {

            activityMainBinding.activityMainLlLoadMoreProgress.setVisibility(isLoadmore ? View.VISIBLE : View.GONE);
            mainActivityViewModel.getPostList(tags, page + "");


        } else {
            skeletonScreen = Skeleton.bind(activityMainBinding.rcvPostList)
                    .load(R.layout.no_internet)
                    .adapter(postListAdapter)
                    .shimmer(false)
                    .show();

        }
    }

    /**
     * Error handling for api calling
     *
     * @param throwable
     */

    @Override
    public void handleError(Throwable throwable) {

        Utils.snackbar(llContainer, throwable.getMessage(), true, MainActivity.this);
        emptyView();
    }


    /**
     * Post list response
     * PostListResponse is model class
     *
     * @param postList
     */
    @Override
    public void postListResponse(PostListResponse postList) {

        skeletonScreen.hide();

        try {
            if (postList != null && postList.getHits() != null && postList.getHits().size() > 0) {


                numberOfPost = numberOfPost + postList.getHitsPerPage();

                pageItemCount = postList.getNbPages();
                Utils.writeLog(MainActivity.this, numberOfPost + "");

                activityMainBinding.activityMainPostCount.setText(String.valueOf(numberOfPost));

                postListAdapter.setLoaded();
                activityMainBinding.activityMainLlLoadMoreProgress.setVisibility(View.GONE);

                postListAdapter.addRecord(postList.getHits());
                postListAdapter.notifyDataSetChanged();


            } else {

                emptyView();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * Empty view if no any data found in list then empty skelton set
     */

    private void emptyView() {
        skeletonScreen = Skeleton.bind(activityMainBinding.rcvPostList)
                .adapter(postListAdapter)
                .load(R.layout.empty_skelton)
                .shimmer(false)
                .show();

    }

}
