package default_package;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.awt.image.BufferedImage;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;


public class Entrance_Panel extends JFrame {

	private JPanel contentPane;
	DB_Queries DBQueries;
	static Entrance_Panel frame;
	Boolean isCatJumpable = false;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {

            @Override
            public boolean dispatchKeyEvent(KeyEvent ke) {
                synchronized (Entrance_Panel.class) {
                    switch (ke.getID()) {
                    case KeyEvent.KEY_PRESSED:
                        if (ke.getKeyCode() == KeyEvent.VK_W)
                        {
                            wPressed = true;
                        } if (ke.getKeyCode() == 65) //A
                        {
                        	aPressed = true;
                        } if (ke.getKeyCode() == 68) //D
                        {
                        	dPressed = true;
                        }
                        break;

                    case KeyEvent.KEY_RELEASED:
                        if (ke.getKeyCode() == KeyEvent.VK_W) {
                            wPressed = false;
                        } if (ke.getKeyCode() == KeyEvent.VK_A)
                        {
                        	aPressed = false;
                        } if (ke.getKeyCode() == KeyEvent.VK_D)
                        {
                        	dPressed = false;
                        }
                        break;
                    }
                    return false;
                }
            }
        });
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new Entrance_Panel();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private static volatile boolean wPressed = false;
    public static boolean isWPressed() {
        synchronized (Entrance_Panel.class) {
            return wPressed;
        }
    }
    private static volatile boolean aPressed = false;
    public static boolean isAPressed() {
        synchronized (Entrance_Panel.class) {
            return aPressed;
        }
    }
    private static volatile boolean dPressed = false;
    public static boolean isDPressed() {
        synchronized (Entrance_Panel.class) {
            return dPressed;
        }
    }

	/**
	 * Create the frame.
	 */
	public Entrance_Panel() {
		setResizable(false);
		setTitle("MiniGram");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 552);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		DBQueries = new DB_Queries(null, null);
		
		JPanel panel = new JPanel();
	    contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
	    
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(-5, -23, 805, 560);
		panel.add(tabbedPane);
	    
		JPanel loginPanel = new JPanel();
		loginPanel.setBackground(Color.decode("#8E806A"));
	    JPanel registerPanel = new JPanel();
	    registerPanel.setBackground(Color.decode("#8E806A"));
	    
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
	    loginPanel.add(logoLabel);
	    
	    JLabel divider_1 = new JLabel("┎┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┒");
	    divider_1.setHorizontalAlignment(SwingConstants.CENTER);
	    divider_1.setForeground(Color.LIGHT_GRAY);
	    divider_1.setBounds(200, 170, 400, 30);
	    loginPanel.add(divider_1);
	    
	    JLabel loginResultLabel = new JLabel();
	    loginResultLabel.setFont(new Font("Tahoma", Font.PLAIN, 10));
	    loginResultLabel.setLocation(250, 190);
	    loginResultLabel.setSize(300, 20);
	    loginResultLabel.setForeground(Color.RED);
	    
	    loginPanel.add(loginResultLabel);
	    
	    JLabel emailAddressLabel = new JLabel("USERNAME");
	    emailAddressLabel.setForeground(Color.LIGHT_GRAY);
	    emailAddressLabel.setBounds(250, 200, 300, 30);
	    loginPanel.add(emailAddressLabel);
	    
	    JTextField emailField = new JTextField();
	    emailField.addFocusListener(new FocusAdapter() {
	    	@Override
	    	public void focusGained(FocusEvent e) {
	    		if (emailField.getText().equals("Enter Your Username Here")) emailField.setText("");
	    	}
	    	@Override
	    	public void focusLost(FocusEvent e) {
	    		if (emailField.getText().equals("")) emailField.setText("Enter Your Username Here");	    		
	    	}
	    });
	    emailField.setForeground(Color.WHITE);
	    emailField.setBackground(Color.decode("#8E806A"));
	    emailField.setText("Enter Your Username Here");
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
	    
	    JButton loginButton = new JButton("Login");
	    loginButton.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		String query = "SELECT EXISTS (\r\n"
	    				+ "  SELECT * FROM users WHERE username = \"" + emailField.getText() + "\" AND password = \"" + passwordField.getText() + "\"\r\n"
	    				+ ")";
	    		ResultSet rs = DBQueries.GetQuery(query);
	    		try {
	    			rs.beforeFirst();
	    			rs.next();
					if (rs.getString(1).equals("1"))
					{
						Main_Panel mainPanel = new Main_Panel(emailField.getText());
						
						//mainPanel.username = emailField.getText();
						System.out.println(emailField.getText());
						mainPanel.setVisible(true);
						frame.setVisible(false);
					} else
					{
						loginResultLabel.setText("Wrong username or password.");
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
	    	}
	    });
	    loginButton.setBounds(240, 320, 320, 40);
	    loginButton.setForeground(Color.DARK_GRAY);
	    loginButton.setBackground(Color.LIGHT_GRAY);
	    loginPanel.add(loginButton);
	    
	    JButton registerTabButton = new JButton("Register");
	    registerTabButton.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		tabbedPane.setSelectedIndex(1);
	    	}
	    });
	    registerTabButton.setBounds(320, 360, 160, 30); //240, 360, 160, 30);
	    registerTabButton.setBackground(new Color(0, 0, 0, 0));
	    registerTabButton.setRolloverEnabled(false);
	    registerTabButton.setBorder(null);
	    loginPanel.add(registerTabButton);
	    
	    //┖┈┈┈┈┈┈┈┈┈┈┈┈┈┈┚
	    JLabel divider_2 = new JLabel("┖┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┚");
	    divider_2.setHorizontalAlignment(SwingConstants.CENTER);
	    divider_2.setForeground(Color.LIGHT_GRAY);
	    divider_2.setBounds(200, 380, 400, 30);
	    loginPanel.add(divider_2);
	    
	    JButton goOffline = new JButton("Go Offline Mode");
	    goOffline.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		tabbedPane.setSelectedIndex(2);
	    		
	    	}
	    });
	    goOffline.setBounds(0, 0, -16, -13);
	    loginPanel.add(goOffline);
	    
	    /*JButton forgetPassButton = new JButton("forget password");
	    forgetPassButton.setBounds(400, 360, 160, 30);
	    forgetPassButton.setBackground(new Color(0, 0, 0, 0));
	    forgetPassButton.setForeground(Color.DARK_GRAY);
	    forgetPassButton.setRolloverEnabled(false);
	    forgetPassButton.setBorder(null);
	    loginPanel.add(forgetPassButton);*/
	    
	    //REGISTER PANEL COMPONENTS ----------------------------------------- In C# I could use #regions :'(
	    
	    JLabel registerLogoLabel = new JLabel();
	    registerLogoLabel.setForeground(Color.WHITE);
	    registerLogoLabel.setBounds(355, 30, 100, 100);
	    
		try {
			File imageFile = new File("images\\talia_logo.png");
		    System.out.println(imageFile.exists());
		    
			BufferedImage img = null;
			
			img = ImageIO.read(imageFile);
			Image dimg = img.getScaledInstance(registerLogoLabel.getWidth(), registerLogoLabel.getHeight(),
			        Image.SCALE_SMOOTH);
			
			ImageIcon logo = new ImageIcon(dimg);
			registerLogoLabel.setIcon(logo);
		} catch (IOException e) {
			e.printStackTrace();
		}
	    registerPanel.add(registerLogoLabel);
	    
	    JLabel divider_r = new JLabel("┎┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┒");
	    divider_r.setHorizontalAlignment(SwingConstants.CENTER);
	    divider_r.setForeground(Color.LIGHT_GRAY);
	    divider_r.setBounds(200, 150, 400, 30);
	    registerPanel.add(divider_r);
	    
	    JLabel registerResultLabel = new JLabel();
	    registerResultLabel.setForeground(Color.RED);
	    registerResultLabel.setFont(new Font("Tahoma", Font.PLAIN, 10));
	    registerResultLabel.setBounds(250, 170, 250, 20);
	    registerPanel.add(registerResultLabel);
	    
	    JLabel emailAddressLabelRegister = new JLabel("EMAIL ADDRESS");
	    emailAddressLabelRegister.setForeground(Color.LIGHT_GRAY);
	    emailAddressLabelRegister.setBounds(250, 180, 300, 30);
	    registerPanel.add(emailAddressLabelRegister);
	    
	    JTextField emailFieldRegister = new JTextField();
	    emailFieldRegister.addFocusListener(new FocusAdapter() {
	    	@Override
	    	public void focusGained(FocusEvent e) {
	    		if (emailFieldRegister.getText().equals("Enter Your Email Here")) emailFieldRegister.setText("");
	    	}
	    	@Override
	    	public void focusLost(FocusEvent e) {
	    		if (emailFieldRegister.getText().equals("")) emailFieldRegister.setText("Enter Your Email Here");	    		
	    	}
	    });
	    emailFieldRegister.setForeground(Color.WHITE);
	    emailFieldRegister.setBackground(Color.decode("#8E806A"));
	    emailFieldRegister.setText("Enter Your Email Here");
	    emailFieldRegister.setBounds(250, 205, 300, 30);
	    registerPanel.add(emailFieldRegister);
	    
	    JLabel usernameLabelRegister = new JLabel("USERNAME");
	    usernameLabelRegister.setForeground(Color.LIGHT_GRAY);
	    usernameLabelRegister.setBounds(250, 235, 300, 30);
	    registerPanel.add(usernameLabelRegister);
	    
	    JTextField usernameFieldRegister = new JTextField();
	    usernameFieldRegister.addFocusListener(new FocusAdapter() {
	    	@Override
	    	public void focusGained(FocusEvent e) {
	    		if (usernameFieldRegister.getText().equals("Enter Your Username Here")) usernameFieldRegister.setText("");
	    	}
	    	@Override
	    	public void focusLost(FocusEvent e) {
	    		if (usernameFieldRegister.getText().equals("")) usernameFieldRegister.setText("Enter Your Username Here");	    		
	    	}
	    });
	    usernameFieldRegister.setForeground(Color.WHITE);
	    usernameFieldRegister.setBackground(Color.decode("#8E806A"));
	    usernameFieldRegister.setText("Enter Your Username Here");
	    usernameFieldRegister.setBounds(250, 260, 300, 30);
	    registerPanel.add(usernameFieldRegister);
	    
	    JLabel passwordLabelRegister = new JLabel("PASSWORD");
	    passwordLabelRegister.setForeground(Color.LIGHT_GRAY);
	    passwordLabelRegister.setBounds(250, 290, 300, 30);
	    registerPanel.add(passwordLabelRegister);
	    
	    JPasswordField passwordFieldRegister = new JPasswordField();
	    passwordFieldRegister.addFocusListener(new FocusAdapter() {
	    	@Override
	    	public void focusGained(FocusEvent e) {
	    		if (passwordFieldRegister.getText().equals("Enter Your Password Here"))
	    		{
	    			passwordFieldRegister.setText("");
	    			passwordFieldRegister.setEchoChar('*');
	    		}
	    	}
	    	@Override
	    	public void focusLost(FocusEvent e) {
	    		if (passwordFieldRegister.getText().equals(""))
	    		{
	    			passwordFieldRegister.setText("Enter Your Password Here");
	    			passwordFieldRegister.setEchoChar((char) 0);
	    		}
	    	}
	    });
	    passwordFieldRegister.setForeground(Color.WHITE);
	    passwordFieldRegister.setEchoChar((char) 0);
	    passwordFieldRegister.setBackground(Color.decode("#8E806A"));
	    passwordFieldRegister.setText("Enter Your Password Here");
	    passwordFieldRegister.setBounds(250, 315, 300, 30);
	    registerPanel.add(passwordFieldRegister);
	    
	    JLabel passwordLabelRegisterAgain = new JLabel("PASSWORD REPEAT");
	    passwordLabelRegisterAgain.setForeground(Color.LIGHT_GRAY);
	    passwordLabelRegisterAgain.setBounds(250, 345, 300, 30);
	    registerPanel.add(passwordLabelRegisterAgain);
	    
	    JPasswordField passwordFieldRegisterAgain = new JPasswordField();
	    passwordFieldRegisterAgain.addFocusListener(new FocusAdapter() {
	    	@Override
	    	public void focusGained(FocusEvent e) {
	    		if (passwordFieldRegisterAgain.getText().equals("Enter Your Password Again"))
	    		{
	    			passwordFieldRegisterAgain.setText("");
	    			passwordFieldRegisterAgain.setEchoChar('*');
	    		}
	    	}
	    	@Override
	    	public void focusLost(FocusEvent e) {
	    		if (passwordFieldRegisterAgain.getText().equals(""))
	    		{
	    			passwordFieldRegisterAgain.setText("Enter Your Password Again");
	    			passwordFieldRegisterAgain.setEchoChar((char) 0);
	    		}
	    	}
	    });
	    passwordFieldRegisterAgain.setForeground(Color.WHITE);
	    passwordFieldRegisterAgain.setEchoChar((char) 0);
	    passwordFieldRegisterAgain.setBackground(Color.decode("#8E806A"));
	    passwordFieldRegisterAgain.setText("Enter Your Password Again");
	    passwordFieldRegisterAgain.setBounds(250, 370, 300, 30);
	    registerPanel.add(passwordFieldRegisterAgain);
	    
	    JButton registerButton = new JButton("Register");
	    registerButton.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		if (usernameFieldRegister.getText().equals("Enter Your Username Here") || usernameFieldRegister.getText().equals(""))
	    		{
	    			registerResultLabel.setText("Please enter your username.");
	    		} else
	    		{
	    			if (emailFieldRegister.getText().equals("") || emailFieldRegister.getText().equals("Enter Your Email Here"))
	    			{
	    				registerResultLabel.setText("Please enter your email.");
	    			} else 
	    			{
			    		if (passwordFieldRegister.getText().equals(passwordFieldRegisterAgain.getText()))
			    		{
			    			String query = "INSERT INTO `users` (`UserID`, `username`, `email`, `password`, `post_count`, `biography`, `profile_pic`, `posts`) VALUES (NULL, '" + usernameFieldRegister.getText() + "', '" + emailFieldRegister.getText() + "', '" + passwordFieldRegister.getText() + "', '0', 'This guy is too lazy even cannot write a bio :(', 'http://localhost/java_project/profile-pictures/default.jpg', '');";
			    			DBQueries.ExecuteQuery(query);
			    			tabbedPane.setSelectedIndex(0);
			    		} else
			    		{
			    			registerResultLabel.setText("Passwords do not match.");
			    		}
	    			}
	    		}
	    	}
	    });
	    registerButton.setBounds(240, 412, 320, 40);
	    registerButton.setForeground(Color.DARK_GRAY);
	    registerButton.setBackground(Color.LIGHT_GRAY);
	    registerPanel.add(registerButton);
	    
	    JButton loginTabButton = new JButton("Login");
	    loginTabButton.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		tabbedPane.setSelectedIndex(0);
	    	}
	    });
	    loginTabButton.setBounds(320, 452, 160, 30); //240, 360, 160, 30);
	    loginTabButton.setBackground(new Color(0, 0, 0, 0));
	    loginTabButton.setRolloverEnabled(false);
	    loginTabButton.setBorder(null);
	    registerPanel.add(loginTabButton);
	    
	    JLabel divider_r2 = new JLabel("┖┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┚");
	    divider_r2.setHorizontalAlignment(SwingConstants.CENTER);
	    divider_r2.setForeground(Color.LIGHT_GRAY);
	    divider_r2.setBounds(200, 472, 400, 30);
	    registerPanel.add(divider_r2);
	    
	    JPanel gamePanel = new JPanel();
	    tabbedPane.addTab("game", null, gamePanel, null);
	    gamePanel.setLayout(null);
	    
	    JLabel backgroundLabel = new JLabel("New label");
	    backgroundLabel.setBounds(-5, 0, 805, 523);
	    
	    try {
			File imageFile = new File("images\\background.jpg");
		    
			BufferedImage img = null;
			
			img = ImageIO.read(imageFile);
			Image dimg = img.getScaledInstance(backgroundLabel.getWidth(), backgroundLabel.getHeight(),
			        Image.SCALE_SMOOTH);
			
			ImageIcon logo = new ImageIcon(dimg);
			backgroundLabel.setIcon(logo);
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
	    
	    
	    JLabel catImageLabel = new JLabel();
	    catImageLabel.setBounds(30, 345, 60, 60);
	    
	    try {
			File imageFile = new File("images\\cat.png");
		    
			BufferedImage img = null;
			
			img = ImageIO.read(imageFile);
			Image dimg = img.getScaledInstance(catImageLabel.getWidth(), catImageLabel.getHeight(),
			        Image.SCALE_SMOOTH);
			
			ImageIcon logo = new ImageIcon(dimg);
			catImageLabel.setIcon(logo);
		} catch (IOException e) {
			e.printStackTrace();
		}
	    gamePanel.add(catImageLabel);
	    
	    
	    
	    JLabel[] dogs = new JLabel[2];
	    int[][] dogPos = {{700, 340}, {1300, 340}};
	    
	    int[] catPos = {30, 345};
	    
	    JLabel dogImageLabel = new JLabel();
	    dogImageLabel.setBounds(450, 345, 90, 60);
	    
	    try {
			File imageFile = new File("images\\dog.png");
		    
			BufferedImage img = null;
			
			img = ImageIO.read(imageFile);
			Image dimg = img.getScaledInstance(dogImageLabel.getWidth(), dogImageLabel.getHeight(),
			        Image.SCALE_SMOOTH);
			
			ImageIcon logo = new ImageIcon(dimg);
			dogImageLabel.setIcon(logo);
		} catch (IOException e) {
			e.printStackTrace();
		}
	    gamePanel.add(dogImageLabel);
	    dogs[0] = dogImageLabel;
	    
	    JLabel dog2ImageLabel = new JLabel();
	    dog2ImageLabel.setBounds(700, 345, 90, 60);
	    
	    try {
			File imageFile = new File("images\\dog.png");
		    
			BufferedImage img = null;
			
			img = ImageIO.read(imageFile);
			Image dimg = img.getScaledInstance(dog2ImageLabel.getWidth(), dog2ImageLabel.getHeight(),
			        Image.SCALE_SMOOTH);
			
			ImageIcon logo = new ImageIcon(dimg);
			dog2ImageLabel.setIcon(logo);
		} catch (IOException e) {
			e.printStackTrace();
		}
	    gamePanel.add(dog2ImageLabel);
	    dogs[1] = dog2ImageLabel;
	    
	    
	    int score = 0;
	    
	    JLabel scoreLabel = new JLabel();
	    scoreLabel.setBounds(100, 30, 950, 85);
	    gamePanel.add(scoreLabel);
	    
	    
	    gamePanel.add(backgroundLabel);
	    

	    
	    int dogSpeed = 70;
	    int catJumpSpeed = 25;
	    int catGravity = 30;
	    
	    int catRunSpeed = 30;
	    int catMaxHeight = 240;
	    int catMinHeight = 345;
	    int catMinX = 20;
	    int catMaxX = 150;
	    
	    //START THE GAME IF THE INTERNET CONNECTION NOT AVAILABLE
	    if (!netIsAvailable())
	    {
	    	tabbedPane.setSelectedIndex(2);
	    	
	    	Runnable runnable = new Runnable() {
				@Override public void run() {
			    	dogs[0].setBounds(dogPos[0][0], dogPos[0][1], 90, 60);
			    	dogs[1].setBounds(dogPos[1][0], dogPos[1][1], 90, 60);
			    	
			    	dogPos[0][0] -= dogSpeed; //CHANGE DOG 1 X
			    	if (dogPos[0][0] < 0)
			    	{
			    		dogPos[0][0] = 1300;
			    	}
			    	
			    	dogPos[1][0] -= dogSpeed; //CHANGE DOG 2 X
			    	if (dogPos[1][0] < 0)
			    	{
			    		dogPos[1][0] = 1300;
			    	}
			    	
			    	if (catPos[1] <= catMinHeight - catGravity && (!isCatJumpable || !isWPressed()))
			    	{
			    		catPos[1] += catGravity;
			    	}
			    	
			    	if (isWPressed() && isCatJumpable)
			    	{
			    		catPos[1] -= catJumpSpeed;
			    	}
			    	
			    	if (isAPressed() && catPos[0] > catMinX)
			    		catPos[0] -= catRunSpeed;
			    	else if (isDPressed() && catPos[0] < catMaxX)
			    		catPos[0] += catRunSpeed;
			    	
			    	
			    	if (catPos[1] < catMaxHeight)
			    		isCatJumpable = false;
			    	else if (catPos[1] > catMinHeight - catGravity)
			    		isCatJumpable = true;
			    	
			    	catImageLabel.setBounds(catPos[0], catPos[1], 60, 60);
			    	scoreLabel.setText("dsafa");
			    	
			    	if (((catPos[0] > dogPos[0][0] - 30) && (catPos[0] < dogPos[0][0] + 50) && //CHECK DOG 1 X
			    			(catPos[1] > dogPos[0][1] - 30) && (catPos[1] < dogPos[0][1] + 30)) || //CHECK DOG 1 Y
			    			((catPos[0] > dogPos[1][0] - 30) && (catPos[0] < dogPos[1][0] + 50) && 
			    			(catPos[1] > dogPos[1][1] - 30) && (catPos[1] < dogPos[1][1] + 30))) //CHECK DOG 2 X
			    	{
			    		int answer = JOptionPane.showConfirmDialog(null, "Wanna play again?");
			    		if (answer == 0)
			    		{
			    			dogPos[0][0] = 700;
			    			dogPos[1][0] = 1300;
			    			dogs[1].setBounds(1300, dogPos[1][1], 90, 60);
			    			dogs[0].setBounds(700, dogPos[0][1], 90, 60);
			    			catPos[1] = 345;
			    			catPos[0] = 30;
			    		} else if (answer == 1 || answer == 2)
			    		{
			    			System.exit(0);
			    		}
			    	}
				}
			};
			
			Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(
				    runnable,
				    0,
				    100,
				    TimeUnit.MILLISECONDS);
		
	    }
	}
	
	
	
	private static boolean netIsAvailable() {
	    try {
	        final URL url = new URL("http://www.google.com");
	        final URLConnection conn = url.openConnection();
	        conn.connect();
	        conn.getInputStream().close();
	        return true;
	    } catch (MalformedURLException e) {
	        throw new RuntimeException(e);
	    } catch (IOException e) {
	        return false;
	    }
	}
}