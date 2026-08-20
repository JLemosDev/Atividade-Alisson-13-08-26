package di.demo;

import di.annotations.Inject;

public class UserController {

    @Inject
    private MessageService messageService;

    @Inject
    private OrderService orderService;

    public String welcome(String userEmail) {
        return messageService.send(userEmail, "Bem-vindo!") + " | " +
               orderService.placeOrder(150.0);
    }
}
