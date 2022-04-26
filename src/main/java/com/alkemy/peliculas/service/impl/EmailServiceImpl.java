package com.alkemy.peliculas.service.impl;

import com.alkemy.peliculas.error.exception.UnauthorizedException;
import com.alkemy.peliculas.service.EmailService;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    Environment env; // en este atributo se carga ek apikey no desde el codigo por seguridad

    @Value("${alkemy.challenge.email.sender}")
    private String emailSender; // es el from desde donde vamos a mandar el email

    @Value("${alkemy.challenge.email.enabled}")
    private Boolean enabled; //si esta en false corta la ejecucion del envio de mails

    public void sendEmail(String subject, String to, String body) {
        if(!enabled){
            throw new UnauthorizedException("Envio de mail deshabilitado");
        }
        String apiKey = env.getProperty("EMAIL_API_KEY_ALKEMY"); // seteada en edit configurations..
        Email fromEmail = new Email(emailSender); // le pasamos lo que tenemos configurado en el from
        Email toEmail = new Email(to); // se una clase mail con el destinatario como parametro
        Content content = new Content( // se inicializa el cuerpo y el sujeto del mensaje
                "text/plain",
                body
        );//key y valor
        Mail mail = new Mail(fromEmail, subject, toEmail, content); // crea el mail
        SendGrid sg = new SendGrid(apiKey);// se le pasa el apykey q tiene la cuenta relacionada con los permisos
        Request request = new Request();// request de sengrid
        try{
            request.setMethod(Method.POST); // se setea cual va a ser el metodo en este caso POST
            request.setEndpoint("mail/send"); // se setea a que endpoint lo vamos a pegar en la api
            request.setBody(mail.build()); // le pasamos el mail q construimos como cuerpo a nuestra solicitud
            Response response = sg.api(request); //aca se produce la ejecucion

            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        }catch (IOException ex){
            System.out.println("Error trying to send the email");
        }

    }
}
