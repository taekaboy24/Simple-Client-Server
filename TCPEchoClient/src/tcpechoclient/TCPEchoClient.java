/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcpechoclient;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 *
 * @author Gary Lynch - x17140382 Web Services and API Development
 */
public class TCPEchoClient {

    public static void main(String[] args) throws IOException {
        InetAddress ip = InetAddress.getLocalHost();
        int port = 4444;
        Scanner input = new Scanner(System.in);

        // Step 1: Open the socket connection. 
        Socket s = new Socket(ip, port);

        // Step 2: Communication-get the input and output stream 
        DataInputStream dis = new DataInputStream(s.getInputStream());
        DataOutputStream dos = new DataOutputStream(s.getOutputStream());
        try {
            while (true) {
                // Enter the equation in the form- 
                // "operand1 operation operand2" 
                System.out.print("Enter Equation: ");

                String str = input.nextLine();

                // send the equation to server 
                dos.writeUTF(str);

                // wait till request is processed and sent back to client 
                String answer = dis.readUTF();
                System.out.println("\nSERVER> " + answer);
            }
        } catch (Exception e) {
            System.exit(0);
        }
    }

}
