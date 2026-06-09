package com.andre.escola_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errosDosCampos = new HashMap<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errosDosCampos.put(error.getField(), error.getDefaultMessage());
        }

        Map<String, Object> respostaErro = new HashMap<>();
        respostaErro.put("timestamp", LocalDateTime.now());
        respostaErro.put("status", HttpStatus.BAD_VALUE); // Retorna 400
        respostaErro.put("mensagem", "Falha na validação dos campos preenchidos.");
        respostaErro.put("erros", errosDosCampos);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respostaErro);
    }
}