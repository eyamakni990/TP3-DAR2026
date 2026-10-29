package Clientpackage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        try (
                Socket socket = new Socket("localhost", 1234);
                Scanner scanner = new Scanner(System.in);
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                PrintWriter pw = new PrintWriter(
                        socket.getOutputStream(), true)
        ) {
            // Lire le numéro envoyé par le serveur
            System.out.println(br.readLine());

            System.out.print("Donner le premier opérande : ");
            double a = scanner.nextDouble();

            System.out.print("Donner l'opérateur (+, -, *, /) : ");
            String operateur = scanner.next();

            System.out.print("Donner le deuxième opérande : ");
            double b = scanner.nextDouble();

            pw.println(a);
            pw.println(operateur);
            pw.println(b);

            System.out.println(br.readLine());

        } catch (IOException e) {
            System.out.println("Erreur client : " + e.getMessage());
        }
    }
}
