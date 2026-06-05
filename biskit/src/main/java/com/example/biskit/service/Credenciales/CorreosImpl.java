package com.example.biskit.service.Credenciales;

import com.example.biskit.entities.Citas.Cita;
import com.example.biskit.entities.Client;
import com.example.biskit.entities.Contactable;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
@Transactional
public class CorreosImpl implements CorreosService {

  private static final Logger logger = LoggerFactory.getLogger(CorreosImpl.class);
  private static final String HEADER_CONTENT_ID = "headerCorreo";
  private static final String HEADER_IMAGE_PATH = "images/correo.png";
  private static final Pattern SIMPLE_EMAIL_PATTERN = Pattern.compile(
    "^[^\\s@<>]+@[^\\s@<>]+\\.[^\\s@<>]+$"
  );
  private final RestClient resendClient = RestClient.create();

  @Value("${biskit.mail.from}")
  private String fromEmail;

  @Value("${resend.api-key:}")
  private String resendApiKey;

  @Value("${resend.emails-url:https://api.resend.com/emails}")
  private String resendEmailsUrl;

  @Value("${biskit.frontend.base-url:http://localhost:4200}")
  private String frontendBaseUrl;

  private static final Set<String> ALLOWED_FRONTEND_ORIGINS = Set.of(
    "http://localhost:4200",
    "https://biskit.website",
    "https://biskit-gold.vercel.app"
  );

  @Async
  public void enviarBienvenida(Client cliente) {
    try {
      String baseUrl = "http://localhost:4200";

      String linkResetPassword =
        baseUrl + "/login/reset-password/" + cliente.getId() + "?correo=" + cliente.getCorreo();

      // Reconstruir la contraseña desencriptada
      String passwordDesencriptada = reconstruirContraseña(
        cliente.getNombre(),
        cliente.getCedula()
      );

      enviarConResend(
        cliente.getCorreo(),
        "¡Bienvenido/a a la Veterinaria Biskit!",
        construirCuerpo(
          cliente.getNombre(),
          cliente.getCredenciales().getUsername(),
          passwordDesencriptada,
          linkResetPassword
        )
      );
    } catch (Exception e) {
      logger.error("Error al enviar el correo de bienvenida a {}", cliente.getCorreo(), e);
      throw new RuntimeException("Error al enviar el correo de bienvenida", e);
    }
  }

