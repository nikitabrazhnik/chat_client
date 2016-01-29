/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChatClient;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;

/**
 *
 * @author brazhnik
 */
public class ClientListener implements Runnable {

    private JTextArea messageBox;
    private int serverPort;
    private String serverAddress;
    private Socket cs;

    @Override
    public void run() {
        try {
            DataInputStream dis = new DataInputStream(cs.getInputStream());

            while (true) {
                String message = dis.readUTF();
                if (!"".equals(message)) {
                    messageBox.append(message+"\n");
                } else {
                    if ("exit".equals(message)) {
                        Runtime.getRuntime().exit(0);
                    }
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(ClientListener.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void setMessageBox(JTextArea messageBox) {
        this.messageBox = messageBox;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    void setSocket(Socket cs) {
        this.cs = cs;
    }

}
