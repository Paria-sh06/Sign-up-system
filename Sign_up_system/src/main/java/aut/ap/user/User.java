package aut.ap.user;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(unique = true)
    private String email;

    private String firstName;
    private String lastName;
    private int age;
    private String password;

    // Constructors, Getters and Setters
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
