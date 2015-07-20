package com.maar.dungen.mats;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.sun.glass.ui.Size;

public class block {
	
	private Size content;
	private Sprite img;
	private Vector2 position;
	private Rectangle self;
	
	public block(Vector2 pos){
		content = new Size(8,8);
		img = new Sprite(new Texture(Gdx.files.internal("block.png")));
		this.position = pos;
//		position.x = position.x - content.width/2;
//		position.y = position.y - content.height/2;
		img.setPosition(position.x, position.y);
		self = new Rectangle(position.x, position.y, content.width, content.height);
	}
	public void draw(SpriteBatch batch){
		img.draw(batch);
	}
	public void translate(float x, float y){
		this.position.x += x;
		this.position.y += y;
		self.x = position.x;
		self.y = position.y;
		img.setPosition(position.x, position.y);
	}
	public Rectangle getRectangle(){
		return self;
	}
	public boolean overlaps(block other){
		Rectangle a = self;
		Rectangle b = other.getRectangle();
		if(a.overlaps(b))
			return true;
		return false;
	}
	public boolean connects(block with){
		Rectangle a = self;
		Rectangle b = with.getRectangle();
		Rectangle inter = new Rectangle();
		if(Intersector.intersectRectangles(a, b, inter)){
			return true;
		}
		return false;
	}
	
}
