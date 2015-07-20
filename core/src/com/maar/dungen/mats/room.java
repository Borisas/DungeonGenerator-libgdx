package com.maar.dungen.mats;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.sun.glass.ui.Size;

public class room {
	
	private Size self;
	private ArrayList<block> blocks;
	private Vector2 position;
	
	public room(Size sz, Vector2 position){
		self = sz;
		blocks = new ArrayList<block>();
		this.position = position;
		fill();
	}
	public void draw(SpriteBatch batch){
		for(int i = 0; i < blocks.size(); i++){
			blocks.get(i).draw(batch);
		}
	}
	private void fill(){
		float tpx = 0;
		float tpy = 0;
		for(int i = 0; i < self.width; i ++){
			for(int j = 0; j < self.height; j++){
				tpx = position.x - self.width*4 + j * 8;
				tpy = position.y - self.height*4 + i * 8;
				blocks.add(new block(new Vector2(tpx,tpy)));
			}
		}
	}
}
