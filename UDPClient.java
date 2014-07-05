
import java.io.*;
import java.net.*;
class UDPClient {
    public static void main(String args[]) throws Exception
    {
    	System.out.println("Please enter the name of the file (including extension that you want to download) ex. tux.jpg");
        BufferedReader inFromUser =
        new BufferedReader(new InputStreamReader(System.in));
        DatagramSocket clientSocket = new DatagramSocket();//port # is assigned by OS to the client
        InetAddress IPAddress = 
            InetAddress.getByName("localhost");
        byte[] receiveData = new byte[1024];
        String sentence = inFromUser.readLine();
        String userinput="1 "+sentence +" 0";
        byte[] sendData= userinput.getBytes();
        DatagramPacket sendPacket =
        new DatagramPacket(sendData, sendData.length, 
                               IPAddress, 9876); //data with server's IP and server's port #
        clientSocket.send(sendPacket);
        DatagramPacket receivePacket =
            new DatagramPacket(receiveData,
                               receiveData.length);
        clientSocket.setSoTimeout(10000000);
        clientSocket.receive(receivePacket);
        String modifiedSentence =
            new String(receivePacket.getData(),
                       0,
                       receivePacket.getLength());
        System.out.println("FROM SERVER:" +
                           modifiedSentence);
        DatagramPacket receivePacket2 =
            new DatagramPacket(receiveData,
                               receiveData.length);
        clientSocket.setSoTimeout(10000000);
        clientSocket.receive(receivePacket2);
        String modifiedSentence2 =
            new String(receivePacket2.getData(),
                       0,
                       receivePacket2.getLength());
        System.out.println("FROM SERVER:" +
                           modifiedSentence2);
        String response="4 file not found 0";
        if(modifiedSentence2.equals(response))
        {
        System.out.println("System now exiting");
        System.exit(0);
        }
        else
        {
        DatagramPacket receivePacket3 =
            new DatagramPacket(receiveData,
                               receiveData.length);
        clientSocket.setSoTimeout(10000000);
        clientSocket.receive(receivePacket3);
        String modifiedSentence3 =
            new String(receivePacket3.getData(),
                       0,
                       receivePacket3.getLength());
        System.out.println("FROM SERVER:" +
                           modifiedSentence3);
        }
    }
}
