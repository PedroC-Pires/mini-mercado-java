package minimercado.util;

import java.util.concurrent.atomic.AtomicInteger;

public class GeradorId {
    private static final AtomicInteger CLIENTE_ID = new AtomicInteger(0);
    private static final AtomicInteger PRODUTO_ID = new AtomicInteger(0);
    private static final AtomicInteger VENDA_ID = new AtomicInteger(0);
    private static final AtomicInteger ITEM_VENDA_ID = new AtomicInteger(0);

    public static int getProximoClienteId() {
        return CLIENTE_ID.incrementAndGet();
    }

    public static int getProximoProdutoId() {
        return PRODUTO_ID.incrementAndGet();
    }

    public static int getProximoVendaId() {
        return VENDA_ID.incrementAndGet();
    }

    public static int getProximoItemVendaId() {
        return ITEM_VENDA_ID.incrementAndGet();
    }
}