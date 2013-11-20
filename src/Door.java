import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class Door extends GImage {


public int currentDoor;

private String Door1 = "door 1.png";

private String Door2 = "door 2.png";

private String Door3 = "door 3.png";

private double SCALE = 1.3;


	public Door(){
		
		super("door 1.png");
		
		currentDoor = 1;
		
		super.scale(SCALE);
	
	}
	
	public void openDoor(){
		
		//opens door
		
		if(currentDoor == 1){
			
			currentDoor = 2;
			
			this.setImage(Door2);
			
		}else if(currentDoor == 2){
			
			currentDoor = 3;
			this.setImage(Door3);
		
		}else if(currentDoor == 3){
			
			
			currentDoor = 1;
			
			this.setImage(Door1);
			
		}
		
		super.scale(SCALE);
	}//openDoor brace
	


	
	
	

}//end brace