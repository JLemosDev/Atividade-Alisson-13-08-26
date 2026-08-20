package di.demo;

import di.annotations.Inject;

public class OrderService {

    private final PaymentService paymentService;

    @Inject
    public OrderService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public String placeOrder(double amount) {
        return "Pedido criado. " + paymentService.charge(amount);
    }
}
