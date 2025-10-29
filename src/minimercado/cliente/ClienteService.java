package minimercado.cliente;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClienteService implements IClienteService {
    private final List<Cliente> repositorioClientes = new ArrayList<>();

    @Override
    public void cadastrarCliente(Cliente cliente) {
        repositorioClientes.add(cliente);
        System.out.println("Cliente " + cliente.getNome() + " cadastrado com sucesso!");
    }

    @Override
    public Optional<Cliente> consultarCliente(int id) {
        return repositorioClientes.stream()
                .filter(c -> c.getId() == id)
                .findFirst();
    }

    @Override
    public void editarCliente(int id, Cliente dadosAtualizados) {
        Optional<Cliente> clienteOpt = consultarCliente(id);
        if (!clienteOpt.isPresent()) {
            System.out.println("Erro: Cliente não encontrado para edição.");
            return;
        }

        Cliente existente = clienteOpt.get();
        existente.setNome(dadosAtualizados.getNome());
        existente.setTelefone(dadosAtualizados.getTelefone());
        existente.setCategoria(dadosAtualizados.getCategoria());

        if (existente instanceof ClientePessoaFisica && dadosAtualizados instanceof ClientePessoaFisica) {
            ((ClientePessoaFisica) existente).setCpf(((ClientePessoaFisica) dadosAtualizados).getCpf());
        } else if (existente instanceof ClientePessoaJuridica && dadosAtualizados instanceof ClientePessoaJuridica) {
            ((ClientePessoaJuridica) existente).setCnpj(((ClientePessoaJuridica) dadosAtualizados).getCnpj());
        }

        System.out.println("Cliente atualizado com sucesso.");
    }

    @Override
    public List<Cliente> listarClientes() {
        return new ArrayList<>(repositorioClientes);
    }

    @Override
    public boolean excluirCliente(int id) {
        Optional<Cliente> clienteOpt = consultarCliente(id);
        if (!clienteOpt.isPresent()) {
            System.out.println("Erro: Cliente não encontrado para exclusão.");
            return false;
        }
        repositorioClientes.remove(clienteOpt.get());
        System.out.println("Cliente removido com sucesso.");
        return true;
    }
}