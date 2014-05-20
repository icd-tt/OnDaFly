package com.sample.nevis;

import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class TempSprite {
	private float x;
	private float y;
	private Bitmap bitmap;
	private int life = 15;
	private List<TempSprite> tempSprites;
	
	public TempSprite(List<TempSprite> tempSprites, GameView gameView, float x, float y, Bitmap bitmap){
		this.x = Math.min(Math.max(x - bitmap.getWidth() / 2,  0), gameView.getWidth() - bitmap.getWidth());
		this.y = Math.min(Math.max(y - bitmap.getHeight() / 2, 0), gameView.getHeight() - bitmap.getHeight());
		
		this.bitmap = bitmap;
		this.tempSprites = tempSprites;
	}
	
	public void onDraw(Canvas canvas){
		this.update();
		canvas.drawBitmap(this.bitmap, this.x, this.y, null);
	}
	
	public void update(){
		if(--life < 1){
			this.tempSprites.remove(this);
		}
	}

}
