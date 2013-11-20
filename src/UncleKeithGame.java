import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;


public class UncleKeithGame extends GraphicsProgram {

	//some of the println make the game run. ??? don't know how but it does. done mess with it!!!h

	public static final int APPLICATION_WIDTH = 1250;
	public static final int APPLICATION_HEIGHT = 500;


	private MyCat cat;

	private Door door;

	private Door door2;

	private int lastMouseX;

	private double speed = 5;

	private double catSpeed = 9;

	private int pauseLength = 20;

	private int groundHight = 280;

	private int doorOneX = 250;

	private int doorTwoX = 1000;

	private int doorY = 165;

	private GLabel score;

	private int SCORE;

	private GLabel healthLabel;

	private int HEALTH = 500;

	private boolean start = false;

	private GRect healthBar;

	ArrayList<MyMouse> allMice = new ArrayList<MyMouse>();

	ArrayList<MyDog> allDogs = new ArrayList<MyDog>();

	private RandomGenerator rgen = RandomGenerator.getInstance();

	private boolean crossOnScreen = false;

	private int crossTime;

	private GRect crossX;

	private GRect crossY;
	
	private GOval circle;
	
	private boolean mouseRunOnScreen = false;
	
	private int mouseRunTime;
	
	private GImage runMouse;
	
	private int mouseRunLength = 0;


	public void run(){

		setup();

		gameLoop();
		


	}//run brace

