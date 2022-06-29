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

@WebServlet("/consulta")
public class Consulta extends HttpServlet {
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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            String tipo_consulta=req.getParameter("grupo");
            Statement stmt = connection.createStatement();
            resp.setContentType("text/html");
            PrintWriter out = resp.getWriter();
            //String pagina = null;
            if(tipo_consulta.equals("mostrar_cliente")){
                //select de clientes
                out.println("                <!DOCTYPE html>\n" +
                        "                <html>\n" +
                        "                <head>\n" +
                        "                    <title>Clientes</title>\n" +
                        "                    <link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC\" crossorigin=\"anonymous\">\n" +
                        "                    <link rel=\"stylesheet\" href=\"estilos.css\">\n" +
                        "                </head>\n" +
                        "                <body>\n" +
                        "                    <div class=\"container\" id=\"lista_cliente\">\n" +
                        "                        <div class=\"row\">\n" +
                        "                            <div class=\"col-12\">\n" +
                        "                                <h4 class=\"text-center\">Lista de Clientes</h4>\n" +
                        "                                <hr>");


                ResultSet rs = stmt.executeQuery( "SELECT * FROM cliente ORDER BY id DESC LIMIT 10" );
                while ( rs.next() ) {
                    int id_cliente = rs.getInt("id");
                    String  nombre = rs.getString("nombre");
                    String  apellido = rs.getString("apellido");
                    String  nro_cedula = rs.getString("nro_cedula");
                    String  telefono = rs.getString("telefono");

                    out.println("\t\t\t\t<div class=\"row\">\n" +
                            "\t\t\t\t\t<div class=\"col-12\">\n" +
                            "\t\t\t\t\t\t<div class=\"d-grid gap-3\">\n" +
                            "\t\t\t\t\t\t\t<div class=\"p-2 bg-light border\">"+"<p>" + "id_cliente: "+id_cliente+",nombre: "+nombre+", apellido: "+apellido+", nro_cedula: "+nro_cedula+", telefono: "+telefono + "</p>"+"</div>\n" +
                            "\t\t\t\t\t\t</div>\n" +
                            "\t\t\t\t\t</div>\n" +
                            "\t\t\t\t</div>");
                }
                out.println("                            </div>\n" +
                        "                        </div>\n" +
                        "                    </div>\n" +
                        "\t\t<a href=\"menu_consultas.html\"><button type=\"button\" class=\"btn btn-outline-dark\" id=\"volver\">Volver</button></a>"+
                        "                    <script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js\" integrity=\"sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM\" crossorigin=\"anonymous\"></script>\n" +
                        "                </body>\n" +
                        "                </html>");
                rs.close();
                stmt.close();
            }else if(tipo_consulta.equals("mostrar_producto")){
                //select de productos
                ResultSet rs = stmt.executeQuery( "SELECT * FROM producto ORDER BY id DESC LIMIT 10" );
                out.println("                <!DOCTYPE html>\n" +
                        "                <html>\n" +
                        "                <head>\n" +
                        "                    <title>Productos</title>\n" +
                        "                    <link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC\" crossorigin=\"anonymous\">\n" +
                        "                    <link rel=\"stylesheet\" href=\"estilos.css\">\n" +
                        "                </head>\n" +
                        "                <body>\n" +
                        "                    <div class=\"container\" id=\"lista_producto\">\n" +
                        "                        <div class=\"row\">\n" +
                        "                            <div class=\"col-12\">\n" +
                        "                                <h4 class=\"text-center\">Lista de Productos</h4>\n" +
                        "                                <hr>");
                while ( rs.next() ) {
                    int id_producto = rs.getInt("id");
                    String  nombre_producto = rs.getString("nombre");
                    double  precio = rs.getDouble("precio");
                    int  proveedor_id = rs.getInt("proveedor_id");
                    double  costo = rs.getDouble("costo");

                    out.println("\t\t\t\t<div class=\"row\">\n" +
                            "\t\t\t\t\t<div class=\"col-12\">\n" +
                            "\t\t\t\t\t\t<div class=\"d-grid gap-3\">\n" +
                            "\t\t\t\t\t\t\t<div class=\"p-2 bg-light border\">"+"<p>" + "id_producto: "+id_producto+",nombre producto: "+nombre_producto+", precio: "+precio+", proveedor_id: "+proveedor_id+", costo: "+costo + "</p>"+"</div>\n" +
                            "\t\t\t\t\t\t</div>\n" +
                            "\t\t\t\t\t</div>\n" +
                            "\t\t\t\t</div>");
                }
                out.println("                            </div>\n" +
                        "                        </div>\n" +
                        "                    </div>\n" +
                        "\t\t<a href=\"menu_consultas.html\"><button type=\"button\" class=\"btn btn-outline-dark\" id=\"volver\">Volver</button></a>"+
                        "                    <script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js\" integrity=\"sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM\" crossorigin=\"anonymous\"></script>\n" +
                        "                </body>\n" +
                        "                </html>");
                rs.close();
                stmt.close();
            }else if(tipo_consulta.equals("mostrar_moneda")){
                //select de monedas
                ResultSet rs = stmt.executeQuery( "SELECT * FROM moneda ORDER BY id DESC LIMIT 10" );
                out.println("                <!DOCTYPE html>\n" +
                        "                <html>\n" +
                        "                <head>\n" +
                        "                    <title>Monedas</title>\n" +
                        "                    <link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC\" crossorigin=\"anonymous\">\n" +
                        "                    <link rel=\"stylesheet\" href=\"estilos.css\">\n" +
                        "                </head>\n" +
                        "                <body>\n" +
                        "                    <div class=\"container\" id=\"lista_moneda\">\n" +
                        "                        <div class=\"row\">\n" +
                        "                            <div class=\"col-12\">\n" +
                        "                                <h4 class=\"text-center\">Lista de Monedas</h4>\n" +
                        "                                <hr>");
                while ( rs.next() ) {
                    int id_moneda = rs.getInt("id");
                    String  nombre_moneda = rs.getString("nombre");

                    out.println("\t\t\t\t<div class=\"row\">\n" +
                            "\t\t\t\t\t<div class=\"col-12\">\n" +
                            "\t\t\t\t\t\t<div class=\"d-grid gap-3\">\n" +
                            "\t\t\t\t\t\t\t<div class=\"p-2 bg-light border\">"+"<p>" + "id_producto: "+id_moneda+",nombre producto: "+nombre_moneda+"</p>"+"</div>\n" +
                            "\t\t\t\t\t\t</div>\n" +
                            "\t\t\t\t\t</div>\n" +
                            "\t\t\t\t</div>");
                }
                out.println("                            </div>\n" +
                        "                        </div>\n" +
                        "                    </div>\n" +
                        "\t\t<a href=\"menu_consultas.html\"><button type=\"button\" class=\"btn btn-outline-dark\" id=\"volver\">Volver</button></a>"+
                        "                    <script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js\" integrity=\"sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM\" crossorigin=\"anonymous\"></script>\n" +
                        "                </body>\n" +
                        "                </html>");
                rs.close();
                stmt.close();
            }else if(tipo_consulta.equals("mostrar_proveedores")){
                //select de proveedores
                ResultSet rs = stmt.executeQuery( "SELECT * FROM proveedor ORDER BY id DESC LIMIT 10" );
                out.println("                <!DOCTYPE html>\n" +
                        "                <html>\n" +
                        "                <head>\n" +
                        "                    <title>Proveedores</title>\n" +
                        "                    <link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC\" crossorigin=\"anonymous\">\n" +
                        "                    <link rel=\"stylesheet\" href=\"estilos.css\">\n" +
                        "                </head>\n" +
                        "                <body>\n" +
                        "                    <div class=\"container\" id=\"lista_moneda\">\n" +
                        "                        <div class=\"row\">\n" +
                        "                            <div class=\"col-12\">\n" +
                        "                                <h4 class=\"text-center\">Lista de Proveedores</h4>\n" +
                        "                                <hr>");
                while ( rs.next() ) {
                    int id_proveedor = rs.getInt("id");
                    String  nombre_proveedor = rs.getString("nombre");
                    String  ruc = rs.getString("ruc");
                    String  pais_id = rs.getString("pais_id");

                    out.println("\t\t\t\t<div class=\"row\">\n" +
                            "\t\t\t\t\t<div class=\"col-12\">\n" +
                            "\t\t\t\t\t\t<div class=\"d-grid gap-3\">\n" +
                            "\t\t\t\t\t\t\t<div class=\"p-2 bg-light border\">"+"<p>" + "id_proveedor: "+id_proveedor+", nombre proveedor: "+nombre_proveedor+", ruc: "+ruc+", pais_id:  "+pais_id+"</p>"+"</div>\n" +
                            "\t\t\t\t\t\t</div>\n" +
                            "\t\t\t\t\t</div>\n" +
                            "\t\t\t\t</div>");
                }
                out.println("                            </div>\n" +
                        "                        </div>\n" +
                        "                    </div>\n" +
                        "\t\t<a href=\"menu_consultas.html\"><button type=\"button\" class=\"btn btn-outline-dark\" id=\"volver\">Volver</button></a>"+
                        "                    <script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js\" integrity=\"sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM\" crossorigin=\"anonymous\"></script>\n" +
                        "                </body>\n" +
                        "                </html>");
                rs.close();
                stmt.close();
            }
        }catch(Exception e){
            PrintWriter out = resp.getWriter();
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
}

