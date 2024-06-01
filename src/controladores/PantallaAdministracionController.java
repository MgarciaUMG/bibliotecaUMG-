/*
 * Click nbfs://nbhost/SystemFhaaileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controladores;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.List;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class
 *
 * @author Soporte
 */
public class PantallaAdministracionController implements Initializable {

    static Usuario objUsuario;
    static Libro objLibro;
    Connection conn;
    ArrayList<Usuario> UsuariosDB = new ArrayList<>();
    ArrayList<Libro> LibroDB = new ArrayList<>();

    @FXML
    private TextField txtidusuario;
    @FXML
    private TextField txtcarneusuario;
    @FXML
    private TextField txtcontrausuario;
    @FXML
    private TextField txtnombreusuario;
    @FXML
    private TextField txtapellidousuario;
    @FXML
    private TextField txttelusuario;
    @FXML
    private TextField txtdireusuario;
    @FXML
    private Button btnagregaru;
    @FXML
    private Button btnactualizaru;
    @FXML
    private Button btneliminaru;
    @FXML
    private TableView<Usuario> tablausuario;
    @FXML
    private TableColumn<Usuario, Integer> colid_usuario;
    @FXML
    private TableColumn<Usuario, String> colcarne;
    @FXML
    private TableColumn<Usuario, String> colcontrasena;
    @FXML
    private TableColumn<Usuario, String> colnombre;
    @FXML
    private TableColumn<Usuario, String> colapellido;
    @FXML
    private TableColumn<Usuario, Integer> coltelefono;
    @FXML
    private TableColumn<Usuario, String> coldireccion;
    @FXML
    private TextField txtidlibro;
    @FXML
    private TextField txtisbnlibro;
    @FXML
    private TextField txttitulolibro;
    @FXML
    private TextField txtautorlibro;
    @FXML
    private TextField txtpublilibro;
    @FXML
    private TextField txteditolibro;
    @FXML
    private TextField txtcantidadlibro;
    @FXML
    private Button btnagregarl;
    @FXML
    private Button btnactualizarl;
    @FXML
    private Button btneliminarl;
    @FXML
    private TableView<Libro> tablalibros;
    @FXML
    private TableColumn<Libro, Integer> colid_libro;
    @FXML
    private TableColumn<Libro, Integer> colisbn;
    @FXML
    private TableColumn<Libro, String> coltitulo;
    @FXML
    private TableColumn<Libro, String> colautor;
    @FXML
    private TableColumn<Libro, Integer> colpublicacion;
    @FXML
    private TableColumn<Libro, String> coleditorial;
    @FXML
    private TableColumn<Libro, Integer> colcantidad;
    @FXML
    private TextField txtbuscarusuario;
    @FXML
    private TextField txtbuscarlibro;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colid_usuario.setCellValueFactory(new PropertyValueFactory<>("id_usuario"));
        colcarne.setCellValueFactory(new PropertyValueFactory<>("carne"));
        colcontrasena.setCellValueFactory(new PropertyValueFactory<>("contrasena"));
        colnombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colapellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        coltelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        coldireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));

        colid_usuario.setSortable(true);
        colcarne.setSortable(true);
        colcontrasena.setSortable(true);
        colnombre.setSortable(true);
        colapellido.setSortable(true);
        coltelefono.setSortable(true);
        coldireccion.setSortable(true);

        colid_libro.setCellValueFactory(new PropertyValueFactory<>("id_libro"));
        colisbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        coltitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colautor.setCellValueFactory(new PropertyValueFactory<>("autor"));
        colpublicacion.setCellValueFactory(new PropertyValueFactory<>("publicacion"));
        coleditorial.setCellValueFactory(new PropertyValueFactory<>("editorial"));
        colcantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));

        colid_libro.setSortable(true);
        colisbn.setSortable(true);
        coltitulo.setSortable(true);
        colautor.setSortable(true);
        colpublicacion.setSortable(true);
        coleditorial.setSortable(true);
        colcantidad.setSortable(true);

        conn = ConexionDB.getConnection();
        tablausaurios();
        tablalibros();

        tablausuario.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                txtidusuario.setText(String.valueOf(newValue.getId_usuario()));
                txtcarneusuario.setText(newValue.getCarne());
                txtcontrausuario.setText(newValue.getContrasena());
                txtnombreusuario.setText(newValue.getNombre());
                txtapellidousuario.setText(newValue.getApellido());
                txttelusuario.setText(String.valueOf(newValue.getTelefono()));
                txtdireusuario.setText(newValue.getDireccion());
            }
        });

        tablalibros.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                txtidlibro.setText(String.valueOf(newValue.getId_libro()));
                txtisbnlibro.setText(String.valueOf(newValue.getIsbn()));
                txttitulolibro.setText(newValue.getTitulo());
                txtautorlibro.setText(newValue.getAutor());
                txtpublilibro.setText(String.valueOf(newValue.getCantidad()));
                txteditolibro.setText(newValue.getEditorial());
                txtcantidadlibro.setText(String.valueOf(newValue.getCantidad()));
            }
        });

    }

    public void agregaru(Usuario u) {

        PreparedStatement st = null;

        try {
            String sql = "INSERT INTO usuario (id_usuario, carne, contrasena, nombre, apellido, telefono, direccion) VALUES (?, ?, ?, ?, ?, ?, ?)";

            st = conn.prepareStatement(sql);
            st.setInt(1, u.getId_usuario());
            st.setString(2, u.getCarne());
            st.setString(3, u.getContrasena());
            st.setString(4, u.getNombre());
            st.setString(5, u.getApellido());
            st.setInt(6, u.getTelefono());
            st.setString(7, u.getDireccion());

            st.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void actualizaru(Usuario u) {

        String sql = "UPDATE usuario SET carne = ?, contrasena = ?, nombre = ?, apellido = ?, telefono = ?, direccion = ? WHERE id_usuario = ?";

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, u.getCarne());
            st.setString(2, u.getContrasena());
            st.setString(3, u.getNombre());
            st.setString(4, u.getApellido());
            st.setInt(5, u.getTelefono());
            st.setString(6, u.getDireccion());
            st.setInt(7, u.getId_usuario());

            st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PantallaAdministracionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void eliminaru(int id_usuario) {
        String sql = "DELETE FROM usuario WHERE id_usuario = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id_usuario);
            st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PantallaAdministracionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void clickA(ActionEvent event) {

        objUsuario = new Usuario();
        objUsuario.setId_usuario(Integer.parseInt(txtidusuario.getText()));
        objUsuario.setCarne(txtcarneusuario.getText());
        objUsuario.setContrasena(txtcontrausuario.getText());
        objUsuario.setNombre(txtnombreusuario.getText());
        objUsuario.setApellido(txtapellidousuario.getText());
        objUsuario.setTelefono(Integer.parseInt(txttelusuario.getText()));
        objUsuario.setDireccion(txtdireusuario.getText());

        agregaru(objUsuario);

        txtidusuario.setText("");
        txtcarneusuario.setText("");
        txtcontrausuario.setText("");
        txtnombreusuario.setText("");
        txtapellidousuario.setText("");
        txttelusuario.setText("");
        txtdireusuario.setText("");

        tablausaurios();

    }

    @FXML
    private void clickAC(ActionEvent event) {

        Usuario usuarioActualizado = new Usuario();
        usuarioActualizado.setId_usuario(Integer.parseInt(txtidusuario.getText()));
        usuarioActualizado.setCarne(txtcarneusuario.getText());
        usuarioActualizado.setContrasena(txtcontrausuario.getText());
        usuarioActualizado.setNombre(txtnombreusuario.getText());
        usuarioActualizado.setApellido(txtapellidousuario.getText());
        usuarioActualizado.setTelefono(Integer.parseInt(txttelusuario.getText()));
        usuarioActualizado.setDireccion(txtdireusuario.getText());

        actualizaru(usuarioActualizado);

        txtidusuario.setText("");
        txtcarneusuario.setText("");
        txtcontrausuario.setText("");
        txtnombreusuario.setText("");
        txtapellidousuario.setText("");
        txttelusuario.setText("");
        txtdireusuario.setText("");

        tablausaurios();
    }

    @FXML
    private void clickE(ActionEvent event) {

        Usuario usuarioSeleccionado = tablausuario.getSelectionModel().getSelectedItem();
        if (usuarioSeleccionado != null) {
            eliminaru(usuarioSeleccionado.getId_usuario());

            txtidusuario.setText("");
            txtcarneusuario.setText("");
            txtcontrausuario.setText("");
            txtnombreusuario.setText("");
            txtapellidousuario.setText("");
            txttelusuario.setText("");
            txtdireusuario.setText("");

            tablausaurios();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Advertencia");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, seleccione un usuario para eliminar.");
            alert.showAndWait();
        }

    }

    public void agregarl(Libro l) {

        PreparedStatement st = null;

        try {
            String sql = "INSERT INTO libro (id_libro, isbn, titulo, autor, publicacion, editorial, cantidad) VALUES (?, ?, ?, ?, ?, ?, ?)";

            st = conn.prepareStatement(sql);
            st.setInt(1, l.getId_libro());
            st.setInt(2, l.getIsbn());
            st.setString(3, l.getTitulo());
            st.setString(4, l.getAutor());
            st.setInt(5, l.getPublicacion());
            st.setString(6, l.getEditorial());
            st.setInt(7, l.getCantidad());

            st.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(PantallaAdministracionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void actualizarl(Libro l) {

        String sql = "UPDATE libro SET isbn = ?, titulo = ?, autor = ?, publicacion = ?, editorial = ?, cantidad = ? WHERE id_libro = ?";

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, l.getIsbn());
            st.setString(2, l.getTitulo());
            st.setString(3, l.getAutor());
            st.setInt(4, l.getPublicacion());
            st.setString(5, l.getEditorial());
            st.setInt(6, l.getCantidad());
            st.setInt(7, l.getId_libro());

            st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PantallaAdministracionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void eliminarl(int id_libro) {
        String sql = "DELETE FROM libro WHERE id_libro = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id_libro);
            st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PantallaAdministracionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void tablausaurios() {

        UsuariosDB.clear();
        String sql = "SELECT id_usuario, carne, contrasena, nombre, apellido, telefono, direccion FROM usuario";

        try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Usuario usuario = new Usuario(
                        rs.getInt("id_usuario"),
                        rs.getString("carne"),
                        rs.getString("contrasena"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getInt("telefono"),
                        rs.getString("direccion")
                );
                UsuariosDB.add(usuario);
            }
        } catch (SQLException e) {
            Logger.getLogger(PantallaAdministracionController.class.getName()).log(Level.SEVERE, null, e);
        }

        actualizartabla(UsuariosDB);

    }

    public void actualizartabla(List<Usuario> usuarios) {
        ObservableList<Usuario> observableList = FXCollections.observableArrayList(usuarios);
        tablausuario.setItems(observableList);
        if (!usuarios.isEmpty()) {
            Usuario ultimoUsuario = usuarios.get(usuarios.size() - 1);
            int ultimoIdUsuario = ultimoUsuario.getId_usuario() + 1;
            txtidusuario.setText(Integer.toString(ultimoIdUsuario));
        }
    }

    public void tablalibros() {

        LibroDB.clear();
        String sql = "SELECT id_libro, isbn, titulo, autor, publicacion, editorial, cantidad FROM libro";

        try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Libro libro = new Libro(
                        rs.getInt("id_libro"),
                        rs.getInt("isbn"),
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getInt("publicacion"),
                        rs.getString("editorial"),
                        rs.getInt("Cantidad")
                );
                LibroDB.add(libro);
            }
        } catch (SQLException e) {
            Logger.getLogger(PantallaAdministracionController.class.getName()).log(Level.SEVERE, null, e);
        }

        actualizartablal(LibroDB);

    }

    public void actualizartablal(List<Libro> libros) {
        ObservableList<Libro> observableList = FXCollections.observableArrayList(libros);
        tablalibros.setItems(observableList);
        if (!libros.isEmpty()) {
            Libro ultimoLibro = libros.get(libros.size() - 1);
            int ultimoIdLibro = ultimoLibro.getId_libro() + 1;
            txtidlibro.setText(Integer.toString(ultimoIdLibro));
        }
    }

    @FXML
    private void clickAL(ActionEvent event) {

        objLibro = new Libro();
        objLibro.setId_libro(Integer.parseInt(txtidlibro.getText()));
        objLibro.setIsbn(Integer.parseInt(txtisbnlibro.getText()));
        objLibro.setTitulo(txttitulolibro.getText());
        objLibro.setAutor(txtautorlibro.getText());
        objLibro.setPublicacion(Integer.parseInt(txtpublilibro.getText()));
        objLibro.setEditorial(txteditolibro.getText());
        objLibro.setCantidad(Integer.parseInt(txtcantidadlibro.getText()));

        agregarl(objLibro);

        txtidlibro.setText("");
        txtisbnlibro.setText("");
        txttitulolibro.setText("");
        txtautorlibro.setText("");
        txtpublilibro.setText("");
        txteditolibro.setText("");
        txtcantidadlibro.setText("");

        tablalibros();

    }

    @FXML
    private void clickACL(ActionEvent event) {

        Libro libroActualizado = new Libro();
        libroActualizado.setId_libro(Integer.parseInt(txtidlibro.getText()));
        libroActualizado.setIsbn(Integer.parseInt(txtisbnlibro.getText()));
        libroActualizado.setTitulo(txttitulolibro.getText());
        libroActualizado.setAutor(txtautorlibro.getText());
        libroActualizado.setPublicacion(Integer.parseInt(txtpublilibro.getText()));
        libroActualizado.setEditorial(txteditolibro.getText());
        libroActualizado.setCantidad(Integer.parseInt(txtcantidadlibro.getText()));

        actualizarl(libroActualizado);

        txtidlibro.setText("");
        txtisbnlibro.setText("");
        txttitulolibro.setText("");
        txtautorlibro.setText("");
        txtpublilibro.setText("");
        txteditolibro.setText("");
        txtcantidadlibro.setText("");

        tablalibros();

    }

    @FXML
    private void clickEL(ActionEvent event) {

        Libro libroSeleccionado = tablalibros.getSelectionModel().getSelectedItem();
        if (libroSeleccionado != null) {
            eliminarl(libroSeleccionado.getId_libro());

            txtidlibro.setText("");
            txtisbnlibro.setText("");
            txttitulolibro.setText("");
            txtautorlibro.setText("");
            txtpublilibro.setText("");
            txteditolibro.setText("");
            txtcantidadlibro.setText("");

            tablalibros();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Advertencia");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, seleccione un libro para eliminar.");
            alert.showAndWait();
        }

    }

    @FXML
    private void buscarUsuario(javafx.scene.input.KeyEvent event) {
        String filtro = txtbuscarusuario.getText().toLowerCase();

        List<Usuario> usuariosFiltrados = UsuariosDB.stream()
                .filter(u -> u.getCarne().toLowerCase().contains(filtro)
                || u.getContrasena().toLowerCase().contains(filtro)
                || u.getNombre().toLowerCase().contains(filtro)
                || u.getApellido().toLowerCase().contains(filtro)
                || u.getDireccion().toLowerCase().contains(filtro)
                || String.valueOf(u.getId_usuario()).contains(filtro)
                || String.valueOf(u.getTelefono()).contains(filtro))
                .collect(Collectors.toList());

        actualizartabla(usuariosFiltrados);

    }

    @FXML
    private void buscarLibro(javafx.scene.input.KeyEvent event) {
        String filtro = txtbuscarlibro.getText().toLowerCase();

        List<Libro> librosFiltrados = LibroDB.stream()
                .filter(l -> String.valueOf(l.getId_libro()).contains(filtro)
                || String.valueOf(l.getIsbn()).contains(filtro)
                || l.getTitulo().toLowerCase().contains(filtro)
                || l.getAutor().toLowerCase().contains(filtro)
                || String.valueOf(l.getPublicacion()).contains(filtro)
                || l.getEditorial().toLowerCase().contains(filtro)
                || String.valueOf(l.getCantidad()).contains(filtro))
                .collect(Collectors.toList());

        actualizartablal(librosFiltrados);

    }

}
