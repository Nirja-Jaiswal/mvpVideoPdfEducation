package com.Padhantueducation.view_section.ui.home.Fragment;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.Padhantueducation.CommonClasses.Utils;
import com.Padhantueducation.R;
import com.Padhantueducation.Session.Session;
import com.Padhantueducation.remote.APIService;
import com.Padhantueducation.remote.ApiClient;
import com.Padhantueducation.view_section.ui.home.Models.NotesResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.Padhantueducation.remote.APIUrl.CLASS_IMAGE;
import static com.Padhantueducation.remote.APIUrl.CLASS_VIDEO;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotesFragment extends Fragment {
    View view;
    String chapter_id;
    TextView mtitle, mdescription;
    ImageView chapter_imageview;
    PlayerView mVideoView;
    Session session;
    SimpleExoPlayer simpleExoPlayer;
    ProgressBar progress_bar;
    String video_url;


    public NotesFragment(String id) {
        this.chapter_id = id;
    }

    public NotesFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_notes, container, false);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        getInitview();
        get_Notes_Api();

     //   get_Video_Initialization("http://logicalsofttech.com/olavo/assets/uploaded/video/video.mp4");



        return view;
    }

    private void get_Video_Initialization(String video_url) {
        Uri videourl = Uri.parse(video_url);
        LoadControl loadControl = new DefaultLoadControl();
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));


        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);
        final DefaultHttpDataSourceFactory factory = new DefaultHttpDataSourceFactory("exoplayer_video");
        MediaSource mediaSource = new ExtractorMediaSource.Factory(factory).setExtractorsFactory(new DefaultExtractorsFactory()).createMediaSource(Uri.parse(String.valueOf(videourl)));
        mVideoView.setPlayer(simpleExoPlayer);
        mVideoView.setKeepScreenOn(true);
        simpleExoPlayer.prepare(mediaSource);
        simpleExoPlayer.setPlayWhenReady(false);


    }


    private void get_Notes_Api() {

        Utils.showProgressDialog(getActivity());
        APIService service = ApiClient.getClient().create(APIService.class);
        Call<NotesResult> userCall = service.getNotes(session.getUserId(), Utils.Get_Device_ID(getActivity()), chapter_id);

        userCall.enqueue(new Callback<NotesResult>() {
            @Override
            public void onResponse(Call<NotesResult> call, Response<NotesResult> response) {

                try {
                    if (response != null) {
                        Log.e("res_i_report", "" + response.body().getData());
                        Utils.dismissProgressDialog();
                        if (response.body().getResult().equals("true")) {
                            // Toast.makeText(context, "Successfull", Toast.LENGTH_SHORT).show();
                            String mStr_title_ = response.body().getData().getTitle();
                            String mStr_description_ = response.body().getData().getDescription();
                            String mStr_Image = response.body().getData().getImage();
                            String mStr_Video = response.body().getData().getVideo();
                            mtitle.setText(mStr_title_);
                            mdescription.setText(mStr_description_);
                            if (mStr_Video.equals(""))
                            {
                                mVideoView.setVisibility(View.GONE);
                            }

                            if (mStr_Image.equals(""))
                            {
                                chapter_imageview.setVisibility(View.GONE);
                            }

                            Glide.with(getActivity()).load(CLASS_IMAGE + mStr_Image).into(chapter_imageview);
                            video_url = CLASS_VIDEO + mStr_Video;
                            videoPlay(video_url);


                        } else {

                            Utils.dismissProgressDialog();
                            if (response.body().getMsg().equals("invalid_token")) {
                                Utils.Logout_Api(session.getUserId(), getActivity());
                            }


                        }

                    }
                } catch (Exception e) {
                    Log.e("error_i_report", e.getMessage());
                }
                //  progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<NotesResult> call, Throwable t) {
                Utils.dismissProgressDialog();
                Log.e("error_i_report1", t.getMessage());
                //Toast.makeText(AllCountries.this, ""+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }


    public void videoPlay(String video_url) {

        //Toast.makeText(getActivity(), ""+video_url, Toast.LENGTH_SHORT).show();

        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Uri videourl = Uri.parse(video_url);
        LoadControl loadControl = new DefaultLoadControl();
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));


        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);
        final DefaultHttpDataSourceFactory factory = new DefaultHttpDataSourceFactory("exoplayer_video");
        MediaSource mediaSource = new ExtractorMediaSource.Factory(factory).setExtractorsFactory(new DefaultExtractorsFactory()).createMediaSource(Uri.parse(String.valueOf(videourl)));
        mVideoView.setPlayer(simpleExoPlayer);
        mVideoView.setKeepScreenOn(true);
        simpleExoPlayer.prepare(mediaSource);
        simpleExoPlayer.setPlayWhenReady(false);



        simpleExoPlayer.addListener(new Player.EventListener() {
            @Override
            public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

            }

            @Override
            public void onLoadingChanged(boolean isLoading) {

            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

                if (playbackState == Player.STATE_BUFFERING) {
                    progress_bar.setVisibility(View.VISIBLE);
                } else if (playbackState == Player.STATE_READY) {
                    progress_bar.setVisibility(View.GONE);
                }

            }

            @Override
            public void onRepeatModeChanged(int repeatMode) {

            }

            @Override
            public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {

            }

            @Override
            public void onPositionDiscontinuity(int reason) {

            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

            }

            @Override
            public void onSeekProcessed() {

            }
        });


    }


    private void getInitview() {
        mtitle = view.findViewById(R.id.tv_title);
        mdescription = view.findViewById(R.id.tv_description);
        chapter_imageview = view.findViewById(R.id.imageview);
        mVideoView = view.findViewById(R.id.player_view);
        progress_bar = view.findViewById(R.id.progress_bar);
        session = new Session(getActivity());

    }


/*

      @Override
    public void onPause() {
        super.onPause();
        simpleExoPlayer.stop();
      //  simpleExoPlayer.setPlayWhenReady(false);
       // simpleExoPlayer.getPlaybackState();
    }


    @Override
    public void onResume() {
        super.onResume();
        simpleExoPlayer.stop();
        relase_player();
       // get_Video_Initialization("http://logicalsofttech.com/olavo/assets/uploaded/video/video.mp4");
      //  simpleExoPlayer.setPlayWhenReady(false);
       // simpleExoPlayer.getPlaybackState();
    }


public void relase_player(){

    simpleExoPlayer.release();
}



    @Override
    public void onStop() {
        super.onStop();
        simpleExoPlayer.stop();
        relase_player();
    }

*/



}
