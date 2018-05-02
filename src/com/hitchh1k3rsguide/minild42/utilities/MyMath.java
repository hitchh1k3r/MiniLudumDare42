package com.hitchh1k3rsguide.minild42.utilities;

import org.lwjgl.util.vector.Vector2f;

public class MyMath {

	public static final double PI = 3.141592654;

	public static Vector2f rotate(Vector2f vert, float theta) {
		Vector2f out = new Vector2f();
		float cosa=(float) Math.cos(theta);
		float sina=(float) Math.sin(theta);
		out.x = vert.x*cosa + vert.y*sina;
		out.y = -vert.x*sina + vert.y*cosa;
		return out;
	}

}
