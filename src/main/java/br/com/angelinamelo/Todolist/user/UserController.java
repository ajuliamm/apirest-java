package br.com.angelinamelo.Todolist.user;

import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/users")
public class UserController {
    
    @Autowired
    private IUserRepository userRepository;
    
    // Preciso definir o método, no caso como é criação então é do tipo POST
    @PostMapping("/")
    //aviso que quero receber um objeto do tipo usermodel dentro do meu create
    public ResponseEntity create (@RequestBody UserModel userModel){
        var user = this.userRepository.findByUsername(userModel.getUsername());
        //ao colocar this.userRepository. aparecerão os métodos que estão sendo herdados do JpaRepository
        
        if(user != null){
            System.out.println("Usuário já existe");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário já existe"); 
        }

        var passwordHashred = BCrypt.withDefaults().hashToString(12, userModel.getPassword().toCharArray());
        
        userModel.setPassword(passwordHashred);
        
        var userCreated = this.userRepository.save(userModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(userCreated); 
    }
}
