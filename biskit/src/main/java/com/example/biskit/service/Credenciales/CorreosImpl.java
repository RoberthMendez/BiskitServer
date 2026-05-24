package com.example.biskit.service.Credenciales;

import com.example.biskit.entities.Client;
import com.example.biskit.entities.Contactable;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CorreosImpl implements CorreosService {

  @Autowired
  private JavaMailSender mailSender;

  @Value("${biskit.mail.from}")
  private String fromEmail;

  @Value("${biskit.frontend.base-url:http://localhost:4200}")
  private String frontendBaseUrl;

  public void enviarBienvenida(Client cliente) {
    try {
      MimeMessage mensaje = mailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(mensaje, true, "UTF-8");

      helper.setFrom(fromEmail);
      helper.setTo(cliente.getCorreo());
      helper.setSubject("¡Bienvenido/a a la Veterinaria Biskit!");

      String linkResetPassword =
        frontendBaseUrl +
        "/login/reset-password/" +
        cliente.getId() +
        "?correo=" +
        cliente.getCorreo();

      helper.setText(
        construirCuerpo(
          cliente.getNombre(),
          cliente.getCredenciales().getUsername(),
          cliente.getCredenciales().getPassword(),
          linkResetPassword
        ),
        true
      ); // true = HTML

      ClassPathResource img = new ClassPathResource("images/correo.png");
      helper.addInline("headerCorreo", img, "image/png");

      mailSender.send(mensaje);
    } catch (MessagingException e) {
      throw new RuntimeException("Error al enviar el correo de bienvenida", e);
    }
  }

  public String construirCuerpo(
    String nombre,
    String username,
    String password,
    String linkResetPassword
  ) {
    return """
    <html>
    <body style="margin:0; padding:0; font-family:Arial, sans-serif; background-color:#f9f9f9;">

        <!-- Contenedor general -->
        <div style="max-width:600px; margin:0 auto; background-color:#ffffff;">

            <!-- Header imagen -->
            <img src="cid:headerCorreo"
                alt="Biskit Header"
                border="0"
                style="width:100%%; height:auto; display:block; margin:0; padding:0;" />

            <!-- Contenido principal -->
            <div style="padding:30px 40px;">

                <!-- Saludo -->
                <h2 style="text-align:center; color:#333333; font-size:22px; margin-top:0; margin-bottom:20px; line-height:1.4;">
                    <strong>%s, bienvenid@ a la Veterinaria Biskit.</strong>
                </h2>

                <p style="text-align:center; color:#2b5392; font-size:16px; margin:0 0 10px 0;">
                    ✦ ¡Estamos muy felices de contar contigo! ✦
                </p>

                <p style="text-align:center; color:#555555; font-size:15px; margin:0 0 20px 0; line-height:1.6;">
                    Esperamos que tu experiencia con nosotros sea la mejor.
                </p>

                <hr style="border:none; border-top:1px solid #dddddd; margin:25px 0;" />

                <!-- Credenciales -->
                <p style="color:#333333; font-size:15px; margin:0 0 15px 0; line-height:1.6;">
                    Aquí están tus credenciales de acceso a nuestra plataforma:
                </p>

                <ul style="color:#333333; font-size:15px; line-height:2; padding-left:20px; margin:0 0 20px 0;">

                    <li>
                        Tu usuario es:
                        <strong>%s</strong>
                    </li>

                    <li>
                        Tu contraseña temporal es:
                        <strong>%s</strong>
                    </li>

                    <li>
                        Te recomendamos cambiar tu contraseña.
                        <a href="%s"
                        style="color:#2b5392; font-weight:bold; text-decoration:none;">
                        Haz clic aquí para cambiarla.
                        </a>
                    </li>
                </ul>

                <hr style="border:none; border-top:1px solid #dddddd; margin:25px 0;" />

                <!-- Cierre -->
                <p style="text-align:center; color:#555555; font-size:14px; line-height:1.8; margin:0;">
                    Con cariño,<br/>
                    <strong style="color:#2b5392;">
                        El equipo de Biskit
                    </strong>
                </p>

            </div>
        </div>

    </body>
    </html>
    """.formatted(nombre, username, password, linkResetPassword);
  }

  public void enviarCorreoResetPassword(Contactable contactable) {
    try {
      MimeMessage mensaje = mailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(mensaje, true, "UTF-8");

      helper.setFrom(fromEmail);
      helper.setTo(contactable.getCorreo());
      helper.setSubject("Restablece tu contraseña - Veterinaria Biskit");

      String linkResetPassword =
        frontendBaseUrl +
        "/login/reset-password/" +
        contactable.getId() +
        "?correo=" +
        contactable.getCorreo();

      helper.setText(
        construirCuerpoResetPassword(contactable.getNombre(), linkResetPassword),
        true
      );

      ClassPathResource img = new ClassPathResource("images/correo.png");
      helper.addInline("headerCorreo", img, "image/png");

      mailSender.send(mensaje);
    } catch (MessagingException e) {
      throw new RuntimeException("Error al enviar el correo de reset", e);
    }
  }

  public String construirCuerpoResetPassword(String nombre, String linkResetPassword) {
    return """
    <html>
    <body style="margin:0; padding:0; font-family:Arial, sans-serif; background-color:#f9f9f9;">

        <div style="max-width:600px; margin:0 auto; background-color:#ffffff;">

            <!-- Header imagen -->
            <img src="cid:headerCorreo"
                alt="Biskit Header"
                border="0"
                style="width:100%%; height:auto; display:block; margin:0; padding:0;" />

            <!-- Contenido -->
            <div style="padding:30px 40px;">

                <h2 style="text-align:center; color:#333333; font-size:22px; margin-top:0; margin-bottom:20px; line-height:1.4;">
                    <strong>Hola, %s</strong>
                </h2>

                <p style="text-align:center; color:#2b5392; font-size:16px; margin:0 0 10px 0;">
                    ✦ Recibimos una solicitud para restablecer tu contraseña ✦
                </p>

                <p style="text-align:center; color:#555555; font-size:15px; margin:0 0 20px 0; line-height:1.6;">
                    Si no fuiste tú, puedes ignorar este correo con total tranquilidad.
                </p>

                <hr style="border:none; border-top:1px solid #dddddd; margin:25px 0;" />

                <p style="color:#333333; font-size:15px; margin:0 0 20px 0; line-height:1.6;">
                    Para crear una nueva contraseña haz clic en el botón a continuación.
                </p>

                <!-- Botón -->
                <div style="text-align:center; margin:30px 0;">
                    <a href="%s"
                    style="background-color:#2b5392; color:#ffffff; text-decoration:none;
                            font-size:15px; font-weight:bold; padding:14px 32px;
                            border-radius:50px; display:inline-block;
                            box-shadow:0 6px 18px rgba(43,83,146,0.35);">
                        Restablecer contraseña
                    </a>
                </div>

                <hr style="border:none; border-top:1px solid #dddddd; margin:25px 0;" />

                <p style="text-align:center; color:#555555; font-size:14px; line-height:1.8; margin:0;">
                    Con cariño,<br/>
                    <strong style="color:#2b5392;">El equipo de Biskit</strong>
                </p>

            </div>
        </div>

    </body>
    </html>
    """.formatted(nombre, linkResetPassword, linkResetPassword, linkResetPassword);
  }
}
