import java.util.Scanner;

class RaizQuadrada {
  public static void main(String[] args) {
    Scanner entrada = new Scanner(System.in);
    double numeroEntrada = Double.parseDouble(entrada.nextLine());
    if(numeroEntrada < 0) {
      System.out. println("Números negativos não possuen raizes reais!");
      System.exit(1);
    }
    System.out.println(Math. sqrt (numeroEntrada));
  }
}