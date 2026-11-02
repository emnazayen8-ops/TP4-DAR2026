package clientPackage;

import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class clientUDP4_2 {
    public static void main(String[] args) throws Exception {
        Scanner clavier = new Scanner(System.in);
        System.out.print("Nom d'utilisateur : ");
        String nom = clavier.nextLine().trim();

        DatagramSocket socket = new DatagramSocket();
        InetAddress serveur = InetAddress.getByName("localhost");
        int port = 1234;

        Thread reception = new Thread(() -> {
            byte[] buffer = new byte[1024];
            try {
                while (!socket.isClosed()) {
                    DatagramPacket recu = new DatagramPacket(buffer, buffer.length);
                    socket.receive(recu);
                    System.out.println(new String(recu.getData(), 0, recu.getLength(),
                            StandardCharsets.UTF_8));
                }
            } catch (Exception e) {
            }
        });
        reception.setDaemon(true);
        reception.start();

        envoyer(socket, "[" + nom + "] : s'est connecté(e)", serveur, port);
        while (true) {
            String texte = clavier.nextLine();
            if (texte.equalsIgnoreCase("quit")) break;
            envoyer(socket, "[" + nom + "] : " + texte, serveur, port);
        }
        socket.close();
    }

    private static void envoyer(DatagramSocket s, String msg, InetAddress a, int p) throws Exception {
        byte[] d = msg.getBytes(StandardCharsets.UTF_8);
        s.send(new DatagramPacket(d, d.length, a, p));
    }
}