package com.suyi.filereceiver;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.Date;

@ParseClassName("Post")
public class File extends ParseObject
{
    public static final String KEY_SENDER = "Sender";
    public static final String KEY_FILE = "File";
    public static final String KEY_RECEIVER = "Receiver";
    public static final String KEY_CODE = "Code";
    public static final String KEY_CREATED_AT = "createdAt";

    public String getSender() { return getString(KEY_SENDER); }
    public void setSender(ParseUser
                                  sender) { put(KEY_SENDER, sender); }

    public ParseFile getFile() { return getParseFile(KEY_FILE); }
    public void setFile(ParseFile parseFile) { put(KEY_FILE, parseFile); }

    public String getReceiver() { return getString(KEY_RECEIVER); }
    public void setReceiver(String receiver) { put(KEY_RECEIVER, receiver); }

    public String getCode() { return getString(KEY_CODE); }
    public void setCode(String code) { put(KEY_CODE, code); }


}


/*
* package com.suyi.filereceiver;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Post")
public class Post extends ParseObject {

    public static final String KEY_RECEIVER = "receiver";
    public static final String KEY_FILE = "file";
    public static final String KEY_USER = "user";
    public static final String KEY_CREATED_AT = "createdAt";

    public String getReceiver() { return getString(KEY_RECEIVER); }
    public void setReceiver(String receiver) { put(KEY_RECEIVER, receiver); }

    public ParseFile getFile() {return getParseFile(KEY_FILE); }
    public void setFile(ParseFile parseFile) { put(KEY_FILE,parseFile); }

    public ParseUser getUser() { return getParseUser(KEY_USER); }
    public void setUser(ParseUser user) { put(KEY_USER, user); }
}

*
* */
