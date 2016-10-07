package com.lee.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class Basket extends ApplicationAdapter implements GestureDetector.GestureListener{
	public static  int HEIGHT = 720;
	public static  int WIDTH = 480;
	public static  float AIR_FRICTION = 0.1f;
	public static final float GRAVITY = -20f;
	public float currentTime;
	static Integer hightscore = 10;
    static boolean waitingToTouch = false;
	SpriteBatch batch;
	Ball ball;
	GestureDetector gestureDetector;
	BitmapFont font,font1;

	public static AssetManager manager;
	@Override
	public void create () {
		manager = new AssetManager();
		manager.load("basketballBackground.png", Texture.class);
		manager.load("bet.fnt",BitmapFont.class);
		manager.load("font.fnt",BitmapFont.class);
		manager.load("font/font.fnt",BitmapFont.class);
		manager.finishLoading();
//		font = new BitmapFont(Gdx.files.internal("point.fnt"));
		font = manager.get("bet.fnt",BitmapFont.class);
		font.getData().setScale(2f);
		font1 = manager.get("font.fnt",BitmapFont.class);
		gestureDetector = new GestureDetector(this);
		ball = new Ball();
		batch = new SpriteBatch();

		Gdx.input.setInputProcessor(gestureDetector);
		//hightscore = 10;
		hightscore = readHighScore();
	}
	private int readHighScore(){
		try {
//			File file = new File("hightscore.txt");
			FileHandle highscoreFile = Gdx.files.internal("hightscore.txt");
			hightscore = Integer.parseInt(highscoreFile.readString());

			return hightscore;
		}
		finally {


			return 0;
		}
	}
	private void writeHighScore()
	{
		//FileHandle highscoreFile = Gdx.files.internal("hightscore.txt");
		//highscoreFile.writeString(hightscore.toString(), false); //write, append false
	}
	@Override
	public void render () {
		currentTime = System.currentTimeMillis();
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		ball.render(batch,Gdx.graphics.getDeltaTime());
		font1.draw(batch,"High score:   " +hightscore.toString(),30,Basket.HEIGHT*4/5 + 150);
		font.draw(batch,Ball.point.toString(),Basket.WIDTH*1/5,Basket.HEIGHT*4/5);
		batch.end();
	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		if(!ball.canPlay) ball.canPlay = true;

		float z= Gdx.graphics.getHeight() - y;
		boolean inside = (ball.position.x-ball.width/2-50 < x && ball.position.y-50 < z && ball.position.x +ball.width/2+50> x && ball.position.y+ball.width+50>z ) ? true:false;
		if(inside){
			//System.out.println("inside");
			waitingToTouch = false;
			Ball.point++;
			if(Ball.point > hightscore){hightscore = Ball.point;writeHighScore();}
			if(!ball.orientation_down) {
				if(ball.velocity.y - GRAVITY/3 > 50)
					ball.velocity.y = 50;
				else
					ball.velocity.y -= GRAVITY/3;

				ball.velocity.y *= -1;
			}else {
				if(ball.velocity.y + GRAVITY/3 < -50)
					ball.velocity.y = -50;
				else
					ball.velocity.y += GRAVITY/3;
			}
			float deltax = ball.position.x  - x;
			ball.velocity.x += deltax/20;
		}
		//System.out.println("velocity y :" + ball.velocity.y);

		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {

		return false;
	}

	@Override
	public boolean longPress(float x, float y) {
		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
	//	System.out.println("velocity x :" + velocityX);
	//	System.out.println("velocity y :"+ velocityY);
		//ball.velocity.y += velocityY;
		//ball.velocity.x += velocityX;

		return false;
	}


	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
	/*
	boolean isInside = false;
		float z = Gdx.graphics.getHeight() - y;
		System.out.println("Pan x : " +x+" y:" +y+" delta X :"+ deltaX + " delta y"+deltaY+ " Gotten in ");
		if((x-ball.circle_bound.x)*(x-ball.circle_bound.x) + (z-ball.circle_bound.y)*(z-ball.circle_bound.y) <= ball.circle_bound.radius*ball.circle_bound.radius) {
			System.out.print("is inside");
			if(ball.velocity.x+deltaX<Ball.velocity_max &&ball.velocity.x+deltaX>-Ball.velocity_max )
				ball.velocity.x += deltaX;
			else {
				if(ball.velocity.x + deltaX >Ball.velocity_max) ball.velocity.x = Ball.velocity_max;
				if(ball.velocity.x + deltaX < -Ball.velocity_max) ball.velocity.x = -Ball.velocity_max;
			}
			if(ball.velocity.y+deltaY<Ball.velocity_max &&ball.velocity.y+deltaY>-Ball.velocity_max )
			ball.velocity.y += deltaY;
			else {
				if(ball.velocity.y + deltaY >Ball.velocity_max) ball.velocity.y = Ball.velocity_max;
				if(ball.velocity.y + deltaY < -Ball.velocity_max) ball.velocity.y = -Ball.velocity_max;
			}
		}
		*/
		return false;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
		return false;
	}
}
