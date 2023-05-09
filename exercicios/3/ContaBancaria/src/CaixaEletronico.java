import java.util.Scanner;

public class CaixaEletronico {
    Scanner entrada = new Scanner(System.in);
    ContaBancaria[] contasBancarias;
    Double limitePadrao = 200.0;
    Integer quantidadeContasPermitido = 2;
    Integer quantidadeContas;

    public CaixaEletronico() {
        contasBancarias = new ContaBancaria[quantidadeContasPermitido];
        quantidadeContas = 0;
    }

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
        System.out.println("10. Debug");
        System.out.println("5. Sair");
    }
    
    public void tratarOpcao(int opcao) {
        switch (opcao) {
            case 1:
                Cliente cliente = criarCliente();
                criarContas(cliente);
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

    private void criarContas(Cliente cliente) {
        for(int i = 0; i < quantidadeContasPermitido; i++) {
            ContaBancaria contaBancaria = criarConta(cliente);
            contasBancarias[i] = contaBancaria;
        }
    }

    private ContaBancaria criarConta(Cliente cliente) {
        ContaBancaria contaBancaria;
        System.out.println("Vamos abrir uma nova conta!");
        System.out.println("O cliente possui saldo?(Y/n)");
        String usuarioPossuiSaldo = entrada.nextLine();
        if(usuarioPossuiSaldo.equals("n")) {
            contaBancaria = new ContaBancaria(cliente, limitePadrao);
        } else {
            System.out.println("Qual o saldo do cliente?");
            Double saldoInicialConta = Double.parseDouble(entrada.nextLine());
            contaBancaria = new ContaBancaria(cliente, limitePadrao, saldoInicialConta);
        }
        System.out.println("Número da conta criada: " + contaBancaria.getNumeroConta() + '\n');
        return contaBancaria;
    }

    private Cliente criarCliente() {
        System.out.println("Qual o nome do cliente?");
        String nomeCliente =  entrada.nextLine();
        System.out.println("Qual o cpf do cliente?");
        String cpfCliente =  entrada.nextLine();
        return new Cliente(nomeCliente, cpfCliente);
    }

    public void consultarSaldo() {
        Integer numeroConta = getNumeroConta();
        ContaBancaria contaBancaria = getContaBancaria(numeroConta);
        String mensagem = "Conta não encontrada!";
        if(contaBancaria != null) {
            mensagem = 
                contaBancaria.getNomeTitular() + '\n' + 
                contaBancaria.getNumeroConta() + '\n' +
                "Saldo: " + contaBancaria.getSaldo() + '\n';
        }
        System.out.println(mensagem);
    }

    public void realizarDeposito() {
        Integer numeroConta = getNumeroConta();
        ContaBancaria contaBancaria = getContaBancaria(numeroConta);
        if(contaBancaria == null) {
            System.out.println("Conta não encontrada!");
            return;
        }
        double valorDeposito = getValor();
        boolean depositoOk = contaBancaria.executarDeposito(valorDeposito);
        String resultadoDeposito = "Falha para realizar depósito!";
        if(depositoOk) resultadoDeposito = "Depósito realizado com sucesso!";
        System.out.println(resultadoDeposito);
    }

    public void realizarSaque() {
        Integer numeroConta = getNumeroConta();
        ContaBancaria contaBancaria = getContaBancaria(numeroConta);
        if(contaBancaria == null) {
            System.out.println("Conta não encontrada!");
            return;
        }
        double valorSaque = getValor();
        boolean saqueOk = contaBancaria.executarSaque(valorSaque);
        String resultadoSaque = "Falha ao sacar!";
        if(saqueOk) resultadoSaque = "Saque realizado com sucesso!";
        System.out.println(resultadoSaque);
    }

    private double getValor() {
        System.out.println("Qual o valor?"); 
        return Double.parseDouble(entrada.nextLine());
    }

    private Integer getNumeroConta() {
        System.out.println("Qual número da conta?");
        return Integer.parseInt(entrada.nextLine());
    }

    private ContaBancaria getContaBancaria(Integer numeroConta) {
        for(int i = 0; i < quantidadeContasPermitido; i++) {
            System.out.println("numeroDaConta: " + contasBancarias[i].getNumeroConta());
            if(
                contasBancarias[i] instanceof ContaBancaria &&
                contasBancarias[i].getNumeroConta() == numeroConta
            ) {
                return contasBancarias[i];
            }
        }
        return null;
    }
}