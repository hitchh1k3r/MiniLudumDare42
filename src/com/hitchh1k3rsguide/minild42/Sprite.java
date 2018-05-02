package com.hitchh1k3rsguide.minild42;

import org.lwjgl.opengl.GL11;

public class Sprite {

	private SpriteSheet sheet;
	private String spriteID;
	private Box box;
	private float red = 1, green = 1, blue = 1;
	private float opacity = 1;

	public Sprite(SpriteSheet sheet, String ID, Box box)
	{
		this.sheet = sheet;
		this.spriteID = ID;
		this.box = box;
	}

	public void setID(String id)
	{
		this.spriteID = id;
	}

	public void setSheet(SpriteSheet sheet)
	{
		this.sheet = sheet;
	}

	public String getID()
	{
		return this.spriteID;
	}

	public void setOpacity(float value)
	{
		this.opacity = value;
	}

	public void draw()
	{
		this.sheet.bindTexture();
		GL11.glColor4f(red, green, blue, opacity);
		GL11.glBegin(GL11.GL_QUADS);
		{
			GL11.glTexCoord2d(sheet.getU(spriteID, 0), sheet.getV(spriteID, 0));
			GL11.glVertex2d(box.getX(0), box.getY(0));
			GL11.glTexCoord2d(sheet.getU(spriteID, 1), sheet.getV(spriteID, 1));
			GL11.glVertex2d(box.getX(1), box.getY(1));
			GL11.glTexCoord2d(sheet.getU(spriteID, 2), sheet.getV(spriteID, 2));
			GL11.glVertex2d(box.getX(2), box.getY(2));
			GL11.glTexCoord2d(sheet.getU(spriteID, 3), sheet.getV(spriteID, 3));
			GL11.glVertex2d(box.getX(3), box.getY(3));
		}
		GL11.glEnd();
	}
}
