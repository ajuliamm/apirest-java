package br.com.angelinamelo.Todolist.task;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;


@Data//GETTER E SETTER
@Entity(name = "tb_tasks") //DEFINE QUE ESSA SERÁ UMA ENTIDADE
public class TaskModel {
    @Id//DEFINE QUE O ID É A CHAVE PRIMÁRIA
    @GeneratedValue(generator = "UUID")// DEFINE QUE O ID SERÁ GERADO AUTOMATICAMENTE
    private UUID id; 
    private String description;

    @Column(length = 50)//limita a quantidade de caracteres que seja possícel colocar no title, 50 caracteres. 
    private String title;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private String priority;
    
    private UUID idUser; 

    @CreationTimestamp //PARA QUANDO A TAREFA FOR CRIADA NO BANCO DE DADOS
    private LocalDateTime createdAt; 
    
    //repaso a exceção para a camada acima
    public void setTitle(String title) throws Exception{
        
        if(title.length() > 50){
            //crio a exceção
            throw new Exception("O campo title deconter no máximo 50 caracteres");
        }
        this.title = title; 
    }
    
}
