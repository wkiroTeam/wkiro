
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;


public class Obraz extends JPanel implements MouseListener, MouseMotionListener {


	private BufferedImage obraz;
	private int szerokosc;
	private int wysokosc;
	
	private ArrayList<Marker> markery=null;
	int nrMarkera;
	
	Stereowizja stereowizja;
	
	Marker przesowany=null;
	Marker zaznaczony=null;// slozy do oznazenia zaznaczonego markera, marker bedzie zaznaczony dopoki nie zaznaczymy innego, lub klikniemy costam
	// wpisanie wspolrzednych 3d nastepuje dopiero po kliknieciu innego markera;
	
	public Obraz(Stereowizja stereowizja){
		super();
		//this.setSize(10,10);
		this.stereowizja=stereowizja;
		wysokosc=0;
		szerokosc=0;
		nrMarkera=0;
		addMouseListener(this);
		addMouseMotionListener(this);
	}
		
	public int getSzerokosc() {
		return szerokosc;
	}

	public int getWysokosc() {
		return wysokosc;
	}

	public void resetujObraz(){
		obraz= null;
		markery=null;
		nrMarkera=0;
		zmienRozmiar(0, 0);
		repaint();	
	}
	
	public void zmienRozmiar(int wys, int szer){
		wysokosc=wys;
		szerokosc=szer;
	}
	
	public void wczytajZpliku (File plikObrazu) {
		wysokosc=400;
		szerokosc=400;
		try{
			obraz = ImageIO.read(plikObrazu);
			wysokosc=(obraz.getHeight()<wysokosc?obraz.getHeight():wysokosc); // skalowanie obrazka jak wiekszy to zmniejszamy
			szerokosc=(obraz.getWidth()<szerokosc?obraz.getWidth():szerokosc);
		}
		catch (IOException ex){
			System.out.println("bl¹d poddczas wczytywania");
		}
		markery= new ArrayList<Marker>();
	}
	/*public void update(Graphics g){
		Dimension rozmiar= this.getSize();
		if(obraz!=null){
			Graphics g2d= obraz.getGraphics();
			paint(g2d);
			g2d.dispose();
			g2d.drawImage(obraz, 0,0,szerokosc,wysokosc, this);
		}
	}*/
	
	

	// malowanie funkcja wywolywana poptrzez repaint()
	public void paint(Graphics g){
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		//if(obraz!=null){
			g2d.drawImage(obraz, 0,0,szerokosc,wysokosc, this);
		//}
		this.setSize(szerokosc, wysokosc);
		if(markery!=null){
			for(Marker m : markery){
				m.draw(g2d);
			}
		}		
	}
	
	public void dodajMarker( int x, int y){
		if(zaznaczony!=null){
			stereowizja.dodajWspolrzedne3D(zaznaczony.getNr());
		}
		stereowizja.wyczyscTxtXYZ();
		Marker m =new Marker(nrMarkera,x,y);
		markery.add(m);
		nrMarkera++;
		zaznaczony=m; 
		repaint();
	}
	
	public void usunMarker( int nr){
		Marker mark=null;
		for(Marker m: markery){
			if(m.getNr()==nr){
				mark=m;
				break;
			}
		}
		if( mark!=null)
			markery.remove(mark);
		repaint();
	}
	
	public void dodajDoMarkeraWspolrzedne3d(int nr, int x,int y,int z ){
		for(Marker m:markery){
			if(m.getNr()==nr){
				m.X3d=x;
				m.Y3d=y;
				m.Z3d=z;
				break;
			}
		}
	}
	
	public int sprawdzKtoryJestZaznaczony(){
		if(zaznaczony!=null){
			return zaznaczony.getNr();
		}
		return -1;
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
					stereowizja.dodajWspolrzedne3D(zaznaczony.getNr());	
					zaznaczony=m;
					if(zaznaczony.X3d!=-1){
						stereowizja.wyswietlWspolrzedne3dMarkera(zaznaczony);
					}
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
			//repaint();
		}
	}
	//KONIEC KODU DO PRZESOWANIA

	@Override
	public void mouseMoved(MouseEvent arg0) {
		stereowizja.tekstLabelki(arg0.getX(), arg0.getY());
		
	}
}
