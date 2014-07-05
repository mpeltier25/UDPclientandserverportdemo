
import java.io.*;
import java.net.*;
import java.nio.channels.FileChannel;

class UDPServer {
    public static void main(String args[]) throws Exception
    {
    	BufferedWriter log = new BufferedWriter(new FileWriter("C:/Users/Mat/workspace/UDPServer/bin/log.txt"));
    	DatagramSocket serverSocket = new
            DatagramSocket(9876); //server will run on port #9876
        byte[] receiveData = new byte[1024];
        String sequencenumber=null;
        int sequenceactualnumber=0;
        FileChannel inputc = null;
		FileChannel outc = null;
		FileInputStream ins = null;
		FileOutputStream outs = null;
		long transfering = 0;
        while(true)
            {
           DatagramPacket receivePacket =
           new DatagramPacket(receiveData, 
           receiveData.length);
           serverSocket.receive(receivePacket);
           String sentence = new String(
           receivePacket.getData(),
           0,
           receivePacket.getLength());
           InetAddress IPAddress =
           receivePacket.getAddress(); //get client's IP
           int port = receivePacket.getPort(); //get client's port #
		   System.out.println("client's port # =" + port);
		   log.write("\r\nclient's port # =" + port);
		   System.out.println("client'sIP =" +IPAddress);
		   log.write("\r\n client'sIP =" +IPAddress);
           System.out.println("Server recieved a "+sentence);
           log.write("\r\n Server recieved a "+sentence);
           String zero="0";
           boolean findzero=sentence.endsWith(zero);
           if (findzero==true)
           {
           System.out.println("SERVER: Creating sequence number");
           log.write("\r\nSERVER: Creating sequence number");
           sequenceactualnumber=1;
           sequencenumber="1";
           }
           String replacesentence=
           sentence.replace("1 ", "");
           String replacesentence2=replacesentence.replace(" 0", "");
		   String capitalizedSentence = 
           "3 "+sequencenumber;
           byte[] sendData = capitalizedSentence.
           getBytes();
           DatagramPacket sendPacket =
           new DatagramPacket(sendData,
           sendData.length, 
           IPAddress, port);
            serverSocket.send(sendPacket);
		    System.out.println("The file asked for is "+replacesentence2);
		    log.write("\r\n The file asked for is "+replacesentence2);
		    log.flush();
			File originalfile = new File("C:/Users/Mat/workspace/UDPServer/bin/"+replacesentence2);
			File newFile = new File("C:/Users/Mat/workspace/UDPClient/bin/"+replacesentence2);
			try {
			ins = new FileInputStream(originalfile);
			inputc = ins.getChannel();
			outs = new  FileOutputStream(newFile, false);        
		    outc = outs.getChannel();
			if(sequenceactualnumber==1)
			{
			sequenceactualnumber++;
			sequencenumber="2";
			}
		    String capitalizedSentence2 = ("2 "+sequencenumber);
			byte[] sendData2 = capitalizedSentence2.getBytes();
			DatagramPacket sendPacket2=
			new DatagramPacket(sendData2,
		    sendData2.length,IPAddress, port);
			serverSocket.send(sendPacket2);
			while(transfering < inputc.size()){
			transfering += inputc.transferTo(0, inputc.size(), outc);
			}
			String capitalizedSentence5 = ("5 End of file 0");
		    byte[] sendData5 = capitalizedSentence5.getBytes();
			DatagramPacket sendPacket5=new DatagramPacket(sendData5,sendData5.length,IPAddress, port);
			serverSocket.send(sendPacket5);
			}
			catch (FileNotFoundException ex){
			System.out.println("File not found: " + ex);
			log.write("\r\n File not found: " + ex);
			log.flush();
			String capitalizedSentence3 ="4 file not found 0";
		    byte[] sendData3 = capitalizedSentence3.getBytes();
			DatagramPacket sendPacket3=new DatagramPacket(sendData3,
			                                       sendData3.length, 
			                                       IPAddress, port);
			serverSocket.send(sendPacket3);
			String capitalizedSentence4 = "0";
			byte[] sendData4 = capitalizedSentence4.getBytes();
			DatagramPacket sendPacket4=new DatagramPacket(sendData4,
			                                       sendData4.length, 
			                                       IPAddress, port);
		    serverSocket.send(sendPacket4);
					    }
					    
}
    }
}