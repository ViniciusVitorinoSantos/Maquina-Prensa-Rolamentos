package com.testejava.teste;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	List<Usuario> findByNivel(NivelUsuario nivel);

	Usuario findByLoginAndSenhaAndNivel(String login, String senha, NivelUsuario nivel);

	boolean existsByCpf(String cpf);

	boolean existsByCpfAndIdNot(String cpf, Long id);
}
