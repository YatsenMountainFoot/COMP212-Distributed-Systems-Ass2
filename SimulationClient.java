import java.net.*;
import java.io.*;

public class SimulationClient extends ClientServer {
    protected Socket socket;

    public SimulationClient(String url, int port){
        try{
            socket = new Socket(url, port);
            System.out.println("Client connected to " + url + ": " + port);

        }catch(Exception e){
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void run(){
        try{
            requestService(socket);
            socket.close();
            System.out.println("Client: connection closed");
        }catch (IOException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }


    protected void requestService(Socket socket) throws IOException{

        String servStr = readFromSocket(socket);    //check connection
        System.out.println("SERVER: " + servStr);   //Report server

        if(servStr.substring(0,5).equals("Hello")){
            servStr = readFromSocket(socket);    //check for "He
            System.out.println("SERVER: " + servStr);   //Report ser

            String userStr = "";
            userStr =readFromKeyboard();                //get input from user
            writeToSocket(socket, userStr + "\n");  //Send it to server
            servStr = readFromSocket(socket);           //Read server's response
            System.out.println("SERVER: " + servStr);   //Report server's response

            if(userStr.toLowerCase().equals("1") || userStr.toLowerCase().equals("2")){
                userStr = readFromKeyboard();
                writeToSocket(socket, userStr + "\n");  // get the number of processors

                servStr = readFromSocket(socket);
                System.out.println("SERVER: " + servStr);   //Report server's simulation result
            }
        }
    }


    protected String readFromKeyboard() throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("INPUT: ");
        String line = input.readLine();
        return line;
    }

    public static void main(String args[]){
        SimulationClient client = new SimulationClient("localhost", 10001);
        client.start();
    }

}
