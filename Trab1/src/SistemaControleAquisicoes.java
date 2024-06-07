import java.util.ArrayList;
import java.util.Calendar;
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

    public void inicializarDados() {
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
        pedidos.add(new PedidoAquisicao(funcionarios.get(1), item1));
        pedidos.add(new PedidoAquisicao(funcionarios.get(1), item2));
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
    }

    private Date truncateTime(Date date) { //ignorar a parte de tempo da data
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    cal.set(Calendar.HOUR_OF_DAY, 0);
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.SECOND, 0);
    cal.set(Calendar.MILLISECOND, 0);
    return cal.getTime();
}

    public void buscarPedidosPorFuncionario(String idFuncionario) {
        List<PedidoAquisicao> pedidosFuncionario = pedidos.stream()
                .filter(p -> p.getSolicitante().getId().equals(idFuncionario))
                .collect(Collectors.toList());

        for (PedidoAquisicao pedido : pedidosFuncionario) {
            System.out.println(pedido);
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

    @Override
    public String toString() {
        return "{" +
                " usuarioAtual='" + getUsuarioAtual().getNome() + "'" +
                ", funcionarios='" + getFuncionarios() + "'" +
                ", pedidos='" + getPedidos() + "'" +
                "}";
    }
}
