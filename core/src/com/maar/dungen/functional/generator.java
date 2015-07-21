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
import com.maar.dungen.mats.wall;
import com.sun.glass.ui.Size;

public class generator {
	private int RoomCount;
	private ArrayList<room> rooms;
	private ArrayList<block> allBlocks;
	private ArrayList<wall> walls;
	private float minPosX, minPosY, maxPosX, maxPosY;
	private int minSizeW, minSizeH, maxSizeW, maxSizeH;
	private ArrayList<Line> connections;
	private ShapeRenderer t;
	
	public generator(int roomC){
		RoomCount = roomC;
		rooms = new ArrayList<room>();
		connections = new ArrayList<Line>();
		allBlocks = new ArrayList<block>();
		walls = new ArrayList<wall>();
		
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
		generateWalls();
	}
	public void draw(SpriteBatch batch){
		for(int i = 0; i < allBlocks.size(); i++){
			allBlocks.get(i).draw(batch);
		}
		for(int i = 0; i < walls.size(); i++){
			walls.get(i).draw(batch);
		}
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
		float distance = 500;
		float t = 0;
		for(int i = 0; i < rooms.size(); i++){
			if(i != rooms.size()-1)
				shortest = rooms.get(i+1).getPosition();
			distance = 1024;
			for(int j = i+1; j < rooms.size(); j++){
				t = rooms.get(i).getPosition().dst(rooms.get(j).getPosition());
				if(rooms.get(i).connected(rooms.get(j)))
					continue;
				else if(t < distance){
					shortest = rooms.get(j).getPosition();
					distance = t;
				}
			}
			connections.add(new Line(rooms.get(i).getPosition(), shortest));
		}
		for(int i = 0; i < rooms.size(); i ++){
			allBlocks.addAll(rooms.get(i).getBlocks());
		}
		System.out.println(allBlocks.size());
	}
	private void createCorridors(){
		for(int i = 0; i < connections.size(); i++){
			Vector2 s = connections.get(i).getStart();
			Vector2 e = connections.get(i).getEnd();
			float angle = (float)Math.atan2(s.y - e.y, s.x - e.x);
			angle = (float) Math.toDegrees(angle) + 180;
			int diff = 8;
			Vector2 c = new Vector2(s.x,s.y);
			if(c.x < e.x){
				while(c.x-diff < e.x){
					//to right
					spawnCorridorBlock(c.x, c.y, false);
					c.x += diff;
				}
			}
			else{
				while(c.x+diff > e.x){
					//to left
					spawnCorridorBlock(c.x, c.y, false);
					c.x -= diff;
				}	
			}
			
			if(c.y < e.y){
				while(c.y-diff < e.y){
					//up
					spawnCorridorBlock(c.x, c.y, true);
					c.y += diff;
				}
			}
			else{
				while(c.y+diff > e.y){
					//down
					spawnCorridorBlock(c.x, c.y, true);
					c.y -= diff;
				}
			}
			
		}
	}
	private void generateWalls(){
		block t;
		Vector2 pos;
		for(int i = 0; i < allBlocks.size(); i++){
			t = allBlocks.get(i);
			pos = t.getPosition();
			if(!existsBlockOnSide(i, 1)){
				walls.add(new wall(new Vector2(pos.x, pos.y ), 0));
			}
			if(!existsBlockOnSide(i, 2)){
				walls.add(new wall(new Vector2(pos.x-3, pos.y + 3), 90));
			}
			if(!existsBlockOnSide(i, 3)){
				walls.add(new wall(new Vector2(pos.x, pos.y +7), 0));
			}
			if(!existsBlockOnSide(i, 4)){
				walls.add(new wall(new Vector2(pos.x+5 , pos.y+3), 90));
			}
		}
	}
	private boolean existsBlockOnSide(int id, int side){
		
		block t = allBlocks.get(id);
		Vector2 cpos = t.getPosition();
		Vector2 checkPos;
		int diff = 8;
		for(int i = 0; i < allBlocks.size(); i++){
			checkPos = allBlocks.get(i).getPosition();
			if(cpos.dst(checkPos) <= diff){
				float angle = (float)Math.atan2(cpos.y - checkPos.y, cpos.x - checkPos.x);
				angle = (float) Math.toDegrees(angle) + 180;
				if(angle < 45 || angle > 315){
					//left
					if(side == 4)
						return true;
				}
				if(angle > 225 && angle < 315){
					//up
					if(side == 1)
						return true;
				}
				if(angle < 225 && angle > 135 && checkPos.angle(cpos) != 0){
					//right
					if(side == 2){
						return true;
					}
				}
				if(angle < 135 && angle > 45){
					//down
					if(side == 3)
						return true;
				}
			}
		}
		
		return false;
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
	private void spawnCorridorBlock(float posX, float posY, boolean vertical){
		float tx, ty;
		if(vertical){
			for(int i = 0; i < 3; i++){
				int j = i-1;
				tx = posX + 8*j;
				ty = posY;
				spawnBlock(tx, ty);
			}
		}
		else{
			for(int i = 0; i < 3; i++){
				int j = i-1;
				tx = posX;
				ty = posY + 8*j;
				spawnBlock(tx, ty);
			}
		}
	}
	private void spawnBlock(float atx, float aty){
		if(!blockExist( atx, aty))
			this.allBlocks.add(new block(new Vector2(makeNumber(atx),makeNumber(aty))));
	}
	private boolean blockExist(float atx, float aty){
		int px = makeNumber(atx);
		int py = makeNumber(aty);
		Vector2 c;
		for(int i = 0; i < allBlocks.size(); i++){
			c = allBlocks.get(i).getPosition();
			if(c.x == px && c.y == py)
				return true;
		}
		return false;
	}
	private int randomPos(float from, float to){
		float number = (float) (Math.random() * (to-from) + from);
		int ret = Math.round(number / 8) * 8;
		//System.out.print(ret + " ");
		return ret;
	}
	private int makeNumber(float from){
		return (Math.round(from / 8) * 8);
	}
	private int random(int from, int to){
		return (int) (Math.random() * (to-from) + from);
	}
}