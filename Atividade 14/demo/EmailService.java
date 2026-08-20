package di.demo;

public class EmailService implements MessageService {
    @Override
    public String send(String to, String message) {
        return "E-mail enviado para " + to + ": " + message;
    }
}
