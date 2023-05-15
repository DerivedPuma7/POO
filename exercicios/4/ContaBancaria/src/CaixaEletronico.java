import java.util.Scanner;
import java.util.ArrayList;

public class CaixaEletronico {
    Scanner entrada = new Scanner(System.in);
    ArrayList<ContaBancaria> contasBancarias;
    Double limitePadrao = 200.0;
    Integer quantidadeContasPermitido = 2;
    Integer quantidadeContas;

    public CaixaEletronico() {
        contasBancarias = new ArrayList<ContaBancaria>();
        quantidadeContas = 0;
    }

    public void executar() {
        while (true) {
            mostrarMenu();
            int opcao = Integer.parseInt(entrada.nextLine());
            if (opcao == 9)
                break;
            tratarOpcao(opcao);
        }
        entrada.close();
    }

    public void mostrarMenu() {
        System.out.println("");
        System.out.println("1. Criar Conta");
        if(contasCriadas()) {
            System.out.println("2. Consultar Saldo");
            System.out.println("3. Depositar");
            System.out.println("4. Sacar");
            System.out.println("5. Transferir");
            System.out.println("6. Listar Contas");
            System.out.println("7. Remover Conta");
            System.out.println("8. Filtrar Contas");
        }
        System.out.println("9. Sair");
    }

    private boolean contasCriadas() {
        return contasBancarias.size() > 0;
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
            case 5:
                realizarTransferencia();
                break;
            case 6:
                listarContas();
                break;
            case 7:
                removerConta();
                break;
            case 8:
                filtrarContas();
                break;
            default:
                break;
        }
    }

    private ContaBancaria criarConta(Cliente cliente) {
        ContaBancaria contaBancaria;
        System.out.println("\n" + "Vamos abrir uma nova conta!");
        System.out.println("O cliente possui saldo?(Y/n)");
        String usuarioPossuiSaldo = entrada.nextLine();
        if(usuarioPossuiSaldo.equals("n")) {
            contaBancaria = new ContaBancaria(cliente, limitePadrao);
        } else {
            System.out.println("Qual o saldo do cliente?");
            Double saldoInicialConta = Double.parseDouble(entrada.nextLine());
            contaBancaria = new ContaBancaria(cliente, limitePadrao, saldoInicialConta);
        }
        contasBancarias.add(contaBancaria);
        System.out.println("Número da conta criada: " + contaBancaria.getNumeroConta());
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
            mensagem = "\n" + 
                "Cliente: " + contaBancaria.getNomeTitular() + "\n" + 
                "Número: " + contaBancaria.getNumeroConta() + "\n" +
                "Saldo: " + contaBancaria.getSaldo() + "\n";
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

    public void realizarTransferencia() {
        ContaBancaria contaOrigem = getContaOrigemTransferencia();
        ContaBancaria contaDestino = getContaDestinoTransferencia();
        Double valorTransferencia = getValor();
        if(! validarTransferencia(contaOrigem, contaDestino, valorTransferencia)) {
            System.out.println("Não é possível realizar a transferência!");
            return;
        }
        Boolean transferenciaRealizada = contaOrigem.executarTransferencia(valorTransferencia, contaDestino);
        String mensagem = "Falha na transferência!";
        if(transferenciaRealizada) {
            contaOrigem.executarSaque(valorTransferencia);
            mensagem = "Transferência realizada com sucesso!";
        }
        System.out.println(mensagem);
    }

    private ContaBancaria getContaOrigemTransferencia() {
        System.out.println("\n" + "Conta de origem");
        Integer numeroContaOrigem = getNumeroConta();
        return getContaBancaria(numeroContaOrigem);
    }

    private ContaBancaria getContaDestinoTransferencia() {
        System.out.println("\n" + "Conta de destino");
        Integer numeroContaDestino = getNumeroConta();
        return getContaBancaria(numeroContaDestino);
    }

    private boolean validarTransferencia(ContaBancaria contaOrigem, ContaBancaria contaDestino, Double valorTransferencia) {
        if(contaOrigem == null) return false;
        if(contaDestino == null) return false;
        return contaOrigem.podeTransferir(valorTransferencia);
    }

    private void listarContas() {
        String mensagem;
        for (ContaBancaria conta : contasBancarias) {
            mensagem = "\n" + 
                "Número: " + conta.getNumeroConta() + "\n" +
                "Cliente: " + conta.getNomeTitular();
            System.out.println(mensagem);
        }
    }

    private void removerConta() {
        Integer numeroConta = getNumeroConta();
        ContaBancaria contaBancaria = getContaBancaria(numeroConta);
        boolean podeRemover = validarRemocaoContaBancaria(contaBancaria);
        if(! podeRemover) return;
        int indexConta = getIndexContaBancaria(numeroConta);
        String mensagem = "Falha ao remover conta!";
        if(indexConta != -1) {
            contasBancarias.remove(indexConta);
            mensagem = "Conta bancária removida com sucesso!";
        }
        System.out.println(mensagem);
    }

    private boolean validarRemocaoContaBancaria(ContaBancaria conta) {
        double saldoConta = conta.getSaldo();
        if(saldoConta == 0) return true;
        if(saldoConta < 0) System.out.println("Não é possível cancelar contas em débito!");
        if(saldoConta > 0) System.out.println("Não é possível cancelar contas com saldo disponível!");
        return false;
    }

    private void filtrarContas() {
        String nomeCliente = getNomeCliente().toLowerCase();
        for (ContaBancaria conta : contasBancarias) {
            if(conta.getNomeTitular().toLowerCase().contains(nomeCliente)) {
                System.out.println(
                    conta.getNumeroConta() + " - " + conta.getNomeTitular()
                );
            }
        }
    }

    private double getValor() {
        System.out.println("\n" + "Qual o valor?"); 
        return Double.parseDouble(entrada.nextLine());
    }

    private int getNumeroConta() {
        System.out.println("\n" + "Qual número da conta?");
        return Integer.parseInt(entrada.nextLine());
    }

    private String getNomeCliente() {
        System.out.println("\n" + "Qual nome do cliente?");
        return entrada.nextLine();
    }

    private ContaBancaria getContaBancaria(Integer numeroConta) {
        for (ContaBancaria conta : contasBancarias) {
            if(conta.getNumeroConta() == numeroConta) {
                return conta;
            }
        }
        return null;
    }

    private int getIndexContaBancaria(int numeroConta) {
        ContaBancaria conta;
        for (int i = 0; i < contasBancarias.size(); i++) {
            conta = contasBancarias.get(i);
            if(conta.getNumeroConta() == numeroConta) {
                return i;
            }
        }
        return -1;
    }
}