package sv.edu.utec.tareareposicion.Modelo;

public class Usuario {

    public Usuario(String nombre, String apellidos, String usuario, String password) {
        Nombre = nombre;
        Apellidos = apellidos;
        Usuario = usuario;
        Password = password;
    }

    public boolean isNull(){
        if(Nombre.equals("")|| Apellidos.equals("") || Usuario.equals("") || Password.equals("")){
            return false;
        }
        else
        {
            return true;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    int id;

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getApellidos() {
        return Apellidos;
    }

    public void setApellidos(String apellidos) {
        Apellidos = apellidos;
    }

    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String usuario) {
        Usuario = usuario;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    String Nombre,Apellidos, Usuario, Password;


    public Usuario(){

    }
}
