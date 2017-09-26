package com.huat.Util;


import java.io.*;

public class IOutil {
    public static void writeObject(Object obj, OutputStream os){
        try {
            ObjectOutputStream oos=new ObjectOutputStream(os);
            oos.writeObject(obj);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static Object readObject(InputStream is){
        Object obj=null;
        try {
            ObjectInputStream ois=new ObjectInputStream(is);
            obj=ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }
}
