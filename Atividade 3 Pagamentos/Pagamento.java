public class Pagamento {
    public void executar(Processavel formaPagamento) {
        if (!formaPagamento.validar()) {
            throw new IllegalStateException("Pagamento inválido");
        }
        formaPagamento.processar();
    }
}
