package com.maar.dungen.functional;

import com.badlogic.gdx.math.Vector2;

public class Line {
	private Vector2 from, to;
	
	public Line(){
		from = new Vector2();
		to = new Vector2();
	}
	public Line(Vector2 from, Vector2 to){
		this.from = from;
		this.to = to;
	}
	public void setStart(Vector2 s){
		this.from = s;
	}
	public void setEnd(Vector2 e){
		this.to = e;
	}
	public Vector2 getStart(){
		return this.from;
	}
	public Vector2 getEnd(){
		return this.to;
	}
}
