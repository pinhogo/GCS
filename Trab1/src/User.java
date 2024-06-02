public class User {
    private String id;
    private String nome;
    private TipoUsuario tipo;
    private Departamento departamento;

    public User(String id, String nome, TipoUsuario tipo, Departamento dep) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.departamento = dep;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public TipoUsuario getTipo() {
        return this.tipo;
    }

    public void setTipo(TipoUsuario tipo) {
        this.tipo = tipo;
    }
    
    public Departamento getDepartamento() {
        return this.departamento;
    }

    public void setDepartamento(Departamento dep) {
        this.departamento = dep;
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", nome='" + getNome() + "'" +
            ", tipo='" + getTipo() + "'" +
            ", departamento='" + getDepartamento() + "'" +
            "}";
    }
}

enum TipoUsuario {
    FUNCIONARIO,
    ADMINISTRADOR;
}
