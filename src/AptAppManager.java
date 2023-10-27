import java.sql.*;

public class AptAppManager {

    private static AptAppManager instance; // Singleton pattern

    public static AptAppManager getInstance() {
        if (instance == null) {
            instance = new AptAppManager();
        }
        return instance;
    }
    // Main components of this application

    private Connection connection;

    public Connection getDBConnection() {
        return connection;
    }

    private DataAccess dataAccess;

    private User currentUser = null;

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    private MainScreen mainScreen = new MainScreen();
    private RegisterScreen regScreen = new RegisterScreen();
    private AptListScreen aptList = new AptListScreen();
    private PostScreen postingScreen = new PostScreen();

    private AptDetailScreen aptDetailScreen = new AptDetailScreen();

    public AptDetailScreen getAptDetailScreen() {
        return aptDetailScreen;
    }

    public PostScreen getPostingScreen() {
        return postingScreen;
    }

    public AptListScreen getAptList() {
        return aptList;
    }

    public RegisterScreen getRegScreen() {
        return regScreen;
    }

    private PostScreen newPostScreen = new PostScreen();

    public MainScreen getMainScreen() {
        return mainScreen;
    }

    public PostScreen getNewPostScreen() {
        return newPostScreen;
    }

    public LoginScreen loginScreen = new LoginScreen();

    public LoginScreen getLoginScreen() {
        return loginScreen;
    }

    private LoginController loginScreenCtrl = new LoginController();
    private PostingController postingCtrl;

    private AptListController aptListController;

    public AptListController getAptListController() {
        return aptListController;
    }

    public PostingController getPostingCtrl() {
        return postingCtrl;
    }

    public LoginController getLoginScreenCtrl() {
        return loginScreenCtrl;
    }

    public DataAccess getDataAccess() {
        return dataAccess;
    }

    private AptAppManager() {
        // create SQLite database connection here!

        dataAccess = new RemoteDataAdaptor();

        postingCtrl = new PostingController(newPostScreen);
        aptListController = new AptListController(aptList);
    }

}
