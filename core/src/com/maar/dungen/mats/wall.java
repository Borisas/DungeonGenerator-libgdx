package com.maar.dungen.mats;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class wall {
	
	Sprite img ;
	
	public wall(Vector2 pos, float angle){
		img = new Sprite( new Texture(Gdx.files.internal("wall.png")));
		img.setPosition(pos.x, pos.y);
		img.setRotation(angle);
	}
	public void draw(SpriteBatch batch){
		img.draw(batch);
	}
}
