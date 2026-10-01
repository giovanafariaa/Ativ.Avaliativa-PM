import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Clinica {
    private List<Veterinario> veterinarios = new ArrayList<>();
    private List<Sala> salas = new ArrayList<>();
    private List<Atendimento> atendimentos = new ArrayList<>();
    private int proximoCodigo = 1;

    public void cadastrarVeterinario(Veterinario veterinario) {
        veterinarios.add(veterinario);
    }

    public void cadastrarSala(Sala sala) {
        salas.add(sala);
    }

    public Atendimento cadastrarAtendimento(String nomeAnimal, String especie, String nomeTutor, LocalDate data,
                                            LocalTime horario, StatusDoAtendimento status, String observacoes,
                                            Procedimento procedimento) {
        Atendimento a = new Atendimento(proximoCodigo++, nomeAnimal, especie, nomeTutor, data, horario,
                status, observacoes, procedimento);
        atendimentos.add(a);
        return a;
    }

    public Veterinario buscarVeterinario(String cpf) {
        for (Veterinario v : veterinarios) {
            if (v.getCpf().equals(cpf)) return v;
        }
        throw new IllegalArgumentException("Veterinário não encontrado.");
    }

    public Sala buscarSala(int numero) {
        for (Sala s : salas) {
            if (s.getNumero() == numero) return s;
        }
        throw new IllegalArgumentException("Sala não encontrada.");
    }

    public Atendimento buscarAtendimento(int codigo) {
        for (Atendimento a : atendimentos) {
            if (a.getCodigo() == codigo) return a;
        }
        throw new IllegalArgumentException("Atendimento não encontrado.");
    }

    public List<Veterinario> getVeterinarios() { return veterinarios; }
    public List<Sala> getSalas() { return salas; }

    public void associarVeterinarioASala(Veterinario veterinario, Sala sala) {
        for (Sala s : salas) {
            if (s.getVeterinario() == veterinario && s != sala) {
                throw new IllegalStateException("Este veterinário já é responsável pela sala " + s.getNumero() + ".");
            }
        }
        sala.setVeterinario(veterinario);
    }


    public void atribuirAtendimentoASala(Atendimento atendimento, Sala sala) {
        if (atendimento.getStatus() == StatusDoAtendimento.AGENDADO) {
            throw new IllegalStateException("Atendimentos agendados não podem ter sala atribuída.");
        }
        if (atendimento.getSala() != null) {
            throw new IllegalStateException("Este atendimento já está atribuído à sala "
                    + atendimento.getSala().getNumero() + ".");
        }
        if (sala.getVeterinario() == null) {
            throw new IllegalStateException("A sala ainda não possui veterinário responsável.");
        }
        if (atendimento.getStatus() == StatusDoAtendimento.EM_ANDAMENTO) {
            if (!sala.possuiVaga()) {
                throw new IllegalStateException("A sala atingiu a capacidade máxima de animais.");
            }
            if (!sala.aceitaProcedimento(atendimento.getProcedimento())) {
                throw new IllegalStateException("A sala só recebe atendimentos do mesmo tipo de procedimento.");
            }
            sala.adicionarAtendimento(atendimento);
        }

        atendimento.setSala(sala);
    }

    public void alterarStatus(Atendimento atendimento, StatusDoAtendimento novoStatus) {
        StatusDoAtendimento atual = atendimento.getStatus();
        if (atual == StatusDoAtendimento.FINALIZADO) {
            throw new IllegalStateException("Atendimento finalizado não pode mudar de status.");
        }
        if (novoStatus == StatusDoAtendimento.AGENDADO && atendimento.getSala() != null) {
            throw new IllegalStateException("Atendimento com sala não pode voltar para agendado.");
        }
        if (novoStatus == StatusDoAtendimento.FINALIZADO) {
            if (atendimento.getSala() == null) {
                throw new IllegalStateException("Atribua uma sala antes de finalizar.");
            }
            atendimento.getSala().removerAtendimento(atendimento);
        }
        atendimento.setStatus(novoStatus);
    }

    public List<Atendimento> listarPorSala(Sala sala) {
        List<Atendimento> resultado = new ArrayList<>();
        for (Atendimento a : atendimentos) {
            if (a.getSala() == sala) resultado.add(a);
        }
        return resultado;
    }

    public int contarFinalizadosPorSala(Sala sala) {
        int total = 0;
        for (Atendimento a : atendimentos) {
            if (a.getSala() == sala && a.getStatus() == StatusDoAtendimento.FINALIZADO) total++;
        }
        return total;
    }

    public List<Atendimento> buscarPorStatus(StatusDoAtendimento status) {
        List<Atendimento> resultado = new ArrayList<>();
        for (Atendimento a : atendimentos) {
            if (a.getStatus() == status) resultado.add(a);
        }
        return resultado;
    }
}