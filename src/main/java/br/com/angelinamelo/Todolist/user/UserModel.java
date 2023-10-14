package br.com.angelinamelo.Todolist.user;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;


@Data
@Entity(name = "tb_users")
//modelo de usuário
public class UserModel {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;


    //atributos da classe
    @Column(unique = true)
    private String username;
    private String name;
    private String password;
    // se eu não defino modificador significa que ele vai ser público.

    //Quando esse meu dado for gerado, de forma automática o banco de dados terá a informação do CreationTimestamp
    @CreationTimestamp
    private LocalDateTime createAt; 
}
