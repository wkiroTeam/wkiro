
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Point;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;



import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JLabel;
import javax.swing.JTextField;

import Jama.Matrix;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

public class Stereowizja {
	 
	private JFrame frmStereopara;
	final JFileChooser fc= new JFileChooser();
	Obraz obraz1;// panel zawierajacy pierwszy obrazek +markery
	Obraz obraz2;// panel zawierajacy drogi obrazek + markery
	private final JButton btnPrzekszta = new JButton("Kalibruj");
	JLabel pozycja= new JLabel("");
	private JTextField txtX;
	private JTextField txtY;
	private JTextField txtZ;
	private JLabel lbMarker;
	private JPanel opcje;
	Przetwornik przetwornik = new Przetwornik();
	private boolean skalibrowany = false;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					Stereowizja window = new Stereowizja();
					window.frmStereopara.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Stereowizja() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmStereopara = new JFrame();
		frmStereopara.setTitle("Stereopara");
		
		obraz1 = new Obraz(this);
		obraz2 = new Obraz(this);
		obraz1.setPreferredSize(new Dimension(600,600));
		obraz2.setPreferredSize(new Dimension(600,600));
		
		//zeby nie wczytywac cay czas to mozna odblokowac i wpisac sciezke do pliku

		//File f= new File("D:\\Dokumenty\\gra\\gra1.png");
		//obraz1.wczytajZpliku(f);
		//obraz2.wczytajZpliku(f);
		//
		/**
		 * Ustawianie wygl�du �eby sie w kupie trzymalo, latwo moze sie rozdupcyc
		 */

		frmStereopara.setBounds(100, 100, 303, 134);
		frmStereopara.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		opcje = new JPanel();
		opcje.setLayout(null);
		
		btnPrzekszta.setBounds(3, 10, 87, 23);
		opcje.add(btnPrzekszta);

		txtX = new JTextField();
		txtX.setBounds(100, 11, 30, 20);
		opcje.add(txtX);
		txtX.setText("X");
		txtX.setColumns(10);
		
		txtY = new JTextField();
		txtY.setBounds(140, 12, 30, 20);
		opcje.add(txtY);
		txtY.setText("Y");
		txtY.setColumns(10);
		
		txtZ = new JTextField();
		txtZ.setBounds(180, 12, 30, 20);
		opcje.add(txtZ);
		txtZ.setText("Z");
		txtZ.setColumns(10);
		
		btnPrzekszta.setText("Kalibruj");
		skalibrowany = false;
		txtX.show(true);
		txtY.show(true);
		txtZ.show(true);

		

		pozycja.setBounds(220, 12, 600, 20);
		opcje.add(pozycja);
		
