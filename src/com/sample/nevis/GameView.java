package com.sample.nevis;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView {
	// private Bitmap bitmap;
	private SurfaceHolder surfaceHolder;
	private GameLoopThread gameLoopThread;
	// private int x = 0;
	// private int xSpeed = 1;
	// private Sprite sprite;
	private List<Sprite> sprites = new ArrayList<Sprite>();
	private long lastClick;
	private List<TempSprite> tempSprites = new ArrayList<TempSprite>();
	
	private int spriteCount = 5;

	private Bitmap bloodBitmap;
	private List<Bitmap> splatterBitmaps = new ArrayList<Bitmap>();

	public GameView(Context context) {
		super(context);
		this.gameLoopThread = new GameLoopThread(this);
		this.surfaceHolder = getHolder();
		this.surfaceHolder.addCallback(new SurfaceHolder.Callback() {

			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
				// TODO Auto-generated method stub
				boolean retry = true;
				gameLoopThread.setRunning(false);
				while (retry) {
					try {
						gameLoopThread.join();
						retry = false;
					} catch (InterruptedException ie) {
					}
				}
			}

			@Override
			public void surfaceCreated(SurfaceHolder holder) {
				// TODO Auto-generated method stub
				createSprites();
				gameLoopThread.setRunning(true);
				gameLoopThread.start();
			}

			@Override
			public void surfaceChanged(SurfaceHolder holder, int format,
					int width, int height) {
				// TODO Auto-generated method stub

			}
		});

		// this.bitmap = BitmapFactory.decodeResource(getResources(),
		// R.drawable.image);
		// this.sprite = new Sprite(this, this.bitmap);

		this.bloodBitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.blood);
		
		this.splatterBitmaps.add(BitmapFactory.decodeResource(getResources(),
				R.drawable.splatter));

		this.splatterBitmaps.add(BitmapFactory.decodeResource(getResources(),
				R.drawable.splatter_1));
	}

	@SuppressLint("WrongCall")
	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(Color.BLACK);

		for (int i = this.tempSprites.size() - 1; i >= 0; i--) {
			this.tempSprites.get(i).onDraw(canvas);
		}

		for (Sprite sprite : this.sprites) {
			sprite.onDraw(canvas);
		}
	}

	private Sprite createSprite(int id) {
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), id);
		return new Sprite(this, bitmap);
	}

	private void createSprites() {
		for (int i = 0; i < this.spriteCount; i++) {
			this.sprites.add(createSprite(R.drawable.image));
			this.sprites.add(createSprite(R.drawable.image_1));
		}
	}
	
	private Random random = new Random(System.currentTimeMillis());	
	public void restore(){
		if(this.sprites.size() == 0){
			this.createSprites();
			this.spriteCount++;
			
			for (Sprite sprite : this.sprites) {
				sprite.setXSpeed(this.random.nextInt(this.spriteCount * 2) - this.spriteCount);
				sprite.setYSpeed(this.random.nextInt(this.spriteCount * 2) - this.spriteCount);
			}			
			
			if(this.spriteCount >= 12){
				this.spriteCount = 5;
				
					
				for (Sprite sprite : this.sprites) {
					sprite.setXSpeed(this.random.nextInt(Sprite.MAX_SPEED * 2) - Sprite.MAX_SPEED);
					sprite.setYSpeed(this.random.nextInt(Sprite.MAX_SPEED * 2) - Sprite.MAX_SPEED);
				}			
			}
			
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (System.currentTimeMillis() - this.lastClick > 500) {
			this.lastClick = System.currentTimeMillis();
			float x = event.getX();
			float y = event.getY();
			synchronized (this.getHolder()) {
				for (int i = this.sprites.size() - 1; i >= 0; i--) {

					Sprite sprite = this.sprites.get(i);
					// if (x >= sprite.getX() && x <= sprite.getX() +
					// sprite.getWidth()
					// && y >= sprite.getY() && y <= sprite.getY() +
					// sprite.getHeight()) {
					// sprite.setX((int)(x - (sprite.getWidth()/2)));
					// sprite.setY((int)(y - (sprite.getHeight()/2)));
					// }

					if (sprite.isCollition(x, y)) {
						this.sprites.remove(sprite);
						this.tempSprites.add(new TempSprite(this.tempSprites,
								this, x, y, this.splatterBitmaps.get(this.random.nextInt(this.splatterBitmaps.size()))));
						break;
					}
				}
			}
		}
		return true;
	}
}
