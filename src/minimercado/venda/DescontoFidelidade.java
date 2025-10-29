package minimercado.venda;

import minimercado.cliente.Categoria;

public class DescontoFidelidade {
    private Categoria categoria;
    private double desconto;

    public DescontoFidelidade(Categoria categoria, double desconto) {
        this.categoria = categoria;
        this.desconto = desconto;
    }

    // Getters
    public Categoria getCategoria() { return categoria; }
    public double getDesconto() { return desconto; }

    public static double getDescontoPorCategoria(Categoria categoria) {
        switch (categoria) {
            case BRONZE: return 0.0; // 0%
            case PRATA: return 0.03; // 3%
            case OURO: return 0.05; // 5%
            case DIAMANTE: return 0.10; // 10%
            default: return 0.0;
        }
    }
}