package di.demo;

import di.container.CircularDependencyException;
import di.container.DIContainer;
import di.container.DependencyNotFoundException;

public class Main {
    public static void main(String[] args) {
        DIContainer container = new DIContainer();
        container.register(MessageService.class, EmailService.class);
        container.register(PaymentService.class);
        container.register(OrderService.class);
        container.register(UserController.class);

        UserController controller = container.resolve(UserController.class);
        System.out.println(controller.welcome("cliente@exemplo.com"));

        System.out.println("\n== Dependencia nao encontrada ==");
        DIContainer containerSemBind = new DIContainer();
        containerSemBind.register(UserController.class);
        // MessageService/OrderService/PaymentService nao registrados propositalmente
        try {
            containerSemBind.resolve(UserController.class);
        } catch (DependencyNotFoundException e) {
            System.out.println("Erro esperado: " + e.getMessage());
        }

        System.out.println("\n== Dependencia circular ==");
        DIContainer circularContainer = new DIContainer();
        circularContainer.register(CircularA.class);
        circularContainer.register(CircularB.class);
        try {
            circularContainer.resolve(CircularA.class);
        } catch (CircularDependencyException e) {
            System.out.println("Erro esperado: " + e.getMessage());
        }
    }
}
