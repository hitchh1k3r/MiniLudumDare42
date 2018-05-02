package com.hitchh1k3rsguide.minild42;

import org.lwjgl.input.Keyboard;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.openal.SoundStore;
import org.newdawn.slick.util.ResourceLoader;

import com.hitchh1k3rsguide.minild42.utilities.Timer;

public class Main {

	private static final int TEXT_WIDTH = 58;
	private static final int TEXT_HEIGHT = 32;
	private double blinkTimer = 0;
	private String[][] blinkingLetters = new String[TEXT_WIDTH][TEXT_HEIGHT];
	private boolean onStartScreen = true;
	public static int nextWorld = 0;
	private Audio jingle;
	private static class input
	{
		public static String text = "";
		public static int X = 0;
		public static int Y = 0;
		public static boolean isActive = false;
		public static int maxlength = 0;
		public static Pos cursor;
	}
	private class Pos
	{
		public Pos(int x, int y)
		{
			this.x = x;
			this.y = y;
		}
		public int x;
		public int y;
	}

	private void gameloop()
	{
		try {
			Display.setDisplayMode(new DisplayMode(640, 480));
			Display.setTitle("The Ends of The Earth");
			Display.create();
			jingle = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("res/HappyJingle.wav"));
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glOrtho(0, 640, 480, 0, 1, 0);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		GL11.glTranslated(0.375, 0.375, 0.0);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_TEXTURE_2D);

		Box bgBox = new Box(0, 0, 640, 480, 0);
		bgBox.setOrigin(0, 0);
		Sprite bgSprite = new Sprite(SpriteSheet.WatchBG, "BG", bgBox);
		Box downLinesBox = new Box(0, 0, 640, 494, 0);
		downLinesBox.setOrigin(0, 0);
		Sprite downLinesSprite = new Sprite(SpriteSheet.WatchBG, "DownLines", downLinesBox);
		downLinesSprite.setOpacity(0.05F);
		Box upLinesBox = new Box(0, -76, 640, 494, 0);
		upLinesBox.setOrigin(0, 0);
		Sprite upLinesSprite = new Sprite(SpriteSheet.WatchBG, "UpLines", upLinesBox);
		upLinesSprite.setOpacity(0.15F);
		Box textBoxes[][] = new Box[TEXT_WIDTH][TEXT_HEIGHT];
		Sprite textSprites[][] = new Sprite[TEXT_WIDTH][TEXT_HEIGHT];
		for(int x = 0; x < TEXT_WIDTH; x++)
		{
			for(int y = 0; y < TEXT_HEIGHT; y++)
			{
				textBoxes[x][y] = new Box((x*11)+1, y*15, 11, 15, 0);
				textBoxes[x][y].setOrigin(0, 0);
				textSprites[x][y] = new Sprite(SpriteSheet.TEXT, " ", textBoxes[x][y]);
				textSprites[x][y].setOpacity(0.9F);
			}
		}

		printString(18, 13, "THE ENDS OF THE EARTH", textSprites);
		printString(22, 15, "[DON'T PANIC]", textSprites);
		printBlinkingString(23, 15, "DON'T PANIC");
		
