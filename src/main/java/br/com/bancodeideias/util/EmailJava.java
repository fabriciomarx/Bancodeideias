/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bancodeideias.util;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;


public class EmailJava {

    public EmailJava() {
    }

    /* Aprendi na video aula 
       https://www.youtube.com/watch?v=t0KfBaZjKIo&t=416s */
    
    /* Para funcionar preciso desativar o antivirus e ativar no google 
       atraves do site https://myaccount.google.com/lesssecureapps?pli=1 */
    public void enviarEmail() {
        try {
            Email email = new SimpleEmail();
            email.setHostName("smtp.googlemail.com");
            email.setSmtpPort(465); //porta padrao
            email.setDebug(true); //para ver o processo de envio
            email.setAuthenticator(new DefaultAuthenticator("famarx.29@gmail.com", "estrela2911"));
            email.setSSLOnConnect(true); //conexao segura
            email.setFrom("famarx.29@gmail.com"); //de quem é esse email
            email.setSubject("TestMail"); // assunto
            email.setMsg("This is a test mail ... :-)"); //mensagem
            email.addTo("fabricio_m.s@hotmail.com");  //para quem vai esse email
            email.send(); //fazer envio do email
            System.out.println("Email enviado");
        } catch (EmailException e) {
             System.out.println("Email não enviado " + e.getMessage() );
            
        }

    }
    
    public static void main(String[] args) {
        EmailJava email = new EmailJava();
        email.enviarEmail();
    }
    
   
}
