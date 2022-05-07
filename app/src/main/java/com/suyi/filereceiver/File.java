package com.suyi.filereceiver;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

import java.util.Date;

@ParseClassName("File")
public class File extends ParseObject
{
    public static final String KEY_SENDER = "Sender";
    public static final String KEY_FILE = "File";
    public static final String KEY_RECEIVER = "Receiver";
    public static final String KEY_UPDATEDAT = "updatedAt";

    public String getSender()
    {
        return getString(KEY_SENDER);
    }

    public void setSender(String sender)
    {
        put(KEY_SENDER, sender);
    }

    public ParseFile getFile()
    {
        return getParseFile(KEY_FILE);
    }

    public void setFile(ParseFile file)
    {
        put(KEY_FILE, file);
    }

    public String getReceiver()
    {
        return getString(KEY_RECEIVER);
    }

    public void setReceiver(String receiver)
    {
        put(KEY_RECEIVER, receiver);
    }

    public Date getDate()
    {
        return getDate(KEY_UPDATEDAT);
    }

}
