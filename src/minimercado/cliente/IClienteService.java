package minimercado.cliente;

import java.util.List;
import java.util.Optional;

public interface IClienteService {
    void cadastrarCliente(Cliente cliente);
    Optional<Cliente> consultarCliente(int id);
    void editarCliente(int id, Cliente dadosAtualizados);
    List<Cliente> listarClientes();
    boolean excluirCliente(int id);
}