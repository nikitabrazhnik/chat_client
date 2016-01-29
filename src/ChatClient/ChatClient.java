/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChatClient;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author brazhnik
 */
public class ChatClient {

    static Socket cs;
    final static int SERVER_PORT = 6000;
    final static String SERVER_ADDRESS = "127.0.0.1";
    static boolean serverIsAvailable;

    public static void main(String[] args) throws IOException {

        MainFrameClient mfc = new MainFrameClient();
        mfc.setVisible(true);

        serverIsAvailable = false;
        while (!serverIsAvailable) {
            try {
                cs = new Socket(SERVER_ADDRESS, SERVER_PORT);
                mfc.setServerStatus("Connection is established");
                serverIsAvailable = true;
            } catch (Exception e) {
            }
        }

        DataOutputStream dos = new DataOutputStream(cs.getOutputStream());
        mfc.setDOS(dos);

        ClientListener clientListener = new ClientListener();
        clientListener.setMessageBox(mfc.getMessageBox());
        clientListener.setSocket(cs);

        Thread threadClientListener = new Thread(clientListener);
        threadClientListener.start();

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                try {
                    dos.writeUTF("exit");
                } catch (IOException ex) {
                    Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

    }

}
