package appli_cli;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * This program demonstrates how to download a file from FTP server using FTP
 * URL.
 *
 * @author www.codejava.net
 *
 */
public class FtpUrlDownload {
	private static final int BUFFER_SIZE = 4096;

	public static void main(String[] args) {
		String ftpUrl = "ftp://localhost:2121/examples/essai.txt";
		String savePath = "C:/Users/binhm/OneDrive/Desktop/essai.txt";

		System.out.println("URL: " + ftpUrl);

		try {
			URL url = new URL(ftpUrl);
			URLConnection conn = url.openConnection();
			InputStream inputStream = conn.getInputStream();

			FileOutputStream outputStream = new FileOutputStream(savePath);

			byte[] buffer = new byte[BUFFER_SIZE];
			int bytesRead = -1;
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}

			outputStream.close();
			inputStream.close();

			System.out.println("File downloaded");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}