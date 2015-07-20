package com.maar.dungen.mats;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.sun.glass.ui.Size;

public class room {
	
	private Size self;
	private ArrayList<block> blocks;
	private Vector2 position;
	private Vector2 temporary;
	
	public room(Size sz, Vector2 position){
		self = sz;
		blocks = new ArrayList<block>();
		this.position = position;
		this.temporary = new Vector2(position.x, position.y);
		fill();
	}
	public void draw(SpriteBatch batch){
		for(int i = 0; i < blocks.size(); i++){
			blocks.get(i).draw(batch);
		}
	}
	public void translate(float x, float y){
		for(int i = 0; i < blocks.size(); i++){
			blocks.get(i).translate(x, y);
		}
		this.temporary.x += x;
		this.temporary.y += y;
	}
	public boolean overlaps(room other){
		ArrayList<block> otherb = other.getBlocks();
		for(int i = 0; i < blocks.size(); i++){
			for(int j = 0; j < otherb.size(); j++){
				if(blocks.get(i).overlaps(otherb.get(j))){
					return true;
				}
			}
		}
		return false;
	}
	public void fixPos(){
		this.position = this.temporary;
	}
	public Vector2 getPosition(){
		return new Vector2(position.x + self.width*4 , position.y + self.height*4);
	}
	public boolean connected(room with){
		ArrayList<block> a = blocks;
		ArrayList<block> b = with.getBlocks();
		for(int i = 0; i < a.size(); i++){
			for( int j = 0; j < b.size(); j++){
				if(a.get(i).connects(b.get(j))){
					return true;
				}
			}
		}
		return false;
	}
	public void pushAway(room from){
		Vector2 move = new Vector2(8,8);
		float angle = (float)Math.atan2(from.getPosition().y - position.y, from.getPosition().x - position.x);
		angle = (float) Math.toDegrees(angle) + 180;
		if(angle <= 45 || angle >= 315){
			this.translate(move.x, 0);
			from.translate(-move.x,0);
		}
		if(angle >= 225 && angle <= 315){
			this.translate(0, move.y);
			from.translate(0, -move.y);
		}
		if(angle <= 225 && angle >= 135){
			this.translate(-move.x, 0);
			from.translate(move.x,0);
		}
		if(angle <= 135 && angle >= 45){
			this.translate(0, -move.y);
			from.translate(0, move.y);
		}
	}
	public ArrayList<block> getBlocks(){
		return this.blocks;
	}
	public int getSize(){
		return self.width*self.height;
	}
	private void fill(){
		float tpx = 0;
		float tpy = 0;
		for(int i = 0; i < self.width; i ++){
			for(int j = 0; j < self.height; j++){
				tpx = position.x + j * 8;
				tpy = position.y  + i * 8;
				blocks.add(new block(new Vector2(tpx,tpy)));
			}
		}
	}
}
