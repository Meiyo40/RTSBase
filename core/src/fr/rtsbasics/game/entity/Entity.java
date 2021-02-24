package fr.rtsbasics.game.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Entity {
	private Vector2 position;
	private Vector2 origin;
	private int width;
	private int height;
	private Texture texture;
	private String name;
	private boolean isMovable;
	private boolean toDelete = false;
	private int currentHealth;
	private int MAX_HEALTH;
	
	public Entity(Vector2 pPos, String pName, Texture pTexture)
	{
		this.position = pPos;
		this.origin = pPos;
		this.name = pName;
		this.texture = pTexture;
		this.width = pTexture.getWidth();
		this.height = pTexture.getHeight();
	}
	
	public Entity(Vector2 pPos, String pName, int pWidth, int pHeight)
	{
		this.position = pPos;
		this.origin = pPos;
		this.name = pName;
		this.width = pWidth;
		this.height = pHeight;
	}
	
	/**
	 * @return the position
	 */
	public Vector2 getPosition() {
		return position;
	}
	/**
	 * @param position the position to set
	 */
	public void setPosition(Vector2 position) {
		this.position = position;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	public Texture getTexture() {
		return texture;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}
	
	/**
	 * @return the collision (Circle range)
	 */
	public boolean isCollideRange(Entity src) {
		float dist = getDistance(src.getPosition().x, src.getPosition().y, this.getPosition().x, this.getPosition().y);
		if( dist < this.texture.getWidth() || dist < this.texture.getHeight())
			return true;
		
		return false;
	}	
	
	public boolean isCollide(Entity src)
	{
		float srcX = src.getPosition().x;
		float srcY = src.getPosition().y;
		float unitX = this.getPosition().x;
		float unitY = this.getPosition().y;
		
		if(srcX >= unitX && srcX <= (unitX + this.width)
		&& srcY >= unitY && srcY <= (unitY + this.height))
			return true;
		
		return false;
	}

	/**
	 * @return the distance between 2 points
	 */
	public float getDistance(float x1, float y1, float x2, float y2)
	{
		float dx = x1 - x2;
		float dy = y1 - y2;
		return (float) Math.sqrt(dx*dx + dy*dy);
	}

	/**
	 * @return the origin
	 */
	public Vector2 getOrigin() {
		return origin;
	}

	/**
	 * @param origin the origin to set
	 */
	public void setOrigin(Vector2 origin) {
		this.origin = origin;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * @return the isMovable
	 */
	public boolean isMovable() {
		return isMovable;
	}

	/**
	 * @param isMovable the isMovable to set
	 */
	public void setMovable(boolean isMovable) {
		this.isMovable = isMovable;
	}

	/**
	 * @return the mAX_HEALTH
	 */
	public int getMAX_HEALTH() {
		return MAX_HEALTH;
	}

	/**
	 * @param mAX_HEALTH the mAX_HEALTH to set
	 */
	public void setMAX_HEALTH(int mAX_HEALTH) {
		MAX_HEALTH = mAX_HEALTH;
	}

	/**
	 * @return the currentHealth
	 */
	public int getCurrentHealth() {
		return currentHealth;
	}

	/**
	 * @param currentHealth the currentHealth to set
	 */
	public void setCurrentHealth(int currentHealth) {
		this.currentHealth = currentHealth;
	}

	/**
	 * @return the toDelete
	 */
	public boolean isToDelete() {
		return toDelete;
	}

	/**
	 * @param toDelete the toDelete to set
	 */
	public void setToDelete(boolean toDelete) {
		this.toDelete = toDelete;
	}
}
