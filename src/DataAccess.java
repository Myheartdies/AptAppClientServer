import java.util.*;

public interface DataAccess {
    void Conn();

    User loadUser(String Username, String password);

    User saveUser(User user);

    Post loadPostByID(int postID);

    Post savePost(Post post);

    List<Post> loadPostByPrice(double min, double max);

    List<Post> loadPostByType(String type);

    WishPost savePost2WishList(Post post);

    WishPost loadWishPostByUserID(int userID);

}