package com.hitchh1k3rsguide.minild42;

import java.io.IOException;
import java.util.HashMap;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class SpriteSheet {

	public static class Block
	{
		public final String name;
		public final int x, y, width, height;
		public Block(String name, int x, int y, int width, int height)
		{
			this.name = name;
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
		}
	}

	public static SpriteSheet AlienBG = new SpriteSheet("AlienBG.png", 1024, 512,
			new Block("BG", 0, 0, 640, 480),
			new Block("DownLines", 640, 0, 1, 494),
			new Block("UpLines", 641, 0, 1, 494)
		);
	public static SpriteSheet CometBG = new SpriteSheet("CometBG.png", 1024, 512,
			new Block("BG", 0, 0, 640, 480),
			new Block("DownLines", 640, 0, 1, 494),
			new Block("UpLines", 641, 0, 1, 494)
		);
	public static SpriteSheet NukeBG = new SpriteSheet("RadiationBG.png", 1024, 512,
			new Block("BG", 0, 0, 640, 480),
			new Block("DownLines", 640, 0, 1, 494),
			new Block("UpLines", 641, 0, 1, 494)
		);
	public static SpriteSheet SunBG = new SpriteSheet("SunBG.png", 1024, 512,
			new Block("BG", 0, 0, 640, 480),
			new Block("DownLines", 640, 0, 1, 494),
			new Block("UpLines", 641, 0, 1, 494)
		);
	public static SpriteSheet TakeoverBG = new SpriteSheet("TakeoverBG.png", 1024, 512,
			new Block("BG", 0, 0, 640, 480),
			new Block("DownLines", 640, 0, 1, 494),
			new Block("UpLines", 641, 0, 1, 494)
		);
	public static SpriteSheet WatchBG = new SpriteSheet("WatchBG.png", 1024, 512,
			new Block("BG", 0, 0, 640, 480),
			new Block("DownLines", 640, 0, 1, 494),
			new Block("UpLines", 641, 0, 1, 494)
		);
	public static SpriteSheet TEXT = new SpriteSheet("Text.png", 512, 512,
		new Block("A", 0, 0, 11, 15),
		new Block("B", 11, 0, 11, 15),
		new Block("C", 22, 0, 11, 15),
		new Block("D", 33, 0, 11, 15),
		new Block("E", 44, 0, 11, 15),
		new Block("F", 55, 0, 11, 15),
		new Block("G", 66, 0, 11, 15),
		new Block("H", 77, 0, 11, 15),
		new Block("I", 88, 0, 11, 15),
		new Block("J", 99, 0, 11, 15),
		new Block("K", 110, 0, 11, 15),
		new Block("L", 121, 0, 11, 15),
		new Block("M", 132, 0, 11, 15),
		new Block("N", 143, 0, 11, 15),
		new Block("O", 154, 0, 11, 15),
		new Block("P", 165, 0, 11, 15),
		new Block("Q", 176, 0, 11, 15),
		new Block("R", 187, 0, 11, 15),
		new Block("S", 198, 0, 11, 15),
		new Block("T", 209, 0, 11, 15),
		new Block("U", 220, 0, 11, 15),
		new Block("V", 231, 0, 11, 15),
		new Block("W", 242, 0, 11, 15),
		new Block("X", 253, 0, 11, 15),
		new Block("Y", 264, 0, 11, 15),
		new Block("Z", 275, 0, 11, 15),
		new Block(" ", 0, 15, 11, 15),
		new Block(".", 11, 15, 11, 15),
		new Block("!", 22, 15, 11, 15),
		new Block(",", 33, 15, 11, 15),
		new Block(";", 44, 15, 11, 15),
		new Block(":", 55, 15, 11, 15),
		new Block("'", 66, 15, 11, 15),
		new Block("\"", 77, 15, 11, 15),
		new Block("?", 88, 15, 11, 15),
		new Block("1", 99, 15, 11, 15),
		new Block("2", 110, 15, 11, 15),
		new Block("3", 121, 15, 11, 15),
		new Block("4", 132, 15, 11, 15),
		new Block("5", 143, 15, 11, 15),
		new Block("6", 154, 15, 11, 15),
		new Block("7", 165, 15, 11, 15),
		new Block("8", 176, 15, 11, 15),
		new Block("9", 187, 15, 11, 15),
		new Block("0", 198, 15, 11, 15),
		new Block(">", 209, 15, 11, 15),
		new Block("_", 220, 15, 11, 15),
		new Block("[", 231, 15, 11, 15),
		new Block("]", 242, 15, 11, 15),
		new Block("*", 253, 15, 11, 15),
		new Block("-", 264, 15, 11, 15)
	);

/////////////////////////////////////////////////////////////////////////////////////////

	private HashMap<String, double[]> uvMap = new HashMap<String, double[]>();
	private Texture texture;

	public SpriteSheet(String name, int width, int height, Block... blocks)
	{
		try {
			texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/"+name));
			texture.setTextureFilter(GL11.GL_NEAREST);
		} catch (IOException e) {
			e.printStackTrace();
		}
		for(int i = 0; i < blocks.length; i++)
		{
			double left = (double)blocks[i].x / (double)width;
			double right = ((double)blocks[i].x + (double)blocks[i].width) / (double)width;
			double top = (double)blocks[i].y / (double)height;
			double bottom = ((double)blocks[i].y + (double)blocks[i].height) / (double)height;
			this.uvMap.put(blocks[i].name, new double[]{left, right, top, bottom});
		}
	}

	public void bindTexture()
	{
		texture.bind();
	}

	public double getU(String spriteID, int vertexID) {
		if(this.uvMap.containsKey(spriteID))
			return this.uvMap.get(spriteID)[((vertexID == 1 || vertexID == 2) ? 1 : 0)];
		return 0;
	}

	public double getV(String spriteID, int vertexID) {
		if(this.uvMap.containsKey(spriteID))
			return this.uvMap.get(spriteID)[((vertexID == 0 || vertexID == 1) ? 3 : 2)];
		return 0;
	}
}
