package trailin.myfirstapplication;

/**
 * Created by trail on 2017-02-20.
 */

public class MainThread extends Thread {
    private static MainThread ourInstance = new MainThread();

    public static MainThread getInstance() {
        return ourInstance;
    }

    private ActionThread actionThread = null;

    private String menu = null;

    private MainThread () {

    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public ActionThread getActionThread() {
        return actionThread;
    }

    public void setActionThread(ActionThread actionThread) {
        this.actionThread = actionThread;
    }
}
