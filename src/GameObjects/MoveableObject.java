package GameObjects;

import algorithms.Point2D;

public class MoveableObject extends GameObject implements IMoveable{

	protected double velocity = 1; // default
	private double degree = 0; // default
	protected int score = 0;
	protected double eatingRadius = 10;
	private int eaten_fruits = 0;
	private int eaten_pacmans = 0;
	private int hit_times_by_ghost = 0;
	
	public MoveableObject(int id, Point2D location, double velocity) {
		super(id, location);
		this.velocity = velocity;
	}
	
	public void addToScore(int score) {
		this.score += score;
	}	
	
	public double getScore() {
		return score;
	}	
	
	public double getDegree() {
		return degree;
	}	
	
	public double getVelocity() {
		return velocity;
	}
	
	public void setEatingRadius(double rad) {
		eatingRadius = rad*10;
	}
	
	public double getEatingRadius() {
		return eatingRadius;
	}

	@Override
	public void setDegree(double deg) {
		this.degree = deg;
	}

	@Override
	public void setVelocity(double vel) {
		this.velocity = vel;
	}

	@Override
	public void increaseEatenFruits() {
		eaten_fruits++;
	}

	@Override
	public int getNumEatenFruits() {
		return eaten_fruits;
	}

	public void decreasePoints(int points) {
		score-=points;
	}

	public int getNumEatenPacmans() {
		return eaten_pacmans ;
	}
	
	public int getNumHitsByGhosts() {
		return hit_times_by_ghost;
	}
	

	public void increaseEatenObject(GameObject object) {
		if (object instanceof Pacman){
			eaten_pacmans++;
		}
		else if (object instanceof Fruit)
			eaten_fruits++;
		else if (object instanceof Ghost) {
			hit_times_by_ghost ++;
		}

	}

}
