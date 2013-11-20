import java.awt.Image;

import acm.graphics.GImage;

public class MyCat extends GImage{

	private double SCALE = .20;
	private String CAT_LEFT = "cat left.png";
	private String CAT_RIGHT = "cat right.png";
	private String facing;
	private static int PAUSE = 10;
	private String jumping = "no";
	private int MAX_JUMP = 60;
	private int CAT_GROUND = 255;
	private int catJumpSpeed = 8;
	
	public MyCat(Image image) {
		super(image);
		// TODO Auto-generated constructor stub
	}
	
	public MyCat(String string) {
		// TODO Auto-generated constructor stub
		super(string);
		super.scale(SCALE);
	}

	
	
	public void faceWay(String direction){
		
		if(direction == "left"){
			this.setImage(CAT_LEFT);
			super.scale(SCALE);
			facing = "left";

		}else{
			this.setImage(CAT_RIGHT);
			super.scale(SCALE);
			facing = "right";
		}
	}
		
	public void moveWayCatIsFacing(double distance){
		
		
		if(facing=="left"){
			
			move(-distance, 0);
			
		}else{
			
			move(distance,0);
		}
		
	}
	
	public void processJumpMove(){
		double yMove;
		
		if(jumping =="no"){

			yMove = 0;
			
		}else if(jumping == "up"){
			
			yMove = -catJumpSpeed;
		}else{
			
			yMove = catJumpSpeed;
			
		}
		
		move(0, yMove);
		
		if(jumping == "up" && getY() <= MAX_JUMP){
			
			jumping = "down";
		}
		if(jumping == "down" && getY() >= CAT_GROUND){
			
			jumping = "no";
		}
	
	}
	
	public void catJump(){
		
		if(jumping == "no"){
			
			jumping = "up";
		}
		
		
		
		
	}//catJump brace
	
	
	
	
	
	
	
	
}//end brace
