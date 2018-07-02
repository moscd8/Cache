package main.java.com.hit.server;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import main.java.com.hit.dm.DataModel;
import main.java.services.CacheUnitController;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.Scanner;

public class HandleRequest<T> extends java.lang.Object implements java.lang.Runnable {
    private CacheUnitController<T> controller;
    private Socket socket;
    private Scanner reader;
    private PrintWriter writer;
    static int requestnum = 0;
    String result = "";
    static Boolean outputgson;

    public HandleRequest(Socket s, CacheUnitController<T> controller) {
        this.controller = controller;
        this.socket = s;
    }

    @Override
    public void run() {
        DataModel[] datamodel = null;
        DataModel<T>[] body;
        Request<DataModel<T>[]> request;
        String[] outputToClient = {"Failed", "Succeeded"};

        String temp = null;
        try {
            reader = new Scanner(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            String req = (String) reader.nextLine();

            if (req.equals(null))
                System.out.println("null");
            else if (req.equals("Show Statistics")) {
                temp = "";
                temp = controller.getstats(requestnum) + "#";
                System.out.println("Temp is : *" + temp);
                writer.write(temp.toCharArray());
            } else {
                System.out.println("Json Request:");
                System.out.println("req is :" + req.toString());

                Type ref = new TypeToken<Request<DataModel<T>[]>>() {
                }.getType();
                request = new Gson().fromJson(req, ref);

                String command = request.getHeaders().get("action").toUpperCase();
                System.out.println("Command is : " + command);

                body = request.getBody();
                System.out.println("body is: " + body.toString());
                DataModel[] dataModels;

                if (command != null) {
                    switch (command) {
                        case "GET": {
                            requestnum++;
                            System.out.println("get...");
                            dataModels = controller.get(body);
                            if (dataModels != null) {
                                result = outputToClient[1];
                            } else {
                                result = outputToClient[0];
                            }
                            writer.println(dataModels);
                            System.out.println("result is :" + result);
                            break;
                        }
                        case "UPDATE": {
                            requestnum++;
                            System.out.println("update..");
                            boolean update = controller.update(body);
                            if (update != false) {
                                result = outputToClient[1];
                            } else {
                                result = outputToClient[0];
                            }
                            writer.println(result);
                            break;
                        }
                        case "DELETE": {
                            requestnum++;
                            System.out.println("delete..");
                            boolean delete = controller.delete(body);
                            if (delete != false) {
                                result = outputToClient[1];
                            } else {
                                result = outputToClient[0];
                            }
                            writer.println(result);
                            break;
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("exit");
        writer.println("Exit");
        writer.flush();
        writer.close();
        reader.close();
    }

    private void writeToOutputStream(String s) {
        writer.write(s);
    }

}

