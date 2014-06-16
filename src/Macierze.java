import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.text.DecimalFormat;

import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import Jama.Matrix;

public class Macierze extends JFrame {

	private JPanel contentPane;

	public Macierze(Przetwornik przetwornik) {
		
		Matrix P1 = przetwornik.getP1();
		Matrix P2 = przetwornik.getP2();
		
		
		JFrame frame = new JFrame("Macierze rzutowania");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(150, 150, 350, 250);
		JLabel lblP2 = new JLabel("P2:");
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
		
		macierz1.setBounds(0,5 , 400, 100);
		macierz2.setBounds(0, 150, 400, 100);
		frame.getContentPane().add(lblP2, BorderLayout.CENTER);
		frame.getContentPane().add(macierz1, BorderLayout.CENTER);
		frame.getContentPane().add(macierz2, BorderLayout.CENTER);
		JLabel lblP1 = new JLabel("P1:");
		lblP1.setBounds(5, 5, 50, 50);
		
				
				frame.getContentPane().add(lblP1, BorderLayout.CENTER);
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblP1))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(macierz1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblP2))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(macierz2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(72, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(5)
					.addComponent(lblP1)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(macierz1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(lblP2)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(macierz2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(113))
		);
		frame.getContentPane().setLayout(groupLayout);
		frame.setVisible(true);
	}

}
