
import java.net.*;
import java.io.*;

public class SimulationServer extends ClientServer {
    private ServerSocket port;
    private Socket socket;

    public SimulationServer(int portNum, int nBackLog){
        try{
            port = new ServerSocket(portNum, nBackLog);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void run(){
        try{
            System.out.println("Simulator server at " + Inet4Address.getLocalHost() + " waiting for connetions");
            while(true){
                socket = port.accept();
                System.out.println("Accepted a connection from " + socket.getInetAddress());

                provideService(socket);
                socket.close();
                System.out.println("Close the connection\n");
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    protected void provideService(Socket socket){
        String str = "";
        try{
            writeToSocket(socket, "Hello, connection established. Please choose a simulator for Leader Election Algorithm Demo:\"\n");
            writeToSocket(socket, "1) LCR simulator\t2) HS simulator\t 3) Any other input to Exit");

                str = readFromSocket(socket);
                if (str.equals("1")) {
                    writeToSocket(socket, "LCR Simulator selected. Please enter the number of processors for random LCR simulation: \n");

                    str = readFromSocket(socket);
                    try {
                        int numOfProcessors = Integer.parseInt(str);
                        if (numOfProcessors < 30000 && numOfProcessors > 0)
                            str = Ass1LCRSimulator.LCRSimulator(numOfProcessors);
                        else
                            str = "Invalid input. Only 1 integer between 1-29999 is acceptable. Mission aborted.";
                        writeToSocket(socket, str);

                    }catch (NumberFormatException e){
                        str = "Invalid input. Only 1 integer between 1-29999 is acceptable. Mission aborted.";
                        writeToSocket(socket, str);
                    }
                    //Ass1LCRSimulator.LCRSimulator();
                }
                else if (str.equals("2")) {
                    writeToSocket(socket, "HS Simulator selected. Please enter the number of processors for random HS simulation: \n");

                    str = readFromSocket(socket);
                    try {

                        int numOfProcessors = Integer.parseInt(str);
                        if (numOfProcessors < 30000 && numOfProcessors > 0)
                            str = Ass1HSSimulator.HSSimulator(numOfProcessors);
                        else
                            str = "Invalid input. Only 1 integer between 1-29999 is acceptable. Mission aborted.";
                        writeToSocket(socket, str);
                    }catch (NumberFormatException e){
                        str = "Invalid input. Only 1 integer between 1-29999 is acceptable. Mission aborted.";
                        writeToSocket(socket, str);
                    }
                }
                else
                    writeToSocket(socket, "Goodbye\n");



        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String args[]){
        SimulationServer simServer= new SimulationServer(10001, 3);
        simServer.start();
    }

}
