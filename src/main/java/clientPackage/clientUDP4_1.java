package clientPackage;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;


public class clientUDP4_1 {

    public static void main(String[] args) throws Exception {
        Scanner clavier = new Scanner(System.in);

        System.out.print("Nom d'utilisateur : ");
        String nom = clavier.nextLine().trim();

        DatagramSocket socket = new DatagramSocket();
        InetAddress adresseServeur = InetAddress.getByName("localhost");
        int portServeur = 1234;

        System.out.println("Tapez vos messages ('quit' pour quitter).");
        while (true) {
            String texte = clavier.nextLine();
            if (texte.equalsIgnoreCase("quit")) {
                break;
            }

            String message = "[" + nom + "] : " + texte;
            byte[] donnees = message.getBytes(StandardCharsets.UTF_8);

            DatagramPacket paquet = new DatagramPacket(
                    donnees, donnees.length, adresseServeur, portServeur);
            socket.send(paquet);
        }

        socket.close();
        clavier.close();
        System.out.println("Client fermé.");
    }
}

