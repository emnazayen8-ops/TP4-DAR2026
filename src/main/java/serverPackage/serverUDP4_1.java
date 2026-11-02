package serverPackage;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;


public class serverUDP4_1 {

    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket(null);
        socket.bind(new InetSocketAddress(1234));
        System.out.println("Lancement du serveur sur le port 1234");

        byte[] buffer = new byte[1024];
        while (true) {
            DatagramPacket paquet = new DatagramPacket(buffer, buffer.length);
            socket.receive(paquet);

            String message = new String(paquet.getData(), 0, paquet.getLength(),
                    StandardCharsets.UTF_8);

            System.out.println("De " + paquet.getAddress().getHostAddress()
                    + ":" + paquet.getPort() + " -> " + message);
        }
    }
}

