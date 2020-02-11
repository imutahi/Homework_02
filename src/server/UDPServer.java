// Justin Huffman 
// Ian Mutahi
package ianjustin.hw2;

import java.io.*;
import java.net.*;


public class UDPServer {
  
    
    private static InventoryList inventoryList;
    private DatagramSocket udpServerSocket;
    // The udp server socket.
    private DatagramPacket udpPacketIN, udpPacketOUT;
    // udpPacketIN is the packet received from client
    // udpPacketOUT is the packet sent to the server
    private String msgFromClient, msgToClient;
    // The messages sent to and received from the client
    private boolean morePackets;
    private byte[] buffIn;
    // Buffer for receiving messages
    private byte[] buffOut;
    // Buffer for sending messages
    /**
    * This main method creates a new UDP server.
    */
    public static void main(String[] args) {
       new UDPServer();
    }

    /**
    * This then Instantiates a new UDP server and begins to initialize variables.
    */
    public UDPServer() {

        inventoryList = new InventoryList();
        morePackets = true;
        udpPacketIN = null;
        udpPacketOUT = null;
        msgFromClient = null;
        msgToClient = null;
        buffIn = new byte[256];
        buffOut = new byte[256];

        try {
           udpServerSocket = new DatagramSocket(5678);
        } catch (SocketException e1) {
           System.out.println("Unable to create DatagramSocket.");
           e1.printStackTrace();
        }
        startServer();
    }

    public void startServer() {

        System.out.println("\nServer Started!...");
        System.out.println("Press 'Control+C' at any time to close the server\n");

        while (morePackets) {

            try {
                // To Receive the UDP packet from client
                udpPacketIN = new DatagramPacket(buffIn, buffIn.length);
                udpServerSocket.receive(udpPacketIN);
                msgFromClient = new String(udpPacketIN.getData(), 0, udpPacketIN.getLength());
                System.out.println("New request from " + udpPacketIN.getSocketAddress() +": '" + msgFromClient +"' \n");
                // Uses the clients message to decide what to do
                if (msgFromClient.equals("initialize")) {

                    System.out.println("Client: " + udpPacketIN.getSocketAddress() + " connected.\n");
                    // msgToClient = inventoryList.toString();

                } else if (msgFromClient.equals("exit")) {
                    System.out.println("Client: " + udpPacketIN.getSocketAddress() + " disconnected.\n");
                } else { // compare client input against the Item ID's in Inventory
                    msgToClient = inventoryList.getItem(msgFromClient).toJson();
                    System.out.println("Response to "+ udpPacketIN.getSocketAddress() +": \n"+ msgToClient +" \n\n");
                }

                // send the response to the client at the address and  the port
                InetAddress address = udpPacketIN.getAddress();
                int port = udpPacketIN.getPort();
                buffOut = msgToClient.getBytes();
                udpPacketOUT = new DatagramPacket(buffOut, buffOut.length, address, port);
                udpServerSocket.send(udpPacketOUT);

            } catch (IOException e) {
                e.printStackTrace();
                morePackets = false;
            }
        }
        udpServerSocket.close();
    }
}
