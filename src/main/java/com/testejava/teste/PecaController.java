package com.testejava.teste;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/pecas")
public class PecaController {
    private final PecaRepository repository;

    public PecaController(PecaRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("pecas", repository.findAll());
        model.addAttribute("peca", new Peca());
        return "pecas";
    }

    @PostMapping("/salvar")
    public String salvar(Peca peca, RedirectAttributes attributes) {
        repository.save(peca);
        attributes.addFlashAttribute("mensagem", "Peca cadastrada com sucesso.");
        return "redirect:/pecas";
    }
}
