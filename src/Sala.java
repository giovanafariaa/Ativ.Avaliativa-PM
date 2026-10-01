import java.util.ArrayList;
import java.util.List;

public class Sala {
    private int numero;
    private String bloco;
    private int capacidadeMaxima;
    private String tipoSala;
    private Veterinario veterinario; 
    private List<Atendimento> atendimentosAtivos = new ArrayList<>();

    public Sala(int numero, String bloco, int capacidadeMaxima, String tipoSala) {
        this.numero = numero;
        this.bloco = bloco;
        this.capacidadeMaxima = capacidadeMaxima;
        this.tipoSala = tipoSala;
    }

    public int getNumero() { return numero; }
    public String getBloco() { return bloco; }
    public int getCapacidadeMaxima() { return capacidadeMaxima; }
    public String getTipoSala() { return tipoSala; }
    public Veterinario getVeterinario() { return veterinario; }
    public void setVeterinario(Veterinario veterinario) { this.veterinario = veterinario; }

    public boolean possuiVaga() {
        return atendimentosAtivos.size() < capacidadeMaxima;
    }

    public boolean aceitaProcedimento(Procedimento procedimento) {
        if (atendimentosAtivos.isEmpty()) {
            return true;
        }
        Procedimento atual = atendimentosAtivos.get(0).getProcedimento();
        return atual.getNome().equalsIgnoreCase(procedimento.getNome());
    }

    public void adicionarAtendimento(Atendimento atendimento) {
        atendimentosAtivos.add(atendimento);
    }

    public void removerAtendimento(Atendimento atendimento) {
        atendimentosAtivos.remove(atendimento);
    }

    @Override
    public String toString() {
        String vet = (veterinario == null) ? "sem veterinário" : veterinario.getNome();
        return "Sala de nº " + numero + " Bloco " + bloco + " Capacidade: " + capacidadeMaxima
                + " Tipo: " + tipoSala + " Veterinario: " + vet;
    }
}

