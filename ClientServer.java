
import java.io.*;
import  java.net.*;

public class ClientServer extends Thread{

    protected InputStream iStream;
    protected OutputStream oStream;

    // read from socket
    protected String readFromSocket(Socket sock) throws IOException{
        iStream = sock.getInputStream();
        String str = "";
        char c;
        while( (c = (char) iStream.read()) != '\n')
            str = str + c + "";
        return str;
    }

    // write to socket
    protected void writeToSocket(Socket sock, String str) throws IOException{
        oStream = sock.getOutputStream();
        if(str.charAt(str.length()-1) != '\n')
            str = str + '\n';
        for(int k = 0; k < str.length(); k++)
            oStream.write(str.charAt(k));
    }

}
