package com.frozenkoi.poc.testplaybackinlist;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.ListActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

public class ListWithSongsActivity extends ListActivity {
    private static final int NOT_PLAYING = -1;

    private static final String TAG = "";

    List<String> mSongs = new ArrayList<String>(50);
    private ArrayAdapter<String> mAdapter = null;
    private final MediaPlayer mPlayer = new MediaPlayer();
    private int mPlayingPosition = NOT_PLAYING;
    private Handler mHandler = new Handler();

    private PlaybackUpdater mProgressUpdater = new PlaybackUpdater();

    public ListWithSongsActivity() {
        mSongs.addAll(Arrays.asList(new String[]{
            "/sdcard/Download/B00G2ID7FE_(disc_1)_01_-_Play_Me_(Version).mp3",
            "/sdcard/Download/Bi-Polar Bear/01 - Fuck Her (Produced by August, Cuts by DJ Vilas Park Sniper).mp3",
            "/sdcard/Download/Bi-Polar Bear/02 - Test Your Might (Feat. Kalo)-x (Produced by August).mp3",
            "/sdcard/Download/Bi-Polar Bear/03 - Her For Him (Produced by Ugly Orwell).mp3",
            "/sdcard/Download/Bi-Polar Bear/04 - NPFJ (Produced by August).mp3",
            "/sdcard/Download/Bi-Polar Bear/05 - Good Moanin' (Produced by Ugly Orwell).mp3",
            "/sdcard/Download/Bi-Polar Bear/06 - Today I Found Happy (Produced by Ugly Orwell).mp3",
            "/sdcard/Download/Bi-Polar Bear/07 - Party Girl (Produced by Ugly Orwell, Cuts by DJ Vilas Park Sniper).mp3",
            "/sdcard/Download/Bi-Polar Bear/08 - Home Pt. 2 (Produced by August).mp3",
            "/sdcard/Download/Bi-Polar Bear/09 - Love Begins to Die (Produced by Ugly Orwell).mp3",
            "/sdcard/Download/Bi-Polar Bear/10 - Night's Like This (Part 1 Produced by August, Part 2 Produced by Ugly Orwell).mp3",
            "/sdcard/Download/Bi-Polar Bear/11 - Life (Produced by Ugly Orwell).mp3",
            "/sdcard/Download/Bi-Polar Bear/12 - Untitled.mp3",
            "/sdcard/Download/Blondie/Bride_of_Infinity/B009JDTS48_(disc_1)_01_-_Bride_of_Infinity.mp3",
            "/sdcard/Download/Blondie/Dead_Air/B009J6QEKG_(disc_1)_01_-_Dead_Air.mp3",
            "/sdcard/Download/Blondie/Rock_On/B009JDUXWY_(disc_1)_01_-_Rock_On.mp3",
            "/sdcard/Download/Crystal_Fighters/Cave_Rave/B00CRDB4RQ_(disc_1)_03_-_You_&_I.mp3",
            "/sdcard/Download/Jesse_Dee/On_My_Mind___In_My_Heart/B00B4BC002_(disc_1)_01_-_On_My_Mind,_In_My_Heart.mp3",
            "/sdcard/Download/Jessie_Ware/Devotion/B00C5ZSK2S_(disc_1)_08_-_Sweet_Talk.mp3",
            "/sdcard/Download/Low/C'mon/Low-Especially_Me.mp3",
            "/sdcard/Download/Low/C'mon/Low-Try_To_Sleep.mp3",
            "/sdcard/Download/Low/Drums & Guns/low_-_murder.mp3",
            "/sdcard/Download/Low/Drums & Guns/low_breaker.mp3",
            "/sdcard/Download/Low/The Great Destroyer/Low-Monkey.mp3",
            "/sdcard/Download/Low/The Great Destroyer/Low_-_Silver_Rider.mp3",
            "/sdcard/Download/Low/The Great Destroyer/low_-_california.mp3",
            "/sdcard/Download/Low/The Invisible Way/Low_-_Just_Make_ItStop.mp3",
            "/sdcard/Download/Low/The Invisible Way/Low_-_So_Blue.mp3",
            "/sdcard/Download/Low/Things We Lost In The Fire/Low-In_Metal.mp3",
            "/sdcard/Download/Low/Things We Lost In The Fire/Low-Sunflower.mp3",
            "/sdcard/Download/Sounds like Cocoon Fever/01 EL ALFA.mp3",
            "/sdcard/Download/Sounds like Cocoon Fever/02 INTER 1.mp3",
            "/sdcard/Download/Sounds like Cocoon Fever/03 PCU.mp3",
            "/sdcard/Download/Sounds like Cocoon Fever/04 REGRETS ARE THE BEST.mp3",
            "/sdcard/Download/Sounds like Cocoon Fever/05 PATTY HEARST.mp3",
            "/sdcard/Download/Sounds like Cocoon Fever/06 INTER 2.mp3",
            "/sdcard/Download/Sounds like Cocoon Fever/07 MELOCOTONE.mp3",
            "/sdcard/Download/Sounds like Cocoon Fever/08 HIT AND RUN.mp3",
            "/sdcard/Download/The Slip/01 999,999.mp3",
            "/sdcard/Download/The Slip/02 1,000,000.mp3",
            "/sdcard/Download/The Slip/03 Letting You.mp3",
            "/sdcard/Download/The Slip/04 Discipline.mp3",
            "/sdcard/Download/The Slip/05 Echoplex.mp3",
            "/sdcard/Download/The Slip/06 Head Down.mp3",
            "/sdcard/Download/The Slip/07 Lights in the Sky.mp3",
            "/sdcard/Download/The Slip/08 Corona Radiata.mp3",
            "/sdcard/Download/The Slip/09 The Four of Us are Dying.mp3",
            "/sdcard/Download/The Slip/10 Demon Seed.mp3"
        }));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_list_with_songs);
        mAdapter = new ArrayAdapter<String>(this, R.layout.list_item, android.R.id.text1, mSongs) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                ProgressBar pb = (ProgressBar) v.findViewById(R.id.progressBar1);   //Cast
                if (position == mPlayingPosition) {
                    //pb.setVisibility(View.VISIBLE);
                    mProgressUpdater.mBarToUpdate = pb;
                    mHandler.postDelayed(mProgressUpdater, 100);
                } else {
                    //pb.setVisibility(View.GONE);
                    pb.setProgress(0);
                    if (mProgressUpdater.mBarToUpdate == pb) {
                        //this progress would be updated, but this is the wrong position
                        mProgressUpdater.mBarToUpdate = null;
                    }
                }
                return v;
            }
        };
        setListAdapter(mAdapter);
    }

    private void stopPlayback()
    {
        mPlayingPosition = NOT_PLAYING;
        mProgressUpdater.mBarToUpdate = null;
        mPlayer.stop();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        //start playing item at position
        try {
            mPlayer.reset();
            mPlayer.setDataSource(mSongs.get(position));
            mPlayer.prepare();
            mPlayer.start();
            mPlayingPosition = position;

            mHandler.postDelayed(mProgressUpdater, 500);

            //trigger list refresh, this will make progressbar start updating if visible
            mAdapter.notifyDataSetChanged();
        } catch (IOException e) {
            Log.e(TAG, "unable to play: " + mSongs.get(position));
            e.printStackTrace();
            stopPlayback();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.list_with_songs, menu);
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPlayer.stop();
    }

    private class PlaybackUpdater implements Runnable {
        public ProgressBar mBarToUpdate = null;

        @Override
        public void run() {
            if ((mPlayingPosition != NOT_PLAYING) && (null != mBarToUpdate)) {
                mBarToUpdate.setProgress( (100*mPlayer.getCurrentPosition() / mPlayer.getDuration()) );    //Cast
                mHandler.postDelayed(this, 500);
            } else {
                //not playing so stop updating
            }
        }
    }
}
