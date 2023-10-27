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

    private SQLDataAdapter dataAdapter;

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

    public SQLDataAdapter getDataAdapter() {
        return dataAdapter;
    }

    private AptAppManager() {
        // create SQLite database connection here!
        try {
            Class.forName("org.sqlite.JDBC");

            String url = "jdbc:sqlite:proto.db";

            connection = DriverManager.getConnection(url);
            dataAdapter = new SQLDataAdapter(connection);

        } catch (ClassNotFoundException ex) {
            System.out.println("SQLite is not installed. System exits with error!");
            ex.printStackTrace();
            System.exit(1);
        }

        catch (SQLException ex) {
            System.out.println("SQLite database is not ready. System exits with error!" + ex.getMessage());

            System.exit(2);
        }

        postingCtrl = new PostingController(newPostScreen);
        aptListController = new AptListController(aptList);
    }

}
