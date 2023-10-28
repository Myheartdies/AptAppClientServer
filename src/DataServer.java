import java.io.*;
import java.text.*;
import java.util.*;
import java.net.*;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class DataServer {
    public static void main(String[] args) throws IOException {
        // server is listening on port 5056
        ServerSocket ss = new ServerSocket(9100);

        // running infinite loop for getting
        // client request
        DataInputStream dis = null;
        DataOutputStream dos = null;
        DataAccess dao = new SQLiteDataAdapter();

        System.out.println("Starting server program!!!");
        dao.Conn();
        while (true) {
            Socket s = null;

            try {
                // socket object to receive incoming client requests
                System.out.println("========================\nAccepting new requests:");
                s = ss.accept();
                System.out.println("A new connection");

                // obtaining input and out streams
                dis = new DataInputStream(s.getInputStream());
                dos = new DataOutputStream(s.getOutputStream());

                System.out.println("Creating new clientHandler to process this client");

                // create a client handler object
                ClientHandler t = new ClientHandler(s, dis, dos, dao);

                // Invoking the start() method
                t.handleRequest();
                s.close();

            } catch (Exception e) {
                s.close();
                e.printStackTrace();
            }
        }
    }

}

// ClientHandler class
class ClientHandler {
    final DataInputStream dis;
    final DataOutputStream dos;
    final Socket s;

    Gson gson = new Gson();

    DataAccess dao;

    // Constructor
    ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos, DataAccess dao) {
        this.s = s;
        this.dis = dis;
        this.dos = dos;
        this.dao = dao;
        // dao.connect();
    }

    // List<Integer> test = new ArrayList<>(Arrays.asList(1, 3, 4, 5));

    public void handleRequest() {
        String received;

        try {
            // System.out.println("Json Test: " + gson.toJson(test));
            // receive the answer from client
            received = dis.readUTF();

            System.out.println("Message from client " + received);

            RequestModel req = gson.fromJson(received, RequestModel.class);
            if (req.code == RequestModel.EXIT_REQUEST) {
                System.out.println("Client " + this.s + " sends exit...");
                System.out.println("Closing this connection.");
                this.s.close();
                System.out.println("Connection closed");
                try {
                    // closing resources
                    this.dis.close();
                    this.dos.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
                // break;
            }

            ResponseModel res = new ResponseModel();
            res.code = ResponseModel.UNKNOWN_REQUEST;
            res.body = null;
            if (req.code == RequestModel.USER_LOGIN) {
                LoginReq(req, res);
            } else if (req.code == RequestModel.SAVE_USER_REQEUST) {
                RegisterReq(req, res);
            } else if (req.code == RequestModel.LOAD_USER_BY_ID) {
                LoadUserByIDReq(req, res);
            } else if (req.code == RequestModel.SAVE_POST_REQUEST) {
                SaveAptReq(req, res);
            } else if (req.code == RequestModel.LOAD_POST_REQUEST) {
                LoadOneAptReq(req, res);
            } else if (req.code == RequestModel.LOAD_POST_BY_PRICE) {
            } else if (req.code == RequestModel.LOAD_POST_BY_TYPE) {
            } else if (req.code == RequestModel.LOAD_POST_ALL) {
                LoadAllApts(req, res);
            } else if (req.code == RequestModel.SAVE_APT_TO_WISHLIST) {
            } else if (req.code == RequestModel.LOAD_WISHLIST_BY_USERID) {
                res = loadWishListByUserID(req);
            }

            String json = gson.toJson(res);
            System.out.println("JSON object of ResponseModel: " + json);

            dos.writeUTF(json);
            dos.flush();
        } catch (

        IOException e) {
            e.printStackTrace();
        }
    }


    private ResponseModel loadWishListByUserID(RequestModel req) {
        ResponseModel res = new ResponseModel();
        User userReq = gson.fromJson(req.body, User.class);
        int userID = userReq.getUserID();
        List<Apartment> apartments = dao.loadWishListByUserID(userID);

        if (apartments != null) {
            res.code = ResponseModel.OK;
            res.body = gson.toJson(apartments);
        } else {
            res.code = ResponseModel.DATA_NOT_FOUND;
            res.body = "";
        }

        return res;
    }

    private void LoginReq(RequestModel req, ResponseModel res) {
        System.out.println("The Client asks for load user");
        System.out.println("Body: " + req.body);
        ArrayList<String> args = gson.fromJson(req.body, new TypeToken<ArrayList<String>>() {
        }.getType());
        // System.out.println("Serialized ");
        if (args.size() < 2) {
            res.code = ResponseModel.DATA_NOT_FOUND;
            res.body = null;
        } else {
            User user = dao.loadUser(args.get(0), args.get(1));
            if (user != null) {
                res.code = ResponseModel.OK;
                res.body = gson.toJson(user);
            } else {
                res.code = ResponseModel.DATA_NOT_FOUND;
            }
        }
    }

    private void RegisterReq(RequestModel req, ResponseModel res) {
        System.out.println("The Client asks for save user");
        System.out.println("Body: " + req.body);
        User user = gson.fromJson(req.body, User.class);
        if (dao.saveUser(user)) {
            res.code = ResponseModel.OK;
            res.body = Integer.toString(user.getUserID());
        } else {
            res.code = ResponseModel.SAVE_FAILED;
            res.body = null;
        }

    }

    private void SaveAptReq(RequestModel req, ResponseModel res) {
        System.out.println("The Client asks for save apartment");
        System.out.println("Body: " + req.body);
        Apartment apartment = gson.fromJson(req.body, Apartment.class);
        if (dao.saveApt(apartment)) {
            res.code = ResponseModel.OK;
            res.body = Integer.toString(apartment.getID());
        } else {
            res.code = ResponseModel.SAVE_FAILED;
            res.body = null;
        }
    }

    private void LoadAllApts(RequestModel req, ResponseModel res) {
        System.out.println("The Client asks for all apartment listings");
        System.out.println("Body: " + req.body);
        List<Apartment> listings = dao.loadAptList();
        if (listings == null) {
            res.code = ResponseModel.DATA_NOT_FOUND;
            res.body = null;
        } else {
            res.code = ResponseModel.OK;
            res.body = gson.toJson(listings);
        }
    }

    private void LoadOneAptReq(RequestModel req, ResponseModel res) {
        System.out.println("The Client asks for single apartment info");
        System.out.println("Body: " + req.body);
        try {
            Apartment apt = dao.loadAptByID(Integer.parseInt(req.body));
            if (apt == null) {
                res.code = ResponseModel.DATA_NOT_FOUND;
                res.body = null;
                return;
            }
            res.code = ResponseModel.OK;
            res.body = gson.toJson(apt);
        } catch (Exception e) {
            res.code = ResponseModel.DATA_NOT_FOUND;
            res.body = null;
            e.printStackTrace();
        }
    }

    private void LoadUserByIDReq(RequestModel req, ResponseModel res) {
        System.out.println("The Client asks for a user with userID");
        System.out.println("Body: " + req.body);
        try {
            User user = dao.loadUserByID(Integer.parseInt(req.body));
            if (user == null) {
                res.code = ResponseModel.DATA_NOT_FOUND;
                res.body = null;
                return;
            }
            res.code = ResponseModel.OK;
            res.body = gson.toJson(user);
        } catch (Exception e) {
            res.code = ResponseModel.DATA_NOT_FOUND;
            res.body = null;
            e.printStackTrace();
        }
    }
}