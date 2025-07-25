package com.curpsuite;
import com.curpsuite.exceptions.CURPException;



public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            printUsage();
            System.exit(1);
        }

        String curpValue = args[0];
        String nombre = null;
        String primerApellido = null;
        String segundoApellido = null;
        String nombreCompleto = null;

        // Procesar argumentos
        for (int i = 1; i < args.length; i += 2) {
            if (i + 1 >= args.length) {
                System.err.println("Error: Opción sin valor: " + args[i]);
                printUsage();
                System.exit(1);
            }

            String option = args[i];
            String value = args[i + 1];

            switch (option) {
                case "-n":
                case "--nombre":
                    nombre = value;
                    break;
                case "-p":
                case "--primer-apellido":
                    primerApellido = value;
                    break;
                case "-s":
                case "--segundo-apellido":
                    segundoApellido = value;
                    break;
                case "-c":
                case "--nombre-completo":
                    nombreCompleto = value;
                    break;
                default:
                    System.err.println("Opción desconocida: " + option);
                    printUsage();
                    System.exit(1);
            }
        }

        try {
            CURP curp = new CURP(curpValue, nombre, primerApellido, segundoApellido, nombreCompleto);
            System.out.println(curp.toJson());
        } catch (CURPException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Imprime instrucciones de uso del programa.
     */
    private static void printUsage() {
        System.out.println("Uso: java -jar curpsuite.jar CURP [opciones]");
        System.out.println("Opciones:");
        System.out.println("  -n, --nombre NOMBRE              Nombre de pila para validar la CURP");
        System.out.println("  -p, --primer-apellido APELLIDO   Primer apellido para validar la CURP");
        System.out.println("  -s, --segundo-apellido APELLIDO  Segundo apellido para validar la CURP");
        System.out.println("  -c, --nombre-completo NOMBRE     Nombre completo para validar la CURP");
    }
}