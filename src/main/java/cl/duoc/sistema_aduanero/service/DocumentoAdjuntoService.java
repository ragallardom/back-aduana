package cl.duoc.sistema_aduanero.service;

import cl.duoc.sistema_aduanero.model.DocumentoAdjunto;
import cl.duoc.sistema_aduanero.model.SolicitudAduana;
import cl.duoc.sistema_aduanero.repository.DocumentoAdjuntoRepository;
import cl.duoc.sistema_aduanero.repository.SolicitudAduanaRepository;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class DocumentoAdjuntoService {

    @Autowired
    private DocumentoAdjuntoRepository documentoAdjuntoRepository;

    // Ruta base donde se almacenarán todas las subcarpetas "solicitud_{id}"
    private final String BASE_PATH = "C:/Users/ragal/IdeaProjects/sistema_aduanero/uploads";

    public DocumentoAdjunto guardarArchivo(SolicitudAduana solicitud, String tipoDocumento, MultipartFile archivo) throws IOException {
        if (archivo == null || archivo.isEmpty()) {
            throw new IllegalArgumentException("El archivo está vacío");
        }

        // 1. Crear carpeta "solicitud_{id}" si no existe
        String carpetaSolicitud = BASE_PATH + "/solicitud_" + solicitud.getId();
        File dir = new File(carpetaSolicitud);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // 2. Obtener extensión del archivo original
        String originalFilename = archivo.getOriginalFilename();
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = "." + FilenameUtils.getExtension(originalFilename);
        }

        // 3. Renombrar archivo según el tipo de documento
        String nombreFormateado = tipoDocumento.toLowerCase().replaceAll("\\s+", "_") + extension;

        // 4. Ruta final donde se guarda el archivo
        Path rutaFinal = Paths.get(carpetaSolicitud, nombreFormateado);
        archivo.transferTo(rutaFinal.toFile());

        // 5. Crear entidad DocumentoAdjunto y guardar en BD
        DocumentoAdjunto doc = new DocumentoAdjunto();
        doc.setSolicitud(solicitud);
        doc.setNombreArchivo(nombreFormateado);
        doc.setTipoDocumento(tipoDocumento);
        doc.setRutaArchivo(rutaFinal.toString());
        doc.setFechaSubida(LocalDateTime.now());

        return documentoAdjuntoRepository.save(doc);
    }

    @Autowired
    private SolicitudAduanaRepository solicitudRepository;

    public SolicitudAduana crearSolicitud(SolicitudAduana solicitud) {
        return solicitudRepository.save(solicitud);
    }

    public SolicitudAduana obtenerPorId(Long id) {
        Optional<SolicitudAduana> opt = solicitudRepository.findById(id);
        return opt.orElse(null);
    }
}
