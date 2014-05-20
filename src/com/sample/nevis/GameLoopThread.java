package com.sample.nevis;

import android.annotation.SuppressLint;
import android.graphics.Canvas;

public class GameLoopThread extends Thread{
	static final long FPS = 10;
	private GameView gameView;
	private boolean isRunning = false;
	
	public GameLoopThread(GameView gameView){
		this.gameView = gameView;
	}
	
	public void setRunning(boolean run){
		this.isRunning = run;
	}
	
	@SuppressLint("WrongCall")
	@Override
	public void run(){
		long ticksPS = 1000 / FPS;
		long startTime;
		long sleepTime;
		while(this.isRunning){
			Canvas canvas = null;
			startTime = System.currentTimeMillis();
			try{
				canvas = this.gameView.getHolder().lockCanvas();
				synchronized(this.gameView.getHolder()){
					this.gameView.onDraw(canvas);
				}
			}finally{
				if(null !=canvas){
					this.gameView.getHolder().unlockCanvasAndPost(canvas);
				}
			}
			sleepTime = ticksPS - (System.currentTimeMillis() - startTime);
			try{
				if(sleepTime > 0){
					sleep(sleepTime);
				}else{
					sleep(10);
				}
			}catch(Exception e){}
			this.gameView.restore();
		}
	}
}
