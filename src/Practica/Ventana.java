package Practica;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Ventana extends JFrame{
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;


	public Ventana() {
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);

		JLabel Error = new JLabel("");
		Error.setBounds(49, 208, 324, 14);
		getContentPane().add(Error);


		JLabel IdAlumno = new JLabel("CII");
		IdAlumno.setBounds(31, 23, 69, 14);
		getContentPane().add(IdAlumno);

		JLabel Nombre = new JLabel("Nombre");
		Nombre.setBounds(31, 52, 69, 14);
		getContentPane().add(Nombre);

		JLabel Apellidos = new JLabel("Edad");
		Apellidos.setBounds(31, 81, 69, 14);
		getContentPane().add(Apellidos);

		JLabel Edad = new JLabel("Tipo de Comida");
		Edad.setBounds(31, 106, 99, 14);
		getContentPane().add(Edad);

		JLabel Curso = new JLabel("Cantidad de comida");
		Curso.setBounds(31, 134, 99, 14);
		getContentPane().add(Curso);

		textField = new JTextField();
		textField.setEditable(false);
		textField.setBounds(156, 20, 203, 20);
		getContentPane().add(textField);
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setBounds(156, 49, 203, 20);
		getContentPane().add(textField_1);
		textField_1.setColumns(10);

		textField_2 = new JTextField();
		textField_2.setBounds(156, 78, 203, 20);
		getContentPane().add(textField_2);
		textField_2.setColumns(10);

		textField_3 = new JTextField();
		textField_3.setBounds(156, 103, 203, 20);
		getContentPane().add(textField_3);
		textField_3.setColumns(10);

		textField_4 = new JTextField();
		textField_4.setBounds(156, 131, 203, 20);
		getContentPane().add(textField_4);
		textField_4.setColumns(10);

		JButton btnNewButton = new JButton("Consultar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String x=textField_1.getText();
				
				try {
					
					Class.forName("org.sqlite.JDBC");
					
					Connection conexion;

					conexion = DriverManager.getConnection("jdbc:sqlite:database.db","","");

					Statement objetoStatement = conexion.createStatement();
					String sentencia = "SELECT * FROM Zoo WHERE Nombre = "+"'"+x+"'";
					
					ResultSet resultado = objetoStatement.executeQuery(sentencia);
					while (resultado.next()) {
						textField.setText(String.valueOf(resultado.getInt("CII")));
						textField_2.setText(String.valueOf(resultado.getInt("Edad")));
						textField_3.setText(resultado.getString("TipoComida"));
						textField_4.setText(String.valueOf(resultado.getInt("CantidadComida")));
					}

					if (resultado != null) { 
						resultado.close();
					}
					if (objetoStatement != null) {
						objetoStatement.close();
					}
					if (conexion != null) { 
						conexion.close();
					}

				} catch (ClassNotFoundException e2) {

					e2.printStackTrace();

				} catch (SQLException sqle) {

					Error.setText("Error sintactico al introducir los datos");

				} catch (Exception e1) {

					System.out.println("Error de conexión: " + e1);
				}
			}
		});
		btnNewButton.setBounds(31, 174, 89, 23);
		getContentPane().add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Alta");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Connection conexion;
				try {
					Class.forName("org.sqlite.JDBC");
					
					conexion = DriverManager.getConnection("jdbc:sqlite:database.db","","");
					
					Statement objetoStatement = conexion.createStatement();

					if(!textField_1.getText().equals("")&&!textField_2.getText().equals("")&&textField_3.getText()!=""&&!textField_4.getText().equals("")) {
						String sentenciaInsert = "INSERT INTO Zoo (Nombre,Edad,TipoComida,CantidadComida)  VALUES ('"+textField_1.getText()+"'"+","+textField_2.getText()+","+"'"+textField_3.getText()+"'"+","+textField_4.getText()+")";
						
						objetoStatement.executeUpdate(sentenciaInsert);
						Error.setText("Resgistro completado");
					}
					else {
						Error.setText("Error no estan rellenos todos los campos");
					}
					if (objetoStatement != null) { 
						objetoStatement.close();
					}
					if (conexion != null) { 
						conexion.close();
					}

				} catch (ClassNotFoundException e2) {

					e2.printStackTrace();

				} catch (SQLException sqle) {

					Error.setText("Error sintactico al introducir los datos");

				} catch (Exception e1) {

					System.out.println("Error de conexión: " + e1);
				}
			}
		});
		btnNewButton_1.setBounds(123, 174, 89, 23);
		getContentPane().add(btnNewButton_1);

		JButton btnNewButton_2 = new JButton("Baja");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connection conexion;
				Statement objetoStatement;
				try {
					String a="";
					Class.forName("org.sqlite.JDBC");
					conexion = DriverManager.getConnection("jdbc:sqlite:database.db","","");
					objetoStatement = conexion.createStatement();

					String sentenciaDelete = "DELETE FROM Zoo WHERE Nombre="+"'"+textField_1.getText()+"'";

					String sentencia = "SELECT * FROM Zoo WHERE Nombre = "+"'"+textField_1.getText()+"'";
					ResultSet resultado = objetoStatement.executeQuery(sentencia);

					while (resultado.next()) {
						if(textField_1.getText().equals("Nombre")) {
							a=resultado.getString("Nombre");
						}
					}
					if(a.equals(textField_1.getText())) {
						Error.setText("Animal eliminado");
					}else {
						Error.setText("Este animal no existe");
					}

					objetoStatement.executeUpdate(sentenciaDelete);
					
					if (objetoStatement != null) {
						objetoStatement.close();
					}
					if (conexion != null) { 
						conexion.close();
					}

				} catch (ClassNotFoundException e2) {

					e2.printStackTrace();

				} catch (SQLException sqle) {

					Error.setText("Error sintactico al introducir los datos");

				} catch (Exception e1) {

					System.out.println("Error de conexión: " + e1);
				}


			}
		});
		btnNewButton_2.setBounds(313, 174, 89, 23);
		getContentPane().add(btnNewButton_2);

		JButton btnNewButton_3 = new JButton("Modificar");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connection conexion;
				try {

					Class.forName("org.sqlite.JDBC");
					conexion = DriverManager.getConnection("jdbc:sqlite:database.db","","");
					Statement objetoStatement = conexion.createStatement();

					if(textField.getText()!=""&&!textField_1.getText().equals("")&&!textField_2.getText().equals("")&&textField_3.getText()!=""&&!textField_4.getText().equals("")) {
						String sentenciaUpdate = "UPDATE Zoo SET Edad="+textField_2.getText()+","+"TipoComida='"+textField_3.getText()+"',"+"CantidadComida="+textField_4.getText()+" WHERE Nombre="+"'"+textField_1.getText()+"'";

						objetoStatement.executeUpdate(sentenciaUpdate);
						Error.setText("Animal modificado");
					}else {
						Error.setText("Error no estan rellenos todos los campos");
					}
					
					if (objetoStatement != null) {
						objetoStatement.close();
					}
					if (conexion != null) {
						conexion.close();
					}

				} catch (ClassNotFoundException e2) {

					e2.printStackTrace();

				} catch (SQLException sqle) {

					Error.setText("Error sintactico al introducir los datos");

				} catch (Exception e1) {

					System.out.println("Error de conexión: " + e1);
				}
			}
		});
		btnNewButton_3.setBounds(217, 174, 89, 23);
		getContentPane().add(btnNewButton_3);


	}
}


