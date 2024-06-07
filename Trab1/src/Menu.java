import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.List;
import java.util.Calendar;
import java.util.Comparator;
import java.util.stream.Collectors;

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
            System.out.println("Sistema de Controle de Aquisições");
            System.out.println("1. Adicionar Usuário");
            System.out.println("2. Logar Usuário");
            System.out.println("3. Registrar Pedido");
            System.out.println("4. Aprovar Pedido");
            System.out.println("5. Gerenciamento de Pedidos");
            System.out.println("6. Buscar Pedidos por Funcionário");
            System.out.println("7. Listar Funcionários");
            System.out.println("8. Excluir Pedido");
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
                    avaliarPedido();
                    break;
                case 5:
                    gerenciamentoDePedidos();
                    break;
                case 6:
                    buscarPedidosPorFuncionario();
                    break;
                case 7:
                    System.out.print(sistema.getFuncionarios());
                    break;
                case 8:
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
            System.out.println("Gerenciamento de Pedidos:");
            System.out.println("1. Número de pedidos total");
            System.out.println("2. Número de pedidos nos últimos 30");
            System.out.println("3. Pedido de aquisição de maior valor");
            System.out.println("4. Listar Pedidos entre Datas");
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
                    numeroPedidosAprovadosReprovados();
                    break;
                case 2:
                    pedidosUltimos30Dias();
                    break;
                case 3:
                    pedidoMaiorValorAberto();
                    break;
                case 4:
                    listarPedidosEntreDatas();
                    break;
                case 0:
                    System.out.println("Voltando ao menu principal...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    @SuppressWarnings("unlikely-arg-type")
    private void numeroPedidosAprovadosReprovados() {
        List<PedidoAquisicao> pedidos = sistema.getPedidos();
        long totalPedidos = pedidos.size();
        long aprovados = pedidos.stream().filter(p -> p.getStatus().equals("true")).count();
        long reprovados = pedidos.stream().filter(p -> p.getStatus().equals("false")).count();

        System.out.println("Total de pedidos: " + totalPedidos);
        System.out.println("Aprovados: " + aprovados + " (" + (100.0 * aprovados / totalPedidos) + "%)");
        System.out.println("Reprovados: " + reprovados + " (" + (100.0 * reprovados / totalPedidos) + "%)");
    }

    private void pedidosUltimos30Dias() {
        List<PedidoAquisicao> pedidos = sistema.getPedidos();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -30);
        Date dataLimite = calendar.getTime();

        List<PedidoAquisicao> ultimos30Dias = pedidos.stream()
                .filter(p -> p. getDataPedido().after(dataLimite))
                .collect(Collectors.toList());

        double valorTotal = ultimos30Dias.stream().mapToDouble(PedidoAquisicao::getValorTotal).sum();
        double valorMedio = ultimos30Dias.size() > 0 ? valorTotal / ultimos30Dias.size() : 0;

        System.out.println("Número de pedidos nos últimos 30 dias: " + ultimos30Dias.size());
        System.out.println("Valor médio dos pedidos nos últimos 30 dias: " + valorMedio);
    }

    private void pedidoMaiorValorAberto() {
        List<PedidoAquisicao> pedidos = sistema.getPedidos();
        PedidoAquisicao maiorPedidoAberto = pedidos.stream()
                .filter(p -> p.getStatus().equals("Aberto"))
                .max(Comparator.comparingDouble(PedidoAquisicao::getValorTotal))
                .orElse(null);

        if (maiorPedidoAberto != null) {
            System.out.println("Pedido de maior valor ainda aberto: " + maiorPedidoAberto);
        } else {
            System.out.println("Não há pedidos abertos.");
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

    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.exibirMenu();
    }
}