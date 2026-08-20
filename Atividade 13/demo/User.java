package orm.demo;

import orm.annotations.Column;
import orm.annotations.Entity;
import orm.annotations.Id;

@Entity(table = "users")
public class User {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    public User() {}

    public User(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }

    @Override
    public String toString() {
        return "User{id=%s, name='%s', email='%s'}".formatted(id, name, email);
    }
}
