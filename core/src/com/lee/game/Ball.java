package com.lee.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Lam on 7/30/2016.
 */
public class Ball {
    public static Integer point= 0;
    public static float velocity_max = 30;
    float weight = 3 ;
    boolean counted = false;
    float height;
    float width;
    Texture texture;
    public Vector2 velocity;
    boolean orientation_down = true;
    boolean orientation_right = true;
    public Vector2 position;
    public Vector2 center_point;
    public Circle circle_bound = new Circle();
   // float gravity = -25;
    float air_friction = 0.05f;
    public boolean canPlay = false;
    public Ball (){
        velocity = new Vector2(0f,0f);
        Basket.WIDTH = Gdx.graphics.getWidth();
        Basket.HEIGHT = Gdx.graphics.getHeight();
        position = new Vector2(Basket.WIDTH/2 ,Basket.HEIGHT/2 );
        texture = new Texture("basketball.png");
        height = Basket.WIDTH / 3;
        width = Basket.WIDTH / 3;
        center_point = new Vector2();
    }
    public void restartGame()
    {
        canPlay = false;
        point = 0 ;
        position.y = Basket.HEIGHT/2;
        position.x = Basket.WIDTH/2;
        velocity.y=0;
        orientation_down=true;
        velocity.x=0;
        orientation_right=true;
    }
    public void update(float dt){
        if(position.y >0 )velocity.y = velocity.y + Basket.GRAVITY*dt;
        if(velocity.y>0) orientation_down = false; else orientation_down = true;
        if(velocity.x>0) orientation_right = true;else orientation_right= false;

        if(position.y <= 0 && orientation_down) {
            velocity.y -= Basket.AIR_FRICTION*velocity.y;
            if(velocity.y >0) velocity.y = 0;
            velocity.y *= -1;
            if(orientation_right)
            {
                velocity.x -= velocity.x*air_friction*3;
                if(velocity.x <=0) velocity.x = 0;
            }
            else
            {
                velocity.x += velocity.x*air_friction;
                if(velocity.x >=0) velocity.x = 0;
            }
            orientation_down = false;
        }
        if(position.x<= 0 + width/2 && !orientation_right)
            {
                velocity.x *= -1;
                velocity.x -= velocity.x*air_friction*3;
                orientation_right = true;
            }
        if(position.y>= Basket.HEIGHT - height && !orientation_down)
            { velocity.y*=-1; orientation_down = true;restartGame();}
        if(position.x >= Basket.WIDTH - width/2 && orientation_right)
            {
                velocity.x -= velocity.x*air_friction*3;
                if(velocity.x < 0) velocity.x =0;
                velocity.x *= -1;
                orientation_right = false;
            }

        if(position.y >= Basket.HEIGHT/2 && !counted && !orientation_down)
        {
            counted = true;
            //point +=1;
        }
        if(orientation_down && counted && position.y<Basket.HEIGHT/2)
        {
            counted = false;
        }
        position.x += velocity.x;
        position.y += velocity.y;
        if(position.y<=5 && velocity.y*velocity.y<=5) restartGame();
        center_point.x = position.x ;
        center_point.y = position.y ;
        circle_bound.set(center_point,width/2);
       // System.out.println("circe point " + circle_bound.x + " " + circle_bound.y + " R:" + circle_bound.radius);
    }
    public void render(SpriteBatch sb,float dt)
    {
        if( canPlay)update(dt);
        sb.draw(texture,position.x-width/2,position.y,width,height);

    }
}
