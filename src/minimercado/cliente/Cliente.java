package minimercado.cliente;

public abstract class Cliente {
    private int id;
    private String nome;
    private String telefone;
    private Categoria categoria;

    public Cliente(String nome, String telefone, Categoria categoria) {
        this.id = minimercado.util.GeradorId.getProximoClienteId();
        this.nome = nome;
        this.telefone = telefone;
        this.categoria = categoria;
    }

    // Getters e Setters
    public int getId() { return id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }
    public abstract String getDocumento();
}