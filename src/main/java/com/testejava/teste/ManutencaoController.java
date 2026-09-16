package com.testejava.teste;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/manutencoes")
public class ManutencaoController {
    private final ManutencaoRepository repository;

    public ManutencaoController(ManutencaoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("manutencoes", repository.findAll());
        model.addAttribute("manutencao", new DadoManutencao());
        return "manutencoes";
    }

    @PostMapping("/salvar")
    public String salvar(DadoManutencao manutencao, RedirectAttributes attributes) {
        repository.save(manutencao);
        attributes.addFlashAttribute("mensagem", "Manutencao registrada com sucesso.");
        return "redirect:/manutencoes";
    }
}
