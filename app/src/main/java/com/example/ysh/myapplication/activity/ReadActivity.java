package com.example.ysh.myapplication.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.example.ysh.myapplication.R;
import com.example.ysh.myapplication.util.Constant;
import com.example.ysh.myapplication.util.FileUtils;
import com.example.ysh.myapplication.util.LogUtils;
import com.example.ysh.myapplication.util.SharedPreferencesUtil;
import com.example.ysh.myapplication.view.readview.BaseReadView;
import com.example.ysh.myapplication.view.readview.BookApi;
import com.example.ysh.myapplication.view.readview.BookMixAToc;
import com.example.ysh.myapplication.view.readview.BookReadContract;
import com.example.ysh.myapplication.view.readview.BookReadPresenter;
import com.example.ysh.myapplication.view.readview.CacheManager;
import com.example.ysh.myapplication.view.readview.ChapterRead;
import com.example.ysh.myapplication.view.readview.OnReadStateChangeListener;
import com.example.ysh.myapplication.view.readview.OverlappedWidget;
import com.example.ysh.myapplication.view.readview.PageWidget;
import com.example.ysh.myapplication.view.readview.Recommend;
import com.example.ysh.myapplication.view.readview.SettingManager;
import com.example.ysh.myapplication.view.readview.TocListAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;

/**
 * 类说明：
 *
 * @author yangsh
 * @version 1.0
 *          CreateTime:  2017/2/28 16:53
 *          Description:
 */

public class ReadActivity extends AppCompatActivity implements BookReadContract.View {

    private FrameLayout mReadLayout;
    private BaseReadView mReadView;
    private int mCurrentChapter = 1;
    private String mBookId;
    private TocListAdapter mTocListAdapter;
    private List<BookMixAToc.mixToc.Chapters> mChapterList = new ArrayList<>();
    private Recommend.RecommendBooks mRecommendBooks;
    private BookReadPresenter mPresenter;
    private boolean mStartRead;
    private int mCurTheme = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        mReadLayout = (FrameLayout) findViewById(R.id.read_layout);

        initData();

        initTocList();

        initReadView();

        mCurTheme = SettingManager.getInstance().getReadTheme();
        mPresenter = new BookReadPresenter(this, BookApi.getInstance(new OkHttpClient()));
        mPresenter.attachView(this);
        // 本地收藏  直接打开
        if (mRecommendBooks.isFromSD) {
            BookMixAToc.mixToc.Chapters chapters = new BookMixAToc.mixToc.Chapters();
            chapters.title = mRecommendBooks.title;
            mChapterList.add(chapters);
            showChapterRead(null, mCurrentChapter);
            return;
        }
        mPresenter.getBookMixAToc(mBookId, "chapters");
    }

    public void initData() {
        mRecommendBooks = new Recommend.RecommendBooks();
        mRecommendBooks._id = "官神";
        mRecommendBooks.title = "官神";
        mRecommendBooks.isFromSD = true;
        mRecommendBooks.lastChapter = "17.07M";
        mRecommendBooks.path = "/storage/emulated/0/官神.txt";

        mBookId = mRecommendBooks._id;
        File desc = FileUtils.createWifiTranfesFile(mRecommendBooks._id);
        FileUtils.fileChannelCopy(new File(mRecommendBooks.path), desc);

    }

    private void initTocList() {
        mTocListAdapter = new TocListAdapter(this, mChapterList, mBookId, mCurrentChapter);
    }

    private void initReadView() {
        if (SharedPreferencesUtil.getInstance().getInt(Constant.FLIP_STYLE, 0) == 0) {
            mReadView = new PageWidget(this, mBookId, mChapterList, new ReadListener());
        } else {
            mReadView = new OverlappedWidget(this, mBookId, mChapterList, new ReadListener());
        }
        if (SharedPreferencesUtil.getInstance().getBoolean(Constant.ISNIGHT, false)) {
            mReadView.setTextColor(ContextCompat.getColor(this, R.color.chapter_content_night),
                    ContextCompat.getColor(this, R.color.chapter_title_night));
        }
        mReadLayout.removeAllViews();
        mReadLayout.addView(mReadView);

    }

    @Override
    public void showBookToc(List<BookMixAToc.mixToc.Chapters> list) {

    }

    @Override
    public void showChapterRead(ChapterRead.Chapter data, int chapter) {
        if (data != null) {
            CacheManager.getInstance().saveChapterFile(mBookId, chapter, data);
        }

        if (!mStartRead) {
            mStartRead = true;
            mCurrentChapter = chapter;
            if (!mReadView.isPrepared) {
                mReadView.init(mCurTheme);
            } else {
                mReadView.jumpToChapter(mCurrentChapter);
            }
        }
    }

    @Override
    public void netError(int chapter) {

    }

    @Override
    public void showError() {

    }

    @Override
    public void complete() {

    }

    private class ReadListener implements OnReadStateChangeListener {
        @Override
        public void onChapterChanged(int chapter) {
            LogUtils.i("onChapterChanged:" + chapter);
            mCurrentChapter = chapter;
            mTocListAdapter.setCurrentChapter(mCurrentChapter);
            // 加载前一节 与 后三节
            for (int i = chapter - 1; i <= chapter + 3 && i <= mChapterList.size(); i++) {
                if (i > 0 && i != chapter
                        && CacheManager.getInstance().getChapterFile(mBookId, i) == null) {
                    mPresenter.getChapterRead(mChapterList.get(i - 1).link, i);
                }
            }
        }

        @Override
        public void onPageChanged(int chapter, int page) {
            LogUtils.i("onPageChanged:" + chapter + "-" + page);
        }

        @Override
        public void onLoadChapterFailure(int chapter) {
            LogUtils.i("onLoadChapterFailure:" + chapter);
            mStartRead = false;
            if (CacheManager.getInstance().getChapterFile(mBookId, chapter) == null)
                mPresenter.getChapterRead(mChapterList.get(chapter - 1).link, chapter);
        }

        @Override
        public void onCenterClick() {
            LogUtils.i("onCenterClick");
//            toggleReadBar();
        }

        @Override
        public void onFlip() {
//            hideReadBar();
        }


    }
}