		double scanTimer = 0;
		while(!Display.isCloseRequested())
		{
			Timer.startFrame();
			double frameLength = Timer.getFrameTime();
			Display.update();
			
			while(Keyboard.next())
			{
				processKey(Keyboard.getEventKey(), Keyboard.getEventKeyState(), textSprites);
				if(onStartScreen)
				{
					if(nextWorld == 1)
					{
						bgSprite.setSheet(SpriteSheet.AlienBG);
						downLinesSprite.setSheet(SpriteSheet.AlienBG);
						upLinesSprite.setSheet(SpriteSheet.AlienBG);
					}
					else if(nextWorld == 2)
					{
						bgSprite.setSheet(SpriteSheet.NukeBG);
						downLinesSprite.setSheet(SpriteSheet.NukeBG);
						upLinesSprite.setSheet(SpriteSheet.NukeBG);
					}
					else if(nextWorld == 3)
					{
						bgSprite.setSheet(SpriteSheet.CometBG);
						downLinesSprite.setSheet(SpriteSheet.CometBG);
						upLinesSprite.setSheet(SpriteSheet.CometBG);
					}
					else if(nextWorld == 4)
					{
						bgSprite.setSheet(SpriteSheet.TakeoverBG);
						downLinesSprite.setSheet(SpriteSheet.TakeoverBG);
						upLinesSprite.setSheet(SpriteSheet.TakeoverBG);
					}
					else if(nextWorld == 5)
					{
						bgSprite.setSheet(SpriteSheet.SunBG);
						downLinesSprite.setSheet(SpriteSheet.SunBG);
						upLinesSprite.setSheet(SpriteSheet.SunBG);
					}
					else if(nextWorld == 6)
					{
						bgSprite.setSheet(SpriteSheet.WatchBG);
						downLinesSprite.setSheet(SpriteSheet.WatchBG);
						upLinesSprite.setSheet(SpriteSheet.WatchBG);
					}
					nextWorld = 0;
				}
			}
			
			scanTimer += frameLength * 100;
			if(scanTimer >= 76)
				scanTimer %= 76;
			downLinesBox.move(0, (float)(-76+scanTimer));
			upLinesBox.move(0, (float)(-scanTimer));
			
			updateBlinkingLeters(textSprites, frameLength);
			
			bgSprite.draw();
			for(int x = 0; x < TEXT_WIDTH; x++)
			{
				for(int y = 0; y < TEXT_HEIGHT; y++)
				{
					textSprites[x][y].draw();
				}
			}
			upLinesSprite.draw();
			downLinesSprite.draw();
			
			Display.sync(60);
			SoundStore.get().poll(0);
		}
		Display.destroy();
		AL.destroy();
	}

	private void startInput(int x, int y, int maxLen)
	{
		input.isActive = true;
		input.text = "";
		input.X = x;
		input.Y = y;
		input.maxlength = maxLen;
		input.cursor = new Pos(x, y);
	}

	private void updateBlinkingLeters(Sprite[][] textSprites, double frameLength)
	{
		blinkTimer += frameLength;
		blinkTimer %= 1;
		if(blinkTimer < 0.7)
		{
			for(int x = 0; x < TEXT_WIDTH; x++)
			{
				for(int y = 0; y < TEXT_HEIGHT; y++)
				{
					if(blinkingLetters[x][y] != null)
						textSprites[x][y].setID(blinkingLetters[x][y]);
				}
			}
		}
		else
		{
			for(int x = 0; x < TEXT_WIDTH; x++)
			{
				for(int y = 0; y < TEXT_HEIGHT; y++)
				{
					if(blinkingLetters[x][y] != null)
						textSprites[x][y].setID(" ");
				}
			}
		}
		if(blinkTimer < 0.5)
		{
			if(input.isActive)
				textSprites[input.cursor.x][input.cursor.y].setID("_");
		}
		else
		{
			if(input.isActive)
				textSprites[input.cursor.x][input.cursor.y].setID(" ");
		}
	}

	private void processKey(int key, boolean state, Sprite[][] textSprites)
	{
		if(input.isActive && state)
		{
			unprintString(input.X, input.Y, input.text, textSprites);
			textSprites[input.cursor.x][input.cursor.y].setID(" ");
			if(key == Keyboard.KEY_RETURN && input.isActive)
			{
				if(!input.text.trim().equals(""))
				{
					input.isActive = false;
					input.cursor = printString(input.X, input.Y, input.text, textSprites);
					String prompt = Gameplay.lookUpPrompt(input.text);
//					Pos loc = printString(2, 1, input.text, textSprites);
					Pos loc = printString(0, input.cursor.y+2, prompt, textSprites);
					if(nextWorld == 0)
					{
						startInput(2, loc.y+2, 50);
						printString(0, loc.y+2, ">", textSprites);
					}
				}
			}
			else if(key == 14) // backspace
			{
				if(input.text.length() > 0)
					input.text = input.text.substring(0, input.text.length()-1);
			}
			else if(Character.isLetterOrDigit(Keyboard.getEventCharacter()) || key == 57)
			{
				if(input.text.length() < input.maxlength)
					input.text += Keyboard.getEventCharacter();
			}
			if(input.isActive)
				input.cursor = printString(input.X, input.Y, input.text, textSprites);
		}
		else if(onStartScreen && state)
		{
			jingle.playAsSoundEffect(1.0F, 1.0F, false);
			onStartScreen = false;
			clearScreen(textSprites);
			String prompt = Gameplay.lookUpPrompt("");
			Pos loc = printString(0, 1, prompt, textSprites);
			if(nextWorld == 0)
			{
				printString(0, loc.y+2, ">", textSprites);
				startInput(2, loc.y+2, 50);
			}
		}
		else if(nextWorld != 0 && state)
		{
			onStartScreen = true;
			clearScreen(textSprites);
			printString(18, 13, "THE ENDS OF THE EARTH", textSprites);
			if(nextWorld == 6)
			{
				printString(22, 15, "[      PANIC]", textSprites);
				printBlinkingString(29, 15, "PANIC");
				printString(TEXT_WIDTH-21, TEXT_HEIGHT-2, "Thanks for playing!", textSprites);
				onStartScreen = false;
				nextWorld = 0;
			}
			else
			{
				printString(22, 15, "[DON'T PANIC]", textSprites);
				printBlinkingString(23, 15, "DON'T PANIC");
			}
		}
	}

	private void clearScreen(Sprite[][] textSprites)
	{
		for(int x = 0; x < TEXT_WIDTH; x++)
		{
			for(int y = 0; y < TEXT_HEIGHT; y++)
			{
				textSprites[x][y].setID(" ");
				blinkingLetters[x][y] = null;
			}
		}
	}

	private void unprintString(int x, int y, String text, Sprite[][] textSprites)
	{
		while(y >= TEXT_HEIGHT)
		{
			--y;
			scrollText(textSprites);
		}
		String[] words = (text.toUpperCase()+" \0").split(" ");
		for(String word : words)
		{
			if(word.equals("\0"))
				continue;
			if(x + word.length() > TEXT_WIDTH)
			{
				++y;
				x = 0;
				while(y >= TEXT_HEIGHT)
				{
					--y;
					scrollText(textSprites);
				}
			}
			for(int i = 0; i < word.length(); i++)
			{
				if(word.charAt(i) == '\n')
				{
					x = -i-1;
					++y;
					while(y >= TEXT_HEIGHT)
					{
						--y;
						scrollText(textSprites);
					}
				}
				else
				{
					textSprites[x+i][y].setID(" ");
					blinkingLetters[x+i][y] = null;
				}
			}
			x += word.length()+1;
			if(x >= TEXT_WIDTH)
			{
				x = 0;
				++y;
				while(y >= TEXT_HEIGHT)
				{
					--y;
					scrollText(textSprites);
				}
			}
			if(blinkingLetters[x][y] != null)
			{
				textSprites[x][y].setID(" ");
				blinkingLetters[x][y] = null;
			}
		}
	}

	private void scrollText(Sprite[][] textSprites)
	{
		--input.Y;
		--input.cursor.y;
		for(int x = 0; x < TEXT_WIDTH; x++)
		{
			for(int y = 1; y < TEXT_HEIGHT; y++)
			{
				textSprites[x][y-1].setID(textSprites[x][y].getID());
				blinkingLetters[x][y-1] = blinkingLetters[x][y];
			}
			textSprites[x][TEXT_HEIGHT-1].setID(" ");
			blinkingLetters[x][TEXT_HEIGHT-1] = null;
		}
	}

	private Pos printString(int x, int y, String text, Sprite[][] textSprites)
	{
		while(y >= TEXT_HEIGHT)
		{
			--y;
			scrollText(textSprites);
		}
		String[] words = (text.toUpperCase()+" \0").split(" ");
		for(String word : words)
		{
			if(word.equals("\0"))
				continue;
			if(x + word.length() > TEXT_WIDTH)
			{
				++y;
				x = 0;
				while(y >= TEXT_HEIGHT)
				{
					--y;
					scrollText(textSprites);
				}
			}
			for(int i = 0; i < word.length(); i++)
			{
				if(word.charAt(i) == '\n')
				{
					x = -i-1;
					++y;
					while(y >= TEXT_HEIGHT)
					{
						--y;
						scrollText(textSprites);
					}
				}
				else
				{
					textSprites[x+i][y].setID(word.substring(i, i+1));
					blinkingLetters[x+i][y] = null;
				}
			}
			x += word.length()+1;
			if(x >= TEXT_WIDTH)
			{
				x = 0;
				++y;
				while(y >= TEXT_HEIGHT)
				{
					--y;
					scrollText(textSprites);
				}
			}
			if(blinkingLetters[x][y] != null)
			{
				textSprites[x][y].setID(" ");
				blinkingLetters[x][y] = null;
			}
		}
		--x;
		if(x == -1)
			x = 0;
		return new Pos(x, y);
	}

	private void printBlinkingString(int x, int y, String text)
	{
		String[] words = text.toUpperCase().split(" ");
		for(String word : words)
		{
			if(x + word.length() > TEXT_WIDTH)
			{
				++y;
				x = 0;
				while(y >= TEXT_HEIGHT)
				{
					--y;
				}
			}
			for(int i = 0; i < word.length(); i++)
			{
				if(word.charAt(i) == '\n')
				{
					x = -i-1;
					++y;
					while(y >= TEXT_HEIGHT)
					{
						--y;
					}
				}
				else
					blinkingLetters[x+i][y] = word.substring(i, i+1);
			}
			x += word.length()+1;
		}
	}
	
	public static void main(String[] args)
	{
		Main instance = new Main();
		instance.gameloop();
	}

}
