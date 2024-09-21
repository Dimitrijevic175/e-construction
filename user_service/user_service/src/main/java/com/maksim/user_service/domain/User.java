package com.maksim.user_service.domain;



import javax.persistence.*;


@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class User {

    private static int brojac = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String last_name;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String username;
    private String password;
    private boolean active;

    private String stringZaAktivaciju = "string" + brojac++;

    @ManyToOne(optional = false)
    private Role role;

    public String getStringZaAktivaciju() {
        return stringZaAktivaciju;
    }

    public void setStringZaAktivaciju(String stringZaAktivaciju) {
        this.stringZaAktivaciju = stringZaAktivaciju;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
