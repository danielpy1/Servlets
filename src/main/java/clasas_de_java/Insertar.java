package clasas_de_java;


import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/insertar")
public class Insertar extends HttpServlet {
    Connection connection;
    public void init(ServletConfig config) {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/backup_market","postgres", "postgres");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("estamos en el doPost");
        String tipo_formulario=req.getParameter("tipo_formulario");
        Statement stmt = null;
        PrintWriter out = resp.getWriter();
        try{
            if(tipo_formulario.equals("formulario_cliente")){
                String id_cliente=req.getParameter("id_cliente");
                String nombre_cliente=req.getParameter("nombre_cliente");
                String apellido_cliente=req.getParameter("apellido_cliente");
                String cedula_cliente=req.getParameter("cedula_cliente");
                String telefono_cliente=req.getParameter("telefono_cliente");
                stmt = connection.createStatement();
                String sql="INSERT INTO cliente(id,nombre,apellido,nro_cedula,telefono) values(?,?,?,?,?);";
                PreparedStatement consulta=connection.prepareStatement(sql);
                consulta.setInt(1,Integer.parseInt(id_cliente));
                consulta.setString(2,nombre_cliente);
                consulta.setString(3,apellido_cliente);
                consulta.setString(4,cedula_cliente);
                consulta.setString(5,telefono_cliente);
                stmt.execute(String.valueOf(consulta));
                stmt.close();
                printExito(out);
            }else if(tipo_formulario.equals("formulario_moneda")){
                String id_moneda=req.getParameter("id_moneda");
                String nombre_moneda=req.getParameter("nombre_moneda");
                stmt=connection.createStatement();
                String sql="INSERT INTO moneda(id,nombre) values(?,?);";
                PreparedStatement consulta=connection.prepareStatement(sql);
                consulta.setInt(1,Integer.parseInt(id_moneda));
                consulta.setString(2,nombre_moneda);
                stmt.execute(String.valueOf(consulta));
                stmt.close();
                printExito(out);
            }else if(tipo_formulario.equals("formulario_producto")){
                String id_producto=req.getParameter("id_producto");
                String nombre_producto=req.getParameter("nombre_producto");
                String precio_producto=req.getParameter("precio_producto");
                String proveedor_id=req.getParameter("proveedor_id");
                String costo_producto=req.getParameter("costo_producto");
                stmt = connection.createStatement();
                String sql="INSERT INTO producto(id,nombre,precio,proveedor_id,costo) values(?,?,?,?,?);";
                PreparedStatement consulta=connection.prepareStatement(sql);
                consulta.setInt(1,Integer.parseInt(id_producto));
                consulta.setString(2,nombre_producto);
                consulta.setDouble(3,Double.parseDouble(precio_producto));
                consulta.setInt(4,Integer.parseInt(proveedor_id));
                consulta.setDouble(5,Double.parseDouble(costo_producto));
                stmt.execute(String.valueOf(consulta));
                stmt.close();
                printExito(out);
            }
        }catch(Exception e){
            out = resp.getWriter();
            out.println(e.getClass().getName() +" Error: " + e.getMessage());
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }
    public void destroy() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public PrintWriter printExito(PrintWriter out){
        out.println("<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "\t<meta charset=\"UTF-8\">\n" +
                "\t<title>Exito</title>\n" +
                "\t<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC\" crossorigin=\"anonymous\">\n" +
                "\t<link rel=\"stylesheet\" href=\"estilos.css\">\n" +
                "</head>\n" +
                "<body>\n" +
                "\t<div class=\"container border border-4 border-dark\" id=\"mensaje_exito\">\n" +
                "\t\t<div class=\"row \">\n" +
                "\t\t\t<div class=\"col-12 \">\n" +
                "\t\t\t\t<h3 class=\"text-center\">Se ha agregado de forma exitosa :)</h3>\n" +
                "\t\t\t</div>\n" +
                "\t\t\t<div class=\"col-12 \" id=\"prueba2\">\n" +
                "\t\t\t\t<a href=\"menu_consultas.html\"><button type=\"button\" class=\"btn btn-success\" id=\"volver\">Volver</button></a>\n" +
                "\t\t\t</div>\n" +
                "\t\t</div>\n" +
                "\t</div>\n" +
                "<script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js\" integrity=\"sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM\" crossorigin=\"anonymous\"></script>\n" +
                "</body>\n" +
                "</html>");
        return out;
    }
}
