package org.example;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Console {
    public static void console() throws SQLException {
        while (true) {
            Connection connection = DB_Connection.connection_to_db();

            System.out.println("1. List of clubs");
            System.out.println("2. List of users");
            System.out.println("3. Add user to club");
            System.out.println("4. Delete user");
            System.out.println("5. Delete club");
            System.out.println("6. Change President of club");
            System.out.println("7. Add user");
            System.out.println("8. Add club");
            System.out.println("9. Exit");

            Scanner scan = new Scanner(System.in);
            int choice = scan.nextInt();

            if (choice == 1) {
                DB_tasks.print_clubs(connection);
            }

            if (choice == 2) {
                DB_tasks.print_users(connection);
            }

            if (choice == 3) {
                DB_tasks.add_user_to_club(connection);
            }

            if (choice == 4) {
                DB_tasks.delete_user(connection);
            }

            if (choice == 5) {
                DB_tasks.delete_club(connection);
            }

            if (choice == 6) {
                DB_tasks.print_president(connection);
            }

            if (choice == 7) {
                DB_tasks.add_user(connection);
            }

            if (choice == 8) {
                DB_tasks.add_club(connection);
            }

            if (choice == 9) {
                System.exit(0);
            }
        }
    }

}
