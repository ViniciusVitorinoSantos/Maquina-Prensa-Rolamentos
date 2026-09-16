package com.testejava.teste;

import jakarta.validation.Valid;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {
    private static final String ADMIN_SESSION = "adminAutenticado";
    private final UsuarioRepository repository;
    private final String senhaAdmin;
    private final String loginAdmin;

    public UsuarioController(UsuarioRepository repository,
            @Value("${app.admin.login}") String loginAdmin,
            @Value("${app.admin.password}") String senhaAdmin) {
        this.repository = repository;
        this.loginAdmin = loginAdmin;
        this.senhaAdmin = senhaAdmin;
    }

    @GetMapping
    public String listar(Model model, HttpSession session) {
        if (!adminAutenticado(session)) {
            return "login-admin";
        }

        model.addAttribute("usuarios", repository.findAll());
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("niveis", NivelUsuario.values());
        return "usuarios";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model, HttpSession session) {
        if (!adminAutenticado(session)) {
            return "redirect:/usuarios";
        }

        model.addAttribute("usuarios", repository.findAll());
        model.addAttribute("usuario", repository.findById(id).orElseThrow());
        model.addAttribute("niveis", NivelUsuario.values());
        return "usuarios";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid Usuario usuario, BindingResult result, Model model,
            RedirectAttributes attributes, HttpSession session) {
        if (!adminAutenticado(session)) {
            return "redirect:/usuarios";
        }

        if (result.hasErrors()) {
            model.addAttribute("usuarios", repository.findAll());
            model.addAttribute("niveis", NivelUsuario.values());
            return "usuarios";
        }

        repository.save(usuario);
        attributes.addFlashAttribute("mensagem", "Usuario salvo com sucesso.");
        return "redirect:/usuarios";
    }

    @PostMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes attributes, HttpSession session) {
        if (!adminAutenticado(session)) {
            return "redirect:/usuarios";
        }

        repository.deleteById(id);
        attributes.addFlashAttribute("mensagem", "Usuario excluido com sucesso.");
        return "redirect:/usuarios";
    }

    @PostMapping("/login")
    public String login(@RequestParam String login, @RequestParam String senha,
            HttpSession session, Model model) {
        if (loginAdmin.equals(login) && senhaAdmin.equals(senha)) {
            session.setAttribute(ADMIN_SESSION, true);
            return "redirect:/usuarios";
        }

        model.addAttribute("erro", "Senha incorreta.");
        return "login-admin";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/usuarios";
    }

    private boolean adminAutenticado(HttpSession session) {
        return Boolean.TRUE.equals(session.getAttribute(ADMIN_SESSION));
    }
}
