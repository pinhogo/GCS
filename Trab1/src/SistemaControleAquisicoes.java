import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class SistemaControleAquisicoes {
    private User usuarioAtual;
    private List<User> funcionarios;
    private List<PedidoAquisicao> pedidos; // Corrigido: pedidos agora é uma lista de PedidoAquisicao

    public SistemaControleAquisicoes() {
        funcionarios = new ArrayList<>();
        pedidos = new ArrayList<>(); // Inicializa a lista de pedidos
        inicializarDados();
    }

    public List<User> getFuncionarios() {
        return this.funcionarios;
    }

    public List<PedidoAquisicao> getPedidos() {
        return this.pedidos;
    }

    public void setPedidos(List<PedidoAquisicao> pedidos) {
        this.pedidos = pedidos;
    }

    public void inicializarDados() { // Método para inicializar os dados de demonstração
        funcionarios.add(new User("1", "Pedro", TipoUsuario.ADMINISTRADOR, Departamento.FINANCEIRO));
        funcionarios.add(new User("2", "Maria", TipoUsuario.FUNCIONARIO, Departamento.RH));
        funcionarios.add(new User("3", "João", TipoUsuario.FUNCIONARIO, Departamento.MARKETING));
        funcionarios.add(new User("4", "José", TipoUsuario.FUNCIONARIO, Departamento.ENGENHARIA));
        funcionarios.add(new User("5", "Ana", TipoUsuario.FUNCIONARIO, Departamento.FINANCEIRO));
        funcionarios.add(new User("6", "Carlos", TipoUsuario.FUNCIONARIO, Departamento.RH));
        funcionarios.add(new User("7", "Beatriz", TipoUsuario.FUNCIONARIO, Departamento.MARKETING));
        funcionarios.add(new User("8", "Lucas", TipoUsuario.FUNCIONARIO, Departamento.ENGENHARIA));
        funcionarios.add(new User("9", "Fernanda", TipoUsuario.FUNCIONARIO, Departamento.FINANCEIRO));
        funcionarios.add(new User("10", "Pedro", TipoUsuario.FUNCIONARIO, Departamento.RH));
        funcionarios.add(new User("11", "Mariana", TipoUsuario.FUNCIONARIO, Departamento.MARKETING));
        funcionarios.add(new User("12", "Ricardo", TipoUsuario.FUNCIONARIO, Departamento.MANUTENCAO));
        funcionarios.add(new User("13", "Sofia", TipoUsuario.FUNCIONARIO, Departamento.FINANCEIRO));
        funcionarios.add(new User("14", "Henrique", TipoUsuario.FUNCIONARIO, Departamento.RH));
        funcionarios.add(new User("15", "Gabriela", TipoUsuario.FUNCIONARIO, Departamento.MARKETING)); 

        Item item1 = new Item("Computador", 3000, 2);
        Item item2 = new Item("Impressora", 500, 3);
        Item item3 = new Item("Mouse", 50, 10);
        Item item4 = new Item("Teclado", 100, 5);
        Item item5 = new Item("Monitor", 700, 3);
        Item item6 = new Item("Cadeira", 150, 7);
        Item item7 = new Item("Mesa", 300, 2);
        Item item8 = new Item("Notebook", 2500, 4);
        Item item9 = new Item("Headset", 150, 6);
        Item item10 = new Item("Webcam", 80, 5);
        Item item11 = new Item("Microfone", 120, 3);
        Item item12 = new Item("Roteador", 200, 2);
        Item item13 = new Item("Cabo HDMI", 30, 10);
        Item item14 = new Item("Pendrive", 20, 15);
        Item item15 = new Item("HD Externo", 400, 4);

        pedidos.add(new PedidoAquisicao(funcionarios.get(1), item1));
        pedidos.add(new PedidoAquisicao(funcionarios.get(1), item2));
        pedidos.add(new PedidoAquisicao(funcionarios.get(2), item3));
        pedidos.add(new PedidoAquisicao(funcionarios.get(2), item4));
        pedidos.add(new PedidoAquisicao(funcionarios.get(3), item5));
        pedidos.add(new PedidoAquisicao(funcionarios.get(3), item6));
        pedidos.add(new PedidoAquisicao(funcionarios.get(4), item7));
        pedidos.add(new PedidoAquisicao(funcionarios.get(4), item8));
        pedidos.add(new PedidoAquisicao(funcionarios.get(5), item9));
        pedidos.add(new PedidoAquisicao(funcionarios.get(5), item10));
        pedidos.add(new PedidoAquisicao(funcionarios.get(6), item11));
        pedidos.add(new PedidoAquisicao(funcionarios.get(6), item12));
        pedidos.add(new PedidoAquisicao(funcionarios.get(7), item13));
        pedidos.add(new PedidoAquisicao(funcionarios.get(7), item14));
        pedidos.add(new PedidoAquisicao(funcionarios.get(8), item15));
    }

    public void adicionarUsuario(User usuario) {
        funcionarios.add(usuario);
    }

    public User getUsuarioAtual() {
        return this.usuarioAtual;
    }

    public void setUsuarioAtual(User usuarioAtual) {
        this.usuarioAtual = usuarioAtual;
    }

    public void trocarUsuario(String idUsuario) {
        for (User usuario : funcionarios) {
            if (usuario.getId().equals(idUsuario)) {
                usuarioAtual = usuario;
                System.out.println("Usuário alterado para: " + usuarioAtual.getNome());
                return;
            }
        }
        System.out.println("Usuário não encontrado.");
    }

    public void registrarPedido(Item item) {
        if (usuarioAtual != null) {
            PedidoAquisicao pedido = new PedidoAquisicao(usuarioAtual, item);
            if (pedido.getValorTotal() <= usuarioAtual.getDepartamento().getLimite()) {
                pedidos.add(pedido); // Corrigido: adiciona o pedido à lista de pedidos
                System.out.println("Pedido registrado com sucesso.");
            } else {
                System.out.println("Valor do pedido excede o limite permitido para o departamento.");
            }
        } else {
            System.out.println("Usuário não logado ou não autorizado.");
        }
    }

    public void avaliarPedido(int idPedido, boolean aprovado) {
        if (usuarioAtual != null && usuarioAtual.getTipo() == TipoUsuario.ADMINISTRADOR) {
            for (PedidoAquisicao pedido : pedidos) { // Corrigido: iteração sobre a lista de pedidos
                if (pedido.getId() == idPedido && pedido.getStatus() == StatusPedido.ABERTO) {
                    pedido.setStatus(aprovado ? StatusPedido.APROVADO : StatusPedido.REPROVADO);
                    pedido.setDataConclusao(new Date());
                    System.out.println("Pedido " + (aprovado ? "aprovado" : "reprovado") + " com sucesso.");
                    return;
                }
            }
            System.out.println("Pedido não encontrado ou já avaliado.");
        } else {
            System.out.println("Usuário não autorizado.");
        }
    }

    public void listarPedidosEntreDatas(Date dataInicio, Date dataFim) {
        if (usuarioAtual != null && usuarioAtual.getTipo() == TipoUsuario.ADMINISTRADOR) {
            Date startDate = truncateTime(dataInicio);
            Date endDate = truncateTime(dataFim);

            System.out.println("Data de Início: " + startDate);
            System.out.println("Data de Fim: " + endDate);

            List<PedidoAquisicao> pedidosFiltrados = pedidos.stream()
                    .filter(p -> {
                        Date dataPedido = truncateTime(p.getDataPedido());
                        System.out.println("Verificando pedido com data: " + dataPedido);
                        return !dataPedido.before(startDate) && !dataPedido.after(endDate);
                    })
                    .collect(Collectors.toList());

            for (PedidoAquisicao pedido : pedidosFiltrados) {
                System.out.println(pedido);
            }
        } else {
            System.out.println("Usuário não autorizado.");
        }
    }

    private Date truncateTime(Date date) { // ignorar a parte de tempo da data
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public void buscarPedidosPorFuncionario(String idFuncionario) {
    if (usuarioAtual != null && usuarioAtual.getTipo() == TipoUsuario.ADMINISTRADOR) {
        List<PedidoAquisicao> pedidosFuncionario = pedidos.stream()
                .filter(p -> p.getSolicitante().getId().equals(idFuncionario))
                .collect(Collectors.toList());

        for (PedidoAquisicao pedido : pedidosFuncionario) {
            System.out.println(pedido);
        }
    } else {
        System.out.println("Usuário não autorizado.");
    }
}
    public void excluirPedido(int idPedido) {  // Método para excluir pedido
        if (usuarioAtual == null) {
            System.out.println("Usuário não logado.");
            return;
        }
    
        for (PedidoAquisicao pedido : pedidos) {
            if (pedido.getId() == idPedido) {
                if (!pedido.getSolicitante().getId().equals(usuarioAtual.getId())) {
                    System.out.println("Somente o funcionário que criou o pedido pode excluí-lo.");
                    return;
                }
                if (pedido.getStatus() != StatusPedido.ABERTO) {
                    System.out.println("Pedido já foi avaliado e não pode ser excluído.");
                    return;
                }
                pedidos.remove(pedido);
                System.out.println("Pedido excluído com sucesso.");
                return;
            }
        }
        System.out.println("Pedido não encontrado.");
    }

    public void numeroPedidosAprovadosReprovados() {  // Método para contar o número de pedidos aprovados e reprovados

        if (usuarioAtual != null && usuarioAtual.getTipo() == TipoUsuario.ADMINISTRADOR) {
        List<PedidoAquisicao> pedidos = getPedidos();
        long totalPedidos = pedidos.size();
        long aprovados = pedidos.stream().filter(p -> p.getStatus() == StatusPedido.APROVADO).count();
        long reprovados = pedidos.stream().filter(p -> p.getStatus() == StatusPedido.REPROVADO).count();
        long abertos = pedidos.stream().filter(p -> p.getStatus() == StatusPedido.ABERTO).count();

        System.out.println("Total de pedidos: " + totalPedidos);
        System.out.println("Abertos: " + abertos + " (" + (totalPedidos > 0 ? 100.0 * abertos / totalPedidos : 0) + "%)");
        System.out.println("Aprovados: " + aprovados + " (" + (totalPedidos > 0 ? 100.0 * aprovados / totalPedidos : 0) + "%)");
        System.out.println("Reprovados: " + reprovados + " (" + (totalPedidos > 0 ? 100.0 * reprovados / totalPedidos : 0) + "%)");
    } else {
        System.out.println("Usuário não autorizado.");}
    }

    public void pedidosUltimos30Dias() { // Método para calcular o número de pedidos e o valor médio dos pedidos nos últimos 30 dias
        if (usuarioAtual != null && usuarioAtual.getTipo() == TipoUsuario.ADMINISTRADOR) {
        List<PedidoAquisicao> pedidos = getPedidos();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -30);
        Date dataLimite = truncateTime(calendar.getTime());

        List<PedidoAquisicao> ultimos30Dias = pedidos.stream()
                .filter(p -> truncateTime(p.getDataPedido()).after(dataLimite))
                .collect(Collectors.toList());

        double valorTotal = ultimos30Dias.stream().mapToDouble(PedidoAquisicao::getValorTotal).sum();
        double valorMedio = ultimos30Dias.size() > 0 ? valorTotal / ultimos30Dias.size() : 0;

        System.out.println("Número de pedidos nos últimos 30 dias: " + ultimos30Dias.size());
        System.out.println("Valor médio dos pedidos nos últimos 30 dias: " + valorMedio);
    } else {
        System.out.println("Usuário não autorizado.");}
    }

    public void pedidoMaiorValorAberto() { // Método para encontrar o pedido de maior valor ainda aberto
        if (usuarioAtual != null && usuarioAtual.getTipo() == TipoUsuario.ADMINISTRADOR) {
        List<PedidoAquisicao> pedidos = getPedidos();
        PedidoAquisicao maiorPedidoAberto = pedidos.stream()
                .filter(p -> p.getStatus() == StatusPedido.ABERTO)
                .max(Comparator.comparingDouble(PedidoAquisicao::getValorTotal))
                .orElse(null);

        if (maiorPedidoAberto != null) {
            System.out.println("Pedido de maior valor ainda aberto: " + maiorPedidoAberto);
        } else {
            System.out.println("Não há pedidos abertos.");
        }
    } else {
        System.out.println("Usuário não autorizado.");}
    }

    public List<PedidoAquisicao> buscarPedidosPorDescricaoItem(String descricao) {
        if (usuarioAtual != null && usuarioAtual.getTipo() == TipoUsuario.ADMINISTRADOR) {
        List<PedidoAquisicao> pedidosFiltrados = pedidos.stream()
                .filter(p -> p.getItens().stream().anyMatch(item -> item.getDescricao().equalsIgnoreCase(descricao)))
                .collect(Collectors.toList());
            
        return pedidosFiltrados;

    } else {
        System.out.println("Usuário não autorizado.");
        return new ArrayList<>();
    }
    }


    
    @Override
    public String toString() {
        return "{" +
                " usuarioAtual='" + getUsuarioAtual().getNome() + "'" +
                ", funcionarios='" + getFuncionarios() + "'" +
                ", pedidos='" + getPedidos() + "'" +
                "}";
    }
}
