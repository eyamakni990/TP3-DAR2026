
package Serverpackage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;

public class ClientProcess2 extends Thread {

    private final Socket socket;
    private final int numero;

    // Compteur global partagé par tous les threads
    private static final AtomicInteger compteurOperations =
            new AtomicInteger(0);

    public ClientProcess2(Socket socket, int numero) {
        this.socket = socket;
        this.numero = numero;
    }

    @Override
    public void run() {

        try {
            // Créer le flux de sortie en premier
            ObjectOutputStream oos =new ObjectOutputStream(socket.getOutputStream());
            oos.flush();

            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

            // Recevoir l'objet Operation
            Operation operation = (Operation) ois.readObject();

            double a = operation.getOperande1();
            double b = operation.getOperande2();
            String operateur = operation.getOperateur();

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
                        operation.setMessage(
                                "Erreur : division par zéro."
                        );
                        oos.writeObject(operation);
                        oos.flush();
                        return;
                    }
                    resultat = a / b;
                    break;

                default:
                    operation.setMessage("Erreur : opérateur invalide.");
                    oos.writeObject(operation);
                    oos.flush();
                    return;
            }

            // Enregistrer le résultat
            operation.setResultat(resultat);
            operation.setMessage("Calcul effectué avec succès.");

            // Incrémenter le compteur de façon atomique
            int total = compteurOperations.incrementAndGet();

            System.out.println("Client n°" + numero + " : résultat envoyé = " + resultat);

            System.out.println("Nombre total d'opérations traitées : " + total);

            // Envoyer l'objet résultat au client
            oos.writeObject(operation);
            oos.flush();

        } catch (IOException | ClassNotFoundException e) {

            System.out.println(
                    "Erreur client n°" + numero + " : " + e.getMessage()
            );

        } finally {

            try {
                socket.close();
            } catch (IOException e) {
                System.out.println("Erreur lors de la fermeture du socket.");
            }

            System.out.println("Client n°" + numero + " déconnecté.");
        }
    }
}
