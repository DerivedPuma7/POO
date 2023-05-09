public class ContaBancaria {
    private double saldo;
    private double limite;
    private Cliente cliente;

    public ContaBancaria(Cliente cliente) {
        saldo = 0;
        limite = 200;
        this.cliente = cliente;
    }

    public double getSaldo() {
        return saldo;
    }

    public boolean executarSaque(double valorSaque) {
        boolean saqueValido = validarSaque(valorSaque);
        if(saqueValido) saldo -= valorSaque;
        return saqueValido;
    }

    private boolean validarSaque(double valorSaque) {
        double limiteSaque = saldo + limite;
        if(valorSaque < 0) return false;
        if(valorSaque > limiteSaque) return false;
        return true;
    }

    public boolean executarDeposito(double valorDeposito) {
        boolean depositoValido = validarDeposito(valorDeposito);
        if(depositoValido) saldo += valorDeposito;
        return depositoValido;
    }

    private boolean validarDeposito(double valorDeposito) {
        return valorDeposito > 0;
    }

    public String getNomeTitular() {
        return cliente.getNome();
    }
}