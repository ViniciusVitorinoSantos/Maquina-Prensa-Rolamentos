package com.testejava.teste;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {
    private final RegistroProducaoRepository producaoRepository;
    private final ManutencaoRepository manutencaoRepository;

    public DashboardController(RegistroProducaoRepository producaoRepository,
            ManutencaoRepository manutencaoRepository) {
        this.producaoRepository = producaoRepository;
        this.manutencaoRepository = manutencaoRepository;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        List<RegistroProducao> registros = producaoRepository.findAll();
        List<DadoManutencao> manutencoes = manutencaoRepository.findAll();

        int pecasProduzidas = registros.stream()
                .mapToInt(RegistroProducao::getQuantidadeProduzida)
                .sum();
        long minutosFuncionamento = registros.stream()
                .mapToLong(RegistroProducao::getMinutosFuncionamento)
                .sum();
        int minutosParada = manutencoes.stream()
                .mapToInt(DadoManutencao::getMinutosParada)
                .sum();
        double disponibilidade = minutosFuncionamento + minutosParada == 0
                ? 0
                : minutosFuncionamento * 100.0 / (minutosFuncionamento + minutosParada);
        List<LeituraSensor> leituras = leiturasSimuladas();
        long alertas = leituras.stream()
                .filter(leitura -> !"Normal".equals(leitura.status()))
                .count();

        model.addAttribute("pecasProduzidas", pecasProduzidas);
        model.addAttribute("minutosFuncionamento", minutosFuncionamento);
        model.addAttribute("minutosParada", minutosParada);
        model.addAttribute("totalParadas", manutencoes.size());
        model.addAttribute("disponibilidade", String.format("%.1f", disponibilidade));
        model.addAttribute("alertas", alertas);
        model.addAttribute("analise", alertas == 0
                ? "Nenhuma anomalia detectada nas leituras atuais."
                : "Atenção: há leituras fora dos limites definidos.");
        model.addAttribute("leituras", leituras);
        return "dashboard";
    }

    private List<LeituraSensor> leiturasSimuladas() {
        int minuto = LocalDateTime.now().getMinute();
        double vibracao = 2.4 + (minuto % 5) * 0.1;
        double corrente = 8.5 + (minuto % 4) * 0.2;
        double tensao = 220.0 + (minuto % 3);
        double temperatura = 42.0 + (minuto % 6) * 0.4;
        return List.of(
                new LeituraSensor("Vibracao", String.format("%.1f mm/s", vibracao), vibracao > 4.5 ? "Alerta" : "Normal"),
                new LeituraSensor("Corrente", String.format("%.1f A", corrente), corrente > 12 ? "Alerta" : "Normal"),
                new LeituraSensor("Tensao", String.format("%.0f V", tensao), tensao < 210 || tensao > 230 ? "Alerta" : "Normal"),
                new LeituraSensor("Temperatura", String.format("%.1f C", temperatura), temperatura > 70 ? "Alerta" : "Normal"));
    }

    public record LeituraSensor(String nome, String valor, String status) {
    }
}
