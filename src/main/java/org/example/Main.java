package org.example;

import java.sql.*;
import java.util.Collection;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        while (true) {
            String url = "jdbc:postgresql://localhost:2222/Project";
            String name = "postgres";
            String pass = "kanekiken_7";

            Connection connection = DriverManager.getConnection(url, name, pass);

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
                print_clubs(connection);
            }

            if (choice == 2) {
                print_users(connection);
            }

            if (choice == 3) {
                add_user_to_club(connection);
            }

            if (choice == 4) {
                delete_user(connection);
            }

            if (choice == 5) {
                delete_club(connection);
            }

            if (choice == 6) {
                print_president(connection);
            }

            if (choice == 7) {
                add_user(connection);
            }

            if (choice == 8) {
                add_club(connection);
            }

            if (choice == 9) {
                break;
            }
        }


    }

    public static void print_clubs(Connection connection) throws SQLException {
        Statement stat = connection.createStatement();
        ResultSet res = stat.executeQuery("select * from clubs");
        while (res.next()) {
            System.out.print(res.getString("id") + ", ");
            System.out.println(res.getString("name") + ", ");
        }
    }

    public static void print_users(Connection connection) throws SQLException {
        Statement stat = connection.createStatement();
        ResultSet res = stat.executeQuery("select * from users");
        while (res.next()) {
            System.out.print("Barcode: " + res.getString("barcode") + ", ");
            System.out.print("Name: " + res.getString("name") + ", ");
            System.out.print("Surname: " + res.getString("surname") + ", ");
            System.out.println("Age: " + res.getString("age") + ", ");
            System.out.println("Club: " + res.getString("club"));
        }
    }

    public static void add_user_to_club(Connection connection) throws SQLException {
        print_users(connection);
        print_clubs(connection);

        System.out.println("Barcode: ");
        Scanner scan = new Scanner(System.in);
        int barcode = scan.nextInt();

        PreparedStatement name = connection.prepareStatement("select name from users where barcode = ?");
        name.setInt(1, barcode);
        ResultSet res = name.executeQuery();
        String nn = null;
        while (res.next() == true) {
            String name1 = res.getString("name");
            nn = name1;
        }

        PreparedStatement club_name = connection.prepareStatement("select club from users where name = ?");
        club_name.setString(1, nn);
        ResultSet res2 = club_name.executeQuery();
        String cc = null;
        while (res2.next() == true) {
            String club_name2 = res2.getString("club");
            cc = club_name2;
        }


        PreparedStatement pstat = connection.prepareStatement("update clubs set users = concat(users, ?) where name = ?");
        pstat.setString(1, nn + ", ");
        pstat.setString(2, cc);
        pstat.executeUpdate();

        PreparedStatement pstat1 = connection.prepareStatement("delete from users  where barcode= ?");
        pstat1.setInt(1, barcode);
        pstat1.executeUpdate();

    }

    public static void delete_user(Connection connection) throws SQLException {
        print_users(connection);

        System.out.println("Barcode: ");
        Scanner scan = new Scanner(System.in);
        int barcode = scan.nextInt();

        PreparedStatement pstat = connection.prepareStatement("delete from users  where barcode= ?");
        pstat.setInt(1, barcode);
        pstat.executeUpdate();

    }

    public static void delete_club(Connection connection) throws SQLException {
        print_clubs(connection);

        System.out.println("ID: ");
        Scanner scan = new Scanner(System.in);
        int id = scan.nextInt();

        PreparedStatement pstat = connection.prepareStatement("delete from clubs  where id= ?");
        pstat.setInt(1, id);
        pstat.executeUpdate();

    }

    public static void print_president(Connection connection) throws SQLException {
        Scanner scan = new Scanner(System.in);
        PreparedStatement pstat1 = null;
        ResultSet res = null;


        Statement stat = connection.createStatement();
        res = stat.executeQuery("SELECT president FROM clubs");

        int i = 1;
        while (res.next()) {
            System.out.println(i++ + ": " + res.getString("president"));
        }

        System.out.println("Choose president to change:");
        String pres = scan.nextLine();

        System.out.println("Enter new President:");
        String name = scan.nextLine();

        pstat1 = connection.prepareStatement("UPDATE clubs SET president = ? WHERE president = ?");
        pstat1.setString(1, name);
        pstat1.setString(2, pres);
        int rowsAffected = pstat1.executeUpdate();

        if (rowsAffected > 0) {
            System.out.println("President updated successfully.");
        } else {
            System.out.println("President not found or update failed.");
        }


    }

    public static void add_user(Connection connection) throws SQLException {
        System.out.println("Enter barcode: ");
        Scanner scan = new Scanner(System.in);
        int barcode = scan.nextInt();

        System.out.println("Enter name: ");
        String name = scan.next();

        System.out.println("Enter surname: ");
        String surname = scan.next();

        System.out.println("Enter age: ");
        int age = scan.nextInt();

        System.out.println("Enter name of club that you want to join: ");
        String club = scan.nextLine();

        PreparedStatement pstat = connection.prepareStatement("insert into users (barcode, name, surname, age, club) values (?, ?, ?, ?, ?)");
        pstat.setInt(1, barcode);
        pstat.setString(2, name);
        pstat.setString(3, surname);
        pstat.setInt(4, age);
        pstat.setString(5, club);
        pstat.executeUpdate();
    }

    public static void add_club(Connection connection) throws SQLException {
        Scanner scan = new Scanner(System.in);

        System.out.println("Enter name: ");
        String name = scan.nextLine();

        System.out.println("Enter president: ");
        String president = scan.next();

        System.out.println("Enter number of users: ");
        int num_users = scan.nextInt();
        scan.nextLine();

        System.out.println("Enter name of users: ");
        String users = scan.nextLine();

        PreparedStatement pstat = connection.prepareStatement("insert into clubs (name, president, num_users, users) values (?, ?, ?, ?)");
        pstat.setString(1, name);
        pstat.setString(2, president);
        pstat.setInt(3, num_users);
        pstat.setString(4, users);
        pstat.executeUpdate();
    }

}
