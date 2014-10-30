package com.sorazodia.filereader;

import java.io.FileReader;
import java.util.Scanner;

import com.sorazodia.antimation.Image;
import com.sorazodia.antimation.SoundEffect;
import com.sorazodia.antimation.TextDisplay;
import com.sorazodia.main.AntimationEngine;

/**
 * Class that reads the file and send the info into the Image class
 * @author SoraZodia
 *
 */
public class FileParser {
  public Scanner reader;
  private Image image;
  private float x;
  private float y;
  private float time;
  private float degree;
  private float size;
  private short push;
  private boolean play = true;
  private static String soundName;
  private static String backgroundName;
  private static String text;
  private String name;
  
  //Constructor 
  public FileParser(String fileName, String imageName, String soundName, int x, int y) throws java.io.IOException{
	  reader = new Scanner(new FileReader(fileName));
	  image = new Image(imageName, soundName, x, y);
	  name = fileName;
  }
  
  /**
   * Reads the file and send to Image to interpret
   */
  public void read(){
	  if(reader.hasNext() && !image.getMove()){
		  String command = reader.next();
		  switch(command.toLowerCase()){
		  case "move":
			  x = reader.nextFloat();
			  y = reader.nextFloat();
			  time = reader.nextFloat();
			  image.setInterpolTran(x, y, time);
			  System.out.printf("[%s Log] Calling command: %s to X:%s Y:%s in %s secord \n", name, command,x,y,time);
			  break;

		  case "rotate":
			  degree = reader.nextFloat();
			  time = reader.nextInt();
			  image.setInterpolSpin(degree, time);
			  System.out.printf("[%s Log] Calling command: %s to %s degree in %s secord \n", name, command,degree,time);
			  break;

		  case "resize":
			  size = reader.nextFloat();
			  time = reader.nextInt();
			  image.setInterpolScale(size, time);
			  System.out.printf("[%s Log] Calling command: %s to %s pixel in %s secord \n", name, command,size,time);
			  break;

		  case "hide":
			  image.hide();
			  System.out.printf("[%s Log] Calling command: %s image \n", name, command);
			  break;

		  case "show":
			  image.show();
			  System.out.printf("[%s Log] Calling command: %s image \n", name, command);
			  break;
			 
		  case "addsound":
			  soundName = reader.next();
			  SoundEffect.setPlay(soundName);
			  System.out.printf("[%s Log] Calling command: %s, %s \n", name, command, soundName);
			  break;
			  
		  case "loopsound":
			  SoundEffect.loop();
			  System.out.printf("[%s Log] Calling command: %s, %s \n", name, command, soundName);
			  break;
			  
		  case "stopsound":
			  SoundEffect.stop();
			  System.out.printf("[%s Log] Calling command: %s, %s \n", name, command, soundName);
			  break;
			  
		  case "playsound":
			  SoundEffect.play();
			  System.out.printf("[%s Log] Calling command: %s, %s \n", name, command, soundName);
			  break;
			  
		  //Just a filler command to help with the different actor timing, 
		  //It can be used as a delay
		  case "wait":
			  System.out.printf("[%s Log] Calling command: %s \n", name, command);
			  break;
			  
		  case "background":
			  backgroundName = reader.next();
			  Image.addBackground(backgroundName);
			  System.out.printf("[%s Log] Adding background: %s \n", name, backgroundName);
			  break;
			  
		  case "layerdownbackground":
			  push = reader.nextShort();
			  Image.layerDownBackground(push);
			  System.out.printf("[%s Log] Pushing background %s down %s layer \n", name, backgroundName, push);
			  break;
			  
		  case "layerupbackground":
			  push = reader.nextShort();
			  Image.layerUpBackground(push);
			  System.out.printf("[%s Log] Pushing background %s up %s layer \n", name, backgroundName, push);
			  break;
			  
		  case "hidebackground":
			  Image.hideBackground();
			  System.out.printf("[%s Log] Hiding background: %s \n", name, backgroundName);
			  break;
			  
		  case "showbackground":
			  Image.showBackground();
			  System.out.printf("[%s Log] Showing background: %s \n", name, backgroundName);
			  break;

		  case "resizebackground":
			  double scale = reader.nextDouble();
			  Image.resizeBackground(scale);
			  System.out.printf("[%s Log] Rescaling background %s to %s\n", name, backgroundName, scale);
			  break;
			  
		  case "addtext":
			  x = reader.nextFloat();
			  y = reader.nextFloat();
			  int size = reader.nextInt();
			  text = reader.nextLine();
			  TextDisplay.addText(text, (int)x, (int)y, size);
			  System.out.printf("[%s Log] Adding text -%s-, sized %s, at X: %s and Y: %s \n", name, text, size, x, y);
			  break;
			  
		  case "hidetext":
			  TextDisplay.hideText();
			  System.out.printf("[%s Log] Hiding text -%s- \n", name, text);
			  break;
			  
		  case "showtext":
			  TextDisplay.showText();
			  System.out.printf("[%s Log] Showing text -%s- \n", name, text);
			  break;
			    
		  case "exit":
			  AntimationEngine.stop();
			  System.out.printf("[%s Log] Closing \n", name);
			  break;
			  
		  // Cause the parser to skip the line // is found in
		  case "//":
			  reader.nextLine();
			  break;
			  
		  default:
			  System.out.printf("[%s Log] ERROR: Unable To Parse Command, Double Check Your Code \n", name);
			  break;
		  }	 
		  
		 
	  }
	  
  }
  
  /**
   * Checks if FileReader has any more lines
   * @return play
   */
  public boolean getPlay(){
	  return play;
  }
  
  /**
   * Makes the images dance
   */
  public void go(){
	  image.go();
  }
  
  /**
   * Checks if the image is in reading or moving mode
   * @return image.getMove()
   */
  public boolean isMoving(){
	  return image.getMove();
  }
 
}
