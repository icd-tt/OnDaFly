package com.sample.nevis;

import java.io.IOException;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

public class GameActivity extends Activity {

	private MediaPlayer mediaPlayer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(new GameView(this));

		this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
		this.mediaPlayer = new MediaPlayer();
		try {
			AssetManager assetManager = this.getAssets();
			AssetFileDescriptor assetFileDescriptor = assetManager.openFd("extralongwarp.ogg");
			this.mediaPlayer.setDataSource(
					assetFileDescriptor.getFileDescriptor(),
					assetFileDescriptor.getStartOffset(),
					assetFileDescriptor.getLength());
			this.mediaPlayer.prepare();
			this.mediaPlayer.setLooping(true);

		} catch (IOException ioe) {
			this.mediaPlayer = null;
			Toast.makeText(getBaseContext(), "MediaPlayer is null", Toast.LENGTH_SHORT).show();
		}
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		if(null != this.mediaPlayer){
			this.mediaPlayer.start();
		}
	}

	@Override
	protected void onPause(){
		super.onPause();
		if(null != this.mediaPlayer){
			this.mediaPlayer.pause();
			if(this.isFinishing()){
				this.mediaPlayer.stop();
				this.mediaPlayer.release();
			}
		}
	}	
}
