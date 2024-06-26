import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private SistemaControleAquisicoes sistema;
    private Scanner scanner;

    public Menu() {
        sistema = new SistemaControleAquisicoes();
        scanner = new Scanner(System.in);
    }

    public void exibirMenu() {
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("-------------------------------------------");
            System.out.println("|    Sistema de Controle de Aquisições    |");
            System.out.println("-------------------------------------------");
            System.out.println("1. Adicionar Usuário");
            System.out.println("2. Logar Usuário");
            System.out.println("3. Registrar Pedido");
            System.out.println("4. Gerenciamento de Pedidos(Administrador)");
            System.out.println("5. Listar Funcionários");
            System.out.println("6. Excluir Pedido");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida. Por favor, digite um número.");
                continue;
            }

            switch (opcao) {
                case 1:
                    adicionarUsuario();
                    break;
                case 2:
                    trocarUsuario();
                    break;
                case 3:
                    registrarPedido();
                    break;
                case 4:
                    gerenciamentoDePedidos();
                    break;
                case 5:
                    System.out.print(sistema.getFuncionarios());
                    break;
                case 6:
                    excluirPedido();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private void excluirPedido(){
        System.out.print("ID do pedido: ");
        int idPedido = 0;
        boolean valido = false;
        while (!valido) {
            try {
                idPedido = Integer.parseInt(scanner.nextLine());
                valido = true;
            } catch (NumberFormatException e) {
                System.out.println("ID inválido. Por favor, digite um número.");
            }
        }
        sistema.excluirPedido(idPedido);
    }

    private void adicionarUsuario() {
        System.out.print("Nome do usuário: ");
        String nome = scanner.nextLine();
        System.out.print("ID do usuário: ");
        String id = scanner.nextLine();
        TipoUsuario tipo = selecionarTipoUsuario();
        Departamento departamento = selecionarDepartamento();

        User usuario = new User(id, nome, tipo, departamento);
        sistema.adicionarUsuario(usuario);
        System.out.println(sistema.getFuncionarios());
    }

    private TipoUsuario selecionarTipoUsuario() {
        TipoUsuario tipo = null;
        boolean valido = false;

        while (!valido) {
            System.out.println("Tipo de usuário:");
            System.out.println("1. ADMINISTRADOR");
            System.out.println("2. FUNCIONARIO");
            System.out.print("Escolha uma opção: ");
            try {
                int tipoOpcao = Integer.parseInt(scanner.nextLine());
                switch (tipoOpcao) {
                    case 1:
                        tipo = TipoUsuario.ADMINISTRADOR;
                        valido = true;
                        break;
                    case 2:
                        tipo = TipoUsuario.FUNCIONARIO;
                        valido = true;
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, digite um número.");
            }
        }
        return tipo;
    }

    private Departamento selecionarDepartamento() {
        Departamento departamento = null;
        boolean valido = false;

        while (!valido) {
            System.out.println("Departamentos disponíveis:");
            for (Departamento dep : Departamento.values()) {
                System.out.println(dep.ordinal() + 1 + ". " + dep);
            }
            System.out.print("Escolha uma opção: ");
            try {
                int deptOpcao = Integer.parseInt(scanner.nextLine());
                if (deptOpcao >= 1 && deptOpcao <= Departamento.values().length) {
                    departamento = Departamento.values()[deptOpcao - 1];
                    valido = true;
                } else {
                    System.out.println("Opção inválida. Tente novamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, digite um número.");
            }
        }
        return departamento;
    }

    private void trocarUsuario() {
        System.out.print("ID do usuário: ");
        String id = scanner.nextLine();
        sistema.trocarUsuario(id);
    }

    private void registrarPedido() {
        System.out.print("Nome do item: ");
        String nomeItem = scanner.nextLine();
        System.out.print("Valor do item: ");
        double valorItem = 0;
        boolean valido = false;
        while (!valido) {
            try {
                valorItem = Double.parseDouble(scanner.nextLine());
                valido = true;
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido. Por favor, digite um número.");
            }
        }

        System.out.print("Quantidade do item: ");
        int qnt = 0;
        valido = false;
        while (!valido) {
            try {
                qnt = Integer.parseInt(scanner.nextLine());
                valido = true;
            } catch (NumberFormatException e) {
                System.out.println("Quantidade inválida. Por favor, digite um número.");
            }
        }

        Item item = new Item(nomeItem, valorItem, qnt);
        sistema.registrarPedido(item);
    }

    private void avaliarPedido() {
        System.out.print("ID do pedido: ");
        int idPedido = 0;
        boolean valido = false;
        while (!valido) {
            try {
                idPedido = Integer.parseInt(scanner.nextLine());
                valido = true;
            } catch (NumberFormatException e) {
                System.out.println("ID inválido. Por favor, digite um número.");
            }
        }

        System.out.print("Aprovar pedido? (true/false): ");
        boolean aprovado = false;
        valido = false;
        while (!valido) {
            try {
                aprovado = Boolean.parseBoolean(scanner.nextLine());
                valido = true;
            } catch (Exception e) {
                System.out.println("Entrada inválida. Por favor, digite true ou false.");
            }
        }
        sistema.avaliarPedido(idPedido, aprovado);
    }

    private void gerenciamentoDePedidos() {
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("-------------------------------------------");
            System.out.println("|         Painel Admnistrador             |");
            System.out.println("-------------------------------------------");
            System.out.println("1. Número de pedidos total");
            System.out.println("2. Número de pedidos nos últimos 30");
            System.out.println("3. Pedido de aquisição de maior valor");
            System.out.println("4. Listar Pedidos entre Datas");
            System.out.println("5. Listar Pedidos por Descrição de Item"); // adicionar
            System.out.println("6. Avaliar pedido");
            System.out.println("7. Buscar Pedidos por Funcionário");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida. Por favor, digite um número.");
                continue;
            }

            switch (opcao) {
                case 1:
                    sistema.numeroPedidosAprovadosReprovados();
                    break;
                case 2:
                    sistema.pedidosUltimos30Dias();
                    break;
                case 3:
                    sistema.pedidoMaiorValorAberto();
                    break;
                case 4:
                    listarPedidosEntreDatas();
                    break;
                case 5:
                    buscarPedidosPorDescricaoItem();
                    break;
                case 6:
                    avaliarPedido();
                    break;
                case 7:
                    buscarPedidosPorFuncionario();
                    break;
                case 0:
                    System.out.println("Voltando ao menu principal...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private void listarPedidosEntreDatas() {
        System.out.print("Data de início (dd/MM/yyyy): ");
        Date dataInicio = null;
        boolean valido = false;
        while (!valido) {
            dataInicio = parseDate(scanner.nextLine());
            if (dataInicio != null) {
                valido = true;
            }
        }
        System.out.print("Data de fim (dd/MM/yyyy): ");
        Date dataFim = null;
        valido = false;
        while (!valido) {
            dataFim = parseDate(scanner.nextLine());
            if (dataFim != null) {
                valido = true;
            }
        }
        sistema.listarPedidosEntreDatas(dataInicio, dataFim);
    }

    private void buscarPedidosPorFuncionario() {
        System.out.print("ID do funcionário: ");
        String idFuncionario = scanner.nextLine();
        sistema.buscarPedidosPorFuncionario(idFuncionario);
    }

    private Date parseDate(String dateStr) {
        try {
            return new SimpleDateFormat("dd/MM/yyyy").parse(dateStr);
        } catch (ParseException e) {
            System.out.println("Data inválida. Tente novamente.");
            return null;
        }
    }

    private void buscarPedidosPorDescricaoItem() {
        System.out.print("Digite a descrição do item: ");
        String descricao = scanner.nextLine();
        List<PedidoAquisicao> pedidos = sistema.buscarPedidosPorDescricaoItem(descricao);
    
        for (PedidoAquisicao pedido : pedidos) {
            System.out.println(pedido);
        }
    
        if (pedidos.isEmpty()) {
            System.out.println("Nenhum pedido encontrado com a descrição: " + descricao);
        }
    }

    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.exibirMenu();
    }
}
