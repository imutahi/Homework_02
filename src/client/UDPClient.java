// Ian Mutahi 
// Justin Huffman
package ianjustin.hw2;

import java.io.*;
import java.net.*;
import java.sql.Timestamp;
import java.util.*;
import com.google.gson.Gson;

/**
 * UDPClient requests item inventory from a remote server.
 */
public class UDPClient {
        
        // This Class is tailored for client/server communication
        private DatagramSocket udpSocket;
        // Input from user command line
        private BufferedReader systemInput;
        // Messages are sent from the server and user
        private String msgFromServer, msgFromUser;
        // Address o the server to connect with the User
        private InetAddress address;
        // Buffer for sending messages to server// Address o the server to connect with the User
        private byte[] bufferQuery;
        // Buffer for sending messages to server
        private DatagramPacket udpPacketRequest, udpPacketResponse;
        // udpPacketRequest is the packet that is sent to server 
        // udpPacketResponse is packet that is received from server
        // Message buffer for receiving messages from server
        private byte[] bufferReply;
        // Message buffer for receiving messages from server
        private Timestamp requestTime, responseTime;
        // used to calculate elapsed time
        /**
         * The main method creates a new UDPClient.
         */
        public static void main(String[] args) {
                new UDPClient();
        }

        /**
         * This Instantiates a new UDP client and prompts for a server address. 
         */
        public UDPClient() {
                // Create an UDPSocket for client/server communication
                try {udpSocket = new DatagramSocket();} 
                catch (SocketException e) {
                    System.out.println("Unable to create DatagramSocket.");
                    e.printStackTrace();
                }
                systemInput = new BufferedReader(new InputStreamReader(System.in));
                msgFromServer = null;
                msgFromUser = null;
                // Ask user for Internet address of the server
                System.out.println("\nPlease input the DNS or IP of the machine on which the Server Program runs");
                try {address = InetAddress.getByName(systemInput.readLine());} 
                catch (UnknownHostException e) {e.printStackTrace();} 
                catch (IOException e) {e.printStackTrace();}
                // Send packet that contains 'initialize' to let the server know that a new client is trying to connect"
                bufferQuery = "initialize".getBytes();
                bufferReply = new byte[256];
                startClient();
        }

        /**
         * This Section starts the client and allows the user to make requests until they type 'exit'.
         */
        public void startClient() {
                System.out.println("Connection started...\n");
                // Send initial request
                udpPacketRequest = new DatagramPacket(bufferQuery, bufferQuery.length, address, 5140);
                try {udpSocket.send(udpPacketRequest);} 
                catch (IOException e) {e.printStackTrace();}
                // Get the initial inventory table
                udpPacketResponse = new DatagramPacket(bufferReply, bufferReply.length);
                try {udpSocket.receive(udpPacketResponse);} 
                catch (IOException e) {e.printStackTrace();}
                System.out.println("Items in inventory: ");
                System.out.println(new String(udpPacketResponse.getData(), 0, udpPacketResponse.getLength()));
                System.out.println("Please input an ID to make a query or 'exit' to close connection");
                try {
                    while ((msgFromUser = systemInput.readLine()) != null) {
                        // send request for item details
                        bufferQuery = msgFromUser.getBytes();
                        udpPacketRequest = new DatagramPacket(bufferQuery, bufferQuery.length, address, 5140);
                        requestTime = new Timestamp(System.currentTimeMillis());
                        udpSocket.send(udpPacketRequest);
                        // get the response
                        udpPacketResponse = new DatagramPacket(bufferReply, bufferReply.length);
                        udpSocket.receive(udpPacketResponse);
                        responseTime = new Timestamp(System.currentTimeMillis());
                        long elapsedTime = responseTime.getTime() - requestTime.getTime();
                        // display the response
                        msgFromServer = new String(udpPacketResponse.getData(), 0, udpPacketResponse.getLength());
                        printMsg(msgFromServer,elapsedTime);
                        // the exit option for the user
                        if (msgFromUser.equals("exit")) {
                            System.out.println("Connection closing...");
                            break;
                        }
                        System.out.println("Please input an ID to make a query or 'exit' to close connection");
                    }
                } catch (IOException e) {e.printStackTrace();}
                udpSocket.close();
        }

        public void printMsg(String msgFromServer, Long elapsedTime) {
            Gson gson = new Gson();
            InventoryItem item = gson.fromJson(msgFromServer,InventoryItem.class);

            final Object[][] table = new String[2][]; 
            table[0] = new String[] {"Item ID","Item Description","Unit Price","Inventory","RTT"};
            List<String> values = new ArrayList<String>();
            for (String value : item.getValues()) {
                values.add(value);
            }
            values.add(elapsedTime.toString()+"ms");
            Object[] objValues = values.toArray();
            String[] strValues = Arrays.copyOf(objValues,objValues.length,String[].class);
            table[1] = strValues;

            for (final Object[] row : table) {
                System.out.format("%-10s %-30s %-20s %-10s %-10s\n", row);
            }
        }
}
