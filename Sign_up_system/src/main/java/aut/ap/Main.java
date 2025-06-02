package aut.ap;

import jakarta.persistence.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.Scanner;

@Entity
@Table(name = "users")
class User {
    @Id
    @Column(unique = true)
    private String email;

    private String firstName;
    private String lastName;
    private int age;
    private String password;

    public User() {}

    public User(String email, String firstName, String lastName, int age, String password) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.password = password;
    }


    public String getEmail() { return email; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public int getAge() { return age; }
    public String getPassword() { return password; }
}

public class Main {
    public static void main(String[] args) {
        SessionFactory sessionFactory = new Configuration()
                .configure()
                .addAnnotatedClass(User.class)
                .buildSessionFactory();

        Scanner sc = new Scanner(System.in);
        System.out.print("[L]ogin, [S]ign up: ");
        String choice = sc.nextLine().trim().toLowerCase();

        if (choice.equals("s") || choice.equals("sign up")) {
            signUp(sc, sessionFactory);
        } else if (choice.equals("l") || choice.equals("login")) {
            login(sc, sessionFactory);
        } else {
            System.out.println("Invalid option.");
        }

        sc.close();
        sessionFactory.close();
    }

    private static void signUp(Scanner sc, SessionFactory sessionFactory) {
        System.out.print("First Name: ");
        String firstName = sc.nextLine();

        System.out.print("Last Name: ");
        String lastName = sc.nextLine();

        System.out.print("Age: ");
        int age = Integer.parseInt(sc.nextLine());

        System.out.print("Email: ");
        String email = sc.nextLine();

        System.out.print("Password: ");
        String password = sc.nextLine();

        if (password.length() < 8) {
            System.out.println("Weak password");
            return;
        }

        Session session = sessionFactory.openSession();
        User existing = session.get(User.class, email);
        if (existing != null) {
            System.out.println("An account with this email already exists");
            session.close();
            return;
        }

        Transaction tx = session.beginTransaction();
        User user = new User(email, firstName, lastName, age, password);
        session.persist(user);
        tx.commit();
        session.close();

        System.out.println("Sign up successful!");
    }

    private static void login(Scanner sc, SessionFactory sessionFactory) {
        System.out.print("Email: ");
        String email = sc.nextLine();

        System.out.print("Password: ");
        String password = sc.nextLine();

        Session session = sessionFactory.openSession();
        User user = session.get(User.class, email);

        if (user == null) {
            System.out.println("Not registered");
        } else if (!user.getPassword().equals(password)) {
            System.out.println("Incorrect password");
        } else {
            System.out.println("Welcome, " + user.getFirstName() + " " + user.getLastName() + "!");
        }

        session.close();
    }

}
