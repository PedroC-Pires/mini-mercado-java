package minimercado.cliente;

public class ClientePessoaJuridica extends Cliente {
    private String cnpj;

    public ClientePessoaJuridica(String nome, String telefone, Categoria categoria, String cnpj) {
        super(nome, telefone, categoria);
        this.cnpj = cnpj;
    }

    @Override
    public String getDocumento() {
        return this.cnpj;
    }

    // Getter/Setter para cnpj
    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }
}