	public void setup(){

		GRect ground = new GRect(0, groundHight + 81, this.getWidth(), 300 );
		ground.setColor(Color.lightGray);
		ground.setFilled(true);
		ground.setFillColor(Color.lightGray);
		add(ground);

		door = new Door();
		add(door, doorOneX, doorY);



		door2 = new Door();
		add(door2, doorTwoX - door.getWidth(), doorY);




		cat = new MyCat("cat left.png");
		add(cat, 150, groundHight - 25);
		cat.faceWay("right");




		score = new GLabel("score: " + SCORE ,50, 440);
		score.setFont(new Font(Font.SANS_SERIF, Font.BOLD,20));
		add(score);


		healthLabel = new GLabel("health: ", 350, 440);
		healthLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD,20));
		add(healthLabel);


		GRect healthBarFrame = new GRect(427, 420, 152, 25);
		add(healthBarFrame);


		healthBar = new GRect(428, 421, 150, 23);
		healthBar.setColor(new Color(28, 186, 0));
		healthBar.setFilled(true);
		healthBar.setFillColor(new Color(28, 186, 0));
		add(healthBar);


		

		setBackground(Color.blue);
		addKeyListeners();
		addMouseListeners();


	}




	public void mouseMoved(MouseEvent mouse){

		if(start == true){

			if(mouse.getX() >= cat.getX() + cat.getWidth()/2){

				cat.faceWay("right");

			}else{

				cat.faceWay("left");

			}

			lastMouseX = mouse.getX();

		}

	}




	public void mouseClicked(MouseEvent mouse){

		start = true;

	}



	public void gameLoop(){

		int rand;
		int doorDelay =0;
		int doorDelay2 =0;

		boolean alreadyStarted = false;
		while (true){

			//necessary to get start variable to work
			if(!alreadyStarted){
				println(start);
				if(start){
					alreadyStarted = true;
				}
			}

			if(start == true){


				//make the cat move
				if(cat.getX() + cat.getWidth()/2 + catSpeed <= lastMouseX || cat.getX() + cat.getWidth()/2 - catSpeed >= lastMouseX){   
					cat.moveWayCatIsFacing(catSpeed);
				}


				//makes cat not go off screen
				if(cat.getX() <=0){

					cat.setLocation(0, cat.getY());
				}

				//makes cat not go off screen
				if(cat.getX() >= APPLICATION_WIDTH - cat.getWidth()){

					cat.setLocation(APPLICATION_WIDTH - cat.getWidth(), cat.getY());

				}



				//if door two is closed
				if(door2.currentDoor == 1){

					//sometimes open the door
					
					if(mouseRunLength > 0){
						
						rand = rgen.nextInt(0, 50);
						
					}else{
					
						rand = rgen.nextInt(0, 200);
					
					}
					
					if(rand < 1){
					
						door2.openDoor();
						doorDelay2 = 0;

						//half the time dog, half mouse comes out
						rand = rgen.nextInt(0, 2);
						if(rand < 1 || mouseRunLength >0 ){

							MyMouse mouse = new MyMouse();
							add(mouse, doorTwoX  - door.getWidth(), groundHight + 25);				   
							allMice.add(mouse);
							
						}else{
							
							MyDog dog = new MyDog();
							add(dog, doorTwoX  - door.getWidth() , groundHight);
							allDogs.add(dog);


						}



					}

				}else{
					//keep opening door
					doorDelay2++;
					if(doorDelay2 == 10){
						door2.openDoor();
						doorDelay2 = 0;
					}

				}



				//if door one is closed
				if(door.currentDoor == 1){

					//sometimes open the door
					
					if(mouseRunLength >0){
						
						rand = rgen.nextInt(0, 50);
						
					}else{
					
						rand = rgen.nextInt(0, 200);
					
					}
					if(rand < 1){
					
						door.openDoor();
						doorDelay = 0;

						//half the time dog, half mouse comes out
						rand = rgen.nextInt(0, 2);
						if(rand < 1 || mouseRunLength > 0){

							MyMouse mouse = new MyMouse();
							add(mouse, doorOneX, groundHight + 25);
							allMice.add(mouse);

						}else{

							MyDog dog = new MyDog();
							add(dog,doorOneX, groundHight);
							allDogs.add(dog);

						}




					}

				}else{
					//keep opening door
					doorDelay++;
					if(doorDelay == 10){
						door.openDoor();
						doorDelay = 0;
					}

				}




				for(int i = 0; i < allDogs.size(); i++){

					MyDog currentDog = allDogs.get(i);
					rand = rgen.nextInt(0, 100);

					if(rand < 1){
						rand = rgen.nextInt(0,2);
						if(rand == 0){

							currentDog.faceWay("left");
						}else{

							currentDog.faceWay("right");

						}
					}


					//removes dog from off screen.
					if(currentDog.getX() <= 0 - currentDog.getWidth()|| currentDog.getX() >= this.getWidth()){

						allDogs.remove(i);
						remove(currentDog);



					}

					currentDog.moveWayAnimalIsFacing(speed);

					processCollision(currentDog);

				}



				for(int w = 0; w < allMice.size(); w++){

					MyMouse currentMouse = allMice.get(w);
					rand = rgen.nextInt(0, 100);


					//makes mouse switch ways.
					if(rand < 1){
						rand = rgen.nextInt(0,2);

						if(rand == 0){

							currentMouse.faceWay("left");

						}else{

							currentMouse.faceWay("right");


						} 

					}

					//removes mouse from off screen.
					if(currentMouse.getX() <= 0 - currentMouse.getWidth() || currentMouse.getX() >= this.getWidth()){

						allMice.remove(w);
						remove(currentMouse);

					}

					currentMouse.moveWayAnimalIsFacing(speed);

					processCollision(currentMouse);

				}

				cat.processJumpMove();

				pause(pauseLength);





				//if heath = 0 game break
				if(HEALTH <= 0){

					start = false;

					remove(healthBar);
				}


				//makes cross apear on screen in a random spot.
				rand = rgen.nextInt(0, 2500);



				if(rand < 1 && crossOnScreen == false){

					rand = rgen.nextInt(21, APPLICATION_WIDTH - 61);

					redCross(rand ,280);

					crossTime = 0;

					crossOnScreen = true;

					println("New cross");
				}


				if(crossOnScreen == true){
					crossTime = crossTime + 1;

					if(crossTime % 100 == 0){
						println("cross time: "+ crossTime);
					}
					
					//check if touching cat
					
					if(cat.getX() + cat.getWidth() > crossY.getX() && cat.getX() < crossY.getX() + crossY.getWidth()

							&& cat.getY() > groundHight - 25 - crossY.getHeight() + 15){
						
						
						HEALTH = HEALTH + 100;
						healthBar.setSize(healthBar.getWidth() + 30, healthBar.getHeight());
						
						//restrics the size of the health bar so it does not go out of its box.
						//also makes health 500 if health is  in he 400's.
						if(HEALTH > 500){
							
							HEALTH = 500;
							healthBar.setSize( 150, healthBar.getHeight());
							
						
							
						}
						
						remove(crossX);
						remove(crossY);
						
						crossOnScreen = false;
						
						
					}

					
					
					if(crossTime == 300){
						

						remove(crossX);
						remove(crossY);

						crossOnScreen = false;

					}

				}

				
				
				
				
				
				//makes runMouse apear on screen in a random spot.
				
				rand = rgen.nextInt(0, 7000);



				if(rand < 1 && mouseRunOnScreen == false){

					rand = rgen.nextInt(21, APPLICATION_WIDTH - 61);

					mouseRun(rand ,280);

					mouseRunTime = 0;

					mouseRunOnScreen = true;

					println("mouseRun");
				}


				if(mouseRunOnScreen == true){
					mouseRunTime = mouseRunTime + 1;

					if(mouseRunTime % 100 == 0){
						println("mouseRun Time: "+ mouseRunTime);
					}
					
					//check if touching cat
					
					if(cat.getX() + cat.getWidth() > circle.getX() && cat.getX() < circle.getX() + circle.getWidth()

							&& cat.getY() > groundHight - 25 - circle.getHeight() + 15){
						
						
						
						
						
						remove(circle);
						remove(runMouse);
						
						mouseRunLength = 600;
						
						
						mouseRunOnScreen = false;
						
						
					}

					
					
					if(mouseRunTime == 125){
						
						

						remove(circle);
						remove(runMouse);

						mouseRunOnScreen = false;

					}
				}

				
				mouseRunLength = mouseRunLength - 1;
				
				
				
			}//start == true brace

		}//while true brace





	}






	private void processCollision(MyMouse mouse){



		if(cat.getX() + cat.getWidth() > mouse.getX() && cat.getX() < mouse.getX() + mouse.getWidth()

				&& cat.getY() > groundHight - 25 - mouse.getHeight() + 15){



			allMice.remove(mouse);
			remove(mouse);

			SCORE = SCORE + 1;

			score.setLabel("score: " + SCORE);
		}






	}



	private void processCollision(MyDog dog){



		if(cat.getX() + cat.getWidth() > dog.getX() && cat.getX() < dog.getX() + dog.getWidth()

				&& cat.getY() > groundHight - 25 - dog.getHeight() + 15){




			//makes health go down if dog has hit you.

			HEALTH = HEALTH - 1;
			healthLabel.setLabel("health: " );

			healthBar.setSize(healthBar.getWidth() - .3, healthBar.getHeight());


		}






	}



	public void keyTyped(KeyEvent e) {



		//if w or W are pressed move paddle
		if(e.getKeyChar()=='w' || e.getKeyChar()=='W'){

			cat.catJump();


		}	

	}



	private void redCross(int x, int y){

		crossX = new GRect(x, y, 20, 60);
		crossX.setColor(Color.red);
		crossX.setFilled(true);
		crossX.setFillColor(Color.red);
		add(crossX);

		crossY = new GRect(x - 20, y + 21, 60, 20);
		crossY.setColor(Color.red);
		crossY.setFilled(true);
		crossY.setFillColor(Color.red);
		add(crossY);

	}


	private void mouseRun(int x, int y){
		
		
		circle = new GOval(x, y, 75, 75);
		circle.setColor(Color.ORANGE);
		circle.setFilled(true);
		circle.setFillColor(Color.ORANGE);
		add(circle);
		
		
		runMouse = new GImage("mouse right.png", x + 1, y + 23);
		add(runMouse);
		
		runMouse.scale(.07);
		
		
	}






}//end brace