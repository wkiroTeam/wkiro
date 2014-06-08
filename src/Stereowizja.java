import java.awt.EventQueue;
import java.awt.Label;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;


import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager;
import javax.swing.JSlider;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JLabel;



public class Stereowizja {
	
	
	private JFrame frame;
	final JFileChooser fc= new JFileChooser();
	Obraz obraz1;// panel zawierajacy pierwszy obrazek +markery
	Obraz obraz2;// panel zawierajacy drogi obrazek + markery
	private final JButton btnPrzekszta = new JButton("Przekszta\u0142\u0107");
	JLabel pozycja = new JLabel("pozycja punktu nad ktorym jest myszek w 2d a pozniej w 3d");
	
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
		obraz1=new Obraz(this,1);
		obraz2=new Obraz(this,2);
		//zeby nie wczytywac cay czas to mozna odblokowac i wpisac sciezke do pliku
		//File f= new File("D:\\Dokumenty\\gra\\gra1.png");
		//obraz1.wczytajZpliku(f);
		//obraz2.wczytajZpliku(f);
		//
		/**
		 * Ustawianie wygl¹du ¿eby sie w kupie trzymalo, latwo moze sie rozdupcyc
		 */

		frame.setBounds(100, 100, 410, 227);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnPrzekszta)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(pozycja))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(obraz1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(obraz2, GroupLayout.PREFERRED_SIZE, 400, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(28, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(btnPrzekszta, GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
						.addComponent(pozycja, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(obraz2, GroupLayout.PREFERRED_SIZE, 400, GroupLayout.PREFERRED_SIZE)
						.addComponent(obraz1, GroupLayout.PREFERRED_SIZE, 400, GroupLayout.PREFERRED_SIZE))
					.addGap(40))
		);
		
		obraz2.setBorder(UIManager.getBorder("PopupMenu.border"));
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
				if(otworzOknoDialogowe(obraz1))
					otworzOknoDialogowe(obraz2);
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
	
	public void tekstLabelki(int x, int y){
		pozycja.setText("X:"+x+", Y:"+y);
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
