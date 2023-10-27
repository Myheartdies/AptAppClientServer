import java.util.*;

public interface DataAccess {
    void Conn();

    User loadUser(String Username, String password);

    boolean saveUser(User user);

    List<Apartment> loadAptList();

    Apartment loadAptByID(int postID);

    boolean saveApt(Apartment post);

    List<Apartment> loadAptByPrice(double min, double max);

    List<Apartment> loadAptByType(String type);

    WishApt saveApt2WishList(Post post);

    List<Integer> loadWishListByUserID(int userID);

    User loadUserByID(int ID);
}