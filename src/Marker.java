import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;

import javax.swing.JLabel;


public class Marker  {
	private int nr; //numer markera
	private int x, y; // pozycja na Jpanelu Obraz
	private Color kolor;
	int rozmiar=5; //rozmiar plusika
	Color [] kolory={Color.MAGENTA, Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW,Color.CYAN, Color.GRAY, Color.ORANGE,Color.PINK};
	
	Marker(int nr, int x, int y){
		this.nr=nr;
		this.x=x; 
		this.y=y;
		this.kolor=kolory[nr%kolory.length];
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public int getNr() {
		return nr;
	}
	public int getXmin() {
		return x-rozmiar;
	}
	public int getXmax() {
		return x+rozmiar;
	}
	

	public void setX(int x) {
		this.x = x;
	}

	public int getYmin() {
		return y-rozmiar;
	}
	public int getYmax() {
		return y+rozmiar;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void draw(Graphics2D g2d){ // malowanie krzy¿yka
		g2d.setColor(kolor);
		g2d.drawLine(x-rozmiar, y, x+rozmiar, y);
		g2d.drawLine(x-rozmiar, y+1, x+rozmiar, y+1);
		g2d.drawLine(x, y-rozmiar, x, y+rozmiar);
		g2d.drawLine(x+1, y-rozmiar, x+1, y+rozmiar);

	}
			
}
