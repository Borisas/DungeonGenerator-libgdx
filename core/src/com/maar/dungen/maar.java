package com.maar.dungen;

import java.util.Stack;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.maar.dungen.functional.view;
import com.maar.dungen.main.Game;
import com.sun.glass.ui.Size;

public class maar extends ApplicationAdapter {
	SpriteBatch batch;
	public static Stack<view> Viewer;
	public static Size Resolution;
	public static float delta = 1/60;
	
	@Override
	public void create () {
		
		
		Resolution = new Size(1024, 768);
		Gdx.graphics.setDisplayMode(Resolution.width, Resolution.height, false);
		
		batch = new SpriteBatch();
		
		Viewer = new Stack<view>();
		
		Viewer.push(new Game());
	}

	@Override
	public void render () {
		
		Viewer.peek().update(delta);
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		
		Viewer.peek().draw(batch);
		
		batch.end();
	}
}
