
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Atendimento {
    private int codigo;
    private String nomeAnimal;
    private String especie;
    private String nomeTutor;
    private LocalDate data;
    private LocalTime horario;
    private StatusDoAtendimento status;
    private String observacoes;
    private Procedimento procedimento;
    private Sala sala; 

    public Atendimento(int codigo, String nomeAnimal, String especie, String nomeTutor, LocalDate data,
    LocalTime horario, StatusDoAtendimento status, String observacoes, Procedimento procedimento) {
        this.codigo = codigo;
        this.nomeAnimal = nomeAnimal;
        this.especie = especie;
        this.nomeTutor = nomeTutor;
        this.data = data;
        this.horario = horario;
        this.status = status;
        this.observacoes = observacoes;
        this.procedimento = procedimento;
    }

    public int getCodigo() { return codigo; }
    public String getNomeAnimal() { return nomeAnimal; }
    public String getEspecie() { return especie; }
    public String getNomeTutor() { return nomeTutor; }
    public LocalDate getData() { return data; }
    public LocalTime getHorario() { return horario; }
    public StatusDoAtendimento getStatus() { return status; }
    public void setStatus(StatusDoAtendimento status) { this.status = status; }
    public String getObservacoes() { return observacoes; }
    public Procedimento getProcedimento() { return procedimento; }
    public Sala getSala() { return sala; }
    public void setSala(Sala sala) { this.sala = sala; }

    public String detalhes() {
        DateTimeFormatter fd = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter fh = DateTimeFormatter.ofPattern("HH:mm");
        StringBuilder sb = new StringBuilder();
        sb.append("Código: ").append(codigo).append("\n");
        sb.append("Animal: ").append(nomeAnimal).append(" (").append(especie).append(")\n");
        sb.append("Tutor: ").append(nomeTutor).append("\n");
        sb.append("Data/Horário: ").append(data.format(fd)).append(" às ").append(horario.format(fh)).append("\n");
        sb.append("Status: ").append(status.getDescricao()).append("\n");
        sb.append("Observações: ").append(observacoes).append("\n");
        sb.append("Procedimento: ").append(procedimento).append("\n");
        if (sala == null) {
            sb.append("Sala: não atribuída\n");
            sb.append("Veterinário: -\n");
        } else {
            sb.append("Sala: ").append(sala.getNumero()).append(" (Bloco ").append(sala.getBloco()).append(")\n");
            Veterinario v = sala.getVeterinario();
            sb.append("Veterinário: ").append(v == null ? "sem veterinário" : v.getNome()).append("\n");
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return "[" + codigo + "] " + nomeAnimal + " (" + especie + ") - Tutor: " + nomeTutor
                + " - " + procedimento.getNome() + " - " + status.getDescricao();
    }
}