
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
import javax.swing.JTable;
import javax.swing.LayoutStyle;
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
	private final JButton btnMacierze = new JButton("Macierze");
	JLabel pozycja= new JLabel("");
	private JTextField txtX;
	private JTextField txtY;
	private JTextField txtZ;
	private JLabel lblX;
	private JLabel lblY;
	private JLabel lblZ;
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

//		File f= new File("/home/michal/Pulpit/1.png");
//		obraz1.wczytajZpliku(f);
//		obraz2.wczytajZpliku(f);
		//


		frmStereopara.setBounds(100, 100, 357, 377);
		frmStereopara.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		opcje = new JPanel();
		opcje.setLayout(null);
		
		btnPrzekszta.setBounds(3, 10, 100, 23);
		opcje.add(btnPrzekszta);
		
		btnMacierze.setBounds(3, 40, 100, 23);
		opcje.add(btnMacierze);
		
		lblX = new JLabel("X:");
		lblX.setBounds(13, 74, 15, 16);
		opcje.add(lblX);
		
		txtX = new JTextField();
		txtX.setBounds(28, 74, 30, 20);
		opcje.add(txtX);
		txtX.setText("");
		txtX.setColumns(10);
		
		lblY = new JLabel("Y:");
		lblY.setBounds(13, 101, 15, 16);
		opcje.add(lblY);
		
		txtY = new JTextField();
		txtY.setBounds(28, 101, 30, 20);
		opcje.add(txtY);
		txtY.setText("");
		txtY.setColumns(10);
		
		lblZ = new JLabel("Z:");
		lblZ.setBounds(13, 129, 15, 16);
		opcje.add(lblZ);
		
		txtZ = new JTextField();
		txtZ.setBounds(28, 128, 30, 20);
		opcje.add(txtZ);
		txtZ.setText("");
		txtZ.setColumns(10);
		
		btnPrzekszta.setText("Kalibruj");
		skalibrowany = false;
		

		pozycja.setBounds(10, 159, 93, 53);
		opcje.add(pozycja);

		GroupLayout groupLayout = new GroupLayout(frmStereopara.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(opcje, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE)
							.addComponent(obraz1, GroupLayout.PREFERRED_SIZE, 600, GroupLayout.PREFERRED_SIZE)
							.addComponent(obraz2, GroupLayout.PREFERRED_SIZE, 600, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(opcje, GroupLayout.PREFERRED_SIZE, 600, GroupLayout.PREFERRED_SIZE)
						.addComponent(obraz1, GroupLayout.PREFERRED_SIZE, 600, GroupLayout.PREFERRED_SIZE)
						.addComponent(obraz2, GroupLayout.PREFERRED_SIZE, 600, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
				ukryjPokazXYZlabelki(true);
				btnMacierze.disable();
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
				ukryjPokazXYZlabelki(true);
				btnMacierze.disable();
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
									ukryjPokazXYZlabelki(false);
									skalibrowany = true;
									btnPrzekszta.setText("Szacuj punkt");
									btnMacierze.enable();
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
		
		btnMacierze.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				pokazMacierze();
				
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
	
	private void ukryjPokazXYZlabelki(boolean widoczne){
		if(widoczne){
			btnPrzekszta.setBounds(3, 10, 100, 23);			
		}
		else{
			btnPrzekszta.setBounds(3, 10, 150, 23);
		}
		txtX.setVisible(widoczne);
		lblX.setVisible(widoczne);
		txtY.setVisible(widoczne);
		lblY.setVisible(widoczne);
		txtZ.setVisible(widoczne);
		lblZ.setVisible(widoczne);

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
		
		pozycja.setText("<html>X:" + df.format(x) + "<br>Y:"+ df.format(y) + "<br>Z:" + df.format(z)+"</html>");;
		return result;
	}
	
	public void pokazMacierze() {
		
		Matrix P1 = przetwornik.getP1();
		Matrix P2 = przetwornik.getP2();
		
		JFrame frame = new JFrame("Macierze rzutowania");
		JLabel lblP1 = new JLabel("P1:");
		JLabel lblP2 = new JLabel("P2:");
		lblP1.setBounds(5, 5, 50, 50);
		lblP2.setBounds(5, 155, 50, 50);
		frame.setResizable(false);
		
		DecimalFormat df = new java.text.DecimalFormat();
		df.setMaximumFractionDigits(4);
		df.setMinimumFractionDigits(4);

		
		Object rowData[][] = { 	{ df.format(new Double(P1.get(0, 0))).toString(), df.format(new Double(P1.get(0, 1))).toString(), df.format(new Double(P1.get(0, 2))).toString(), df.format(new Double(P1.get(0, 3))).toString()},
								{ df.format(new Double(P1.get(1, 0))).toString(), df.format(new Double(P1.get(1, 1))).toString(), df.format(new Double(P1.get(1, 2))).toString(), df.format(new Double(P1.get(1, 3))).toString()},
								{ df.format(new Double(P1.get(2, 0))).toString(), df.format(new Double(P1.get(2, 1))).toString(), df.format(new Double(P1.get(2, 2))).toString(), df.format(new Double(P1.get(2, 3))).toString()},
							};

		Object rowData2[][] = { { df.format(new Double(P2.get(0, 0))).toString(), df.format(new Double(P2.get(0, 1))).toString(), df.format(new Double(P2.get(0, 2))).toString(), df.format(new Double(P2.get(0, 3))).toString()},
								{ df.format(new Double(P2.get(1, 0))).toString(), df.format(new Double(P2.get(1, 1))).toString(), df.format(new Double(P2.get(1, 2))).toString(), df.format(new Double(P2.get(1, 3))).toString()},
								{ df.format(new Double(P2.get(2, 0))).toString(), df.format(new Double(P2.get(2, 1))).toString(), df.format(new Double(P2.get(2, 2))).toString(), df.format(new Double(P2.get(2, 3))).toString()},
							};

		Object columnNames[] = { "", "", "", ""};

		JTable macierz1 = new JTable(rowData, columnNames);
		JTable macierz2 = new JTable(rowData2, columnNames);

		macierz1.setCellSelectionEnabled(false);
		macierz2.setCellSelectionEnabled(false);
		
		
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(macierz1, GroupLayout.PREFERRED_SIZE, 400, GroupLayout.PREFERRED_SIZE)
							.addComponent(macierz2, GroupLayout.PREFERRED_SIZE, 400, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(macierz1, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
						.addComponent(macierz2, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		
		frame.setLayout(groupLayout);
		
		macierz1.setBounds(0, 50, 400, 100);
		macierz2.setBounds(0, 150, 400, 100);

		
		frame.getContentPane().add(lblP1, BorderLayout.CENTER);
		frame.getContentPane().add(lblP2, BorderLayout.CENTER);
		frame.getContentPane().add(macierz1, BorderLayout.CENTER);
		frame.getContentPane().add(macierz2, BorderLayout.CENTER);
		frame.setSize(400, 300);
		frame.setVisible(true);
	}

}
