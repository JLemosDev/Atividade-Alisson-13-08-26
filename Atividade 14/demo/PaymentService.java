package di.demo;

public class PaymentService {
    public String charge(double amount) {
        return "Cobranca de R$" + amount + " processada";
    }
}
