
package Clientpackage;

import Serverpackage.Operation;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client2 {

    public static void main(String[] args) {

        try (
                Socket socket = new Socket("localhost", 1234);
                Scanner scanner = new Scanner(System.in)
        ) {
            // Créer le flux de sortie en premier
            ObjectOutputStream oos =new ObjectOutputStream(socket.getOutputStream());
            oos.flush();

            ObjectInputStream ois =new ObjectInputStream(socket.getInputStream());

            System.out.print("Donner le premier opérande : ");
            double a = scanner.nextDouble();

            System.out.print("Donner l'opérateur (+, -, *, /) : ");
            String operateur = scanner.next();

            System.out.print("Donner le deuxième opérande : ");
            double b = scanner.nextDouble();

            // Construire et envoyer l'objet
            Operation operation = new Operation(a, operateur, b);

            oos.writeObject(operation);
            oos.flush();

            // Recevoir le résultat
            Operation reponse = (Operation) ois.readObject();

            System.out.println(reponse.getMessage());

            if ("Calcul effectué avec succès.".equals(reponse.getMessage())) {
                System.out.println("Résultat : " + reponse.getResultat());
            }

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erreur client : " + e.getMessage());
        }
    }
}
