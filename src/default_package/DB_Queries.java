package default_package;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import java.awt.image.*;
import java.awt.*;

public class DB_Queries {

	String serverUrl;
	String username;

	static Connection dbCon;

	public DB_Queries(String username, String serverUrl) {
		super();
		this.username = username;
		try {
			dbCon = DriverManager.getConnection(
					(serverUrl != null ? serverUrl : "jdbc:mysql://localhost:3306/java_project"), "root", "");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void CloseConnection()
	{
		try {
			if (!dbCon.isClosed())
				dbCon.close();
		} catch (Exception e) { e.printStackTrace(); }
	}
	
	public ResultSet GetFeed()
	{
		ResultSet rs;

		try {
			Statement stmt = dbCon.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery("SELECT * FROM `posts` ORDER BY upload_date DESC LIMIT 20");

			return rs;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void ExecuteQuery(String _query) {
		try {
			Statement stmt = dbCon.createStatement();
			stmt.execute(_query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ResultSet GetQuery(String _query) {
		ResultSet rs;

		try {
			Statement stmt = dbCon.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery(_query);

			return rs;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public ImageIcon GetImageFromURL(String _url, Integer _photoSizes)
	{
		try {
		BufferedImage masterNotResized = ImageIO.read(new URL(_url).openStream());
		BufferedImage master = resizeImage(masterNotResized, _photoSizes, _photoSizes);
		return new ImageIcon(master);
		} catch (Exception e) {e.printStackTrace(); return null;}
	}

	public ImageIcon GetUserProfilePic(String _username) {
		Integer photoSizes = 110;
		String query = "SELECT profile_pic FROM users where username = \"" + _username + "\"";
		ResultSet queryResult = GetQuery(query);
		System.out.println(query);

		try {
			queryResult.next();
			String resultLink = queryResult.getString("profile_pic");
			BufferedImage masterNotResized = ImageIO.read(new URL(resultLink).openStream());
			BufferedImage master = resizeImage(masterNotResized, photoSizes, photoSizes);

			int diameter = Math.min(photoSizes, photoSizes); // master.getWidth(), master.getHeight());
			BufferedImage mask = new BufferedImage(photoSizes, photoSizes,
					/* master.getWidth(), master.getHeight(), */BufferedImage.TYPE_INT_ARGB);

			Graphics2D g2d = mask.createGraphics();
			applyQualityRenderingHints(g2d);
			g2d.fillOval(0, 0, diameter - 1, diameter - 1);
			g2d.dispose();

			BufferedImage masked = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
			g2d = masked.createGraphics();
			applyQualityRenderingHints(g2d);
			int x = (diameter - master.getWidth()) / 2;
			int y = (diameter - master.getHeight()) / 2;
			g2d.drawImage(master, x, y, null);
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.DST_IN));
			g2d.drawImage(mask, 0, 0, null);
			g2d.dispose();

			return new ImageIcon(masked);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void applyQualityRenderingHints(Graphics2D g2d) {

		g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
		g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
	}

	BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
		BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics2D = resizedImage.createGraphics();
		graphics2D.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
		graphics2D.dispose();
		return resizedImage;
	}

	public String CreateRandomID() {
		Random rand = new Random();
		int upperBound = 999999999; // 9 digit ID
		int lowerBound = 100000000;

		while (true) {
			Integer idGenerated = lowerBound + rand.nextInt(upperBound - lowerBound);

			try {
				ResultSet set = GetQuery("SELECT 1 FROM posts WHERE id = \"" + idGenerated + "\";");
				if (!set.isBeforeFirst()) {
					return idGenerated.toString();
				} else continue;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}
}