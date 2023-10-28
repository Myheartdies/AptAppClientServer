import java.util.*;

public interface DataAccess {
    void Conn();

    User loadUser(String Username, String password);

    // Save and update ID of input with insert row id
    boolean saveUser(User user);

    List<Apartment> loadAptList();

    Apartment loadAptByID(int postID);

    // Save and update ID of input with insert row id
    boolean saveApt(Apartment post);

    List<Apartment> loadAptByPrice(double min, double max);

    List<Apartment> loadAptByType(String type);

    WishApt saveApt2WishList(Post post);

    List<Apartment> loadWishListByUserID(int userID);

    User loadUserByID(int ID);
}