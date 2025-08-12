package es.cic.curso25.proy014.globaException;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalHanlderException {

    @ExceptionHandler(PlazaException.class)
    public ResponseEntity<Map<String, String>> handlerPlazaException(PlazaException e) {
        Map<String, String> body = new HashMap<>();
        body.put("message", e.getMessage());
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(VehiculoException.class)
    public ResponseEntity<Map<String, String>> handlerVehiculoException(VehiculoException e) {
        Map<String, String> body = new HashMap<>();
        body.put("message", e.getMessage());
        return ResponseEntity.badRequest().body(body);
    }

}
