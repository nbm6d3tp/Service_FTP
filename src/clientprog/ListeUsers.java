package clientprog;

import java.util.List;

import bri.User;

public class ListeUsers {
	private static List<User> users;

	public static User getUser(int index) {
		return users.get(index);
	}

	protected static User getUser(String login, String mdp) {
		for (User u : users) {
			if (u.getLogin().equals(login) && u.getMdp().equals(mdp)) {
				return u;
			}
		}
		return null;
	}

	public static void setUsers(List<User> newusers) {
		users = newusers;
	}
}
