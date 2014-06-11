
import java.awt.EventQueue;
import java.awt.Point;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;


import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.sun.javafx.scene.layout.region.Margins.Converter;
import java.awt.BorderLayout;
import java.awt.FlowLayout;



public class Stereowizja {
	 
	private JFrame frame;
	final JFileChooser fc= new JFileChooser();
	Obraz obraz1;// panel zawierajacy pierwszy obrazek +markery
	Obraz obraz2;// panel zawierajacy drogi obrazek + markery
	private final JButton btnPrzekszta = new JButton("Przekszta\u0142\u0107");
	JLabel pozycja= new JLabel("vc");
	private JTextField txtX;
	private JTextField txtY;
	private JTextField txtZ;
	private JLabel lbMarker;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Stereowizja window = new Stereowizja();
					window.frame.setVisible(true);
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
		frame = new JFrame();

		//zeby nie wczytywac cay czas to mozna odblokowac i wpisac sciezke do pliku
		File f= new File("D:\\Dokumenty\\gra\\gra1.png");
		//
		/**
		 * Ustawianie wygl�du �eby sie w kupie trzymalo, latwo moze sie rozdupcyc
		 */

		frame.setBounds(100, 100, 410, 227);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		txtX = new JTextField();
		txtX.setText("X");
		txtX.setColumns(10);
		
		txtY = new JTextField();
		txtY.setText("Y");
		txtY.setColumns(10);
		
		txtZ = new JTextField();
		txtZ.setText("Z");
		txtZ.setColumns(10);
		
		lbMarker = new JLabel();
		lbMarker.setSize(10, 10);
		obraz2=new Obraz(this);
		obraz2.wczytajZpliku(f);
		
		obraz2.setBorder(UIManager.getBorder("PopupMenu.border"));
		obraz2.setLayout(new BorderLayout(0, 0));
		obraz1=new Obraz(this);
		obraz1.wczytajZpliku(f);
		GroupLayout groupLayout_1 = new GroupLayout(obraz1);
		groupLayout_1.setHorizontalGroup(
			groupLayout_1.createParallelGroup(Alignment.LEADING)
				.addGap(0, 398, Short.MAX_VALUE)
		);
		groupLayout_1.setVerticalGroup(
			groupLayout_1.createParallelGroup(Alignment.LEADING)
				.addGap(0, 413, Short.MAX_VALUE)
		);
		obraz1.setBorder(UIManager.getBorder("FileChooser.listViewBorder"));
		obraz1.setLayout(groupLayout_1);
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(obraz1, GroupLayout.PREFERRED_SIZE, 122, GroupLayout.PREFERRED_SIZE)
							.addGap(300)
							.addComponent(obraz2, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(9)
							.addComponent(btnPrzekszta)
							.addGap(5)
							.addComponent(txtX, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(5)
							.addComponent(txtY, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(5)
							.addComponent(txtZ, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(5)
							.addComponent(pozycja)))
					.addContainerGap(9, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(5)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(btnPrzekszta)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(1)
							.addComponent(txtX, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(1)
							.addComponent(txtY, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(1)
							.addComponent(txtZ, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(4)
							.addComponent(pozycja)))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(obraz1, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
						.addComponent(obraz2, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE))
					.addGap(195))
		);
		frame.getContentPane().setLayout(groupLayout);
		/**
		 * MENU
		 */
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnPlik = new JMenu("Plik");
		mnPlik.setMnemonic(KeyEvent.VK_P);
		menuBar.add(mnPlik);
		
		JMenuItem mntmWczytajObrazy = new JMenuItem("Wczytaj obrazy",KeyEvent.VK_W);
		mnPlik.add(mntmWczytajObrazy);
		
		JMenuItem mntmResetuj = new JMenuItem("Resetuj",KeyEvent.VK_R);
		mnPlik.add(mntmResetuj);
		
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
			}
		});
		
		mntmZakocz.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			frame.setVisible(false);
				
			}
		});
		
		btnPrzekszta.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO-zrobienia trzeba pobrac wartosci z listMarkerow z Obrazkow zrobic na nich operacje i costam jeszcze
				
			}
		});
	}
	public boolean usunMarkerZPanelow( int nrMarkera){
			obraz1.usunMarker(nrMarkera);
			obraz2.usunMarker(nrMarkera);

		return true;
	}
	
	public boolean dodawanieMarkeraDoPanelow(int x, int y){
			obraz1.dodajMarker(x, y);
			obraz2.dodajMarker(x, y);
		return true;
	}
	
	public void dodajWspolrzedne3D(int nr){
		int x=-1, y=-1,z=-1;
		try{
			x=Integer.parseInt(txtX.getText());
			y=Integer.parseInt(txtY.getText());
			z=Integer.parseInt(txtZ.getText());
		}
		catch(Exception e){
			System.out.println("Blad panie Kaziu "+e);
		}
		if(x!=-1 && y!= -1 && z!=-1){
			obraz1.dodajDoMarkeraWspolrzedne3d(nr, x, y, z );
			obraz2.dodajDoMarkeraWspolrzedne3d(nr, x, y, z );
		}
	}
	
	public void tekstLabelki(int x, int y){
		pozycja.setText("X:"+x+", Y:"+y);
	}
	public void wyswietlWspolrzedne3dMarkera(Marker m){
		txtX.setText(String.valueOf(m.X3d));
		txtY.setText(String.valueOf(m.Y3d));
		txtZ.setText(String.valueOf(m.Z3d));
	}
	
	
	/**
	 * Funkcja sluzy do wczytywania obrazkow z dysku.
	 * @param panel - w ktorym panelu umiescic obrazek
	 * @return zwraca bul czy sie dobrze wczytalo, jak nie to okienko wczytywania 2 obrazka sie nie wyswietli- pacz mntmWczytajObrazy.addActionListener
	 */
	private boolean otworzOknoDialogowe(Obraz panel){
		int wartosc= fc.showOpenDialog(frame);
		if(wartosc == JFileChooser.APPROVE_OPTION){
			File plik = fc.getSelectedFile();
			plik.getPath().toLowerCase();
			if(plik.getPath().endsWith(".jpg") || plik.getPath().endsWith(".png") ){
				panel.wczytajZpliku(plik);
				frame.pack();
				return true;
			}
		}
		return false;
	}
}
