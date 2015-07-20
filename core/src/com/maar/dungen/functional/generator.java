package com.maar.dungen.functional;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;
import com.maar.dungen.mats.room;
import com.sun.glass.ui.Size;

public class generator {
	private int RoomCount;
	private ArrayList<room> rooms;
	
	public generator(int roomC){
		RoomCount = roomC;
		rooms = new ArrayList<room>();
		
		generate();
	}
	private void generate(){
		Size roomsize;
		Vector2 roompos;
		for( int i = 0; i < RoomCount; i++){
			
			roomsize = new Size(1,1);
			roompos = new Vector2(0,0);
			
			rooms.add(new room(roomsize, roompos));
		}
	}
}
