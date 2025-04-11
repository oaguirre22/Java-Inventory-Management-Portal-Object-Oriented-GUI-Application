public class Automovil implements Producto {
    private String tipoAutomovil;
    private String nombre;
    private String marca;
    private String modelo;
    private double precio;
    private int stock;
    private boolean disponible;
    private int anio;

    public Automovil(String tipoAutomovil, String nombre, String marca, String modelo, double precio, int stock, boolean disponible, int anio) {
        this.tipoAutomovil = tipoAutomovil;
        this.nombre = nombre;
        this.marca = marca;
        this.modelo = modelo;
        this.precio = precio;
        this.stock = stock;
        this.disponible = disponible;
        this.anio = anio;
    }

    @Override
    public String getDetalles() {
        return "Tipo: " + tipoAutomovil + ", Nombre: " + nombre + ", Marca: " + marca + ", Modelo: " + modelo + ", Año: " + anio + ", Precio: " + precio + ", Stock: " + stock + ", Disponible: " + disponible;
    }

    @Override
    public int getAño() {
        return anio;
    }

    @Override
    public void setAño(int anio) {
        this.anio = anio;
    }

    public String getTipoAutomovil() {
        return tipoAutomovil;
    }

    public void setTipoAutomovil(String tipoAutomovil) {
        this.tipoAutomovil = tipoAutomovil;
    }

    // Getters y setters para los otros campos
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }
}
