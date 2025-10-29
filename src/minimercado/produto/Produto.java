package minimercado.produto;

public class Produto {
    private int id;
    private String nome;
    private String codigoBarras;
    private double preco;
    private double custoMedio;
    private int estoque;

    public Produto(String nome, String codigoBarras, double preco, double custoMedio, int estoqueInicial) {
        this.id = minimercado.util.GeradorId.getProximoProdutoId();
        this.nome = nome;
        this.codigoBarras = codigoBarras;
        this.preco = preco;
        this.custoMedio = custoMedio;
        this.estoque = estoqueInicial;
    }

    // Getters e Setters
    public int getId() { return id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCodigoBarras() { return codigoBarras; }
    public double getPreco() { return preco; }
    public void setPreco(double preco) { this.preco = preco; }
    public double getCustoMedio() { return custoMedio; }
    public void setCustoMedio(double custoMedio) { this.custoMedio = custoMedio; }
    public int getEstoque() { return estoque; }

    public void adicionarEstoque(int quantidade) {
        if (quantidade > 0) {
            this.estoque += quantidade;
        }
    }

    public boolean baixarEstoque(int quantidade) {
        if (quantidade > 0 && this.estoque >= quantidade) {
            this.estoque -= quantidade;
            return true; // Baixa realizada
        }
        return false; // Estoque insuficiente
    }
}