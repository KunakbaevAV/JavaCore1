package lesson8.server.service;

import java.util.ArrayList;
import java.util.List;

/**
 * @autor Kunakbaev Artem
 */
public class BaseAuthService implements AuthService {


    private class Entry {
        private String login;
        private String password;
        private String nickName;

        Entry(final String login, final String password, final String nickName) {
            this.login = login;
            this.password = password;
            this.nickName = nickName;
        }
    }

    private List<Entry> entrys;

    public BaseAuthService() {
        entrys = new ArrayList<>();
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public String getNickByLoginPass(String login, String password) {
        for (Entry currentEntry : entrys) {
            if (currentEntry.login.equals(login) && currentEntry.password.equals(password)) {
                return currentEntry.nickName;
            }
        }
        return null;
    }

    public void addEntry(final String login, final String password, final String nickName) {
        entrys.add(new Entry(login, password, nickName));
    }

    public boolean containsNickName(final String nickName) {
        if (nickName == null) return false;
        for (Entry account : entrys) {
            if (nickName.equals(account.nickName)) return true;
        }
        return false;
    }

    public boolean containsLogin(final String login) {
        if (login == null) return false;
        for (Entry account : entrys) {
            if (login.equals(account.login)) return true;
        }
        return false;
    }

}

