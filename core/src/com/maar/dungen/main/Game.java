package com.maar.dungen.main;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.maar.dungen.functional.generator;
import com.maar.dungen.functional.view;

public class Game extends view{

	private generator floor;
	
	public Game(){
		//test = new room(new Size(300,300), new Vector2(maar.Resolution.width/2, maar.Resolution.height/2));
		floor = new generator(200);
		
	}
	public void draw(SpriteBatch batch) {
		//test.draw(batch);
		floor.draw(batch);
	
	}
	public void update(float dt) {
		
	}

}
