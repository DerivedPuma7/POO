import java.util.Scanner;

public class Principal {
    Scanner entrada = new Scanner(System.in);
    Carro[] carros;
    Carro carro = null;
    int quantidadeCarros;
    int quantidadeMaximaCarros = 5;

    Principal() {
        carros = new Carro[quantidadeMaximaCarros];
        quantidadeCarros = 0;
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
        System.out.println("O que você deseja fazer?");
        System.out.println("1) Criar o carro");
        System.out.println("2) Acelerar o carro");
        System.out.println("3) Reduzir a velocidade");
        System.out.println("4) Exibir os dados do carro");
        System.out.println("5) Sair");
    }

    public void tratarOpcao(int opcao) {
        switch (opcao) {
            case 1:
                criarCarro();
                break;
            case 2:
                acelerarCarro();
                break;
            case 3:
                reduzirCarro();
                break;
            case 4:
                exibirInformacoesCarros();
                break;
            default:
                return;
        }
    }

    public void criarCarro() {
        if (quantidadeCarros == quantidadeMaximaCarros) {
            capacidadeMaximaAtingida();
            return;
        } 
        System.out.println("Qual o modelo de carro? ");
        String modeloCarro = entrada.nextLine();
        Carro carroJaExistente = getCarroPorModelo(modeloCarro);
        if(carroJaExistente != null) {
            modeloJaExistente();
            return;
        }
        carro = new Carro(modeloCarro, 15);
        carros[quantidadeCarros] = carro;
        quantidadeCarros++;
    }

    public void capacidadeMaximaAtingida() {
        System.out.println("Não é possível criar mais carros!");
    }

    public void modeloJaExistente() {
        System.out.println("Modelo já existente. Crie outro!");
    }

    public void acelerarCarro() {
        System.out.println("Qual carro deseja acelerar? ");
        String modeloCarro = entrada.nextLine();
        carro = getCarroPorModelo(modeloCarro);
        carro.acelerar();
    }

    public void reduzirCarro() {
        System.out.println("Qual carro deseja reduzir? ");
        String modeloCarro = entrada.nextLine();
        carro = getCarroPorModelo(modeloCarro);
        carro.acelerar();
    }

    public Carro getCarroPorModelo(String modeloCarro) {
        Carro carro = null;
        for(int i = 0; i < quantidadeCarros; i++) {
            if(carros[i].getModelo().equals(modeloCarro)) {
                carro = carros[i];
            }
        }
        return carro;
    }

    public void exibirInformacoesCarros() {
        for(int i = 0; i < quantidadeCarros; i++) {
            System.out.println(i + ":");
            System.out.println("modelo: " + carros[i].getModelo());
            System.out.println("velocidade: " + carros[i].getVelocidade());
            System.out.println("");
        }
    }
}