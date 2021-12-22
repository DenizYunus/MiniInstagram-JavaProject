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
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

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
		loginPanel.setBackground(Color.decode("#8E806A"));
	    JPanel registerPanel = new JPanel();
	    tabbedPane.add("login", loginPanel);
	    tabbedPane.add("register", registerPanel);
	    loginPanel.setLayout(null);
	    registerPanel.setLayout(null);
	    
	    
	    //LOGIN PANEL ELEMENTS
	    JLabel logoLabel = new JLabel();
	    logoLabel.setForeground(Color.WHITE);
	    logoLabel.setBounds(355, 50, 100, 100);

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
	    
	    
	    JLabel divider_1 = new JLabel("┎┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┒");
	    divider_1.setHorizontalAlignment(SwingConstants.CENTER);
	    divider_1.setForeground(Color.LIGHT_GRAY);
	    divider_1.setBounds(200, 170, 400, 30);
	    loginPanel.add(divider_1);
	    
	    JLabel emailAddressLabel = new JLabel("EMAIL ADDRESS");
	    emailAddressLabel.setForeground(Color.LIGHT_GRAY);
	    emailAddressLabel.setBounds(250, 200, 300, 30);
	    loginPanel.add(emailAddressLabel);
	    
	    JTextField emailField = new JTextField();
	    emailField.addFocusListener(new FocusAdapter() {
	    	@Override
	    	public void focusGained(FocusEvent e) {
	    		if (emailField.getText().equals("Enter Your Email Here")) emailField.setText("");
	    	}
	    	@Override
	    	public void focusLost(FocusEvent e) {
	    		if (emailField.getText().equals("")) emailField.setText("Enter Your Email Here");	    		
	    	}
	    });
	    emailField.setForeground(Color.WHITE);
	    emailField.setBackground(Color.decode("#8E806A"));
	    emailField.setText("Enter Your Email Here");
	    emailField.setBounds(250, 225, 300, 30);
	    loginPanel.add(emailField);
	    
	    JLabel passwordLabel = new JLabel("PASSWORD");
	    passwordLabel.setForeground(Color.LIGHT_GRAY);
	    passwordLabel.setBounds(250, 250, 300, 30);
	    loginPanel.add(passwordLabel);
	    
	    JPasswordField passwordField = new JPasswordField();
	    passwordField.addFocusListener(new FocusAdapter() {
	    	@Override
	    	public void focusGained(FocusEvent e) {
	    		if (passwordField.getText().equals("Enter Your Password Here"))
	    			{
	    				passwordField.setText("");
	    				passwordField.setEchoChar('*');
	    			}
	    	}
	    	@Override
	    	public void focusLost(FocusEvent e) {
	    		if (passwordField.getText().equals(""))
	    			{
	    				passwordField.setText("Enter Your Password Here");
	    				passwordField.setEchoChar((char) 0);
	    			}
	    	}
	    });
	    passwordField.setForeground(Color.WHITE);
	    passwordField.setEchoChar((char) 0);
	    passwordField.setBackground(Color.decode("#8E806A"));
	    passwordField.setText("Enter Your Password Here");
	    passwordField.setBounds(250, 275, 300, 30);
	    loginPanel.add(passwordField);
	    
	}
}
