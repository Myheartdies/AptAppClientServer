import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.sql.*;
import java.util.*;
import java.util.List;

public class RemoteDataAdaptor implements DataAccess {

    @Override
    public void Conn() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'Conn'");
    }

    @Override
    public User loadUser(String Username, String password) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'loadUser'");
    }

    @Override
    public boolean saveUser(User user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveUser'");
    }

    @Override
    public List<Apartment> loadAptList() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'loadAptList'");
    }

    @Override
    public Apartment loadAptByID(int postID) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'loadAptByID'");
    }

    @Override
    public boolean saveApt(Apartment post) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveApt'");
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

    @Override
    public User loadUserByID(int ID) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'loadUserByID'");
    }

}
