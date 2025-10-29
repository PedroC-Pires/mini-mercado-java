package minimercado.venda;

import java.util.List;
import java.util.Map;

public interface IVendaService {
    Venda registrarVenda(Map<Integer, Integer> itensVenda, Integer idCliente) throws Exception;

    List<Venda> listarVendas();
}