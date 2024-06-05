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
            System.out.println("Sistema de Controle de Aquisições");
            System.out.println("1. Adicionar Usuário");
            System.out.println("2. Logar Usuário");
            System.out.println("3. Registrar Pedido");
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
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
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
        System.out.println("Usuário adicionado com sucesso.");
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
    
    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.exibirMenu();
    }
}