import java.sql.*;

public class Application {

    private static Application instance; // Singleton pattern

    public static Application getInstance() {
        if (instance == null) {
            instance = new Application();
        }
        return instance;
    }
    // Main components of this application

    private Connection connection;

    public Connection getDBConnection() {
        return connection;
    }

    private DataAdapter dataAdapter;

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
    private NewPostScreen postingScreen = new NewPostScreen();

    private AptDetailScreen aptDetailScreen = new AptDetailScreen();

    public AptDetailScreen getAptDetailScreen() {
        return aptDetailScreen;
    }

    public NewPostScreen getPostingScreen() {
        return postingScreen;
    }

    public AptListScreen getAptList() {
        return aptList;
    }

    public RegisterScreen getRegScreen() {
        return regScreen;
    }

    private NewPostScreen newPostScreen = new NewPostScreen();

    public MainScreen getMainScreen() {
        return mainScreen;
    }

    public NewPostScreen getNewPostScreen() {
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

    public DataAdapter getDataAdapter() {
        return dataAdapter;
    }

    private Application() {
        // create SQLite database connection here!
        try {
            Class.forName("org.sqlite.JDBC");

            String url = "jdbc:sqlite:proto.db";

            connection = DriverManager.getConnection(url);
            dataAdapter = new DataAdapter(connection);

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

    public static void main(String[] args) {
        Application.getInstance().getLoginScreen().setVisible(true);
    }
}
