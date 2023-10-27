import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.sql.*;
import java.util.*;
import java.util.List;

public class SQLDataAdaptor implements DataAccess{

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
    public User saveUser(User user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveUser'");
    }

    @Override
    public Post loadPostByID(int postID) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'loadPostByID'");
    }

    @Override
    public Post savePost(Post post) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'savePost'");
    }

    @Override
    public List<Post> loadPostByPrice(double min, double max) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'loadPostByPrice'");
    }

    @Override
    public List<Post> loadPostByType(String type) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'loadPostByType'");
    }

    @Override
    public WishPost savePost2WishList(Post post) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'savePost2WishList'");
    }

    @Override
    public WishPost loadWishPostByUserID(int userID) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'loadWishPostByUserID'");
    }
    
}
