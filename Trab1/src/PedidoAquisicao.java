import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
//

public class PedidoAquisicao {
    private static int countId = 0;

    private User solicitante;
    private int id;
    private Departamento departamento;
    private Date dataPedido;
    private Date dataConclusao;
    private StatusPedido status;
    private List<Item> itens;
    private double valorTotal;

    public PedidoAquisicao(User solicitante, Item item) {
        this.solicitante = solicitante;
        this.departamento = solicitante.getDepartamento();
        this.dataPedido = new Date();
        this.status = StatusPedido.ABERTO;
        this.itens = new ArrayList<>();
        this.id = ++countId;

        addItem(item); // Adiciona o item e atualiza o valor total
    }

    private double calcularValorTotal() {
        return itens.stream().mapToDouble(Item::getTotal).sum();
    }

    public void addItem(Item item) {
        itens.add(item);
        this.valorTotal = calcularValorTotal();
    }

    public User getSolicitante() {
        return this.solicitante;
    }

    public void setSolicitante(User solicitante) {
        this.solicitante = solicitante;
    }

    public Departamento getDepartamento() {
        return this.departamento;
    }

    public Date getDataPedido() {
        return this.dataPedido;
    }

    public void setDataPedido(Date dataPedido) {
        this.dataPedido = dataPedido;
    }

    public Date getDataConclusao() {
        return this.dataConclusao;
    }

    public void setDataConclusao(Date dataConclusao) {
        this.dataConclusao = dataConclusao;
    }

    public StatusPedido getStatus() {
        return this.status;
    }

    public void setStatus(StatusPedido status) {
        this.status = status;
    }

    public List<Item> getItens() {
        return this.itens;
    }

    public void setItens(List<Item> itens) {
        this.itens = itens;
        this.valorTotal = calcularValorTotal();
    }

    public double getValorTotal() {
        return this.valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Item getItem() {
        return this.itens.isEmpty() ? null : this.itens.get(0); // Retorna o primeiro item, se houver
    }

    private String formatarData(Date data) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(data);
    }

    @Override
    public String toString() {
        return "{" +
            " solicitante='" + getSolicitante() + "'" +
            ", id='" + getId() + "'" +
            ", departamento='" + getDepartamento() + "'" +
            ", dataPedido='" + formatarData(getDataPedido()) + "'" +
            ", dataConclusao='" + (getDataConclusao() != null ? formatarData(getDataConclusao()) : "N/A") + "'" +
            ", status='" + getStatus() + "'" +
            ", itens='" + getItens() + "'" +
            ", valorTotal='" + getValorTotal() + "'" +
            "}";
    }
}