package minimercado.venda;

import minimercado.cliente.Cliente;
import minimercado.cliente.IClienteService;
import minimercado.produto.IProdutoService;
import minimercado.produto.Produto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class VendaService implements IVendaService {
    private final List<Venda> repositorioVendas = new ArrayList<>();

    private final IProdutoService produtoService;
    private final IClienteService clienteService;

    public VendaService(IProdutoService produtoService, IClienteService clienteService) {
        this.produtoService = produtoService;
        this.clienteService = clienteService;
    }

    @Override
    public Venda registrarVenda(Map<Integer, Integer> itensVenda, Integer idCliente) throws Exception {
        Venda novaVenda = new Venda();

        if (idCliente != null) {
            Optional<Cliente> clienteOpt = clienteService.consultarCliente(idCliente);
            if (clienteOpt.isPresent()) {
                novaVenda.setCliente(clienteOpt.get());

                // Desconto é aplicado dentro de Venda#setCliente()
            } else {
                System.out.println("Aviso: ID de cliente fidelidade não encontrado.");
            }
        }

        for (Map.Entry<Integer, Integer> item : itensVenda.entrySet()) {
            int idProduto = item.getKey();
            int quantidade = item.getValue();

            Optional<Produto> produtoOpt = produtoService.consultarProduto(idProduto);

            if (!produtoOpt.isPresent()) {
                throw new Exception("Produto com ID " + idProduto + " não encontrado.");
            }

            Produto produto = produtoOpt.get();

            boolean baixouEstoque = produtoService.registrarBaixaEstoque(idProduto, quantidade);

            if (!baixouEstoque) {
                throw new Exception("Estoque insuficiente para o produto: " + produto.getNome());
            }

            ItemVenda itemVenda = new ItemVenda(produto.getNome(), quantidade, produto.getPreco());
            novaVenda.adicionarItem(itemVenda);
        }

        repositorioVendas.add(novaVenda);
        System.out.println("Venda " + novaVenda.getId() + " registrada com sucesso!");
        return novaVenda;
    }

    @Override
    public List<Venda> listarVendas() {
        return new ArrayList<>(repositorioVendas);
    }
}