package orm.demo;

import orm.persistence.EntityManager;
import orm.persistence.InMemoryDatabase;

public class Main {
    public static void main(String[] args) {
        InMemoryDatabase database = new InMemoryDatabase();
        EntityManager<User> users = new EntityManager<>(User.class, database);

        System.out.println("== save (insert) ==");
        users.save(new User(1L, "Ana Souza", "ana@exemplo.com"));
        users.save(new User(2L, "Bruno Lima", "bruno@exemplo.com"));

        System.out.println("\n== findById ==");
        User found = users.findById(1L);
        System.out.println("Encontrado: " + found);

        System.out.println("\n== save (update, mesmo id) ==");
        users.save(new User(1L, "Ana Souza Costa", "ana.costa@exemplo.com"));
        System.out.println("Apos update: " + users.findById(1L));

        System.out.println("\n== delete ==");
        boolean removed = users.delete(2L);
        System.out.println("Removido? " + removed);
        System.out.println("Busca apos remocao: " + users.findById(2L));
    }
}
