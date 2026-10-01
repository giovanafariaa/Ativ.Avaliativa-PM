public enum StatusDoAtendimento {
    AGENDADO("Agendado"),
    EM_ANDAMENTO("Em andamento"),
    FINALIZADO("Finalizado");

    private final String descricao;

    StatusDoAtendimento(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
