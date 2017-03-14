package com.example.ysh.myapplication.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.example.ysh.myapplication.R;
import com.example.ysh.myapplication.encoder.MediaAudioEncoder;
import com.example.ysh.myapplication.encoder.MediaEncoder;
import com.example.ysh.myapplication.encoder.MediaMuxerWrapper;
import com.example.ysh.myapplication.encoder.MediaVideoEncoder;
import com.example.ysh.myapplication.view.ShortVideoPreview;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ysh on 2017/3/14.
 */

public class ShortVideoActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    CountDownTimer mCountDownTimer;
    TextView mTimeTextView;
    CheckBox mVideoCheckBox;
    ShortVideoPreview mShortVideoPreview;
    private MediaMuxerWrapper mMuxer;
    int mSessionId = 0;
    List<NumberProgressBar> mSessionViews = new ArrayList<>();
    List<String> mVideoPaths = new ArrayList<>();
    ImageButton mVideoCancel;
    ImageButton mVideoOk;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_short_video);

        initView();
    }

    private void initView() {
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        mShortVideoPreview = new ShortVideoPreview(this);
        preview.addView(mShortVideoPreview);

        mVideoCheckBox = (CheckBox) findViewById(R.id.video);
        mVideoCheckBox.setOnCheckedChangeListener(this);
        mTimeTextView = (TextView) findViewById(R.id.video_time);
        mTimeTextView.setText("03:00");

        mSessionViews.add((NumberProgressBar) findViewById(R.id.session_1));
        mSessionViews.add((NumberProgressBar) findViewById(R.id.session_2));
        mSessionViews.add((NumberProgressBar) findViewById(R.id.session_3));
        mVideoCancel = (ImageButton) findViewById(R.id.video_cancel);
        mVideoOk = (ImageButton) findViewById(R.id.video_ok);
        mVideoCancel.setOnClickListener(this);
        mVideoOk.setOnClickListener(this);

        mCountDownTimer = new CountDownTimer(3000, 10) {
            public void onTick(long millisUntilFinished) {
                long first = millisUntilFinished / 10000;
                long second = millisUntilFinished % 10000 / 1000;
                long third = millisUntilFinished % 1000 / 100;
                long four = millisUntilFinished % 100 / 10;

                mTimeTextView.setText(first + "" + second + ":" + third + "" + four);
                mSessionViews.get(mSessionId).setProgress(100 - (int)(millisUntilFinished / 3000f * 100));
            }

            public void onFinish() {
                stopRecording();
            }
        };
    }

    private void startRecording() {
        try {
            mMuxer = new MediaMuxerWrapper(".mp4");    // if you record audio only, ".m4a" is also OK.
            mVideoPaths.add(mMuxer.getOutputPath());
            if (true) {
                // for video capturing
                new MediaVideoEncoder(mMuxer, mMediaEncoderListener, mShortVideoPreview.getVideoWidth(), mShortVideoPreview.getVideoHeight());
            }
            if (true) {
                // for audio capturing
                new MediaAudioEncoder(mMuxer, mMediaEncoderListener);
            }
            mMuxer.prepare();
            mMuxer.startRecording();
            mVideoCheckBox.setEnabled(false);
            mTimeTextView.setText("03:00");
            mCountDownTimer.start();
            Toast.makeText(ShortVideoActivity.this, "video start", Toast.LENGTH_SHORT).show();
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    private void stopRecording() {
        if (mMuxer != null) {
            mMuxer.stopRecording();
            mMuxer = null;
        }

        mTimeTextView.setText("00:00");
        mVideoCheckBox.setChecked(false);
        mVideoCheckBox.setEnabled(true);
        Toast.makeText(ShortVideoActivity.this, "video finish", Toast.LENGTH_SHORT).show();
        if (mSessionId < mSessionViews.size()) {
            mSessionId++;
            mVideoCancel.setVisibility(View.VISIBLE);
            if (mSessionId == mSessionViews.size()) {
                mVideoOk.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.video:
                if (isChecked) {
                    if (mSessionId < mSessionViews.size()) {
                        startRecording();
                    } else {
                        mVideoCheckBox.setChecked(false);
                        Toast.makeText(ShortVideoActivity.this, "video maximum " + mSessionViews.size() + " sessions ", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.video_cancel:
                if (mSessionId > 0) {
                    mSessionId--;
                    String videoPath = mVideoPaths.remove(mSessionId);
                    deleteFile(videoPath);
                    mSessionViews.get(mSessionId).setProgress(0);
                    if (mVideoOk.getVisibility() == View.VISIBLE) {
                        mVideoOk.setVisibility(View.GONE);
                    }
                    if (mSessionId == 0) {
                        mVideoCancel.setVisibility(View.GONE);
                    }
                }
                break;
            case R.id.video_ok:
                combineVideo();
                break;
        }
    }

    /**
     * callback methods from encoder
     */
    private final MediaEncoder.MediaEncoderListener mMediaEncoderListener = new MediaEncoder.MediaEncoderListener() {
        @Override
        public void onPrepared(final MediaEncoder encoder) {
            if (encoder instanceof MediaVideoEncoder)
                mShortVideoPreview.setVideoEncoder((MediaVideoEncoder) encoder);
        }

        @Override
        public void onStopped(final MediaEncoder encoder) {
            if (encoder instanceof MediaVideoEncoder)
                mShortVideoPreview.setVideoEncoder(null);
        }
    };

    private void combineVideo() {

    }

    public boolean deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.isFile() && file.exists()) {
            return file.delete();
        }
        return false;
    }
}
