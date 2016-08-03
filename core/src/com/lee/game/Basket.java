package com.lee.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.DistanceFieldFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.particles.ParticleSorter;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;


public class Basket extends ApplicationAdapter implements GestureDetector.GestureListener{
	public static  int HEIGHT = 720;
	public static  int WIDTH = 480;
	public static  float AIR_FRICTION = 0.1f;
	public static final float GRAVITY = -20f;
	public float currentTime;
	static Integer hightscore = 10;
	FileInputStream readfile = null;
	FileOutputStream writefile = null;

	SpriteBatch batch;
	Ball ball;
	GestureDetector gestureDetector;
	BitmapFont font;
	@Override
	public void create () {
		ball = new Ball();
		batch = new SpriteBatch();
		font = new BitmapFont(Gdx.files.internal("win_table.fnt"));
		font.getData().setScale(2f);
		gestureDetector = new GestureDetector(this);
		Gdx.input.setInputProcessor(gestureDetector);
		//hightscore = 10;
		readHighScore();
	}
	private void readHighScore()
	{

		try {
			//Scanner scanner = new Scanner(new File("hightscore.txt"));
			readfile = new FileInputStream("hightscore.txt");
			//String s = readfile.read();
			/*if(scanner.hasNextInt()) {
				hightscore = scanner.nextInt();
			}*/
			System.out.print("hight score is " + hightscore);

		}
		catch (Exception e){
			System.out.print("error to read file");
		}
		finally {

		}
		try {
			if (readfile != null) readfile.close();
		}catch (Exception e){
			System.out.print("error to close file");
		}
	}
	private void writeHighScore()
	{
		try {
			writefile = new FileOutputStream("hightscore.txt");
			writefile.write(hightscore);
		}
		catch (Exception e){
			System.out.print("error to read file");
		}
		try {
			if (writefile != null) writefile.close();
		}catch (Exception e){
			System.out.print("error to close file");
		}
	}
	@Override
	public void render () {
		currentTime = System.currentTimeMillis();
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		ball.render(batch,Gdx.graphics.getDeltaTime());
		font.draw(batch,hightscore.toString(),30,Basket.HEIGHT*4/5 + 100);
		font.draw(batch,Ball.point.toString(),Basket.WIDTH*1/5,Basket.HEIGHT*4/5);
		batch.end();
	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		if(!ball.canPlay) ball.canPlay = true;

		float z= Gdx.graphics.getHeight() - y;
		boolean inside = (ball.position.x-ball.width/2-50 < x && ball.position.y-50 < z && ball.position.x +ball.width/2+50> x && ball.position.y+ball.width+50>z ) ? true:false;
		if(inside){
			System.out.println("inside");
			Ball.point++;
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
		System.out.println("velocity y :" + ball.velocity.y);

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
