package minimercado.produto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProdutoService implements IProdutoService {
    private final List<Produto> repositorioProdutos = new ArrayList<>();

    @Override
    public void cadastrarProduto(Produto produto) {
        repositorioProdutos.add(produto);
        System.out.println("Produto " + produto.getNome() + " cadastrado!");
    }

    @Override
    public Optional<Produto> consultarProduto(int id) {
        return repositorioProdutos.stream()
                .filter(p -> p.getId() == id)
                .findFirst();
    }

    @Override
    public void editarProduto(int id, Produto dadosAtualizados) {
        Optional<Produto> produtoOpt = consultarProduto(id);
        if (!produtoOpt.isPresent()) {
            System.out.println("Erro: Produto não encontrado para edição.");
            return;
        }

        Produto existente = produtoOpt.get();
        existente.setNome(dadosAtualizados.getNome());
        existente.setPreco(dadosAtualizados.getPreco());
        existente.setCustoMedio(dadosAtualizados.getCustoMedio());

        System.out.println("Produto atualizado com sucesso.");
    }

    @Override
    public List<Produto> listarProdutos() {
        return new ArrayList<>(repositorioProdutos);
    }

    @Override
    public void registrarEntradaEstoque(int idProduto, int quantidade) {
        Optional<Produto> produtoOpt = consultarProduto(idProduto);
        if (produtoOpt.isPresent()) {
            produtoOpt.get().adicionarEstoque(quantidade);
            System.out.println("Estoque de " + produtoOpt.get().getNome() + " atualizado para: " + produtoOpt.get().getEstoque());
        } else {
            System.out.println("Erro: Produto não encontrado para entrada de estoque.");
        }
    }

    @Override
    public boolean registrarBaixaEstoque(int idProduto, int quantidade) {
        Optional<Produto> produtoOpt = consultarProduto(idProduto);
        if (produtoOpt.isPresent()) {
            boolean sucesso = produtoOpt.get().baixarEstoque(quantidade);
            if (sucesso) {
                System.out.println("Baixa de " + quantidade + " unidades de " + produtoOpt.get().getNome() + " realizada.");
                return true;
            } else {
                System.out.println("Erro: Estoque insuficiente para " + produtoOpt.get().getNome());
                return false;
            }
        }
        System.out.println("Erro: Produto não encontrado para baixa de estoque.");
        return false;
    }

    @Override
    public boolean excluirProduto(int id) {
        Optional<Produto> produtoOpt = consultarProduto(id);
        if (!produtoOpt.isPresent()) {
            System.out.println("Erro: Produto não encontrado para exclusão.");
            return false;
        }
        repositorioProdutos.remove(produtoOpt.get());
        System.out.println("Produto removido com sucesso.");
        return true;
    }
}