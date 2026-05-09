package com.example.biskit.service.Credenciales;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class CorreosImpl implements CorreosService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${biskit.mail.from}")
    private String fromEmail;

    public void enviarBienvenida(String destinatario, String nombre, String usuario, String password) {
        try {
            MimeMessage mensaje = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensaje, true, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(destinatario);
            helper.setSubject("¡Bienvenido/a a la Veterinaria Biskit! 🐾");
            helper.setText(construirCuerpo(nombre, usuario, password), true); // true = HTML

            mailSender.send(mensaje);

        } catch (MessagingException e) {
            throw new RuntimeException("Error al enviar el correo de bienvenida", e);
        }
    }

    public String construirCuerpo(String nombre, String usuario, String password) {
        return """
                <html>
                <body style="font-family: Arial, sans-serif; color: #333;">
                    <h2 style="color: #2b5392;">¡Bienvenido/a a la Veterinaria Biskit! 🐾</h2>
                    <p>Hola <strong>%s</strong>,</p>
                    <p>Tu cuenta ha sido creada exitosamente. Aquí están tus credenciales de acceso:</p>
                    <table style="border-collapse: collapse; margin: 10px 0;">
                        <tr>
                            <td style="padding: 8px; font-weight: bold;">Usuario:</td>
                            <td style="padding: 8px;">%s</td>
                        </tr>
                        <tr>
                            <td style="padding: 8px; font-weight: bold;">Contraseña:</td>
                            <td style="padding: 8px;">%s</td>
                        </tr>
                    </table>
                    <p>Te recomendamos cambiar tu contraseña al iniciar sesión por primera vez.</p>
                    <p>¡Gracias por confiar en nosotros para el cuidado de tu mascota!</p>
                </body>
                </html>
                """.formatted(nombre, usuario, password);
    }
    
}
