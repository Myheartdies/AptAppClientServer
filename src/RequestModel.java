public class RequestModel {
  static public int EXIT_REQUEST = 101; 
  static public int USER_LOGIN = 201;
  static public int SAVE_USER_REQEUST = 202;
  static public int LOAD_USER_BY_ID = 203;

  static public int SAVE_POST_REQUEST = 301;
  static public int LOAD_POST_REQUEST = 302;
  static public int LOAD_POST_BY_PRICE = 303;
  static public int LOAD_POST_BY_TYPE = 304;
  static public int LOAD_POST_ALL = 305;

  static public int SAVE_APT_TO_WISHLIST = 401;
  static public int LOAD_WISHLIST_BY_USERID = 402;

  int code;
  String body;

}
