package com.hitchh1k3rsguide.minild42;

import org.lwjgl.util.vector.Vector2f;

import com.hitchh1k3rsguide.minild42.utilities.MyMath;

public class Box {

	private float width, height, theta;
	private Vector2f[] originalVerts = new Vector2f[]{new Vector2f(), new Vector2f(), new Vector2f(), new Vector2f()};
	private Vector2f[] vertices = new Vector2f[]{new Vector2f(), new Vector2f(), new Vector2f(), new Vector2f()};
	private Vector2f pos = new Vector2f();
	private Vector2f origin = new Vector2f();

	public Box(float x, float y, float width, float height, float angle)
	{
		pos.x = x;
		pos.y = y;
		this.width = width;
		this.height = height;
		origin.x = width/2;
		origin.y = height/2;
		this.theta = angle;
		updateVerts();
	}

	public void updateVerts()
	{
		originalVerts[0].x = -origin.x;
		originalVerts[3].x = -origin.x;
		originalVerts[1].x = -origin.x+this.width;
		originalVerts[2].x = -origin.x+this.width;
		originalVerts[0].y = -origin.y+this.height;
		originalVerts[1].y = -origin.y+this.height;
		originalVerts[2].y = -origin.y;
		originalVerts[3].y = -origin.y;
		vertices[0] = MyMath.rotate(originalVerts[0], theta);
		vertices[1] = MyMath.rotate(originalVerts[1], theta);
		vertices[2] = MyMath.rotate(originalVerts[2], theta);
		vertices[3] = MyMath.rotate(originalVerts[3], theta);
	}

	public double getX(int i) {
		return vertices[i].x + pos.x;
	}

	public double getY(int i) {
		return vertices[i].y + pos.y;
	}

	public void move(float x, float y)
	{
		pos.x = x;
		pos.y = y;
	}

	public void setOrigin(float x, float y)
	{
		origin.x = x;
		origin.y = y;
		updateVerts();
	}

	public void rotate(double angle) {
		this.theta += angle;
		while(this.theta < 0)
			theta += MyMath.PI * 2;
		while(this.theta > MyMath.PI * 2)
			theta -= MyMath.PI * 2;
		updateVerts();
	}
}
