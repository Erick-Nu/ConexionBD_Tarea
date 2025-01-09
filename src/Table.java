import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class Table {
    public JPanel Tabla;
    private JLabel Bienvenidos;
    private JTable contenido;

    public Table() {
        /* Arreglo con las columnas */
        String[] columnas = {"Nombre", "Correo"};

        /* Conexion a la database comunidadepn */
        String url = "jdbc:mysql://localhost:3306/comunidadepn";
        String username = "root";
        String password = "1234";
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Conexión exitosa a la base de datos");

            String query = "SELECT nombre_usuario, correo_institucional FROM usuarios";

            /* Crear el statement para ejecutar la consulta */

            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            /* Crear el DefaultTableModel con las columnas*/
            DefaultTableModel modelo = new DefaultTableModel();
            modelo.addColumn("Nombre");
            modelo.addColumn("Correo");

            /* Iterar sobre los resultados de la consulta y agregar filas al modelo */
            while (rs.next()) {
                Object[] fila = new Object[2];
                fila[0] = rs.getString("nombre_usuario");
                fila[1] = rs.getString("correo_institucional");

                /* Agregar la fila al modelo */
                modelo.addRow(fila);
            }

            /* Asignar el modelo a la tabla seteamos los valores de la tabla*/
            contenido.setModel(modelo);

        } catch (SQLException e) {
            System.err.println("Error de conexión: " + e.getMessage());
            JOptionPane.showMessageDialog(Tabla, "Error al conectar a la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            // Cerrar la conexión a la base de datos
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
}
