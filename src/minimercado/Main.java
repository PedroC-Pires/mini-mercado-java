package minimercado;

import minimercado.cliente.*;
import minimercado.produto.IProdutoService;
import minimercado.produto.Produto;
import minimercado.produto.ProdutoService;
import minimercado.venda.IVendaService;
import minimercado.venda.VendaService;
import minimercado.venda.Venda;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static final IClienteService clienteService = new ClienteService();
    private static final IProdutoService produtoService = new ProdutoService();
    private static final IVendaService vendaService = new VendaService(produtoService, clienteService);

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Bem-vindo ao Sistema de Mini Mercado de Auto Serviço!");

        carregarDadosIniciais();
        menuPrincipal();
    }

    private static void menuPrincipal() {
        int modo = -1;
        while (modo != 0) {
            System.out.println("\nSelecione o modo de operação:");
            System.out.println("1 - Modo Gestor");
            System.out.println("2 - Modo Cliente (Registrar Venda)");
            System.out.println("\n0 - Sair");

            modo = lerInteiro("Opção: ");

            switch (modo) {
                case 1:
                    menuGestor();
                    break;
                case 2:
                    menuClienteAutoServico();
                    break;
                case 0:
                    System.out.println("Saindo do sistema...");
                    break;
                default:
                    System.out.println("Modo inválido!");
            }
        }
    }

    private static void menuGestor() {
        int opcao = -1;
        do {
            System.out.println("\n--- MODO GESTOR ---");
            System.out.println("\n--- PRODUTOS ---");
            System.out.println("1. Cadastrar Produto");
            System.out.println("2. Consultar Produto");
            System.out.println("3. Editar Produto");
            System.out.println("4. Listar Todos Produtos");
            System.out.println("\n--- ESTOQUE ---");
            System.out.println("5. Gerenciar Estoque");
            System.out.println("\n--- CLIENTES ---");
            System.out.println("6. Cadastrar Novo Cliente");
            System.out.println("7. Consultar Cliente");
            System.out.println("8. Editar Cliente");
            System.out.println("9. Listar Todos Clientes");
            System.out.println("\n0. Voltar");

            opcao = lerInteiro("Opção: ");

            switch (opcao) {
                case 1:
                    cadastrarProdutoFluxo();
                    break;
                case 2:
                    consultarProdutoFluxo();
                    break;
                case 3:
                    editarProdutoFluxo();
                    break;
                case 4:
                    listarProdutosFluxo();
                    break;
                case 5:
                    gerenciarEstoqueFluxo();
                    break;
                case 6:
                    cadastrarClienteFluxo();
                    break;
                case 7:
                    consultarClienteFluxo();
                    break;
                case 8:
                    editarClienteFluxo();
                    break;
                case 9:
                    listarClientesFluxo();
                    break;
                case 0:
                    menuPrincipal();
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private static void menuClienteAutoServico() {
        System.out.println("\n--- MODO CLIENTE (Auto Serviço) ---");
        try {
            Map<Integer, Integer> carrinho = new HashMap<>();

            boolean adicionando = true;
            while (adicionando) {
                System.out.println("\nProdutos disponíveis:");
                produtoService.listarProdutos().forEach(p ->
                        System.out.printf("ID: %d | Produto: %s | Preço: R$%.2f | Estoque: %d\n",
                                p.getId(), p.getNome(), p.getPreco(), p.getEstoque())
                );

                int idProduto = lerInteiro("Digite o ID do produto (ou 0 para finalizar): ");
                if (idProduto == 0) {
                    adicionando = false;
                    break;
                }

                int quantidade = lerInteiro("Quantidade: ");
                if (quantidade <= 0) {
                    System.out.println("Quantidade inválida.");
                    continue;
                }

                carrinho.put(idProduto, carrinho.getOrDefault(idProduto, 0) + quantidade);
                System.out.println("Item adicionado ao carrinho.");
            }

            if (carrinho.isEmpty()) {
                System.out.println("Carrinho vazio. Cancelando venda.");
                return;
            }

            System.out.print("Você possui cadastro fidelidade? (S/N): ");
            Integer idCliente = null;
            String resp = scanner.nextLine().trim();
            if (resp.equalsIgnoreCase("S")) {
                idCliente = lerInteiro("Digite o ID do seu cadastro fidelidade: ");
            }

            Venda venda = vendaService.registrarVenda(carrinho, idCliente);
            System.out.printf("Venda %d registrada. Total: R$%.2f\n", venda.getId(), venda.getValorTotal());

        } catch (Exception e) {
            System.err.println("Erro ao registrar venda: " + e.getMessage());
        }
    }

    private static void carregarDadosIniciais() {
        produtoService.cadastrarProduto(new Produto("Arroz 1kg", "789001", 5.50, 4.00, 100));
        produtoService.cadastrarProduto(new Produto("Feijão 1kg", "789002", 7.00, 5.00, 100));
        produtoService.cadastrarProduto(new Produto("Óleo de Soja", "789003", 9.00, 7.50, 50));

        clienteService.cadastrarCliente(new ClientePessoaFisica("Joao Bobao", "91234-5678", Categoria.OURO, "123.456.789-10"));
        clienteService.cadastrarCliente(new ClientePessoaFisica("Maria Boba", "91234-4321", Categoria.DIAMANTE, "987.654.321-00"));
        clienteService.cadastrarCliente(new ClientePessoaJuridica("Empresa do Joao Bobao", "98765-4321", Categoria.PRATA, "00.001.002/0001-02"));
    }

    private static int lerInteiro(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número inteiro.");
            }
        }
    }

    private static double lerDouble(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(scanner.nextLine().trim().replace(",", "."));
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número (use vírgula ou ponto).");
            }
        }
    }

    private static void cadastrarProdutoFluxo() {
        System.out.println("\nCadastro de Produto");
        System.out.print("Nome (vazio para voltar): ");
        String nome = scanner.nextLine();
        if (nome.isEmpty()) return;

        System.out.print("Código de Barras (vazio para voltar): ");
        String codigo = scanner.nextLine();
        if (codigo.isEmpty()) return;

        double preco = lerDouble("Preço de venda (0 para voltar): ");
        if (preco <= 0) return;

        double custo = lerDouble("Custo médio (0 para voltar): ");
        if (custo <= 0) return;

        int estoqueInicial = lerInteiro("Estoque inicial: ");

        produtoService.cadastrarProduto(new Produto(nome, codigo, preco, custo, estoqueInicial));
    }

    private static void listarProdutosFluxo() {
        System.out.println("\nLista de Produtos:");
        produtoService.listarProdutos().forEach(p ->
                System.out.printf("ID: %d | Produto: %s | Preço: R$%.2f | Estoque: %d\n",
                        p.getId(), p.getNome(), p.getPreco(), p.getEstoque())
        );
    }

    private static void entradaEstoqueFluxo() {
        System.out.println("\nEntrada de Estoque");
        int id = lerInteiro("ID do produto (0 para voltar): ");
        if (id <= 0) return;

        int qtd = lerInteiro("Quantidade a adicionar (0 para voltar): ");
        if (qtd <= 0) return;

        produtoService.registrarEntradaEstoque(id, qtd);
    }


    private static void baixaEstoqueFluxo() {
        System.out.println("\nDar baixa no Estoque");
        int id = lerInteiro("ID do produto (0 para voltar): ");
        if (id <= 0) return;

        int qtd = lerInteiro("Quantidade a dar baixa (0 para voltar): ");
        if (qtd <= 0) return;

        boolean ok = produtoService.registrarBaixaEstoque(id, qtd);
        if (!ok) {
            System.out.println("Falha na baixa de estoque (verifique ID e quantidade).");
        }
    }

    private static void gerenciarEstoqueFluxo() {
        int op;
        do {
            System.out.println("\n--- Gerenciar Estoque ---");
            System.out.println("1. Adicionar ao estoque");
            System.out.println("2. Dar baixa do estoque");
            System.out.println("\n0. Voltar");
            op = lerInteiro("Opção: ");
            switch (op) {
                case 1:
                    entradaEstoqueFluxo();
                    break;
                case 2:
                    baixaEstoqueFluxo();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (true);
    }

    private static void consultarProdutoFluxo() {
        int id = lerInteiro("ID do produto (0 para voltar): ");
        if (id <= 0) return;
        java.util.Optional<Produto> opt = produtoService.consultarProduto(id);
        if (opt.isPresent()) {
            Produto p = opt.get();
            System.out.printf("ID: %d | Produto: %s | Código: %s | Preço: R$%.2f | Custo: R$%.2f | Estoque: %d\n",
                    p.getId(), p.getNome(), p.getCodigoBarras(), p.getPreco(), p.getCustoMedio(), p.getEstoque());
        } else {
            System.out.println("Produto não encontrado.");
        }
    }

    private static void editarProdutoFluxo() {
        System.out.println("\nEditar/Excluir Produto");
        System.out.println("1. Editar");
        System.out.println("2. Excluir");
        System.out.println("0. Voltar");
        int op = lerInteiro("Opção: ");
        if (op == 0) return;

        int id = lerInteiro("ID do produto (0 para voltar): ");
        if (id <= 0) return;

        java.util.Optional<Produto> opt = produtoService.consultarProduto(id);
        if (!opt.isPresent()) {
            System.out.println("Produto não encontrado.");
            return;
        }

        if (op == 2) {
            produtoService.excluirProduto(id);
            return;
        }

        Produto pAtual = opt.get();
        System.out.print("Novo nome (vazio para manter): ");
        String nome = scanner.nextLine();
        System.out.print("Novo código de barras (vazio para manter): ");
        String codigo = scanner.nextLine();
        System.out.print("Novo preço (vazio para manter): ");
        String precoStr = scanner.nextLine().trim();
        System.out.print("Novo custo médio (vazio para manter): ");
        String custoStr = scanner.nextLine().trim();

        String novoNome = nome.isEmpty() ? pAtual.getNome() : nome;
        String novoCodigo = codigo.isEmpty() ? pAtual.getCodigoBarras() : codigo;
        double novoPreco = precoStr.isEmpty() ? pAtual.getPreco() : Double.parseDouble(precoStr.replace(",", "."));
        double novoCusto = custoStr.isEmpty() ? pAtual.getCustoMedio() : Double.parseDouble(custoStr.replace(",", "."));

        Produto dados = new Produto(novoNome, novoCodigo, novoPreco, novoCusto, pAtual.getEstoque());
        produtoService.editarProduto(id, dados);
    }

    private static void listarClientesFluxo() {
        System.out.println("\nLista de Clientes:");
        clienteService.listarClientes().forEach(c ->
                System.out.printf("ID: %d | Nome: %s | Telefone: %s | Categoria: %s | Doc: %s\n",
                        c.getId(), c.getNome(), c.getTelefone(), c.getCategoria(), c.getDocumento())
        );
    }

    private static void consultarClienteFluxo() {
        int id = lerInteiro("ID do cliente (0 para voltar): ");
        if (id <= 0) return;

        java.util.Optional<minimercado.cliente.Cliente> opt = clienteService.consultarCliente(id);
        if (opt.isPresent()) {
            minimercado.cliente.Cliente c = opt.get();
            System.out.printf("ID: %d | Nome: %s | Telefone: %s | Categoria: %s | Doc: %s\n",
                    c.getId(), c.getNome(), c.getTelefone(), c.getCategoria(), c.getDocumento());
        } else {
            System.out.println("Cliente não encontrado.");
        }
    }

    private static void cadastrarClienteFluxo() {
        System.out.println("\nCadastro de Cliente");
        System.out.println("1. Pessoa Física");
        System.out.println("2. Pessoa Jurídica");
        System.out.println("\n0. Voltar");
        int tipo = lerInteiro("Tipo: ");
        if (tipo == 1) {
            System.out.print("Nome (vazio para voltar): ");
            String nome = scanner.nextLine();
            if (nome.isEmpty()) return;

            System.out.print("Telefone: ");
            String telefone = scanner.nextLine();

            Categoria categoria = lerCategoriaFluxo();

            System.out.print("CPF: ");
            String cpf = scanner.nextLine();

            clienteService.cadastrarCliente(new ClientePessoaFisica(nome, telefone, categoria, cpf));
        } else if (tipo == 2) {
            System.out.print("Nome: (vazio para voltar)");
            String nome = scanner.nextLine();
            if (nome.isEmpty()) return;

            System.out.print("Telefone: ");
            String telefone = scanner.nextLine();

            Categoria categoria = lerCategoriaFluxo();

            System.out.print("CNPJ: ");
            String cnpj = scanner.nextLine();

            clienteService.cadastrarCliente(new ClientePessoaJuridica(nome, telefone, categoria, cnpj));
        } else if (tipo == 0) {
            return;
        } else {
            System.out.println("Opção inválida.");
        }
    }

    private static void editarClienteFluxo() {
        System.out.println("\nEditar/Excluir Cliente");
        System.out.println("1. Editar");
        System.out.println("2. Excluir");
        System.out.println("0. Voltar");
        int op = lerInteiro("Opção: ");
        if (op == 0) return;

        int id = lerInteiro("ID do cliente: ");
        java.util.Optional<minimercado.cliente.Cliente> opt = clienteService.consultarCliente(id);
        if (!opt.isPresent()) {
            System.out.println("Cliente não encontrado.");
            return;
        }

        if (op == 2) {
            clienteService.excluirCliente(id);
            return;
        }

        minimercado.cliente.Cliente cAtual = opt.get();
        System.out.print("Novo nome (vazio p/ manter): ");
        String nome = scanner.nextLine();
        System.out.print("Novo telefone (vazio p/ manter): ");
        String telefone = scanner.nextLine();
        System.out.println("--- DESCONTOS ---");
        System.out.println("BRONZE: 0%");
        System.out.println("PRATA: 3%");
        System.out.println("OURO: 5%");
        System.out.println("DIAMANTE: 10%");
        System.out.println("Alterar categoria?");
        Categoria categoria = lerCategoriaFluxoOpcional(cAtual.getCategoria());

        String novoNome = nome.isEmpty() ? cAtual.getNome() : nome;
        String novoTelefone = telefone.isEmpty() ? cAtual.getTelefone() : telefone;
        Categoria novaCategoria = categoria == null ? cAtual.getCategoria() : categoria;

        if (cAtual instanceof ClientePessoaFisica) {
            String cpf = ((ClientePessoaFisica) cAtual).getCpf();
            ClientePessoaFisica dados = new ClientePessoaFisica(novoNome, novoTelefone, novaCategoria, cpf);
            clienteService.editarCliente(id, dados);
        } else if (cAtual instanceof ClientePessoaJuridica) {
            String cnpj = ((ClientePessoaJuridica) cAtual).getCnpj();
            ClientePessoaJuridica dados = new ClientePessoaJuridica(novoNome, novoTelefone, novaCategoria, cnpj);
            clienteService.editarCliente(id, dados);
        }
    }

    private static Categoria lerCategoriaFluxo() {
        System.out.println("Categoria (1-BRONZE, 2-PRATA, 3-OURO, 4-DIAMANTE)");
        int op = lerInteiro("Opção: ");
        switch (op) {
            case 1: return Categoria.BRONZE;
            case 2: return Categoria.PRATA;
            case 3: return Categoria.OURO;
            case 4: return Categoria.DIAMANTE;
            default:
                System.out.println("Opção inválida. Padrão BRONZE escolhido.");
                return Categoria.BRONZE;
        }
    }

    private static Categoria lerCategoriaFluxoOpcional(Categoria atual) {
        System.out.printf("Categoria atual: %s. Deseja alterar? (S/N): ", atual);
        String r = scanner.nextLine().trim();
        if (!r.equalsIgnoreCase("S")) return null;
        return lerCategoriaFluxo();
    }
}