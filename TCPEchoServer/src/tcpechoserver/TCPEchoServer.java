/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcpechoserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;

/**
 *
 * @author Gary Lynch - x17140382 Web Services and API Development
 */
public class TCPEchoServer {

    private static final int PORT = 4444;

    public static void main(String args[]) throws IOException {

        String error = "Error, Format needs to be Operand Operator Operand. e.g. 2 + 2 ... SPACE IN BETWEEN EACH CHARACTER! - 2(space)+(space)2" + "\n";

        System.out.println("Opening port...\n");
        try {
            ServerSocket servSock = new ServerSocket(PORT);
            Socket socket = servSock.accept();

            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            try {
                while (true) {
                    try {
                        String input = dis.readUTF();
                        boolean flag = false;
                        if (input.toUpperCase().equals("STOP")) {
                            System.exit(0);
                        }

                        System.out.println("Input received: " + input);
                        int result = 0;

                        // Use StringTokenizer to break the equation into operand and operation
                        StringTokenizer st = new StringTokenizer(input);

                        int oprnd1 = Integer.parseInt(st.nextToken());
                        String operation = st.nextToken();
                        int oprnd2 = Integer.parseInt(st.nextToken());

                        // perform the operation based on input
                        if (operation.equals("+")) {
                            result = oprnd1 + oprnd2;
                        } else if (operation.equals("-")) {
                            result = oprnd1 - oprnd2;
                        } else if (operation.equals("*")) {
                            result = oprnd1 * oprnd2;
                        } else if (operation.equals("/")) {
                            result = oprnd1 / oprnd2;
                        } else {
                            flag = true;
                        }
                        System.out.println("Equation Format Correct.");
                        System.out.println("Sending Answer...");

                        // send the result or error back to the client. 
                        if (!flag) {
                            dos.writeUTF("Answer = " + Integer.toString(result) + "\n");
                        } else {
                            dos.writeUTF(error);
                        }
                    } catch (Exception e) {
                        dos.writeUTF(error);
                    }
                }
            } finally {		//If exception thrown, close connection.
                System.out.println("\n Closing connection... ");
                socket.close();				//Step 9.
            }
        } catch (IOException e) {
            System.out.println("Unable to attach to port!");
            System.exit(1);
        }

    }

}
