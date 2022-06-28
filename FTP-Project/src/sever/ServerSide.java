package sever;
import java.net.*;
import java.io.*;



public class ServerSide implements Runnable{
    
    Socket ClientSoc;

    public ServerSide(Socket ClientSoc) {
        this.ClientSoc = ClientSoc;
    }  
    void SendFile(DataOutputStream dout,OutputStream os) throws Exception
    {   
        byte b[]=new byte[544114];
        try (FileInputStream fI = new FileInputStream("C:\\Users\\Mohamed Fayez\\Desktop\\send from server.txt")) {
            fI.read(b, 0, b.length);
            
            os.write(b);
            
            dout.writeUTF("File send Successfully");
            System.out.println("File send Successfully");
        }
    }   
    void ReceiveFile(DataOutputStream dout,InputStream is) throws Exception
    {
        byte b[]=new byte[544114];
        try (FileOutputStream fI = new FileOutputStream("C:\\Users\\Mohamed Fayez\\Desktop\\recived from client.txt")) {
            is.read(b, 0, b.length);
            fI.write(b, 0, b.length);
            
            dout.writeUTF("File Received Successfully");
            System.out.println("File Received Successfully");
        }
    }
    @Override
    public void run()
    {
        try
        {
            boolean flag=true;
        
            DataOutputStream dout;
            OutputStream os;
            InputStream is;
            try (DataInputStream din = new DataInputStream(ClientSoc.getInputStream())) {
                dout = new DataOutputStream(ClientSoc.getOutputStream());
                os = ClientSoc.getOutputStream();
                is = ClientSoc.getInputStream();
                System.out.println("Waiting for Action  ...");
                int Command;
                while (flag) {
                    
                    
                    Command=din.readInt();
                    switch(Command){
                        case 1:
                            System.out.println("\t Receive Command Received ...");
                            ReceiveFile(dout, is);
                            break;
                        case 2:
                            System.out.println("\tSEND Command Accepted ...");
                            SendFile(dout, os);
                            break;
                        case 3:
                            System.out.println("\tDisconnect Command Received ...");
                            flag=false;
                            break;
                    }
                }
            dout.close();
            os.close();
            is.close();
            ClientSoc.close();
            }
            

        }
        catch(Exception ex)
        {
        }    
    }
    public static void main(String args[]) throws Exception
    {
        ServerSocket soc=new ServerSocket(5000);
        System.out.println("FTP Server Will Start On Port = 5000");
        while(true)
        {
            System.out.println("Connecting......");
            Socket S = soc.accept();
            Thread client =new Thread(new ServerSide(S));
            client.start();
            
        }
    }
}
