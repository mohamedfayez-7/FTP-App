package client;

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class ClientSide {

    
    
    public static void main(String args[]) throws Exception
    {
        boolean flag=true;
        Scanner in = new Scanner(System.in);
        Socket soc=new Socket("localhost",5000);
        DataInputStream din=new DataInputStream(soc.getInputStream());
        DataOutputStream dout=new DataOutputStream(soc.getOutputStream());
        OutputStream os=soc.getOutputStream();
        InputStream is=soc.getInputStream();
        
        
        System.out.println("[ MENU ]");
        System.out.println("1. Send A File");
        System.out.println("2. Receive A File");
        System.out.println("3. Exit");
        
        String m;
        int choice;
        while(flag)
        { 
            System.out.print("\nEnter Choice :");
            choice=in.nextInt();
            switch (choice) {
                case 1:
                    dout.writeInt(1);
                    System.out.println("Sending");
                    byte b[]=new byte[544114];
                    FileInputStream fi=new FileInputStream("C:\\Users\\Mohamed Fayez\\Desktop\\send from client.txt");
                    fi.read(b, 0, b.length);
                    os.write(b);
                     m=din.readUTF();
                    System.out.println(m);
                    fi.close();
                    break;
                case 2:
                    dout.writeInt(2);
                    System.out.println("reciving");
                    byte b2[]=new byte[544114];
                    FileOutputStream f2=new FileOutputStream("C:\\Users\\Mohamed Fayez\\Desktop\\recived from server.txt");
                    is.read(b2, 0, b2.length);
                    f2.write(b2, 0, b2.length);
        
                     m=din.readUTF();
                     System.out.println(m);
        
                    f2.close();
                    break;
                case 3:
                    dout.writeInt(3);
                    System.out.println("Bye");
                    flag=false;
                    break;
            }
        }
        din.close();
        dout.close();
        os.close();
        is.close();
        soc.close();
    }
}