  private String reconstruirContraseña(String nombre, String cedula) {
    String parteNombre =
      nombre.length() >= 3 ? nombre.substring(0, 3).toLowerCase() : nombre.toLowerCase();
    String parteCedula = cedula.length() >= 3 ? cedula.substring(0, 3) : cedula;
    return parteNombre + parteCedula;
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

  @Async
  public void enviarCorreoResetPassword(Contactable contactable) {
    try {
      String baseUrl = "http://localhost:4200";

      String linkResetPassword =
        baseUrl +
        "/login/reset-password/" +
        contactable.getId() +
        "?correo=" +
        contactable.getCorreo();

      enviarConResend(
        contactable.getCorreo(),
        "Restablece tu contraseña - Veterinaria Biskit",
        construirCuerpoResetPassword(contactable.getNombre(), linkResetPassword)
      );
    } catch (Exception e) {
      logger.error("Error al enviar el correo de reset a {}", contactable.getCorreo(), e);
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

  private String resolverFrontendBaseUrl() {

    return "https://biskit.website";

    /* 
    String requestOrigin = obtenerOriginDeLaPeticion();

    if (requestOrigin != null && ALLOWED_FRONTEND_ORIGINS.contains(requestOrigin)) {
      return requestOrigin;
    }

    return frontendBaseUrl;
    */
  }

  private String obtenerOriginDeLaPeticion() {
    RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();

    if (!(requestAttributes instanceof ServletRequestAttributes)) {
      return null;
    }

    ServletRequestAttributes servletRequestAttributes =
      (ServletRequestAttributes) requestAttributes;
    return servletRequestAttributes.getRequest().getHeader("Origin");
  }

  private String resolverFromAddress() {
    String fromAddress = clean(fromEmail);

    if (fromAddress.isEmpty()) {
      fromAddress = clean(System.getenv("BISKIT_MAIL_FROM"));
      if (!fromAddress.isEmpty()) {
        logger.warn("Remitente resuelto desde variable de entorno BISKIT_MAIL_FROM.");
      }
    }

    if (fromAddress.isEmpty()) {
      fromAddress = clean(System.getenv("RESEND_FROM_EMAIL"));
      if (!fromAddress.isEmpty()) {
        logger.warn("Remitente resuelto desde variable de entorno RESEND_FROM_EMAIL.");
      }
    }

    if (fromAddress.isEmpty()) {
      throw new IllegalStateException(
        "No hay remitente configurado para correo. Define BISKIT_MAIL_FROM o RESEND_FROM_EMAIL."
      );
    }

    if (isValidFromAddress(fromAddress)) {
      logger.info("Correo remitente resuelto correctamente: {}", maskEmail(fromAddress));
      return fromAddress;
    }

    throw new IllegalStateException(
      "El remitente configurado no es un correo valido: " + fromAddress
    );
  }

  private String clean(String value) {
    return value == null ? "" : value.trim();
  }

  private String maskEmail(String email) {
    int atIndex = email.indexOf('@');
    if (atIndex <= 1) {
      return "***";
    }

    return email.charAt(0) + "***" + email.substring(atIndex);
  }

  private boolean isValidFromAddress(String fromAddress) {
    String email = fromAddress;
    int start = fromAddress.indexOf('<');
    int end = fromAddress.indexOf('>');

    if (start >= 0 && end > start) {
      email = fromAddress.substring(start + 1, end);
    }

    return SIMPLE_EMAIL_PATTERN.matcher(email.trim()).matches();
  }

  private void enviarConResend(String to, String subject, String html) {
    String apiKey = clean(resendApiKey);

    if (apiKey.isEmpty()) {
      throw new IllegalStateException("No hay API key de Resend configurada. Define RESEND_API_KEY.");
    }

    Map<String, Object> body = new HashMap<>();
    body.put("from", resolverFromAddress());
    body.put("to", List.of(to));
    body.put("subject", subject);
    body.put("html", html);
    body.put("attachments", List.of(construirHeaderInlineAttachment()));

    try {
      resendClient
        .post()
        .uri(resendEmailsUrl)
        .contentType(MediaType.APPLICATION_JSON)
        .header(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
        .body(body)
        .retrieve()
        .toBodilessEntity();
    } catch (RestClientResponseException e) {
      logger.error(
        "Resend rechazo el correo a {}. Status: {}. Respuesta: {}",
        maskEmail(to),
        e.getStatusCode(),
        e.getResponseBodyAsString(),
        e
      );
      throw new RuntimeException("Error al enviar el correo con Resend", e);
    } catch (RestClientException e) {
      logger.error("No se pudo enviar el correo a {} usando Resend", maskEmail(to), e);
      throw new RuntimeException("Error al enviar el correo con Resend", e);
    }
  }

  private Map<String, Object> construirHeaderInlineAttachment() {
    ClassPathResource img = new ClassPathResource(HEADER_IMAGE_PATH);

    try (InputStream inputStream = img.getInputStream()) {
      Map<String, Object> attachment = new HashMap<>();
      attachment.put("content", Base64.getEncoder().encodeToString(inputStream.readAllBytes()));
      attachment.put("filename", "correo.png");
      attachment.put("content_id", HEADER_CONTENT_ID);
      attachment.put("content_type", "image/png");
      return attachment;
    } catch (IOException e) {
      throw new IllegalStateException("No se pudo cargar la imagen del correo", e);
    }
  }

  @Override
  public void enviarConfirmacionCita(Cita cita, Client owner) {
    try {
      enviarConResend(
        owner.getCorreo(),
        "Confirmación de cita agendada - Veterinaria Biskit",
        construirCuerpoConfirmacionCita(cita, owner)
      );
    } catch (Exception e) {
      throw new RuntimeException("Error al enviar el correo de confirmación de cita", e);
    }
  }

  private String construirCuerpoConfirmacionCita(Cita cita, Client owner) {
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    String fecha = cita.getFechaHora().format(dateFormatter);
    String hora = cita.getFechaHora().format(timeFormatter);
    String tipoCita = cita.getTipoCita().getNombre();
    String veterinario = cita.getVet().getNombre();
    String mascota = cita.getPet().getNombre();
    String duracion = cita.getTipoCita().getDuracionMinutos() + " minutos";

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
                    <strong>¡Cita agendada exitosamente!</strong>
                </h2>

                <p style="text-align:center; color:#2b5392; font-size:16px; margin:0 0 10px 0;">
                    ✦ Tu cita ha sido confirmada ✦
                </p>

                <p style="text-align:center; color:#555555; font-size:15px; margin:0 0 20px 0; line-height:1.6;">
                    Te confirmamos que tu cita ha sido agendada exitosamente con todos los detalles.
                </p>

                <hr style="border:none; border-top:1px solid #dddddd; margin:25px 0;" />

                <!-- Detalles de la cita -->
                <p style="color:#333333; font-size:15px; margin:0 0 15px 0; line-height:1.6; font-weight:bold;">
                    Detalles de tu cita:
                </p>

                <table style="width:100%%; color:#333333; font-size:15px; line-height:2; border-collapse:collapse;">
                    <tr style="background-color:#f5f5f5;">
                        <td style="padding:10px; border:1px solid #dddddd; font-weight:bold;">Mascota:</td>
                        <td style="padding:10px; border:1px solid #dddddd;">%s</td>
                    </tr>
                    <tr>
                        <td style="padding:10px; border:1px solid #dddddd; font-weight:bold;">Tipo de cita:</td>
                        <td style="padding:10px; border:1px solid #dddddd;">%s</td>
                    </tr>
                    <tr style="background-color:#f5f5f5;">
                        <td style="padding:10px; border:1px solid #dddddd; font-weight:bold;">Veterinario:</td>
                        <td style="padding:10px; border:1px solid #dddddd;">%s</td>
                    </tr>
                    <tr>
                        <td style="padding:10px; border:1px solid #dddddd; font-weight:bold;">Fecha:</td>
                        <td style="padding:10px; border:1px solid #dddddd;">%s</td>
                    </tr>
                    <tr style="background-color:#f5f5f5;">
                        <td style="padding:10px; border:1px solid #dddddd; font-weight:bold;">Hora:</td>
                        <td style="padding:10px; border:1px solid #dddddd;">%s</td>
                    </tr>
                    <tr>
                        <td style="padding:10px; border:1px solid #dddddd; font-weight:bold;">Duración:</td>
                        <td style="padding:10px; border:1px solid #dddddd;">%s</td>
                    </tr>
                </table>

                <hr style="border:none; border-top:1px solid #dddddd; margin:25px 0;" />

                <p style="color:#555555; font-size:14px; line-height:1.8; margin:0;">
                    Si necesitas reprogramar o cancelar tu cita, contáctanos con anticipación.
                </p>

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
    """.formatted(mascota, tipoCita, veterinario, fecha, hora, duracion);
  }
}
