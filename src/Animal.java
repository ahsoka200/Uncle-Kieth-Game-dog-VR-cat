import java.awt.Image;

import acm.graphics.GImage;
import acm.util.RandomGenerator;



public class Animal extends GImage{
	
	private String leftImage;
	public String rightImage;
	private double SCALE;
	private String facing;

	public Animal(Image image) {
		super(image);
		// TODO Auto-generated constructor stub
	}
	
	public Animal(String left, String right, double scale){
		super(left);
		SCALE = scale;
		scale(SCALE);
		
		leftImage = left;
		rightImage = right;
	
		RandomGenerator rgen = RandomGenerator.getInstance();
		int rand = rgen.nextInt(0, 2);
		
		if(rand == 0){
			facing = "left";
		}else{
			this.faceWay("right");
		}
	
	}
	
	public void faceWay(String direction){
		
		if(direction == "left"){
			this.setImage(leftImage);
			super.scale(SCALE);
			facing = "left";
			
		}else{
			this.setImage(rightImage);
			super.scale(SCALE);
			facing = "right";
		}
		
		
	}
	
	public void moveWayAnimalIsFacing(double distance){
		
		if(facing=="left"){
			
			move(-distance,0);
			
		}else{
			
			move(distance,0);
		}
		
	}
	
	
	
}