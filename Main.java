import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static final DateTimeFormatter FD = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter FH = DateTimeFormatter.ofPattern("HH:mm");

    public static void main(String[] args) {
        Clinica clinica = new Clinica();
        inicializarDados(clinica);

        int opcao;
        do {
            exibirMenu();
            opcao = lerInt("Opção: ");
            try {
                switch (opcao) {
                    case 1: cadastrarAtendimento(clinica); break;
                    case 2: associarVeterinario(clinica); break;
                    case 3: atribuirAtendimento(clinica); break;
                    case 4: exibirAtendimentosDaSala(clinica); break;
                    case 5: exibirFinalizadosPorSala(clinica); break;
                    case 6: buscarPorStatus(clinica); break;
                    case 7: exibirDetalhes(clinica); break;
                    case 8: alterarStatus(clinica); break;
                    case 0: System.out.println("O sistema irá encerrar"); break;
                    default: System.out.println("Opção inválida.");
                }
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        } while (opcao != 0);
    }

    private static void inicializarDados(Clinica clinica) {
        clinica.cadastrarVeterinario(new Veterinario("Dra. Giovana Faria", "146.448.432-58", "Clínica Geral", "(31) 124787-5482"));
        clinica.cadastrarVeterinario(new Veterinario("Dr. Glender Braz", "147.278.378-33", "Cirurgia", "(31) 978799-1744"));
        clinica.cadastrarVeterinario(new Veterinario("Dra. Clara Faia", "378.325.424-77", "Dermatologia", "(31) 921487-1648"));

        clinica.cadastrarSala(new Sala(414, "A", 2, "Consulta"));
        clinica.cadastrarSala(new Sala(257, "C", 1, "Cirurgia"));
        clinica.cadastrarSala(new Sala(445, "B", 3, "Exames"));
    }

    private static void exibirMenu() {
        System.out.println("\n CLÍNICA VETERINÁRIA GLENDER");
        System.out.println("1 - Cadastrar atendimento");
        System.out.println("2 - Associar veterinário a uma sala");
        System.out.println("3 - Atribuir atendimento a uma sala");
        System.out.println("4 - Exibir atendimentos de uma sala");
        System.out.println("5 - Total de atendimentos finalizados por sala");
        System.out.println("6 - Buscar atendimentos por status");
        System.out.println("7 - Exibir detalhes de um atendimento");
        System.out.println("8 - Alterar status de um atendimento");
        System.out.println("0 - Sair");
    }

   
    private static void cadastrarAtendimento(Clinica clinica) {
        String tutor = lerTexto("Nome do tutor: ");
        String animal = lerTexto("Nome do animal: ");
        String especie = lerTexto("Espécie: ");
        LocalTime hora = lerHora("Horário (HH:mm): ");
        LocalDate data = lerData("Data (dd/MM/aaaa): ");
        StatusDoAtendimento status = lerStatus();
        String obs = lerTexto("Observações: ");

        System.out.println("Procedimento");
        String nomeProc = lerTexto("Nome: ");
        String nivel = lerTexto("Nível de complexidade: ");
        int duracao = lerInt("Duração estimada (min): ");
        double valor = lerDouble("Valor (R$): ");
        Procedimento proc = new Procedimento(nomeProc, duracao, valor, nivel);

        Atendimento a = clinica.cadastrarAtendimento(animal, especie, tutor, data, hora, status, obs, proc);
        System.out.println("Atendimento cadastrado com código " + a.getCodigo() + ".");
    }

    private static void associarVeterinario(Clinica clinica) {
        System.out.println("Veterinários");
        for (Veterinario v : clinica.getVeterinarios()) System.out.println(v);
        Veterinario vet = clinica.buscarVeterinario(lerTexto("CPF do veterinário: "));

        System.out.println("Salas ");
        for (Sala s : clinica.getSalas()) System.out.println(s);
        Sala sala = clinica.buscarSala(lerInt("Número da sala: "));

        clinica.associarVeterinarioASala(vet, sala);
        System.out.println(vet.getNome() + " agora é responsável pela sala " + sala.getNumero() + ".");
    }

    private static void atribuirAtendimento(Clinica clinica) {
        Atendimento a = clinica.buscarAtendimento(lerInt("Código do atendimento: "));
        for (Sala s : clinica.getSalas()) System.out.println(s);
        Sala sala = clinica.buscarSala(lerInt("Número da sala: "));

        clinica.atribuirAtendimentoASala(a, sala);
        System.out.println("Atendimento " + a.getCodigo() + " atribuído à sala " + sala.getNumero() + ".");
    }

    private static void exibirAtendimentosDaSala(Clinica clinica) {
        Sala sala = clinica.buscarSala(lerInt("Número da sala: "));
        List<Atendimento> lista = clinica.listarPorSala(sala);
        for (Atendimento a : lista) {
            System.out.println(a);
        }
        System.out.println("Total de atendimentos: " + lista.size());
    }

    private static void exibirFinalizadosPorSala(Clinica clinica) {
        for (Sala s : clinica.getSalas()) {
            System.out.println("Sala " + s.getNumero() + ": " + clinica.contarFinalizadosPorSala(s) + " finalizado(s)");
        }
    }

    private static void buscarPorStatus(Clinica clinica) {
        StatusDoAtendimento status = lerStatus();
        List<Atendimento> lista = clinica.buscarPorStatus(status);
        if (lista.isEmpty()) {
            System.out.println("Nenhum atendimento com esse status.");
        }
        for (Atendimento a : lista) {
            System.out.println(" ");
            System.out.print(a.detalhes());
        }
    }

    private static void exibirDetalhes(Clinica clinica) {
        Atendimento a = clinica.buscarAtendimento(lerInt("Código do atendimento: "));
        System.out.println(" ");
        System.out.print(a.detalhes());
    }

    private static void alterarStatus(Clinica clinica) {
        Atendimento a = clinica.buscarAtendimento(lerInt("Código do atendimento: "));
        StatusDoAtendimento novo = lerStatus();
        clinica.alterarStatus(a, novo);
        System.out.println("Status atualizado para " + novo.getDescricao() + ".");
    }

     private static LocalDate lerData(String msg) {
        while (true) {
            try {
                return LocalDate.parse(lerTexto(msg), FD);
            } catch (DateTimeParseException e) {
                System.out.println("Data inserida inválida. Use dd/MM/aaaa.");
            }
        }
    }
        private static int lerInt(String msg) {
        while (true) {
            try {
                return Integer.parseInt(lerTexto(msg));
            } catch (NumberFormatException e) {
                System.out.println("Digite um número inteiro válido.");
            }
        }
    }
    
    private static String lerTexto(String msg) {
        System.out.print(msg);
        return sc.nextLine().trim();
    }



    private static double lerDouble(String msg) {
        while (true) {
            try {
                return Double.parseDouble(lerTexto(msg).replace(",", "."));
            } catch (NumberFormatException e) {
                System.out.println("Digite um valor numéro válido.");
            }
        }
    }

    private static LocalTime lerHora(String msg) {
        while (true) {
            try {
                return LocalTime.parse(lerTexto(msg), FH);
            } catch (DateTimeParseException e) {
                System.out.println("Horário inválido. Use HH:mm.");
            }
        }
    }

    private static StatusDoAtendimento lerStatus() {
        while (true) {
            System.out.println("Status: 1 - Agendado / 2 - Em andamento / 3 - Finalizado");
            int op = lerInt("Escolha: ");
            switch (op) {
                case 1: return StatusDoAtendimento.AGENDADO;
                case 2: return StatusDoAtendimento.EM_ANDAMENTO;
                case 3: return StatusDoAtendimento.FINALIZADO;
                default: System.out.println("Opção inválida.");
            }
        }
    }
}
