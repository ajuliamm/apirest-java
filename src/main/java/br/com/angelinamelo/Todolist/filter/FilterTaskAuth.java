package br.com.angelinamelo.Todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.angelinamelo.Todolist.user.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component // toda classe que quero que o string gerencie, preciso colocar essa anotation,
           // essa é a classe mais genérica de gerenciamento do spring
public class FilterTaskAuth extends OncePerRequestFilter {
    @Autowired
    private IUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        var serveletPath = request.getServletPath(); // pegar a rota de tasks
        if (serveletPath.startsWith("/tasks/")) {//verificar se a rota é de tasks

            // pegar a autenticação(usuário e senha)
            var authorization = request.getHeader("Authorization");

            var authEncoded = authorization.substring("Basic".length()).trim();

            byte[] authDecode = Base64.getDecoder().decode(authEncoded);

            var authString = new String(authDecode); // converter array d ebyte em uma string

            String[] credentials = authString.split(":");
            String username = credentials[0];
            String password = credentials[1];

            System.out.println(username + password);

            // validar usuário
            var user = this.userRepository.findByUsername(username);
            if (user == null) {
                response.sendError(401);
            } else {
                // validar senha
                var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
                if (passwordVerify.verified) { // retorna true ou false
                    request.setAttribute("idUser", user.getId());//vou setar um atributo chamado idUser e coloco o valor no segundo parâmetro
                    // segue viagem

                    filterChain.doFilter(request, response); // doFilter - Permite que a requisição continue seu fluxo
                                                             // normal de processamento.
                } else {
                    response.sendError(401);

                }
            }
        } else{
            filterChain.doFilter(request, response);// se não for rota de path vai seguir viagem
        }

    }

}
