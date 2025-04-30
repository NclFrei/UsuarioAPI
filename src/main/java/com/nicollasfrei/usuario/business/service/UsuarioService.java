package com.nicollasfrei.usuario.business.service;

import com.nicollasfrei.usuario.business.converter.UsuarioConverter;
import com.nicollasfrei.usuario.business.dto.UsuarioDTO;
import com.nicollasfrei.usuario.infrastructure.entity.Usuario;
import com.nicollasfrei.usuario.infrastructure.exceptions.ConflictException;
import com.nicollasfrei.usuario.infrastructure.exceptions.ResourceNotFoundException;
import com.nicollasfrei.usuario.infrastructure.repository.UsuarioRepository;
import com.nicollasfrei.usuario.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO){
        emailExiste(usuarioDTO.getEmail());
        usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
        return usuarioConverter.paraUsuarioDTO(
                usuarioRepository.save(usuario));
    }


    public void emailExiste(String email) {
        try {
            boolean existe = verificaEmailExistente(email);
            if (existe) {
                throw new ConflictException("Email já cadastrado" + email);
            }
        } catch(ConflictException e) {
            throw new ConflictException("Email já cadastrado", e.getCause());
        }
    }


    public boolean verificaEmailExistente(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    public Usuario buscarUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Email não encontrado" + email));
    }

    public  void deletarUsuarioPorEmail(String email){usuarioRepository.deleteByEmail(email);}

    public UsuarioDTO atualizaDadosUsuario(String token, UsuarioDTO dto){
       String email = jwtUtil.extrairEmailToken(token.substring(7));

       //Criptografia de senha
       dto.setSenha((dto.getSenha()) != null ? passwordEncoder.encode(dto.getSenha()) : null);

       Usuario usuarioEntity = usuarioRepository.findByEmail(email).orElseThrow(() ->
               new ResourceNotFoundException("Email não localizado"));

       Usuario usuario = usuarioConverter.updateUsuario(dto, usuarioEntity);

        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }
}
