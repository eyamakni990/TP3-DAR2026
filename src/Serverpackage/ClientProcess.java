package Serverpackage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientProcess extends Thread {

    private final Socket socket;
    private final int numero;

    public ClientProcess(Socket socket, int numero) {
        this.socket = socket;
        this.numero = numero;
    }

    @Override
    public void run() {
        try (
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                PrintWriter pw = new PrintWriter(
                        socket.getOutputStream(), true)
        ) {
            pw.println("Vous êtes le client n°" + numero);

            double a = Double.parseDouble(br.readLine());
            String operateur = br.readLine();
            double b = Double.parseDouble(br.readLine());

            double resultat;

            switch (operateur) {
                case "+":
                    resultat = a + b;
                    break;
                case "-":
                    resultat = a - b;
                    break;
                case "*":
                    resultat = a * b;
                    break;
                case "/":
                    if (b == 0) {
                        pw.println("Erreur : division par zéro.");
                        return;
                    }
                    resultat = a / b;
                    break;
                default:
                    pw.println("Erreur : opérateur invalide.");
                    return;
            }

            pw.println("Résultat : " + resultat);

            System.out.println("Client n°" + numero
                    + " : résultat envoyé = " + resultat);

        } catch (IOException | NumberFormatException e) {
            System.out.println("Erreur client n°" + numero
                    + " : " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println("Erreur fermeture socket.");
            }

            System.out.println("Client n°" + numero + " déconnecté.");
        }
    }
}
