import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.*;
import java.util.*;
import java.util.List;

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
            dos.writeUTF(jsonReq);
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
        try {
            s = new Socket("localhost", 5056);
            dis = new DataInputStream(s.getInputStream());
            dos = new DataOutputStream(s.getOutputStream());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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
