package clientPackage;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;


public class clientUDP4_3 {

    public static void main(String[] args) throws Exception {
        Scanner clavier = new Scanner(System.in);

        System.out.print("Nom d'utilisateur : ");
        String nom = clavier.nextLine().trim();

        DatagramSocket socket = new DatagramSocket();
        InetAddress adresseServeur = InetAddress.getByName("localhost");
        int portServeur = 1234;

        Thread reception = new Thread(() -> {
            byte[] buffer = new byte[1024];
            try {
                while (!socket.isClosed()) {
                    DatagramPacket recu = new DatagramPacket(buffer, buffer.length);
                    socket.receive(recu);
                    String message = new String(recu.getData(), 0, recu.getLength(),
                            StandardCharsets.UTF_8);
                    System.out.println(message);
                }
            } catch (Exception e) {
                if (!socket.isClosed()) {
                    System.out.println("Erreur de réception : " + e.getMessage());
                }
            }
        });
        reception.setDaemon(true);
        reception.start();

        envoyer(socket, "[" + nom + "] : s'est connecté(e)", adresseServeur, portServeur);

        System.out.println("Tapez vos messages ('quit' pour quitter).");
        while (true) {
            String texte = clavier.nextLine();
            if (texte.equalsIgnoreCase("quit")) {
                break;
            }
            envoyer(socket, "[" + nom + "] : " + texte, adresseServeur, portServeur);
        }

        envoyer(socket, "[" + nom + "] : a quitté le chat", adresseServeur, portServeur);
        socket.close();
        clavier.close();
    }

    private static void envoyer(DatagramSocket socket, String message,
                                InetAddress adresse, int port) throws Exception {
        byte[] donnees = message.getBytes(StandardCharsets.UTF_8);
        socket.send(new DatagramPacket(donnees, donnees.length, adresse, port));
    }
}

