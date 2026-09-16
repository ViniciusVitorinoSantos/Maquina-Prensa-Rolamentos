package com.testejava.teste;

import java.beans.PropertyEditorSupport;

import jakarta.validation.Valid;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/producao")
public class RegistroProducaoController {
    private static final String OPERADOR_SESSION = "operadorAutenticado";
    private final RegistroProducaoRepository producaoRepository;
    private final UsuarioRepository usuarioRepository;

    public RegistroProducaoController(RegistroProducaoRepository producaoRepository,
            UsuarioRepository usuarioRepository) {
        this.producaoRepository = producaoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @InitBinder
    public void configurarConversao(WebDataBinder binder) {
        binder.registerCustomEditor(Usuario.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String texto) {
                setValue(usuarioRepository.findById(Long.valueOf(texto)).orElse(null));
            }
        });
    }

    @GetMapping
    public String relatorio(Model model, HttpSession session) {
        if (!operadorAutenticado(session)) {
            return "login-operador";
        }

        carregarTela(model, new RegistroProducao());
        return "producao";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid RegistroProducao registro, BindingResult result,
            Model model, RedirectAttributes attributes, HttpSession session) {
        if (!operadorAutenticado(session)) {
            return "redirect:/producao";
        }

        if (registro.getInicio() != null && registro.getFim() != null
                && registro.getFim().isBefore(registro.getInicio())) {
            result.rejectValue("fim", "periodo.invalido", "O fim deve ser depois do inicio.");
        }

        if (result.hasErrors()) {
            carregarTela(model, registro);
            return "producao";
        }

        producaoRepository.save(registro);
        attributes.addFlashAttribute("mensagem", "Log de producao registrado com sucesso.");
        return "redirect:/producao";
    }

    @PostMapping("/login")
    public String login(@RequestParam String login, @RequestParam String senha,
            HttpSession session, Model model) {
        Usuario operador = usuarioRepository.findByLoginAndSenhaAndNivel(
                login, senha, NivelUsuario.OPERADOR);
        if (operador != null) {
            session.setAttribute(OPERADOR_SESSION, operador.getId());
            return "redirect:/producao";
        }

        model.addAttribute("erro", "Login ou senha invalidos, ou usuario sem nivel Operador.");
        return "login-operador";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(OPERADOR_SESSION);
        return "redirect:/producao";
    }

    private boolean operadorAutenticado(HttpSession session) {
        return session.getAttribute(OPERADOR_SESSION) != null;
    }

    private void carregarTela(Model model, RegistroProducao registro) {
        model.addAttribute("registros", producaoRepository.findAllByOrderByInicioDesc());
        model.addAttribute("operadores", usuarioRepository.findByNivel(NivelUsuario.OPERADOR));
        model.addAttribute("registro", registro);
    }
}