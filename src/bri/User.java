package bri;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class User {

	private String login;
	private String mdp;
	private String fileNameURL;
	private URLClassLoader urlcl;

	public User() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param login
	 * @param mdp
	 * @param fileNameURL
	 * @throws MalformedURLException
	 */
	public User(String login, String mdp, String fileNameURL) throws MalformedURLException {
		super();
		this.login = login;
		this.mdp = mdp;
		this.fileNameURL = fileNameURL;
		urlcl = URLClassLoader.newInstance(new URL[] { new URL(fileNameURL) });
	}

	public String getFtp() {
		return fileNameURL;
	}

	public String getLogin() {
		return login;
	}

	public String getMdp() {
		return mdp;
	}

	public URLClassLoader getUrlcl() {
		return urlcl;
	}

	public void setfileNameURL(String fileNameURL) throws MalformedURLException {
		urlcl = URLClassLoader.newInstance(new URL[] { new URL(fileNameURL) });
		this.fileNameURL = fileNameURL;

	}
}
