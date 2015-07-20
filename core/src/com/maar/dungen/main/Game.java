package com.maar.dungen.main;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.maar.dungen.maar;
import com.maar.dungen.functional.view;
import com.maar.dungen.mats.room;
import com.sun.glass.ui.Size;

public class Game extends view{

	private room test;
	
	public Game(){
		test = new room(new Size(20,20), new Vector2(maar.Resolution.width/2, maar.Resolution.height/2));
	}
	public void draw(SpriteBatch batch) {
		test.draw(batch);
	}
	public void update(float dt) {
	}

}
