import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.awt.SystemTray;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.Socket;
import java.sql.*;
import java.util.*;

public class RemoteDataAdaptor implements DataAccess {
    Gson gson = new Gson();
    Socket s = null;
    DataInputStream dis = null;
    DataOutputStream dos = null;

    String generateReq(int code, Object content) {
        RequestModel req = new RequestModel();
        req.code = code;
        req.body = gson.toJson(content);
        return gson.toJson(req);
    }

    ResponseModel getResponse(String jsonReq) {
        try {
            this.dos.writeUTF(jsonReq);
            String received = dis.readUTF();
            System.out.println("Server response:" + received);
            return gson.fromJson(received, ResponseModel.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ResponseModel failure = new ResponseModel();
        failure.code = ResponseModel.UNKNOWN_REQUEST;
        return failure;
    }

    @Override
    public void Conn() {
        System.out.println("connectiing");
        try {
            this.s = new Socket("localhost", 9100);
            this.dis = new DataInputStream(s.getInputStream());
            this.dos = new DataOutputStream(s.getOutputStream());
            System.out.println("connected");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public User loadUser(String username, String password) {
        ArrayList<String> args = new ArrayList<>();
        args.add(username);
        args.add(password);
        String json = generateReq(RequestModel.USER_LOGIN, args);
        System.out.println(json);
        try {
            ResponseModel res = getResponse(json);
            User user = gson.fromJson(res.body, User.class);

            if (res.code == ResponseModel.UNKNOWN_REQUEST) {
                System.out.println("The request is not recognized by the Server");
                return null;
            } else // this is a JSON string for a product information
            if (res.code == ResponseModel.DATA_NOT_FOUND) {
                System.out.println("The Server could not find a user with that ID!");
                return null;
            } else {
                return user;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
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
    public List<Apartment> loadWishListByUserID(int userID) {
        Conn();
        RequestModel req = new RequestModel();
        List<Apartment> apartments = new ArrayList<>();
        req.code = req.LOAD_WISHLIST_BY_USERID;
        req.body = gson.toJson(AptAppManager.getInstance().getCurrentUser());
        String json = gson.toJson(req);
        try {
            dos.writeUTF(json);
            String received = dis.readUTF();
            System.out.println("Server Response: " + received);
            ResponseModel res = gson.fromJson(received, ResponseModel.class);
            if (res.code == ResponseModel.UNKNOWN_REQUEST) {
                System.out.println("The request is not recognized by the Server");
                return null;
            } else {
                if (res.code == ResponseModel.DATA_NOT_FOUND) {
                    System.out.println("The Server could not find products within that price range!");
                    return null;
                } else {
                    Type listType = new TypeToken<List<Apartment>>() {}.getType();
                    apartments = gson.fromJson(res.body, listType);
                    System.out.println("Received a list of products");
                    for (Apartment apartment : apartments) {
                        System.out.println("Apt ID = " + apartment.getID());
                        System.out.println("Apt name = " + apartment.getAptName());
                    }
                    return apartments;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public User loadUserByID(int ID) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'loadUserByID'");
    }

}
