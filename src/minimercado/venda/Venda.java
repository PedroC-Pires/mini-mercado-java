package minimercado.venda;

import minimercado.cliente.Cliente;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Venda {
    private int id;
    private LocalDateTime dataHora;
    private Cliente cliente;
    private List<ItemVenda> itens;
    private double desconto;
    private double valorTotal;

    public Venda() {
        this.id = minimercado.util.GeradorId.getProximoVendaId();
        this.dataHora = LocalDateTime.now();
        this.itens = new ArrayList<>();
        this.desconto = 0.0;
        this.valorTotal = 0.0;
    }

    public void adicionarItem(ItemVenda item) {
        this.itens.add(item);
        calcularValorTotal();
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
        aplicarDescontoFidelidade();
    }

    private void aplicarDescontoFidelidade() {
        if (this.cliente != null) {
            this.desconto = DescontoFidelidade.getDescontoPorCategoria(cliente.getCategoria());
        }
        calcularValorTotal();
    }

    private void calcularValorTotal() {
        double subtotal = 0.0;
        for (ItemVenda item : this.itens) {
            subtotal += item.getSubtotal();
        }

        double valorDesconto = subtotal * this.desconto;
        this.valorTotal = subtotal - valorDesconto;
    }

    // Getters
    public int getId() { return id; }
    public LocalDateTime getDataHora() { return dataHora; }
    public Cliente getCliente() { return cliente; }
    public List<ItemVenda> getItens() { return itens; }
    public double getDesconto() { return desconto; }
    public double getValorTotal() { return valorTotal; }
}