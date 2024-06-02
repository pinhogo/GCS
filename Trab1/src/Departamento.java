public enum Departamento {
    FINANCEIRO(10000),
    RH(5000),
    ENGENHARIA(20000),
    MANUTENCAO(8000),
    MARKETING(6000);

    private final double limite;

    Departamento(double limite) {
        this.limite = limite;
    }

    public double getLimite() {
        return limite;
    }
}