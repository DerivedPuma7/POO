public class Carro {
    private String modelo;
    private double velocidade;
    private double velocidadeMaxima;

    public Carro(String modelo, double velocidadeMaxima) {
        this.modelo = modelo;
        this.velocidade = 0;
        this.velocidadeMaxima = velocidadeMaxima;
    }

    public String getModelo() {
        return modelo;
    }

    public double getVelocidade() {
        return velocidade;
    }

    public void acelerar() {
        velocidade += 5;
        if(velocidade > velocidadeMaxima) velocidade = velocidadeMaxima;
    }

    public void reduzir() {
        velocidade -= 5;
        if(velocidade < 0) velocidade = 0;
    }
}