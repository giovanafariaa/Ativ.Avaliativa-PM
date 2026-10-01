public class Procedimento {
    private String nome;
    private int duracaoEstimada; 
    private double valor;
    private String nivelComplexidade;

    public Procedimento(String nome, int duracaoEstimada, double valor, String nivelComplexidade) {
        this.nome = nome;
        this.duracaoEstimada = duracaoEstimada;
        this.valor = valor;
        this.nivelComplexidade = nivelComplexidade;
    }

    public String getNome() { return nome; }
    public int getDuracaoEstimada() { return duracaoEstimada; }
    public double getValor() { return valor; }
    public String getNivelComplexidade() { return nivelComplexidade; }

    @Override
    public String toString() {
        return nome + " Atendimento com duração de : " + duracaoEstimada + " min e Valor: R$ " + String.format("%.2f", valor)
                + " Procedimento com nivel de Complexidade: " + nivelComplexidade;
    }
}
