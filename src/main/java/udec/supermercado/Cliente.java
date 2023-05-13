package udec.supermercado;

class Cliente {
    private int id;
    private int numArticulos;
    private String metodoPago;

    public Cliente(int id, int numArticulos, String metodoPago) {
        this.id = id;
        this.numArticulos = numArticulos;
        this.metodoPago = metodoPago;
    }

    public int getId() {
        return id;
    }

    public int getNumArticulos() {
        return numArticulos;
    }

    public String getMetodoPago() {
        return metodoPago;
    }
}