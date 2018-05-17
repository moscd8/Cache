package main.java.com.hit.server;

import com.google.gson.Gson;
import main.java.com.hit.dm.DataModel;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class Client
{
    String IPAddress;
    public static void main(String[] args) throws IOException
    {
        Scanner scanner = new Scanner (System.in);
        DataModel[] dataModel = new DataModel[1];

        dataModel[0] = new DataModel (Long.valueOf (100),Integer.valueOf (30));


        Map<String, String> map = new HashMap<> ();
        String action = "action";
        String header = "UPDATE";
        header = scanner.nextLine ();
        scanner.reset ();
        map.put (action,header);
        Request request = new Request (map,dataModel);
        Gson gson = new Gson ();
        InetAddress address = InetAddress.getLocalHost ();
        Socket clinetSocket = new Socket (address.getHostAddress (),12345);

        ObjectOutputStream output=new ObjectOutputStream(clinetSocket.getOutputStream());
        ObjectInputStream input=new ObjectInputStream (clinetSocket.getInputStream());
        String gsonModel = gson.toJson (request);
        output.writeObject (gsonModel);
        DataModel model;
        String inputGson;
        while(true)
        {
            try
            {
                inputGson = (String) input.readObject ();
                System.out.println (inputGson);
                model = gson.fromJson (inputGson,DataModel.class);
                System.out.println (model.toString ());
            }
            catch (ClassNotFoundException e)
            {
                e.printStackTrace ();
            }

        }
    }
}

