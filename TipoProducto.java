public class TipoProducto extends Automovil {
    private String nombre;

    public TipoProducto(String tipoAutomovil, String nombre, String marca, String modelo, double precio, int stock, boolean disponible, int anio) {
        super(tipoAutomovil, nombre, marca, modelo, precio, stock, disponible, anio);
        try {
            setNombre(nombre);
        } catch (IllegalArgumentException e) {
            System.out.println("Error al establecer el nombre: " + e.getMessage());
        }
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede ser nulo o vacío.");
        }
        this.nombre = nombre;
    }

    @Override
    public String getDetalles() {
        try {
            String detalles = "Tipo: " + getTipoAutomovil() + ", Nombre: " + nombre + ", Marca: " + getMarca() + ", Modelo: " + getModelo()
                + ", Precio: " + getPrecio() + ", Stock: " + getStock() + ", Disponible: " + isDisponible()
                + ", Año: " + getAño();
            return detalles;
        } catch (Exception e) {
            return "Error al obtener los detalles del producto: " + e.getMessage();
        }
    }
}
