package minimercado.cliente;

public class ClientePessoaFisica extends Cliente {
    private String cpf;

    public ClientePessoaFisica(String nome, String telefone, Categoria categoria, String cpf) {
        super(nome, telefone, categoria);
        this.cpf = cpf;
    }

    @Override
    public String getDocumento() {
        return this.cpf;
    }

    // Getter/Setter para cpf
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
}