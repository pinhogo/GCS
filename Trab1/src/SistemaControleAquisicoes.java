import java.util.ArrayList;
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

    private void inicializarDados() {
        // Inicializar dados de usuários e pedidos
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
        List<PedidoAquisicao> pedidosFiltrados = pedidos.stream()
                .filter(p -> !p.getDataPedido().before(dataInicio) && !p.getDataPedido().after(dataFim))
                .collect(Collectors.toList());

        for (PedidoAquisicao pedido : pedidosFiltrados) {
            System.out.println(pedido);
        }
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
