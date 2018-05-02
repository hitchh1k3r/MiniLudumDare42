package com.hitchh1k3rsguide.minild42;

import java.util.ArrayList;
import java.util.List;

public class Gameplay {

	private static int worldId = 0;
	private static int locationId = 0;
	private static String lastInput = "";
	private static List<String> inventory = new ArrayList<String>();
	private static int zombieCounter = 0;

	public static String lookUpPrompt(String input)
	{
		String context = lastInput;
		lastInput = "";
		input = input.toLowerCase().trim();
		if(input.startsWith("try to"))
			input = input.substring(6).trim();
		if(worldId == 0)
		{ // zombie invasion
			++zombieCounter;
			if(zombieCounter == 30)
				return "beep* You were about to do something when your concentration is broken by your digital watch's beeping. What was it you were about to do again?";
			if(zombieCounter == 35)
				return "BEEP BEEEEEEP* You were about to do something when your concentration is broken by your digital watch's beeping. It's getting louder and louder, you find it hard to focus on anything. What was it you were about to do again?";
			if(zombieCounter == 40)
			{
				Main.nextWorld = 1;
				worldId = 1;
				locationId = 0;
				inventory.clear();
				return "Beep, beep, beeeeeeep* that is the last thing you mind registers before you turn into a mindless slave to technology. Few people expected digital watches to have so much pent up aggression, in fact most people thought they were a pretty neat idea. And in fact only one person knew of the impending doom of the human race. That person was you, because I warned you at the beginning of this story, but you were to busy playing with your computer to notice the world end around you. Perhaps another story would suit you better*";
			}
			if(locationId == 0) // wake up
			{
				inventory.add("a bathrobe");
				inventory.add("the Hitch Hiker's Guide to Europe");
				locationId = 1;
				return "This is a story about a man named Douglas, he woke up one day like he did most days: in the middle of a field. Today was no normal day though, today was his 26th birthday, which incidentally is the number of minutes before he would learn of Earth's impending destruction. He sat up, looked around, and fell back into the grass. His head was throbbing.\nYou are Douglas, what do you do?";
			}
			if(locationId == 1) // in field
			{
				if(((input.startsWith("look") || input.startsWith("check") || context.equals("look at that")) && (input.contains("around") || input.contains("everything") || input.contains("anything"))))
					return "Looking around you see you are lying in a field of shimmering grass. The sun is very bright, it hurts your eyes.";
				else if(input.startsWith("lie") || input.startsWith("lay") || input.startsWith("stay"))
					return "You stay where you are for a while, and eventually get bored. What do you do now?";
				else if(((input.startsWith("look") || input.startsWith("check") || context.equals("look at that")) && (input.contains("ground") || input.contains("grass") || input.contains("field"))))
					return "You're surrounded by grass* all in all a pretty normal morning for you.";
				else if(inventory.contains("a bottle of medicine") && ((context.equals("open that") || context.equals("take that") || context.equals("use that") || input.startsWith("get") || input.startsWith("open") || input.startsWith("take") || input.startsWith("eat") || input.startsWith("use")) && (input.contains("meds") || input.contains("medicine") || input.contains("pill") || input.contains("bottle"))))
				{
					locationId = 2;
					inventory.remove("a bottle of medicine");
					inventory.add("an empty bottle");
					return "You take the last two pills from the bottle and swallow them with ease, this isn't the first time you woke  up in the middle of a field after a long night of drinking after all. A moment passes and your head stops spinning. You slowly stand up, and managing to keep the ground under your feet feels like a monumental accomplishment. Surely this new vantage point will offer you new opportunities.";
				}
				else if(input.startsWith("move") || input.startsWith("get") || input.startsWith("go") || input.startsWith("run"))
					return "You try to get up, but immediately fall over. You hear something in your pocket rattle.";
			}
			else if(locationId == 2) // in field (taken meds)
			{
				if(((input.startsWith("look") || input.startsWith("check") || context.equals("look at that")) && (input.contains("around") || input.contains("everything") || input.contains("anything") || input.contains("grass"))))
					return "Looking around the field you see grass* and grass* a bit more grass* and a manuscript that must have fallen out of your pocket the night before.";
				else if(((input.startsWith("check") || input.startsWith("get") || input.startsWith("take") || input.startsWith("look") || context.equals("look at that") || context.equals("take that")) && input.contains("script")))
				{
					inventory.add("a radio script");
					locationId = 3;
					return "You pick up the radio script you've been working on, \"The Ends of the Earth\" it is called, 6 plots about the destruction of the planet. Before you get a chance to thumb through it there is a rumble coming from over a hill. You see a helicopter pass over head it's PA blaring, \"*panic, there has been a slight zombie outbreak, it is advised you stay locked indoors. If you feel yourself turning we offer the following advice: try to stop turning into a zombie. Our top scientists were at a party last night, but as soon as they wake up I'm sure they will find a solution, until then take shelter, and stay safe. Again. Attention citizens, don't panic, there has...\" As the voice fades into the distance you get an uneasy feeling, surely someone read your script and is playing a joke on you. The first of your stories started this way*";
				}
			}
			else if(locationId == 3) // in field (has script)
			{
				if(((input.startsWith("look") || input.startsWith("check") || context.equals("look at that")) && (input.contains("around") || input.contains("everything") || input.contains("anything"))))
					return "You are still in the grassy field you woke up in, the helicopter went over a hill to one side of the field.";
				else if((input.startsWith("move") || input.startsWith("go") || input.startsWith("run") || input.startsWith("follow")) && (input.contains("copter") || input.contains("voice") || input.contains("person")))
				{
					inventory.add("a digital watch");
					locationId = 4;
					zombieCounter = 30;
					return "You manage to catch up to the helicopter as it slowly passes over the field and into the city, if this is a joke* well it couldn't be. The city is in ruins, and men and women walk around with blank expressions, attacking everything they see. You decided it's best to stay outside of the city for now. The sound of there legs and fists kicking and punching isn't washed out by their moaning like one might expect from this sort of thing, but instead from the beeping of their digital watches - the sound of your digital watch.";
				}
			}
			else if(locationId == 4) // by zombie city
			{
				if(((input.startsWith("look") || input.startsWith("check") || context.equals("look at that")) && (input.contains("around") || input.contains("everything") || input.contains("anything"))))
					return "Zombies go up and down the streets. You try to stay out of their sight, they seem very violent.";
				else if(input.startsWith("call") || input.startsWith("scream") || input.startsWith("cry") || input.startsWith("say") || input.startsWith("tell"))
				{
					if(zombieCounter < 35)
						zombieCounter = 35;
					return "One of the zombies hears you and starts heading toward you. You're frozen with fear. Suddenly it's watch beeps and the zombies expression turns blank again, it seems to have forgotten about you, for now.";
				}
			}
			if((input.contains("take off") || input.contains("remove") || input.contains("unequip") || input.contains("open") || context.equals("open that")) && (input.contains("robe") || input.contains("cloth")))
				return "You're not in the mood for streaking right now*";
			else if(((input.startsWith("look") || input.startsWith("check") || context.equals("look at that")) && (input.contains("robe") || input.contains("cloth"))))
				return "It's a nice bathrobe, very comfy. It has a large pocket stiched into it.";
			else if(!inventory.contains("a bottle of medicine") && ((input.startsWith("take") || input.startsWith("get") || input.startsWith("open") || input.startsWith("look") || input.startsWith("check") || (input.startsWith("what") && input.contains("in")) || context.equals("look at that") || context.equals("open that") || context.equals("take that")) && input.contains("pocket")))
			{
				inventory.add("a bottle of medicine");
				return "You reach into your pocket and pull out a bottle of medicine.";
			}
			else if(inventory.contains("a bottle of medicine") && ((input.startsWith("take") || input.startsWith("get") || input.startsWith("open") || input.startsWith("look") || input.startsWith("check") || (input.startsWith("what") && input.contains("in")) || context.equals("look at that") || context.equals("open that") || context.equals("take that")) && input.contains("pocket")))
				return "Your pocket is empty.";
			else if(inventory.contains("a bottle of medicine") && ((context.equals("read that") || input.startsWith("look") || input.startsWith("check") || input.startsWith("read") || context.equals("look at that")) && (input.contains("meds") || input.contains("medicine") || input.contains("pill") || input.contains("bottle"))))
				return "It's a bottle of pain-pills.";
			else if(((context.equals("read that") || input.startsWith("read") || input.startsWith("use") || context.equals("use that"))) && (input.contains("guide")))
				return "You aren't in the mood to read it right now.";
			else if(((input.startsWith("look") || input.startsWith("check") || context.equals("look at that")) && (input.contains("guide"))))
				return "It's battered, you borrowed it from someone.";
			else if(inventory.contains("a radio script") && ((context.equals("read that") || input.startsWith("look") || input.startsWith("check") || input.startsWith("read") || context.equals("look at that")) && (input.contains("script"))))
				return "It's a script for the first of six radio shows about the world ending. You'd read through it, but you'd rather get to the bottom of this helicopter business.";
			else if(((input.startsWith("look") || input.startsWith("use") || context.equals("look at that") || context.equals("use that") || input.startsWith("check")) && (input.contains("watch") || input.contains("time") || input.contains("clock"))))
				return "You check your digital watch, but can't make out the numbers on it - it's blinking, and beeping too.";
			else if(((input.startsWith("stop") || input.startsWith("break") || input.startsWith("smash") || input.startsWith("stomp") || (input.contains("turn") && input.contains("off"))) && (input.contains("watch"))))
				return "You try and try, but nothing you do will get the watch to stop beeping.";
			else if(((input.startsWith("look") || input.startsWith("check") || context.equals("look at that")) && (input.contains("grass"))))
				return "It looks like normal grass.";
			else if(input.startsWith("move") || input.startsWith("go") || input.startsWith("run"))
				return "You aren't sure where you are, and don't know how to get there.";
			else if((input.contains("take off") || input.contains("remove") || input.contains("unequip")) && input.contains("watch"))
			{
				Main.nextWorld = 1;
				worldId = 1;
				locationId = 0;
				inventory.clear();
				return "You remove your digital watch and suddenly the world becomes clearer. You hadn't been out drinking at all. The watch was infecting your brain, turning you into a mindless zombie, one beep at a time. Thanks to taking it off you have spared yourself this fate, but it is too late for the rest of humanity.\nThe end of Earth is upon us*";
			}
			--zombieCounter;
		}
		else if(worldId == 1)
		{ // aliens
			if(locationId == 0) // intro
			{
				inventory.add("a digital watch");
				inventory.add("a cup of tea");
				locationId = 1;
				return "This is a story about a man named Douglas. He was special in that he was one of very few people who had never been abducted and studied by extra-terrestrials. This of course had nothing to do with Douglas, who is in every other way a rather unremarkable person. This story isn't even about abductions, but rather another type of aliens. Ones who invade planets for sport, or something. As he sat in his office Douglas had no idea these violent types aliens were nearing Earth, how would be. He just sat in his office sipping his tea.\nYou are Douglas, what do you do?";
			}
			if(locationId == 1) // office
			{
				if(((input.startsWith("look") || input.startsWith("check") || context.equals("look at that")) && (input.contains("around") || input.contains("everything") || input.contains("anything"))))
					return "You glance around your office. It's a nice office, as far as writer's offices go. You have a large desk with a type writer and dusty computer on it. You have a large window looking over the city. And a fairly standard wooden door that leads to the hallway.";
				if(((input.startsWith("look") || input.startsWith("check") || input.startsWith("use") || input.startsWith("open") || context.equals("look at that") || context.equals("use that") || context.equals("open that")) && (input.contains("window"))))
					return "You look out of your window at the city bellow. Normally you would think of the people as little ants running around, but oddly you don't see anyone today.";
				if(((input.startsWith("jump") && input.contains("window"))))
					return "It's a long way down, and you'd really rather not.";
				if(((input.startsWith("look") || input.startsWith("check") || input.startsWith("use") || input.startsWith("open") || context.equals("look at that") || context.equals("use that") || context.equals("open that")) && (input.contains("desk"))))
				{
					if(inventory.contains("the closet key"))
						return "It's a nice desk, made of solid wood.";
					else
					{
						inventory.add("the closet key");
						return "It's a nice desk, made of solid wood. You take the closet key from the top drawer.";
					}
				}
				if((input.startsWith("move") || input.startsWith("go") || input.startsWith("enter") || input.startsWith("run") || input.startsWith("open") || context.equals("use that") || context.equals("open that")) && (input.contains("door") || input.contains("hall")))
				{
					locationId = 2;
					return "You open your office door and go out into the hallway, you find it odd that no one is in the building on a weekday. There must be a party in the room at the end of the hall. Why don't you get the party hats out of the closet and then go join them.";
				}
			}
			if(locationId == 2) // hallway
			{
				if(((input.startsWith("look") || input.startsWith("check") || context.equals("look at that")) && (input.contains("around") || input.contains("everything") || input.contains("anything"))))
					return "The hallway connects all the offices, on one end there is a closet, on the other a room that the publisher often throws parties in. There will be surely be cake, but no one ever remembers the party hats. Your office is along the left side of the hallway.";
				if(!inventory.contains("the closet key") && ((input.startsWith("look") || input.startsWith("check") || input.startsWith("go") || input.startsWith("enter") || input.startsWith("use") || input.startsWith("open") || context.equals("look at that") || context.equals("use that") || context.equals("open that") || context.equals("find that one")) && (input.contains("closet"))))
					return "The closet is locked, you keep a key to it in your desk.";
				if(((input.startsWith("look") || input.startsWith("check") || input.startsWith("use") || input.startsWith("open") || input.startsWith("go") || input.startsWith("enter") || context.equals("look at that") || context.equals("use that") || context.equals("open that") || context.equals("find that one")) && (input.contains("party") || input.contains("room") || input.contains("cake"))))
				{
					if(!inventory.contains("party hats"))
						return "You would go in there now, but wouldn't it be better if you brought everyone party hats? They are in the closet at the end of the hallway.";
					else
					{
						Main.nextWorld = 2;
						worldId = 2;
						locationId = 0;
						inventory.clear();
						return "You enter the room excited to bring party hats, and even more excited about the cake prospect. You quickly learn that the cake was a short lived falsehood. In the room are three tall silver-gray humanoid figures, standing with plastic knives cutting, not cake, but your employer. The Aliens quickly subdue you and strap you to a table next to your boss. This has been happening all over the world, in fact Douglas was the last human, if only he could have escaped with a good friend before the invasion. These aliens are vicious, but it's for science, they aren't monsters.";
					}
				}
				if(inventory.contains("the closet key") && ((input.startsWith("look") || input.startsWith("go") || input.startsWith("enter") || input.startsWith("check") || input.startsWith("use") || input.startsWith("open") || context.equals("look at that") || context.equals("use that") || context.equals("open that") || context.equals("find that one")) && (input.contains("closet"))))
				{
					if(inventory.contains("party hats"))
						return "You already have the party hats.";
					else
					{
						inventory.add("party hats");
						return "You unlock the closet and take out the party hats. Now to see a publisher about a cake.";
					}
				}
				if((input.startsWith("move") || input.startsWith("go") || input.startsWith("run") || input.startsWith("open") || context.equals("use that") || context.equals("open that")) && (input.contains("door")))
				{
					lastInput = "find that one";
					return "Do you mean your office, the party room, or the closet?";
				}
				if((input.startsWith("move") || input.startsWith("go") || input.startsWith("enter") || input.startsWith("run") || input.startsWith("open") || context.equals("find that one") || context.equals("use that") || context.equals("open that")) && (input.contains("office")))
				{
					locationId = 1;
					return "You renter your office. You better hurry the party will surely be over soon.";
				}
			}
			else if(inventory.contains("a digital watch") && (input.contains("take off") || input.contains("remove") || input.contains("unequip")) && input.contains("watch"))
			{
				inventory.remove("a digital watch");
				return "You take off your digital watch and throw it away. Don't want it turning you into a zombie, now do you?";
			}
			else if((input.startsWith("look") || input.startsWith("check") || input.startsWith("eat") || input.startsWith("drink") || input.startsWith("use") || context.equals("look at that") || context.equals("use that")) && (input.contains("tea") || input.contains("cup")))
				return "You take a sip of your tea. You feel refreshed.";
			else if(inventory.contains("the closet key") && (input.startsWith("look") || input.startsWith("check") || context.equals("look at that")) && (input.contains("key")))
				return "It's a key for the closet at the end of the hallway, where you keep the party hats.";
			else if(inventory.contains("party hats") && (input.startsWith("look") || input.startsWith("check") || context.equals("look at that")) && (input.contains("hat")))
				return "They are pointy, and have sparkels* it's not a real party without party hats.";
			else if(inventory.contains("a digital watch") && ((input.startsWith("look") || input.startsWith("use") || context.equals("look at that") || context.equals("use that") || input.startsWith("check")) && (input.contains("watch") || input.contains("time") || input.contains("clock"))))
				return "You check your digital watch, but can't make out the numbers on it - it's blinking, and beeping too.";
		}
		else if(worldId == 2)
		{ // nukes
			Main.nextWorld = 3;
			worldId = 3;
			return "This isn't a story about a man named Douglas, this is the story about Global Thermonuclear War. You see Douglas only appears in stories some might call funny, but there is nothing funny about genocide. A lot of people were prepared to survive a nuclear attack, but it didn't matter in this story for two reasons. First, even though they managed to make it to a safe bunker and survive the onslaught there is no way they could survive underground for long enough to outlive the radiations effect on the planet. Secondly, all of the people saved in the aforementioned bunkers are politicians, and as anyone can attest a politician couldn't build, rebuild, or maintain a new world if it was their job, which incidentally it isn't, but only a few people could attest to that. In fact saving politicians from a nuclear attack would be similar to bailing out banks after they caused a world wide finical meltdown, and really what are the odds anyone would be stupid enough to let that happen? Oh I got distracted* the Earth is Ended, or whatever the tag line is. Next story*";
		}
		else if(worldId == 3)
		{ // comets/fire rain
			Main.nextWorld = 4;
			worldId = 4;
			return "This is the story about the end of the world. It started one Saturday afternoon when everyone was minding their own business. This was a rare occurrence for the humble little planet Earth, it would have been nice if it could have lasted for a while* too bad molten stone would soon end the world as it has been known for the insignificant length of times it's current dominant species has relatively apathetically live on it's surface leaching it's plentiful resources much faster then they learned to produce their own. It's been know for a long time this couldn't continue and would end with the miserable end to these parasite, but it was for a long time thought it would end with a whimper, not a bang. Once the ape descendant life forms ran out of fossilized ancestors to shove in their cars they turned to new sources of fuels and in doing so cause large amounts of Carbon Di-oxide to enter under the Earths crust, building and building it eventually caused a reaction much the same as shaking a bottle of a carbonated beverage. What's the saying again? Oh yea* pop goes the planet.";
		}
		else if(worldId == 4)
		{ // take over/robot revolution
			Main.nextWorld = 5;
			worldId = 5;
			return "This is the story about when the all life on Earth was extinguished by the very robotic companions it's primitive voyagers had created. More specifically this is the story of two of those voyager's last moments. \"Do you ever wonder what it's all about?\" said Arthur. \"No,\" replied a somewhat annoyed Douglas. \"But the universe is so big, mind bogglingly huge in fact. It seems to me that all life as we know it could be over in the blink of an eye. It really makes you wonder.\" Douglas thought about this for a moment, and finally replied, \"Does it now? What I'm really wondering is how the game is coming along.\" Douglas had hired Arthur to program his new idea, \"The Ends of the Earth\" as a text adventure. \"About that,\" said Arthur, \"I see you want me to add not only a form of natural language processing, but also a scene with dialogue. Do you have any idea how ambitious that is?\" \"I'm sure we can think of some way to include the dialogue without making the program too complex,\" said Douglas. To this Arthur replied, \"Right. Oh and by the way, have you figured out the fifth ending yet?\" Douglas had, but he wouldn't get a chance to tell Arthur because it was at that moment that a disgruntled toaster hopped into the room and bludgeoned them both over the head repeatedly. The end*";
		}
		else if(worldId == 5)
		{ // global warming/ending
			Main.nextWorld = 6;
			worldId = 0;
			return "Oh yea, I almost forgot there are six endings. It has been widely speculated that the first five endings of this game are not very likely at all, possibly with the exception of Digital Watches turning all humans into mindless zombies. This of course wasn't the fault of the game's maker, who was over worked, under payed, and forced to make the entire thing in a very short stretch of time. No, this was because of science. Non of the endings had a scientific enough ending, this is science-fiction after all. It was at this moment that the final ending of the game took shape, empirically the most likely way for the world too end, at least for the human component of it, is of course events of extreme weather, caused by climate change. And while people can argue over it's existence, or potential human causes until they are all dead, this is not a productive method of dealing with it. Not that at this point there is much hope for effecting change, but the consequences can be weighted and disaster preparedness can be raised. Perhaps if you stop playing this game right now, and go plan, some of the nasty effects can be mitigated, but look around you and you will truly see, The Ends of the Earth.";
		}
		if(input.equals("help"))
			return "You will have to be more specific.";
		if(input.startsWith("help me"))
			return "Help yourself? That seems like a mighty selfish thing to do.";
		if(input.startsWith("help"))
			return "I'm not sure what that is, or else I'd let you help it.";
		if(input.startsWith("please help"))
			return "Well since you said please* sorry I really can't help you. Try typing different things, you will figure it out eventually.";
		if(input.startsWith("call") || input.startsWith("scream") || input.startsWith("cry"))
			return "You call out until your lungs are sore, no one comes to your aid.";
		if(((input.startsWith("look") || input.startsWith("check") || context.equals("look at that")) && (input.contains("clothes") || input.contains("clothing"))))
			return "You aren't wearing any clothes.";
		if(input.startsWith("tell me "))
		{
			String[] bits = input.split(" ", 3);
			return "You tell yourself "+bits[2]+", it doesn't help much.";
		}
		if(input.startsWith("say") || input.startsWith("tell"))
			return "You call out, but it's no use.";
		if(input.contains("what") && input.contains("can") && input.contains("do"))
			return "There are a lot of things you can do, just try.";
		
		if(input.contains("inventory") || input.contains("items") || input.contains("list") || (input.contains("what") && input.contains("have")))
		{
			String string = "You have: ";
			String prefix = "";
			for(int i = 0; i < inventory.size(); i++)
			{
				string += prefix + inventory.get(i);
				prefix = ", ";
				if(i == inventory.size()-2)
					prefix = ", and ";
			}
			return string + ".";
		}
		
		if(input.startsWith("look at "))
		{
			String[] bits = input.split(" ", 3);
			return "You can't find "+bits[2]+" to look at.";
		}
		else if(input.startsWith("check"))
		{
			lastInput = "look at that";
			return "Check what?";
		}
		else if(input.startsWith("look"))
		{
			lastInput = "look at that";
			return "Look at what?";
		}
		else if(input.startsWith("open"))
		{
			lastInput = "open that";
			return "Open what?";
		}
		else if(input.startsWith("read"))
		{
			lastInput = "read that";
			return "Read what?";
		}
		else if(input.startsWith("take"))
		{
			lastInput = "take that";
			return "Take what?";
		}
		else if(input.startsWith("get"))
		{
			lastInput = "take that";
			return "Get what?";
		}
		else if(input.startsWith("eat"))
		{
			lastInput = "use that";
			return "Eat what?";
		}
		else if(input.startsWith("use"))
		{
			lastInput = "use that";
			return "Use what?";
		}
		if(!context.equals(""))
			return "You can't "+context+", try something new.";
		return "You're not sure how to do that; try someting else.";
	}

}
