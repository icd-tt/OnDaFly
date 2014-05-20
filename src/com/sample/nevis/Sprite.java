package com.sample.nevis;

import java.util.Random;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Sprite {
	//direction = 0 up, 1 left, 2 down, 3 right
	//animation = 3 back, 1 left, 0 front, 2 right
	int[] DIRECTION_TO_ANIMATION_MAP = {3,1,0,2};
	private static final int BMP_ROWS = 4;
	private static final int BMP_COLUMNS = 3;
	public static final int MAX_SPEED = 5;
	private int x  = 0;
	private int y = 0;
	
	private GameView gameView;
	private Bitmap bitmap;
	private int currentFrame = 0;
	private int width;
	private int height;
	private int xSpeed = 5;
	private int ySpeed;
	protected boolean draggable;
	
	public Sprite(GameView gameView, Bitmap bitmap){
		this.gameView = gameView;
		this.bitmap = bitmap;
		this.width = this.bitmap.getWidth() / BMP_COLUMNS;
		this.height = this.bitmap.getHeight() / BMP_ROWS;
		
		Random random = new Random(System.currentTimeMillis());
		this.x = random.nextInt(this.gameView.getWidth() - this.width);
		this.y = random.nextInt(this.gameView.getHeight() - this.height);			
		this.xSpeed = random.nextInt(MAX_SPEED * 2) - MAX_SPEED;
		this.ySpeed = random.nextInt(MAX_SPEED * 2) - MAX_SPEED;
	}
	
	private void update(){
		if(this.x >  this.gameView.getWidth() - this.width - this.xSpeed || this.x + this.xSpeed < 0){
			this.xSpeed = -this.xSpeed;
		}
		
		this.x += this.xSpeed;
		
		if(this.y > this.gameView.getHeight() - this.height - this.ySpeed || this.y + this.ySpeed < 0){
			this.ySpeed = -this.ySpeed;
		}
		
		this.y += this.ySpeed;
		this.currentFrame = ++this.currentFrame % BMP_COLUMNS;
	}
	
	@SuppressLint("DrawAllocation")
	public void onDraw(Canvas canvas){
		this.update();
		int srcX = this.currentFrame * this.width;
		int srcY = this.getAnimationRow() * this.height;
		Rect src = new Rect(srcX, srcY, srcX + this.width, srcY + this.height);
		Rect dst = new Rect(this.x, this.y, this.x + this.width, this.y + this.height);
		canvas.drawBitmap(this.bitmap, src, dst, null);
	}
	
	public int getAnimationRow(){
		double d = (Math.atan2(this.xSpeed, this.ySpeed) / (Math.PI / 2) + 2);
		int direction = (int)Math.round(d) % BMP_ROWS;
		return DIRECTION_TO_ANIMATION_MAP[direction];
	}
	
	public boolean isCollition(float x2, float y2){
		return x2 > this.x && x2 < this.x + this.width && y2 > this.y && y2 < this.y + this.height;
	}
	
	public void setDraggable(boolean draggable){
		this.draggable = draggable;
	}
	
	public boolean isDraggable(){
		return this.draggable;
	}
	
	public boolean isInside(float x2, float y2){
		return (this.x <= x2 && this.y <= y2 && (this.x + this.width >= x2) && (this.y + this.height >= y2));
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getXSpeed() {
		return xSpeed;
	}

	public void setXSpeed(int xSpeed) {
		this.xSpeed = xSpeed;
	}

	public int getYSpeed() {
		return ySpeed;
	}

	public void setYSpeed(int ySpeed) {
		this.ySpeed = ySpeed;
	}
}
