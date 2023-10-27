
import java.sql.*;
import java.util.*;
import java.util.List;
import java.util.ArrayList;

public class SQLDataAdapter implements DataAccess{
    private Connection connection;
    private int lastPostID;

    public int getLastPostID() {
        return lastPostID;
    }

    public SQLDataAdapter(Connection connection) {
        this.connection = connection;
    }

    // =====================
    public boolean saveUser(User user) {
        // currently only check username
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Users WHERE UserName = ?");
            statement.setString(1, user.getUsername());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                // If the user already exist, fail it
                statement.close();
                resultSet.close();
                return false;
            } else {
                // Else register
                statement = connection.prepareStatement(
                        "INSERT INTO Users (UserName, Password, DisplayName, Email) VALUES (?,?,?,?)");
                statement.setString(1, user.getUsername());
                statement.setString(2, user.getPassword());
                statement.setString(3, user.getFullName());
                statement.setString(4, user.getEmail());
                statement.execute();
                statement.close();
                resultSet.close();
                statement = connection.prepareStatement("SELECT last_insert_rowid()");
                ResultSet set = statement.executeQuery();
                if (set.next()) {
                    user.setUserID(set.getInt(1));
                    System.out.println("User Id " + user.getUserID());
                    return true;
                } else {
                    user.setUserID(-1);
                    System.out.println("something is wrong");
                    return false;
                }
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    public boolean saveApt(Apartment post) {
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(
                    "INSERT INTO RentalApartments (Type, Price, Area, Address,Description," +
                            "AvailableTime,PostUserID, AptName) VALUES (?,?,?,?,?,?,?,?)");
            statement.setString(1, post.getType());
            statement.setDouble(2, post.getPrice());
            statement.setDouble(3, post.getArea());
            statement.setString(4, post.getAddress());
            statement.setString(5, post.getDescr());
            statement.setString(6, post.getAvailableDate());
            statement.setInt(7, post.getPosterID());
            statement.setString(8, post.getAptName());
            statement.execute();
            statement.close();

            statement = connection.prepareStatement("SELECT last_insert_rowid() as ApartmentID");
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                lastPostID = resultSet.getInt("ApartmentID");
            }
            resultSet.close();
            statement.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<Apartment> loadAptList() {
        List<Apartment> aptList = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM RentalApartments");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Apartment apartment = new Apartment();
                apartment.setID(resultSet.getInt("ApartmentID"));
                apartment.setAptName(resultSet.getString("AptName"));
                apartment.setType(resultSet.getString("Type"));
                apartment.setPrice(resultSet.getDouble("Price"));
                apartment.setArea(resultSet.getDouble("Area"));
                apartment.setAddress(resultSet.getString("Address"));
                apartment.setDescr(resultSet.getString("Description"));
                apartment.setAvailableDate(resultSet.getString("AvailableTime"));
                apartment.setPosterID(resultSet.getInt("PostUserID"));
                aptList.add(apartment);
            }

            resultSet.close();
            statement.close();
            return aptList;

        } catch (SQLException e) {
            System.out.println("Database access error!");
            e.printStackTrace();
        }
        return null;
    }

    public Apartment loadAptByID(int id) {
        try {
            String query = "SELECT * FROM RentalApartments WHERE ApartmentID = " + id;

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                Apartment apartment = new Apartment();
                apartment.setID(resultSet.getInt("ApartmentID"));
                apartment.setAptName(resultSet.getString("AptName"));
                apartment.setType(resultSet.getString("Type"));
                apartment.setPrice(resultSet.getDouble("Price"));
                apartment.setArea(resultSet.getDouble("Area"));
                apartment.setAddress(resultSet.getString("Address"));
                apartment.setDescr(resultSet.getString("Description"));
                apartment.setAvailableDate(resultSet.getString("AvailableTime"));
                apartment.setPosterID(resultSet.getInt("PostUserID"));
                resultSet.close();
                statement.close();

                return apartment;
            }

        } catch (SQLException e) {
            System.out.println("Database access error!");
            e.printStackTrace();
        }
        return null;
    }

    // ======================

public User loadUserByID(int id) {
        try {

            PreparedStatement statement = connection
                    .prepareStatement("SELECT * FROM Users WHERE UserID = " + id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setUserID(resultSet.getInt("UserID"));
                user.setUsername(resultSet.getString("UserName"));
                user.setPassword(resultSet.getString("Password"));
                user.setFullName(resultSet.getString("DisplayName"));
                user.setEmail(resultSet.getString("Email"));
                resultSet.close();
                statement.close();
                return user;
            }

        } catch (SQLException e) {
            System.out.println("Database access error!");
            e.printStackTrace();
        }
        return null;
    }

    public User loadUser(String username, String password) {
        try {

            PreparedStatement statement = connection
                    .prepareStatement("SELECT * FROM Users WHERE UserName = ? AND Password = ?");
            // System.out.println(password);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            // System.out.println(resultSet.getInt("UserID"));
            if (resultSet.next()) {
                User user = new User();
                user.setUserID(resultSet.getInt("UserID"));
                user.setUsername(resultSet.getString("UserName"));
                user.setPassword(resultSet.getString("Password"));
                user.setFullName(resultSet.getString("DisplayName"));
                user.setEmail(resultSet.getString("Email"));
                resultSet.close();
                statement.close();
                return user;
            }

        } catch (SQLException e) {
            System.out.println("Database access error!");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void Conn() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'Conn'");
    }

    @Override
    public List<Apartment> loadAptByPrice(double min, double max) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'loadAptByPrice'");
    }

    @Override
    public List<Apartment> loadAptByType(String type) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'loadAptByType'");
    }

    @Override
    public WishApt saveApt2WishList(Post post) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveApt2WishList'");
    }

    @Override
    public List<Integer> loadWishListByUserID(int userID) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'loadWishListByUserID'");
    }

}
