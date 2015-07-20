package com.maar.dungen.mats;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.sun.glass.ui.Size;

public class block {
	
	private Size content;
	private Sprite img;
	private Vector2 position;
	
	public block(Vector2 pos){
		content = new Size(8,8);
		img = new Sprite(new Texture(Gdx.files.internal("block.png")));
		this.position = pos;
		position.x = position.x - content.width/2;
		position.y = position.y - content.height/2;
		img.setPosition(position.x, position.y);
	}
	public void draw(SpriteBatch batch){
		img.draw(batch);
	}
	
}
