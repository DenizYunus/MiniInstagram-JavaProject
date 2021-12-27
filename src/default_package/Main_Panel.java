package default_package;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;

import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFileChooser;
import java.io.File;
import java.io.FileInputStream;

import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class Main_Panel extends JFrame {

	private JPanel contentPane;
	private JSplitPane splitPane;
	private JLabel welcomeUserLabel;
	JLabel profilePicLabel;
	JLabel uploadResultLabel;
	JTextArea captionTextArea;
	JPanel feedPanel;
	
	//INHERITED USER VARIABLES
	public String username = "";
	
	
	DB_Queries dbQueries;
	File imageToShare;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main_Panel frame = new Main_Panel("test");
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
	public Main_Panel(String _username) {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 552);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(200, 0));
		setContentPane(contentPane);
		
		username = _username;
		splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.6);
		splitPane.setDividerSize(0);
		contentPane.add(splitPane, BorderLayout.CENTER);
		
		JScrollPane scrollPane = new JScrollPane();
		splitPane.setLeftComponent(scrollPane);
		
		feedPanel = new JPanel(new MigLayout("filly"));
		feedPanel.setBackground(Color.DARK_GRAY);
		
		scrollPane.getViewport().setView(feedPanel);
		//splitPane.setLeftComponent(feedPanel);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.GRAY);
		splitPane.setRightComponent(panel);
		panel.setLayout(null);
		
		welcomeUserLabel = new JLabel("Welcome back, {username}");
		welcomeUserLabel.setVerticalAlignment(SwingConstants.TOP);
		welcomeUserLabel.setFont(new Font("Rockwell", Font.PLAIN, 17));
		welcomeUserLabel.setBounds(10, 10, 175, 45);
		panel.add(welcomeUserLabel);
		
		JButton uploadButton = new JButton("UploadPost");
		uploadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (imageToShare == null)
					{
						uploadResultLabel.setForeground(Color.RED);
						uploadResultLabel.setText("Don't forget to pick your photo!");
						return;
					}
					String postId = dbQueries.CreateRandomID();
					String resultFilePath = uploadFile(postId, imageToShare.getAbsolutePath());
					
					//INSERT INTO POSTS DATABASE
					String postsQuery = "INSERT INTO `posts` (`id`, `upload_date`, `file_path`, `uploader`, `caption`) VALUES"
							+ "('" + postId + "', CURRENT_TIME(), 'http://localhost/java_project/" + resultFilePath + "', '" + username + "', '" + captionTextArea.getText() + "');";
					dbQueries.ExecuteQuery(postsQuery);
					//System.out.println(postsQuery);
					
					//INSERT INTO USER POST COUNT AND POSTS
					String usersQuery = "SELECT post_count FROM `users` WHERE username = \"" + username + "\"";
					//System.out.println(usersQuery);
					ResultSet queryResult = dbQueries.GetQuery(usersQuery); //Get post count
					queryResult.beforeFirst();
					queryResult.next();
					System.out.println(queryResult.getInt(1));
					Integer newPostCount = queryResult.getInt(1) + 1;
					
					String userPostsQuery = "SELECT posts FROM `users` WHERE username = \"" + username + "\""; 
					queryResult = dbQueries.GetQuery(userPostsQuery); //Get post ids
					queryResult.beforeFirst();
					queryResult.next();
					String postIds = queryResult.getString(1) + postId + ", ";
					
					//HERE UPDATE THE VALUES
					String usersUpdateQuery = "UPDATE users SET post_count = " + newPostCount.toString() + " WHERE username = \"" + username + "\"";
					//System.out.println(usersUpdateQuery);
					dbQueries.ExecuteQuery(usersUpdateQuery);
					usersUpdateQuery = "UPDATE users SET posts = \"" + postIds + "\" WHERE username = \"" + username + "\"";
					System.out.println(usersUpdateQuery);
					dbQueries.ExecuteQuery(usersUpdateQuery);
					
					imageToShare = null;
					
					uploadResultLabel.setForeground(Color.GREEN);
					uploadResultLabel.setText("<html>&ensp;You've successfully<br>uploaded your photo :)</html>");
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		uploadButton.setBounds(0, 315, 195, 40);
		panel.add(uploadButton);

		captionTextArea = new JTextArea();
		captionTextArea.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (captionTextArea.getText().equals("Enter the caption here"))
				{
					captionTextArea.setText("");
					captionTextArea.setForeground(Color.BLACK);
				}
			}
			
			@Override
			public void focusLost(FocusEvent e)
			{
				if (captionTextArea.getText().equals(""))
				{
					captionTextArea.setText("Enter the caption here");
					captionTextArea.setForeground(Color.GRAY);
				}
			}
		});
		captionTextArea.setBounds(0, 221, 200, 88);
		captionTextArea.setText("Enter the caption here");
		captionTextArea.setForeground(Color.GRAY);
		panel.add(captionTextArea);
		
		profilePicLabel = new JLabel("ProfilePicture");
		profilePicLabel.setHorizontalAlignment(SwingConstants.CENTER);
		profilePicLabel.setBounds(33, 55, 123, 117);
		panel.add(profilePicLabel);
		
		JButton selectPictureButton = new JButton("Select Picture");
		selectPictureButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
				int result = fileChooser.showOpenDialog(panel);
				if (result == JFileChooser.APPROVE_OPTION) {
				    imageToShare = fileChooser.getSelectedFile();
				}
			}
		});
		selectPictureButton.setBounds(0, 198, 195, 21);
		panel.add(selectPictureButton);
		
		uploadResultLabel = new JLabel("New label");
		uploadResultLabel.setVerticalAlignment(SwingConstants.TOP);
		uploadResultLabel.setForeground(Color.GRAY);
		uploadResultLabel.setHorizontalAlignment(SwingConstants.CENTER);
		uploadResultLabel.setBounds(0, 355, 195, 45);
		panel.add(uploadResultLabel);
        
		
		//START DATABASE PROCESSES
		
		dbQueries = new DB_Queries(username, null);
		ImageIcon a = dbQueries.GetUserProfilePic(username);
		profilePicLabel.setIcon(a);
		welcomeUserLabel.setText("<html>Welcome back,<br><font color=\"#0000FF\" size=4>" + username + "</font></html>");
		
		//GET FEED HERE
		getFeed();
	}
	
	void getFeed()
	{
		ResultSet feedResult = dbQueries.GetFeed();
		
		try {
			while (feedResult.next()) {
				JPanel rootPanel = new JPanel(new MigLayout("filly"));
				rootPanel.setBackground(Color.GRAY);
				
				JPanel leftPanel = new JPanel(new MigLayout("filly, wrap 1"));
				leftPanel.setBackground(Color.GRAY);
				rootPanel.add(leftPanel);
				
				JPanel rightPanel = new JPanel(new MigLayout("filly"));
				rightPanel.setBackground(Color.GRAY);
				rootPanel.add(rightPanel);
				
				JLabel image = new JLabel();
				image.setPreferredSize(new Dimension(150, 150));
				leftPanel.add(image);
				image.setIcon(dbQueries.GetImageFromURL(feedResult.getString("file_path"), leftPanel.getWidth() == 0 ? 170 : leftPanel.getWidth()));
				
				JLabel usernameLabel = new JLabel();
				usernameLabel.setText(feedResult.getString("uploader"));
				leftPanel.add(usernameLabel, "span 1 2, growy");
				
				JLabel captionLabel = new JLabel();
				captionLabel.setMaximumSize(new Dimension(100, 200));
				captionLabel.setText("<html>&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;<br>"
						+ feedResult.getString("caption") + "</html>");
				rightPanel.add(captionLabel, "span 3");
				
				feedPanel.add(rootPanel, "wrap");
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
    public static String uploadFile(String fileId, String filePath/*, String serverURL*/) throws IOException {
    	URLConnection conn = null;
        OutputStream os = null;
        InputStream is = null;

        String resultFilePath = "";
        try {
        	String CrLf = "\r\n";
            URL url = new URL("http://localhost/java_project/UploadFile.php");
            System.out.println("url:" + url);
            conn = url.openConnection();
            conn.setDoOutput(true);

            //String postData = "";

            //System.out.println(filePath);
            File initialFile = new File(filePath);
            InputStream imgIs = new FileInputStream(initialFile);
            byte[] imgData = new byte[imgIs.available()];
            imgIs.read(imgData);

            String message1 = "";
            message1 += "-----------------------------4664151417711" + CrLf;
            message1 += "Content-Disposition: form-data; name=\"uploadedfile\"; filename=\"" + fileId + ".jpg\""
                    + CrLf;
            message1 += "Content-Type: image/jpeg" + CrLf;
            message1 += CrLf;

            // the image is sent between the messages in the multipart message.

            String message2 = "";
            message2 += CrLf + "-----------------------------4664151417711--"
                    + CrLf;

            conn.setRequestProperty("Content-Type",
                    "multipart/form-data; boundary=---------------------------4664151417711");
            // might not need to specify the content-length when sending chunked
            // data.
            conn.setRequestProperty("Content-Length", String.valueOf((message1
                    .length() + message2.length() + imgData.length)));

            System.out.println("open os");
            os = conn.getOutputStream();

            System.out.println(message1);
            os.write(message1.getBytes());

            // SEND THE IMAGE
            int index = 0;
            int size = 1024;
            do {
                //System.out.println("write:" + index);
                if ((index + size) > imgData.length) {
                    size = imgData.length - index;
                }
                os.write(imgData, index, size);
                index += size;
            } while (index < imgData.length);
            System.out.println("written:" + index);

            System.out.println(message2);
            os.write(message2.getBytes());
            os.flush();

            System.out.println("open is");
            is = conn.getInputStream();

            char buff = 512;
            int len;
            byte[] data = new byte[buff];
            do {
                System.out.println("READ");
                len = is.read(data);

                if (len > 0) {
                    System.out.println(new String(data, 0, len));
                    if (!(new String(data, 0, len).length() < 2)) resultFilePath = new String(data, 0, len);
                }
            } while (len > 0);

            System.out.println("DONE");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("Close connection");
            try {
                os.close();
            } catch (Exception e) {
            }
            try {
                is.close();
            } catch (Exception e) {
            }
        }
        return resultFilePath;
    }
}