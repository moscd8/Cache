package main.java.com.hit.util;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class CLI extends java.util.Observable implements java.lang.Runnable {

        private Scanner scanner;
        private PrintStream printStream;

        private OutputStream outputStream;
        private InputStream inputStream;

        public CLI(InputStream in, OutputStream out)
        {this.inputStream=in;
        this.outputStream=out;
        scanner=new Scanner(inputStream);
        printStream=new PrintStream(outputStream);
        }

    @Override
        public void run()
        {
            String command;
            boolean connection=true;
            while (true)
            {
                System.out.println("Enter start or stop");
                command=scanner.nextLine().toLowerCase();

                if(command.equals("start"))
                {
                    hasChanged();
                    notifyObservers(command);
                    //connection=false;
                    clearChanged();
                }
                else if(command.equals("stop"))
                    {
                       hasChanged();
                       notifyObservers(command);
                       System.out.println("you choose start...");
                    }
                    else {
                        System.out.println("Not Vailed .. choose again..");
                        command=" ";
                    }
             }
        }


    @Override
    public void notifyObservers(Object arg) {
        setChanged();
        super.notifyObservers(arg.toString());
   }
}
