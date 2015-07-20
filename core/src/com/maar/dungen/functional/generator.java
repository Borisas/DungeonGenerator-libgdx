package com.maar.dungen.functional;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.maar.dungen.maar;
import com.maar.dungen.functional.Line;
import com.maar.dungen.mats.block;
import com.maar.dungen.mats.room;
import com.sun.glass.ui.Size;

public class generator {
	private int RoomCount;
	private ArrayList<room> rooms;
	private ArrayList<block> allBlocks;
	private float minPosX, minPosY, maxPosX, maxPosY;
	private int minSizeW, minSizeH, maxSizeW, maxSizeH;
	private ArrayList<Line> connections;
	private ShapeRenderer t;
	
	public generator(int roomC){
		RoomCount = roomC;
		rooms = new ArrayList<room>();
		connections = new ArrayList<Line>();
		allBlocks = new ArrayList<block>();
		
		minPosX = maar.Resolution.width/3;
		maxPosX = maar.Resolution.width/3 * 2;
		minPosY = maar.Resolution.height/3;
		maxPosY = maar.Resolution.height/3 * 2;
		t = new ShapeRenderer();
		t.setColor(new Color(255, 0,0, 255));
		t.setAutoShapeType(true);
		
		minSizeW = 5;
		maxSizeW = 7;
		minSizeH = 4;
		maxSizeH = 9;
		
		generate();
		decenter();
		for(int i = 0; i < rooms.size(); i++){
			rooms.get(i).fixPos();
		}
		connect();
		createCorridors();
	}
	public void draw(SpriteBatch batch){
		for(int i = 0; i < rooms.size(); i++){
			rooms.get(i).draw(batch);
		}
		t.begin();
		for(int i = 0; i < connections.size(); i++){
			t.line(connections.get(i).getStart(), connections.get(i).getEnd());
		}
		t.end();
	}
	private void generate(){
		Size roomsize;
		Vector2 roompos;
		for( int i = 0; i < RoomCount; i++){
			roomsize = new Size(random(minSizeW, maxSizeW),random(minSizeH, maxSizeH));
			roompos = new Vector2(randomPos(minPosX,maxPosX),randomPos(minPosY, maxPosY));
			rooms.add(new room(roomsize, roompos));
		}
	}
	public void connect(){
		Vector2 shortest = new Vector2();
		this.connections.clear();
		float distance = 0;
		float t = 0;
		for(int i = 0; i < rooms.size(); i++){
			if(i != rooms.size()-1)
				shortest = rooms.get(i+1).getPosition();
			distance = rooms.get(i).getPosition().dst(shortest);
			for(int j = i+1; j < rooms.size(); j++){
				if(rooms.get(i).connected(rooms.get(j)))
					continue;
				else if((t = rooms.get(i).getPosition().dst(rooms.get(j).getPosition())) < distance){
					shortest = rooms.get(j).getPosition();
					distance = t;
				}
			}
			connections.add(new Line(rooms.get(i).getPosition(), shortest));
		}
		for(int i = 0; i < rooms.size(); i ++){
			allBlocks.addAll(rooms.get(i).getBlocks());
		}
	}
	private void createCorridors(){
		
	}
	public void decenter(){
		int times = 0;
		for(int i = 0; i < rooms.size(); i++){
			for(int j = i+1; j < rooms.size(); j++){
				while(rooms.get(i).overlaps(rooms.get(j))){
					rooms.get(i).pushAway(rooms.get(j));
					times+=1;
				}
			}
		}
		if(times > 0)
			decenter();
	}
	private int randomPos(float from, float to){
		float number = (float) (Math.random() * (to-from) + from);
		int ret = Math.round(number / 8) * 8;
		//System.out.print(ret + " ");
		return ret;
	}
	private int random(int from, int to){
		return (int) (Math.random() * (to-from) + from);
	}
}