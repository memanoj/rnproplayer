package com.rnproplayer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.ui.DefaultTimeBar;
import com.google.android.exoplayer2.ui.PlayerView;

public class PlayerActivity extends AppCompatActivity implements MenuItem.OnMenuItemClickListener,AudioManager.OnAudioFocusChangeListener{
    private static final String TAG = "PlayerActivity";
    AudioManager am;
    String newString="";
    long playbackPosition;
    Boolean ActivePlay=true;
    ExoPlayer player;
    ImageButton exo_play_pause;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        am = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
        if (am != null) {
            am.requestAudioFocus(PlayerActivity.this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        }
        setContentView(R.layout.activity_player);
        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            newString= null;
        } else {
            newString= extras.getString("videourl");
        }
        ImageButton exo_rew_with_amount = findViewById(R.id.exo_rewind);
        ImageButton exo_next_with_amount = findViewById(R.id.exo_nextind);
         exo_play_pause = findViewById(R.id.exo_play_pause);
        findViewById(R.id.exo_play_pause).requestFocus();
        ImageButton android_dropdown_menu_example = findViewById(R.id.exo_settings);

        PlayerView playerView = findViewById(R.id.video_view);
//       MediaItem mediaItem = MediaItem.fromUri("https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4");
        MediaItem mediaItem = MediaItem.fromUri(newString);
        player = new ExoPlayer.Builder(this).build();
        // Attach player to the view.
        playerView.setPlayer(player);
        // Set the media item to be played.
        player.setMediaItem(mediaItem);
        // Prepare the player.
        player.prepare();
        player.play();
        exo_play_pause.setImageDrawable(getResources().getDrawable(R.drawable.exo_styled_controls_pause));



        exo_play_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                player.play();
                if(player.isPlaying()){
                    ActivePlay=false;
                    exo_play_pause.setImageDrawable(getResources().getDrawable(R.drawable.exo_styled_controls_play));
                    player.pause();
                }
                else {
                    ActivePlay=true;
                    if (am != null) {
                        am.requestAudioFocus(PlayerActivity.this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
                    }
                    exo_play_pause.setImageDrawable(getResources().getDrawable(R.drawable.exo_styled_controls_pause));
                    player.play();
                }
            }
        });
        exo_rew_with_amount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.seekTo(player.getCurrentPosition() - 5000);
            }
        });

        exo_next_with_amount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.seekTo(player.getCurrentPosition() + 5000);
            }
        });

        android_dropdown_menu_example.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu dropDownMenu = new PopupMenu(getApplicationContext(), android_dropdown_menu_example);
                dropDownMenu.getMenuInflater().inflate(R.menu.drop_down_menu, dropDownMenu.getMenu());
                dropDownMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
//                        Log.d("menuitem", String.valueOf(menuItem));
                        String str = "geekss@for@geekss";
                        String[] arrOfStr = String.valueOf(menuItem).split("x");
                        System.out.println(arrOfStr[0]);
                        PlaybackParameters param = new PlaybackParameters(Float.parseFloat(arrOfStr[0]));
                        player.setPlaybackParameters(param);
                        return true;
                    }
                });
                dropDownMenu.show();
            }
        });
    }


    @Override
    public void onAudioFocusChange(int focusChange) {
        switch (focusChange) {
            case AudioManager.AUDIOFOCUS_LOSS:
                // another app gained audio focus
                // STOP PLAYBACK
                exo_play_pause.setImageDrawable(getResources().getDrawable(R.drawable.exo_styled_controls_play));
                player.pause();
                break;
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                // another app want you to pause media
                // PAUSE
                exo_play_pause.setImageDrawable(getResources().getDrawable(R.drawable.exo_styled_controls_play));
                player.pause();
                break;
            case AudioManager.AUDIOFOCUS_GAIN:
                // START PLAYBACK
                if (am != null) {
                    am.requestAudioFocus(PlayerActivity.this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
                }
                player.play();
                break;
        }
    }


    @Override
    public void onStop() {
        super.onStop();
        player.setPlayWhenReady(false);
        player.stop();
        player.seekTo(0);
        Log.d("stop","enter stop");
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (player!=null) {
            player.stop();
            playbackPosition = player.getCurrentPosition();
            Log.d("pause","enter pause");
        }
    }



    @Override
    protected void onResume() {
        super.onResume();
        if (am != null) {
            am.requestAudioFocus(PlayerActivity.this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        }
        if(playbackPosition!=0 && player!=null){
            player.seekTo(playbackPosition);
            Bundle extras = getIntent().getExtras();
           Log.d("resume","enter resume");
            Log.d("videourl",newString);
            player.prepare();
            player.play();
        }

    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
//        Log.d("Item", String.valueOf(item));
        return false;
    }
}