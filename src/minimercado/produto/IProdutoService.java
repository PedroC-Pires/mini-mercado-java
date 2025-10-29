package minimercado.produto;

import java.util.List;
import java.util.Optional;

public interface IProdutoService {
    void cadastrarProduto(Produto produto);
    Optional<Produto> consultarProduto(int id);
    void editarProduto(int id, Produto dadosAtualizados);
    List<Produto> listarProdutos();

    void registrarEntradaEstoque(int idProduto, int quantidade);
    boolean registrarBaixaEstoque(int idProduto, int quantidade);
    boolean excluirProduto(int id);
}