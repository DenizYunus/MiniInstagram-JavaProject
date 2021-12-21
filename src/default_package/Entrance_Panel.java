package default_package;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.awt.image.BufferedImage;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JLabel;

public class Entrance_Panel extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Entrance_Panel frame = new Entrance_Panel();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Entrance_Panel() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 552);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
	    contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
	    
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(-5, -23, 805, 560);
		panel.add(tabbedPane);
	    
		JPanel loginPanel = new JPanel();
		loginPanel.setBackground(new Color(81, 107, 235));
	    JPanel registerPanel = new JPanel();
	    tabbedPane.add("login", loginPanel);
	    tabbedPane.add("register", registerPanel);
	    loginPanel.setLayout(null);
	    registerPanel.setLayout(null);
	    
	    
	    //LOGIN PANEL ELEMENTS
	    JLabel logoLabel = new JLabel();
	    logoLabel.setForeground(Color.WHITE);
	    logoLabel.setBounds(325, 50, 150, 150);

		try {
			File imageFile = new File("images\\talia_logo.png");
		    System.out.println(imageFile.exists());
		    
			BufferedImage img = null;
			
			img = ImageIO.read(imageFile);
			Image dimg = img.getScaledInstance(logoLabel.getWidth(), logoLabel.getHeight(),
			        Image.SCALE_SMOOTH);
			
			ImageIcon logo = new ImageIcon(dimg);
			logoLabel.setIcon(logo);
		} catch (IOException e) {
			e.printStackTrace();
		}
	    /*try {
	    lblNewLabel.setIcon(new ImageIcon("images/talia_logo.png")); //getClass().getResource("images\\talia_logo.png")));
	    } catch (Exception e) {e.printStackTrace();};*/
	    loginPanel.add(logoLabel);
	    
	}
}
