import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.TransferHandler;


public class Obraz extends JPanel implements MouseListener, MouseMotionListener {

	private BufferedImage obraz;
	private int szerokosc;
	private int wysokosc;
	
	private ArrayList<Marker> markery=null;
	int nrMarkera;
	int nrPanelu;
	
	Stereowizja stereowizja;
	
	Marker przesowany=null;
	
	public Obraz(Stereowizja stereowizja, int nrPanelu){
		super();
		this.stereowizja=stereowizja;
		wysokosc=400;
		szerokosc=400;
		this.setSize(10,10);
		nrMarkera=0;
		
		
	}
	
	public void wczytajZpliku (File plikObrazu) {
		try{
			obraz = ImageIO.read(plikObrazu);
			wysokosc=(obraz.getHeight()<wysokosc?obraz.getHeight():wysokosc); // skalowanie obrazka jak wiekszy to zmniejszamy
			szerokosc=(obraz.getWidth()<szerokosc?obraz.getWidth():szerokosc);
		}
		catch (IOException ex){
			System.out.println("bl¹d poddczas wczytywania");
		}
		markery= new ArrayList<Marker>();
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	// malowanie funkcja wywolywana poptrzez repaint()
	public void paint(Graphics g){
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		g2d.drawImage(obraz, 0,0,szerokosc,wysokosc, this);
		
		this.setSize(szerokosc, wysokosc);
		if(markery!=null){
			for(Marker m : markery){
				m.draw(g2d);
			}
		}		
	}
	
	public void dodajMarker( int x, int y){
		markery.add(new Marker(nrMarkera,x,y));
		nrMarkera++;
		repaint();
	}
	
	public void usunMarker( int nr){
		Marker mark=null;
		for(Marker m: markery){
			if(m.getNr()==nr){
				mark=m;
			}
		}
		if( mark!=null)
			markery.remove(mark);
		repaint();
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODONICZEGO 	
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODONICZEGO
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODONICZEGO 
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		int mX=arg0.getX();//pozycja kursorka
		int mY=arg0.getY();
		//USUWANIE MARKEROW - srodkowym myszkiem
		if(!markery.isEmpty() && arg0.getButton() ==MouseEvent.BUTTON2){
			int numer=-1;
			for(Marker m : markery){
				if(mX>m.getXmin() && // kursorek na markerze
						mX<m.getXmax() &&
						mY>m.getYmin() && 
						mY<m.getYmax()){
					
					numer=m.getNr();
					repaint();
					break;
				}
			}
			if(numer>=0){
				stereowizja.usunMarkerZPanelow(numer);
			}
		}
		//DODAWANIE MARKERU- prawym myszkiem
		if (arg0.getButton() == MouseEvent.BUTTON3) {
			if (markery.isEmpty()) {
				stereowizja.dodawanieMarkeraDoPanelow(mX, mY);
				repaint();
			} else {
				boolean dodaj=false;
				for (Marker m : markery) {
					if (mX < m.getXmin() || mX > m.getXmax()
							|| mY < m.getYmin() || mY > m.getYmax()) {
						//dodajMarker(mX, mY);
						dodaj=true;
					}
				}
				if(dodaj){
					stereowizja.dodawanieMarkeraDoPanelow(mX, mY);
				}
			}
		}
		//PRZESOWANIE MARKEROW- CHWYTAMY I LATAMY PO OBRAZKU- lewy myszek
		else if(!markery.isEmpty() && arg0.getButton() ==MouseEvent.BUTTON1){
			for(Marker m : markery){
				if(mX>m.getXmin() && //
						mX<m.getXmax() &&
						mY>m.getYmin() && 
						mY<m.getYmax()){
					m.setX(arg0.getX());
					m.setY(arg0.getY());
					przesowany=m;
					break;
				}
			}	
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		if(przesowany!=null){
			if (przesowany.getX() < 0 || przesowany.getX() > szerokosc
					|| przesowany.getY() < 0 || przesowany.getY() > wysokosc) {
				stereowizja.usunMarkerZPanelow(przesowany.getNr());
			}
		}
		przesowany= null;
	}
	
	public void mouseDragged(MouseEvent arg0){
		if(przesowany !=null){
			przesowany.setX(arg0.getX());
			przesowany.setY(arg0.getY());
			stereowizja.tekstLabelki(arg0.getX(), arg0.getY());
			repaint();
		}
	}
	//KONIEC KODU DO PRZESOWANIA

	@Override
	public void mouseMoved(MouseEvent arg0) {
		stereowizja.tekstLabelki(arg0.getX(), arg0.getY());
		
	}
}
