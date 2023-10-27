public class ResponseModel {
    static public int OK = 0;
    static public int UNKOWN_REQUEST = 1;
    static public int DATA_NOT_FOUND = 2;
    static public int SAVE_FAILED = 3; // Body will contain failure return value

    public int code;
    public String body;
}
