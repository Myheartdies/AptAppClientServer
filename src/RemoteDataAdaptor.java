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
        System.out.println("connecting");
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
        Conn();
        String json = generateReq(RequestModel.SAVE_USER_REQEUST, user);
        // System.out.println(json);
        try {
            ResponseModel res = getResponse(json);
            // User user = gson.fromJson(res.body, User.class);

            if (res.code == ResponseModel.UNKNOWN_REQUEST) {
                System.out.println("The request is not recognized by the Server");
                return false;
            } else // this is a JSON string for a product information
            if (res.code == ResponseModel.SAVE_FAILED) {
                System.out.println("Register Failure");
                return false;
            } else {
                user.setUserID(Integer.parseInt(res.body));
                System.out.println("Register successful with new userID" + user.getUserID());
                return true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Apartment> loadAptList() {
        Conn();
        String json = generateReq(RequestModel.LOAD_POST_ALL, null);

        try {
            ResponseModel res = getResponse(json);
            if (res.code == ResponseModel.UNKNOWN_REQUEST) {
                System.out.println("The request is not recognized by the Server");
                return null;
            } else // this is a JSON string for a product information
            if (res.code == ResponseModel.DATA_NOT_FOUND) {
                System.out.println("Register Failure");
                return null;
            } else {
                ArrayList<Apartment> apts = gson.fromJson(res.body, new TypeToken<ArrayList<Apartment>>() {
                }.getType());
                return apts;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public Apartment loadAptByID(int postID) {
        Conn();
        String json = generateReq(RequestModel.LOAD_POST_REQUEST, postID);
        try {
            ResponseModel res = getResponse(json);
            Apartment apt = gson.fromJson(res.body, Apartment.class);

            if (res.code == ResponseModel.UNKNOWN_REQUEST) {
                System.out.println("The request is not recognized by the Server");
                return null;
            } else // this is a JSON string for a product information
            if (res.code == ResponseModel.DATA_NOT_FOUND) {
                System.out.println("The Server could not find an apartment with that ID!");
                return null;
            } else {
                return apt;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean saveApt(Apartment post) {
        Conn();
        String json = generateReq(RequestModel.SAVE_POST_REQUEST, post);
        // System.out.println(json);
        try {
            ResponseModel res = getResponse(json);
            // User user = gson.fromJson(res.body, User.class);
            if (res.code == ResponseModel.UNKNOWN_REQUEST) {
                System.out.println("The request is not recognized by the Server");
                return false;
            } else // this is a JSON string for a product information
            if (res.code == ResponseModel.SAVE_FAILED) {
                System.out.println("Posting failure");
                return false;
            } else {
                post.setID(Integer.parseInt(res.body));
                System.out.println("Save successful with new apartmentID " + post.getID());
                return true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Apartment> loadAptByPrice(double min, double max) {
        Conn();
        RequestModel req = new RequestModel();
        req.code = req.LOAD_POST_BY_PRICE;
        JsonObject priceRange = new JsonObject();
        priceRange.addProperty("min", min);
        priceRange.addProperty("max", max);
        req.body = gson.toJson(priceRange);
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
                    List<Apartment> apartments = gson.fromJson(res.body, listType);
                    return apartments;
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Apartment> loadAptByType(String type) {
        Conn();
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'loadAptByType'");
    }

    @Override
    public WishApt saveApt2WishList(WishApt wishApt) {
        Conn();
        RequestModel req = new RequestModel();
        req.code = req.SAVE_APT_TO_WISHLIST;
        req.body = gson.toJson(wishApt);

        String json = gson.toJson(req);
        try {
            dos.writeUTF(json);
            String received = dis.readUTF();
            System.out.println("Server response:" + received);
            ResponseModel res = gson.fromJson(received, ResponseModel.class);

            if (res.code == ResponseModel.UNKNOWN_REQUEST) {
                System.out.println("The request is not recognized by the Server");
                return null;
            }
            else         // this is a JSON string for a product information
                if (res.code == ResponseModel.SAVE_FAILED) {
                    System.out.println("Save Failed!");
                    return null;
                }
                else {
                    System.out.println("Save Successfully!");
                    WishApt wishAptRes = gson.fromJson(req.body, WishApt.class);
                    return wishAptRes;
                }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
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
        Conn();
        String json = generateReq(RequestModel.LOAD_USER_BY_ID, ID);
        try {
            ResponseModel res = getResponse(json);
            User user = gson.fromJson(res.body, User.class);

            if (res.code == ResponseModel.UNKNOWN_REQUEST) {
                System.out.println("The request is not recognized by the Server");
                return null;
            } else // this is a JSON string for a product information
            if (res.code == ResponseModel.DATA_NOT_FOUND) {
                System.out.println("The Server could not find an apartment with that ID!");
                return null;
            } else {
                return user;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