		GroupLayout groupLayout = new GroupLayout(frmStereopara.getContentPane());
		groupLayout.setHorizontalGroup(groupLayout
			.createParallelGroup(Alignment.LEADING)
			.addComponent(opcje, GroupLayout.PREFERRED_SIZE, 600,GroupLayout.PREFERRED_SIZE)
				.addGroup(groupLayout
					.createSequentialGroup()
						.addComponent(obraz1,GroupLayout.PREFERRED_SIZE, 600,GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(obraz2,GroupLayout.PREFERRED_SIZE, 600,GroupLayout.PREFERRED_SIZE)
								.addContainerGap(28, Short.MAX_VALUE))

		);
		groupLayout.setVerticalGroup(groupLayout
			.createParallelGroup(Alignment.LEADING)
			.addGroup(groupLayout
				.createSequentialGroup()
					.addComponent(opcje,GroupLayout.PREFERRED_SIZE, 38,GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout
						.createParallelGroup(Alignment.LEADING)
						.addComponent(obraz1,GroupLayout.PREFERRED_SIZE,600,GroupLayout.PREFERRED_SIZE)
						.addComponent(obraz2,GroupLayout.PREFERRED_SIZE,600,GroupLayout.PREFERRED_SIZE))
							.addGap(40))
		);
		frmStereopara.getContentPane().setLayout(groupLayout);
		/**
		 * MENU
		 */
		JMenuBar menuBar = new JMenuBar();
		frmStereopara.setJMenuBar(menuBar);
		
		JMenu mnPlik = new JMenu("Plik");
		mnPlik.setMnemonic(KeyEvent.VK_P);
		menuBar.add(mnPlik);
		
		JMenuItem mntmWczytajObrazy = new JMenuItem("Wczytaj obrazy",KeyEvent.VK_W);
		mnPlik.add(mntmWczytajObrazy);
		
		JMenuItem mntmResetuj = new JMenuItem("Resetuj",KeyEvent.VK_R);
		mnPlik.add(mntmResetuj);

		JMenuItem mntmRekalibruj = new JMenuItem("Skalibruj ponownie", KeyEvent.VK_S);
		mnPlik.add(mntmRekalibruj);
		
		JMenuItem mntmZakocz = new JMenuItem("Zako\u0144cz", KeyEvent.VK_Z);
		mnPlik.add(mntmZakocz);
		
		
		/**
		 * Dzialaja przyciski wczytywania obrazow i zamykania, brak resetu
		 */
		
		mntmWczytajObrazy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(obraz1.getWysokosc() == 0)
					otworzOknoDialogowe(obraz1);
				if(obraz1.getWysokosc() !=0 )
					otworzOknoDialogowe(obraz2);
				int wysokosc= (obraz1.getWysokosc()>obraz2.getWysokosc()?obraz1.getWysokosc():obraz2.getWysokosc());
				int szerokosc= (obraz1.getSzerokosc()>obraz2.getSzerokosc()?obraz1.getSzerokosc():obraz2.getSzerokosc());
				obraz1.zmienRozmiar(wysokosc, szerokosc);
				obraz2.zmienRozmiar(wysokosc, szerokosc);
			}
		});
		
		mntmResetuj.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				obraz1.resetujObraz();
				obraz2.resetujObraz();
				pozycja.setText("");
				btnPrzekszta.setText("Kalibruj");
				skalibrowany = false;
				txtX.show(true);
				txtY.show(true);
				txtZ.show(true);
			}
		});
		
		mntmRekalibruj.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				obraz1.wyczyscListeMarkerow();
				obraz2.wyczyscListeMarkerow();
				pozycja.setText("");
				btnPrzekszta.setText("Kalibruj");
				skalibrowany = false;
				txtX.show(true);
				txtY.show(true);
				txtZ.show(true);
				
			}
		});
		
		mntmZakocz.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				frmStereopara.setVisible(false);
				
			}
		});
		
		btnPrzekszta.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					if (obraz1.czyZaladowany() && obraz2.czyZaladowany()) {
						int nr=obraz1.sprawdzKtoryJestZaznaczony();
						if (nr!=-1 && !skalibrowany){
							dodajWspolrzedne3D(nr);
						}
						
						if (!skalibrowany) {
							if (obraz1.getMarkery().size() < 6) {
								// za mało markerów!
								JOptionPane.showMessageDialog(null, "Podano zbyt mało markerów, proszę podać minimum 6");
							} else {
								if (kalibruj()) {
				
									obraz1.wyczyscListeMarkerow();
									obraz2.wyczyscListeMarkerow();
									txtX.show(false);
									txtY.show(false);
									txtZ.show(false);
									skalibrowany = true;
									btnPrzekszta.setText("Oblicz współrzędne");
									
								}
							}
						} else {
							if (obraz1.getMarkery().size()>0) {
								obliczWspolrzedne();
							} else {
								// brak markerów!
								JOptionPane.showMessageDialog(null, "Nie podano punktu, proszę zaznaczyć na obu obrazach punkt");
								
							}
						}
					}
				} catch (BadCoordsException e) {
					
					JOptionPane.showMessageDialog(null, "Proszę podać poprawne współrzędne rzeczywiste punktu!");
					
				}
			}

	
		});
	}
	public boolean usunMarkerZPanelow( int nrMarkera){
			obraz1.usunMarker(nrMarkera);
			obraz2.usunMarker(nrMarkera);

		return true;
	}
	
	public boolean dodawanieMarkeraDoPanelow(int x, int y, int nr){
		try {
			if (!skalibrowany || skalibrowany && obraz1.getMarkery().size() == 0) {
				obraz1.dodajMarker(x, y);
				obraz2.dodajMarker(x, y);
				if(nr!=-1){
					dodajWspolrzedne3D(nr);
				}
				if (!skalibrowany) {
					txtX.grabFocus();
				}
			}
			wyczyscTxtXYZ();
		} catch (BadCoordsException e) {
			obraz1.getMarkery().remove(obraz1.getMarkery().size()-1);
			obraz2.getMarkery().remove(obraz2.getMarkery().size()-1);
			JOptionPane.showMessageDialog(null, "Proszę podać poprawne współrzędne rzeczywiste punktu!");
			return false;
		}
		return true;
	}
	
	public void dodajWspolrzedne3D(int nr) throws BadCoordsException{
		int x=-1, y=-1,z=-1;
		try{
			x=Integer.parseInt(txtX.getText());
			y=Integer.parseInt(txtY.getText());
			z=Integer.parseInt(txtZ.getText());
		}
		catch(Exception e){
		}
		if(x!=-1 && y!= -1 && z!=-1){
			obraz1.dodajDoMarkeraWspolrzedne3d(nr, x, y, z );
			obraz2.dodajDoMarkeraWspolrzedne3d(nr, x, y, z );
		} else {
			throw new BadCoordsException();
		}
	}
	
	public void oznaczZaznaczonyMarker(int nr){
		obraz1.setZaznaczony(nr);
		obraz2.setZaznaczony(nr);
	}
	
	public void tekstLabelki(int x, int y){
		pozycja.setText("X:"+x+", Y:"+y);
	}
	public void wyswietlWspolrzedne3dMarkera(Marker m){
		txtX.setText(String.valueOf(m.X3d));
		txtY.setText(String.valueOf(m.Y3d));
		txtZ.setText(String.valueOf(m.Z3d));
	}
	
	public void wyczyscTxtXYZ(){
		txtX.setText("");
		txtY.setText("");
		txtZ.setText("");
	}
	
	
	/**
	 * Funkcja sluzy do wczytywania obrazkow z dysku.
	 * @param panel - w ktorym panelu umiescic obrazek
	 * @return zwraca bul czy sie dobrze wczytalo, jak nie to okienko wczytywania 2 obrazka sie nie wyswietli- pacz mntmWczytajObrazy.addActionListener
	 */
	private boolean otworzOknoDialogowe(Obraz panel){
		int wartosc= fc.showOpenDialog(frmStereopara);
		if(wartosc == JFileChooser.APPROVE_OPTION){
			File plik = fc.getSelectedFile();
			plik.getPath().toLowerCase();
			if(plik.getPath().endsWith(".jpg") || plik.getPath().endsWith(".png") ){
				panel.wczytajZpliku(plik);
				frmStereopara.pack();
				return true;
			}
		}
		return false;
	}
	
	private boolean kalibruj() {
		Matrix x1, x2, X;
		
		ArrayList<Marker> markery1 = obraz1.getMarkery();
		ArrayList<Marker> markery2 = obraz2.getMarkery();
		
		int n = Math.min(markery1.size(), markery2.size());
		
		double[][] tempX1 = new double[n][2];
		for( int i = 0; i < n; ++i) {
			tempX1[i][0] = markery1.get(i).getX();
			tempX1[i][1] = markery1.get(i).getY();
		}

		double[][] tempX2 = new double[n][2];
		for( int i = 0; i < n; ++i) {
			tempX2[i][0] = markery2.get(i).getX();
			tempX2[i][1] = markery2.get(i).getY();
		}

		double[][] tempX = new double[n][3];
		for( int i = 0; i < n; ++i) {
			tempX[i][0] = markery1.get(i).X3d;
			tempX[i][1] = markery1.get(i).Y3d;
			tempX[i][2] = markery1.get(i).Z3d;
		}
		
		x1 = new Matrix(tempX1);
		x2 = new Matrix(tempX2);
		X = new Matrix(tempX);

		przetwornik.skalibruj(x1, x2, X);
		
		return true;
	}
	
	public boolean getSkalibrowany() {
		return skalibrowany;
	}
	
	private Matrix obliczWspolrzedne() {
		
		Matrix xt1, xt2;
		
		double[][] tempXt1 = {
				{obraz1.getMarkery().get(0).getX()},{obraz1.getMarkery().get(0).getY()}
		};
		
		double[][] tempXt2 = {
				{obraz2.getMarkery().get(0).getX()},{obraz2.getMarkery().get(0).getY()}
		};
		
		
		xt1 = new Matrix(tempXt1);
		xt2 = new Matrix(tempXt2);
		Matrix result = przetwornik.rekonstruuj(xt1, xt2);
		
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		df.setMinimumFractionDigits(2);
		Double x,y,z;
		x = result.get(0, 0);
		y = result.get(1, 0);
		z = result.get(2, 0);
		
		pozycja.setText("X:" + df.format(x) + ", Y:"+ df.format(y) + ", Z:" + df.format(z));
		return result;
	}
}
