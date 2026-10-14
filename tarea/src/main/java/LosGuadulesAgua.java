import java.util.Scanner;

public class LosGuadualesAgua {

    private static Scanner leer = new Scanner(System.in);
    // Usamos -1 para indicar que un apartamento no tiene consumo registrado
    private static final double SIN_REGISTRO = -1.0;
    private static final int TOTAL_APTOS = 6;
    private static final int CARGO_FIJO = 12000;

    public static void main(String[] args) {
        // Inicializar el arreglo de consumos para 6 apartamentos
        double[] consumos = new double[TOTAL_APTOS];
        for (int i = 0; i < TOTAL_APTOS; i++) {
            consumos[i] = SIN_REGISTRO;
        }

        int opcion;
        do {
            mostrarMenu();
            opcion = leer.nextInt();

            switch (opcion) {
                case 1:
                    registrarConsumo(consumos);
                    break;
                case 2:
                    consultarFactura(consumos);
                    break;
                case 3:
                    generarReporte(consumos);
                    break;
                case 4:
                    System.out.println("Saliendo del programa. ¡Hasta luego!");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
            System.out.println(); // Salto de línea estético
        } while (opcion != 4);
    }

    public static void mostrarMenu() {
        System.out.println("===== LOS GUADUALES - CONTROL DE AGUA =====");
        System.out.println("1. Registrar consumo de un apartamento");
        System.out.println("2. Consultar factura de un apartamento");
        System.out.println("3. Ver reporte general del conjunto");
        System.out.println("4. Salir");
        System.out.print("Elija una opción: ");
    }

    public static void registrarConsumo(double[] consumos) {
        System.out.print("Ingrese el número de apartamento (1 a 6): ");
        int apto = leer.nextInt();
        while (apto < 1 || apto > TOTAL_APTOS) {
            System.out.print("Número inválido. Ingrese el número de apartamento (1 a 6): ");
            apto = leer.nextInt();
        }

        System.out.print("Ingrese el consumo en m³ (mayor a 0): ");
        double consumo = leer.nextDouble();
        while (consumo <= 0) {
            System.out.print("Consumo inválido. Ingrese el consumo en m³ (mayor a 0): ");
            consumo = leer.nextDouble();
        }

        // Se guarda en el arreglo (restando 1 para ajustar al índice 0-5)
        consumos[apto - 1] = consumo;
        System.out.println("¡Consumo registrado con éxito para el apartamento " + apto + "!");
    }

    public static void consultarFactura(double[] consumos) {
        System.out.print("Ingrese el número de apartamento (1 a 6): ");
        int apto = leer.nextInt();
        while (apto < 1 || apto > TOTAL_APTOS) {
            System.out.print("Número inválido. Ingrese el número de apartamento (1 a 6): ");
            apto = leer.nextInt();
        }

        double consumo = consumos[apto - 1];
        if (consumo == SIN_REGISTRO) {
            System.out.println("El apartamento " + apto + " no tiene consumo registrado.");
        } else {
            imprimirFactura(apto, consumo);
        }
    }

    public static void imprimirFactura(int apartamento, double consumo) {
        int tarifaPorM3;
        if (consumo <= 10) {
            tarifaPorM3 = 3000;
        } else if (consumo <= 20) {
            tarifaPorM3 = 4500;
        } else {
            tarifaPorM3 = 6000;
        }

        double valorConsumo = consumo * tarifaPorM3;
        double subtotal = CARGO_FIJO + valorConsumo;
        double recargo = 0;

        if (consumo > 25) {
            recargo = subtotal * 0.10;
        }

        double totalPagar = subtotal + recargo;

        System.out.println("\n--- FACTURA APARTAMENTO " + apartamento + " ---");
        System.out.printf("Consumo: %.2f m³\n", consumo);
        System.out.println("Tarifa aplicada: $" + tarifaPorM3 + " por m³");
        System.out.printf("Valor del consumo: $%.2f\n", valorConsumo);
        System.out.println("Cargo fijo: $" + CARGO_FIJO);
        System.out.printf("Recargo (10%%): $%.2f\n", recargo);
        System.out.printf("TOTAL A PAGAR: $%.2f\n", totalPagar);

        if (consumo > 25) {
            System.out.println("ALERTA: consumo excesivo");
        }
    }

    public static void generarReporte(double[] consumos) {
        double consumoTotal = 0;
        int aptosRegistrados = 0;
        int aptosSinRegistro = 0;
        int aptosExcesivos = 0;

        int aptoMayorConsumo = -1;
        double mayorConsumo = -1.0;

        for (int i = 0; i < TOTAL_APTOS; i++) {
            if (consumos[i] == SIN_REGISTRO) {
                aptosSinRegistro++;
            } else {
                aptosRegistrados++;
                consumoTotal += consumos[i];

                if (consumos[i] > 25) {
                    aptosExcesivos++;
                }

                if (consumos[i] > mayorConsumo) {
                    mayorConsumo = consumos[i];
                    aptoMayorConsumo = i + 1; // +1 para mostrar el apto real (1-6)
                }
            }
        }

        if (aptosRegistrados == 0) {
            System.out.println("Aún no hay consumos registrados.");
        } else {
            double consumoPromedio = consumoTotal / aptosRegistrados;

            System.out.println("\n===== REPORTE GENERAL DEL CONJUNTO =====");
            System.out.printf("Consumo total del conjunto: %.2f m³\n", consumoTotal);
            System.out.printf("Consumo promedio (aptos registrados): %.2f m³\n", consumoPromedio);
            System.out.printf("Apartamento con mayor consumo: Apto %d (%.2f m³)\n", aptoMayorConsumo, mayorConsumo);
            System.out.println("Cantidad de apartamentos sin registro: " + aptosSinRegistro);
            System.out.println("Cantidad de apartamentos con consumo excesivo: " + aptosExcesivos);
        }
    }
}

