package main;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
/**
 * ErrorDialog
 * 
 * Just a tiny dialog to notify the user of invalid MongoDB installation
 * or non-matching configuration.
 * 
 * @author f.petruschke
 *
 */
public class ErrorDialog extends JFrame {
	private JTable table;

	/**
	 * Constructor
	 */
	public ErrorDialog() {
		getContentPane().setLayout(null);
		
		JLabel errorLbl = new JLabel("Fehler");
		errorLbl.setIcon(new ImageIcon(ErrorDialog.class.getResource("/javax/swing/plaf/metal/icons/Error.gif")));
		errorLbl.setHorizontalAlignment(SwingConstants.LEFT);
		errorLbl.setFont(new Font("Dialog", Font.BOLD, 20));
		errorLbl.setBounds(24, 12, 140, 39);
		getContentPane().add(errorLbl);
		
		JLabel notice1 = new JLabel("Es scheint, als wären nicht alle Bedingungen erfüllt,");
		notice1.setFont(new Font("Dialog", Font.PLAIN, 12));
		notice1.setBounds(24, 63, 399, 15);
		getContentPane().add(notice1);
		
		JLabel notice2 = new JLabel("um die Anwendung starten zu können.    ;-) ");
		notice2.setFont(new Font("Dialog", Font.PLAIN, 12));
		notice2.setBounds(24, 78, 399, 15);
		getContentPane().add(notice2);
		
		JLabel notice3 = new JLabel("Bitte überprüfen Sie folgende Punkte:");
		notice3.setBounds(24, 100, 399, 15);
		getContentPane().add(notice3);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(61, 198, 399, 57);
		getContentPane().add(scrollPane);
		
		JLabel questionIcon = new JLabel("");
		questionIcon.setIcon(new ImageIcon(ErrorDialog.class.getResource("/javax/swing/plaf/metal/icons/Question.gif")));
		questionIcon.setSize(32, 32);
		
		// table for displaying the configuration parameters
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{"Host", MongoDbConnector.host},
				{"Port", MongoDbConnector.port},
				{"Datenbankname", MongoDbConnector.databaseName},
			},
			new String[] {
				"", ""
			}
		));
		scrollPane.setViewportView(table);		
		
		JLabel notice4 = new JLabel("Wurde MongoDB ordnungsgemäß auf Ihrem PC oder einem");
		notice4.setFont(new Font("Dialog", Font.PLAIN, 12));
		notice4.setBounds(61, 127, 466, 15);
		getContentPane().add(notice4);
		
		JLabel notice5 = new JLabel("Ihnen zugänglichen Server installiert ?");
		notice5.setFont(new Font("Dialog", Font.PLAIN, 12));
		notice5.setBounds(71, 140, 305, 15);
		getContentPane().add(notice5);
		
		JLabel questionIcon2 = new JLabel("");
		questionIcon2.setIcon(new ImageIcon(ErrorDialog.class.getResource("/javax/swing/plaf/metal/icons/Question.gif")));
		questionIcon2.setBounds(24, 127, 32, 32);
		getContentPane().add(questionIcon2);
		
		JLabel informIcon = new JLabel("");
		informIcon.setIcon(new ImageIcon(ErrorDialog.class.getResource("/javax/swing/plaf/metal/icons/Inform.gif")));
		informIcon.setBounds(24, 171, 32, 32);
		getContentPane().add(informIcon);
		
		JLabel notice6 = new JLabel("Die Anwendung ist folgendermaßen konfiguriert:");
		notice6.setFont(new Font("Dialog", Font.PLAIN, 12));
		notice6.setBounds(61, 171, 388, 15);
		getContentPane().add(notice6);
		
		JButton exitBtn = new JButton("Beenden");
		exitBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		exitBtn.setBounds(333, 267, 127, 25);
		getContentPane().add(exitBtn);
	}
}
