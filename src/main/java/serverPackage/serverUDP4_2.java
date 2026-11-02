package serverPackage;

import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;

public class serverUDP4_2 {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket(null);
        socket.bind(new InetSocketAddress(1234));
        System.out.println("Serveur en attente sur le port 1234...");

        AtomicReference<SocketAddress> client = new AtomicReference<>();

        Thread reception = new Thread(() -> {
            byte[] buffer = new byte[1024];
            try {
                while (true) {
                    DatagramPacket recu = new DatagramPacket(buffer, buffer.length);
                    socket.receive(recu);
                    client.set(recu.getSocketAddress());
                    System.out.println(new String(recu.getData(), 0, recu.getLength(),
                            StandardCharsets.UTF_8));
                }
            } catch (Exception e) {
            }
        });
        reception.setDaemon(true);
        reception.start();

        Scanner clavier = new Scanner(System.in);
        while (true) {
            String texte = clavier.nextLine();
            if (texte.equalsIgnoreCase("quit")) break;

            SocketAddress destinataire = client.get();
            if (destinataire == null) {
                System.out.println("(aucun client connecté pour l'instant)");
                continue;
            }
            byte[] donnees = ("[Serveur] : " + texte).getBytes(StandardCharsets.UTF_8);
            socket.send(new DatagramPacket(donnees, donnees.length, destinataire));
        }
        socket.close();
    }
}