package udec.supermercado;

import java.util.Queue;

// Clase Caja que extiende de Thread para manejar la atención de clientes en hilos separados
class Caja extends Thread {

    // Atributos de la clase Caja
    private int id;
    private int clientesAtendidos;
    private int totalArticulos;
    private Queue<Cliente> colaClientes;

    // Constructor de la clase Caja
    public Caja(int id, Queue<Cliente> colaClientes) {
        this.id = id;
        this.clientesAtendidos = 0;
        this.totalArticulos = 0;
        this.colaClientes = colaClientes;
    }

    // Método run que se ejecuta al iniciar el hilo
    @Override
    public void run() {
        // Mientras haya clientes en la cola
        while (!colaClientes.isEmpty()) {
            Cliente cliente;
            // Obtenemos el siguiente cliente de la cola de forma sincronizada
            synchronized (colaClientes) {
                cliente = colaClientes.poll();
                System.out.println("Clientes en la fila: " + (colaClientes.size() + 1));
            }
            // Si el cliente no es nulo, lo atendemos
            if (cliente != null) {
                atender(cliente);
            }
        }
    }

    // Método para atender a un cliente
    public void atender(Cliente cliente) {
        clientesAtendidos++; // Incrementamos el contador de clientes atendidos
        totalArticulos += cliente.getNumArticulos(); // Incrementamos el total de artículos vendidos
        int tiempoArticulos = cliente.getNumArticulos() * 1500; // Calculamos el tiempo de atención por artículos
        int tiempoPago = cliente.getMetodoPago().equals("efectivo") ? 5000 : 1000; // Calculamos el tiempo de pago según el método de pago
        try {
            // Mostramos que la caja está ocupada por el cliente
            System.out.println(" [X] Caja " + id + " ocupada por el cliente " + cliente.getId());
            long startTime = System.currentTimeMillis(); // Guardamos el tiempo de inicio
            Thread.sleep(tiempoArticulos + tiempoPago); // Hacemos que el hilo duerma por el tiempo de atención
            long endTime = System.currentTimeMillis(); // Guardamos el tiempo de fin
            // Mostramos información sobre la atención del cliente
            System.out.println("   > [Caja " + id + "] Cliente " + cliente.getId() + " fue atendido con " + cliente.getNumArticulos() + " artículos y pago en " + cliente.getMetodoPago());
            System.out.println("   # Tiempo de atención en la caja " + id + ": " + (endTime - startTime) / 1000 + "s");
            System.out.println(" [/] Caja " + id + " disponible" + "\n");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Método para contar los clientes atendidos
    public int contar() {
        return clientesAtendidos;
    }

    // Método para obtener el ID de la caja
    public long getId() {
        return id;
    }

    // Método para obtener el total de artículos vendidos
    public int getTotalArticulos() {
        return totalArticulos;
    }
}
