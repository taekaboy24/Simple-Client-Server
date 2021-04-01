/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udpechoserver;

import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 *
 * @author Gary Lynch - x17140382 Web Services and API Development
 */
public class UDPEchoServer {

    private static final int PORT = 1234;
    private static DatagramSocket dgramSocket;
    private static DatagramPacket inPacket, outPacket;
    private static byte[] buffer;

    public static void main(String[] args) {
        DateTimeFormatter currentDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime now = LocalDateTime.now();
        String date = (currentDate.format(now));
        DateTimeFormatter currentTime = DateTimeFormatter.ofPattern("HH:mm:ss");
        String time = (currentTime.format(now));
        System.out.println("Opening port...\n");
        try {
            dgramSocket = new DatagramSocket(PORT);
        } catch (SocketException e) {
            System.out.println("Unable to attach to port!");
            System.exit(1);
        }
        run(date, time);
    }

    private static void run(String date, String time) {
        try {
            String messageIn, messageOut;
            do {
                buffer = new byte[256];
                inPacket = new DatagramPacket(buffer, buffer.length);
                dgramSocket.receive(inPacket);

                InetAddress clientAddress = inPacket.getAddress();
                int clientPort = inPacket.getPort();

                messageIn = new String(inPacket.getData(), 0, inPacket.getLength());

                System.out.println("Message received.");
                if (messageIn.toUpperCase().equals("DATE")) {
                    messageOut = date;
                } else if (messageIn.toUpperCase().equals("TIME")) {
                    messageOut = time;
                } else {
                    messageOut = ("Message must only be DATE or TIME" + "\n");

                }
                outPacket = new DatagramPacket(messageOut.getBytes(),
                        messageOut.length(),
                        clientAddress,
                        clientPort);
                dgramSocket.send(outPacket);
            } while (true);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {		//If exception thrown, close connection.
            System.out.println("\n Closing connection... ");
            dgramSocket.close();
        }
    }
}
