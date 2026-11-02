package serverPackage;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

public class serverUDP4_3 {

    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket(null);
        socket.bind(new InetSocketAddress(1234));
        System.out.println("Serveur de chat lancé sur le port 1234");

        Set<SocketAddress> clients = new HashSet<>();
        byte[] buffer = new byte[1024];

        while (true) {
            DatagramPacket recu = new DatagramPacket(buffer, buffer.length);
            socket.receive(recu);

            SocketAddress expediteur = recu.getSocketAddress();
            String message = new String(recu.getData(), 0, recu.getLength(),
                    StandardCharsets.UTF_8);

            if (clients.add(expediteur)) {
                System.out.println("Nouveau client enregistré : " + expediteur
                        + " (total : " + clients.size() + ")");
            }

            System.out.println("De " + expediteur + " -> " + message);

            byte[] donnees = message.getBytes(StandardCharsets.UTF_8);
            for (SocketAddress client : clients) {
                if (!client.equals(expediteur)) {
                    DatagramPacket envoi = new DatagramPacket(
                            donnees, donnees.length, client);
                    socket.send(envoi);
                }
            }
        }
    }
}

