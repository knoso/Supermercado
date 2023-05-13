package udec.supermercado;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Clase principal del programa
public class Supermercado { 
    // Constante para el número de cajeros
    private static final int NUM_CAJEROS = 4;
    // Cola para almacenar los clientes
    private static Queue<Cliente> colaClientes = new LinkedList<>();
    // Array para almacenar las cajas
    private static Caja[] cajas = new Caja[NUM_CAJEROS];

    // Método principal del programa
    public static void main(String[] args) {
        generarClientes(); // Generamos los clientes
        atenderClientes(); // Atendemos a los clientes
        resumen(); // Mostramos el resumen de la simulación
    }

    // Método para generar los clientes
    private static void generarClientes() {
        System.out.print("Ingrese la cantidad de clientes: ");
        Scanner sc = new Scanner(System.in);
        var numClientes = sc.nextInt(); // Leemos el número de clientes
        sc.close();
        Random random = new Random(); // Creamos un objeto Random para generar números aleatorios
        // Generamos los clientes y los añadimos a la cola
        for (int i = 1; i <= numClientes; i++) {
            int id = i;
            int numArticulos = random.nextInt(20) + 1; // Generamos un número aleatorio de artículos
            String metodoPago = random.nextBoolean() ? "efectivo" : "tarjeta"; // Generamos un método de pago aleatorio
            colaClientes.add(new Cliente(id, numArticulos, metodoPago)); // Añadimos el cliente a la cola
        }
    }

    // Método para atender a los clientes
    private static void atenderClientes() {
        // Creamos un ExecutorService para manejar los hilos de las cajas
        ExecutorService executor = Executors.newFixedThreadPool(NUM_CAJEROS);
        // Inicializamos las cajas y las ejecutamos en hilos separados
        for (int i = 0; i < NUM_CAJEROS; i++) {
            cajas[i] = new Caja(i + 1, colaClientes);
            executor.execute(cajas[i]);
        }
        executor.shutdown(); // Cerramos el ExecutorService
        // Esperamos a que todos los hilos hayan terminado
        while (!executor.isTerminated()) {}
    }

    // Método para mostrar el resumen de la simulación
    private static void resumen() {
        int totalClientes = 0;
        int totalArticulos = 0;
        // Iteramos sobre las cajas y mostramos sus resultados
        for (Caja caja : cajas) {
            System.out.println("Caja " + caja.getId() + ": " + caja.contar() + " clientes atendidos y " + caja.getTotalArticulos() + " artículos vendidos");
            totalClientes += caja.contar();
            totalArticulos += caja.getTotalArticulos();
        }
        // Mostramos el total de clientes atendidos y artículos vendidos
        System.out.println("Total: " + totalClientes + " clientes atendidos y " + totalArticulos + " artículos vendidos");
    }
}
    /* Este código simula un supermercado con un número de cajeros y clientes definidos 
    por el usuario. Los clientes son atendidos en paralelo por los cajeros utilizando 
    hilos. Al final, se muestra un resumen de la cantidad de clientes atendidos y 
    artículos vendidos por cada caja y en total. */