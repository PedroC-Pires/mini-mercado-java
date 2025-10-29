package minimercado.venda;

public class ItemVenda {
    private int id;
    private String nome;
    private int quantidade;
    private double preco;

    public ItemVenda(String nome, int quantidade, double preco) {
        this.id = minimercado.util.GeradorId.getProximoItemVendaId();
        this.nome = nome;
        this.quantidade = quantidade;
        this.preco = preco;
    }

    public double getSubtotal() {
        return this.quantidade * this.preco;
    }

    // Getters
    public int getId() { return id; }
    public String getNome() { return nome; }
    public int getQuantidade() { return quantidade; }
    public double getPreco() { return preco; }
}