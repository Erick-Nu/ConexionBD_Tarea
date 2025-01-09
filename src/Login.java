import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Login {
    private JLabel Titulo;
    private JLabel Password;
    private JLabel User;
    private JTextField user;
    private JPasswordField contrasena;
    public JPanel login;
    private JButton ingresar;

    public Login() {
        ingresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /* Declaramos la conexión fuera del bloque try para poder ocuparla en todo el codigo */
                Connection connection = null;

                /* Conexion a la base de datos comunidadepn */

                String url = "jdbc:mysql://localhost:3306/comunidadepn"; // jdbc = java database conector
                String username = "root";
                String password = "1234";
                try {
                    connection = DriverManager.getConnection(url, username, password);
                    System.out.println("Conexión exitosa a la base de datos");

                    /* Validacion de datos */

                    /* Obtenemos los datos ingresados por el usuario */
                    String correoIngresado = user.getText();
                    String contrasenaIngresada = new String(contrasena.getPassword());

                    /* Consulta */

                    String query = "SELECT * FROM usuarios WHERE correo_institucional = ? AND contrasena = ?";

                    /* Preparamos la consulta */
                    try (PreparedStatement stmt = connection.prepareStatement(query)) {
                        stmt.setString(1, correoIngresado);
                        stmt.setString(2, contrasenaIngresada);

                        /* Ejecutamos la consulta */
                        ResultSet rs = stmt.executeQuery();

                        /* Verificamos si el usuario existe */
                        if (rs.next()) {
                            System.out.println("Inicio de sesión exitoso");

                            /* Cerramos la ventana realizando una instancia del JFrame */
                            JFrame loginFrame = (JFrame) SwingUtilities.getWindowAncestor(login);
                            loginFrame.dispose();

                            /* Ventana 2: Table */
                            JFrame tableFrame = new JFrame("Table");
                            tableFrame.setContentPane(new Table().Tabla);
                            tableFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                            tableFrame.setSize(600, 600);
                            tableFrame.setPreferredSize(new Dimension(400, 300));
                            tableFrame.pack();
                            tableFrame.setVisible(true);

                        } else {
                            JOptionPane.showMessageDialog(login, "Credenciales incorrectas", "Error", JOptionPane.ERROR_MESSAGE);
                        }

                    } catch (SQLException ex) {
                        System.err.println("Error al preparar la consulta SQL");
                    }

                } catch (SQLException error) {
                    System.err.println("Error de conexión");
                } finally {
                    /* Aseguramos que la conexión se cierra al finalizar */
                    if (connection != null) {
                        try {
                            connection.close();
                        } catch (SQLException e1) {
                            System.err.println("Error al cerrar la conexión");
                        }
                    }
                }
            }
        });
    }
}
