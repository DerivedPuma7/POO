import java.util.Scanner;

public class CaixaEletronico {
    Scanner entrada = new Scanner(System.in);
    ContaBancaria contaBancaria;
    Double limitePadrao = 200.0;

    public void executar() {
        while (true) {
            mostrarMenu();
            int opcao = Integer.parseInt(entrada.nextLine());
            if (opcao == 5)
                break;
            tratarOpcao(opcao);
        }
        entrada.close();
    }

    public void mostrarMenu() {
        System.out.println("");
        System.out.println("1. Criar Conta");
        System.out.println("2. Consultar Saldo");
        System.out.println("3. Depositar");
        System.out.println("4. Sacar");
        System.out.println("5. Sair");
    }
    
    public void tratarOpcao(int opcao) {
        switch (opcao) {
            case 1:
                Cliente cliente = criarCliente();
                criarConta(cliente);
                break;
            case 2:
                consultarSaldo();
                break;
            case 3:
                realizarDeposito();
                break;
            case 4:
                realizarSaque();
                break;
            default:
                break;
        }
    }

    public void criarConta(Cliente cliente) {
        System.out.println("O cliente possui saldo?(Y/n)");
        String usuarioPossuiSaldo = entrada.nextLine();
        if(usuarioPossuiSaldo.equals("n")) {
            contaBancaria = new ContaBancaria(cliente, limitePadrao);
        } else {
            System.out.println("Qual o saldo do cliente?");
            Double saldoInicialConta = Double.parseDouble(entrada.nextLine());
            contaBancaria = new ContaBancaria(cliente, limitePadrao, saldoInicialConta);
        }
    }

    private Cliente criarCliente() {
        System.out.println("Qual o nome do cliente?");
        String nomeCliente =  entrada.nextLine();
        System.out.println("Qual o cpf do cliente?");
        String cpfCliente =  entrada.nextLine();
        return new Cliente(nomeCliente, cpfCliente);
    }

    public void consultarSaldo() {
        String mensagem = "Nenhuma conta bancária cadastrada";
        if(contaBancaria != null) {
            mensagem = 
            contaBancaria.getNomeTitular() + '\n' + 
            contaBancaria.getNumeroConta() + '\n' +
            "Saldo: " + contaBancaria.getSaldo();
        }
        System.out.println(mensagem);
    }

    public void realizarDeposito() {
        if(contaBancaria == null) {
            System.out.println("Nenhuma conta bancária cadastrada");
            return;
        }
        System.out.println("Qual o valor do depósito ?");
        double valorDeposito = getValor();
        boolean depositoOk = contaBancaria.executarDeposito(valorDeposito);
        
        String resultadoDeposito = "Falha para realizar depósito!";
        if(depositoOk) resultadoDeposito = "Depósito realizado com sucesso!";
        System.out.println(resultadoDeposito);
    }

    public void realizarSaque() {
        if(contaBancaria == null) {
            System.out.println("Nenhuma conta bancária cadastrada");
            return;
        }
        System.out.println("Qual o valor do saque ?");       
        double valorSaque = getValor();
        boolean saqueOk = contaBancaria.executarSaque(valorSaque);
        
        String resultadoSaque = "Falha ao sacar!";
        if(saqueOk) resultadoSaque = "Saque realizado com sucesso!";
        System.out.println(resultadoSaque);
    }
    
    private double getValor() {
        return Double.parseDouble(entrada.nextLine());
    }
}