package com.npci.gateway.exception;

import com.npci.gateway.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleAccountNotFound(AccountNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(ex.getMessage()));
    }
    
    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<ApiResponse<Void>> handleInsufficientBalance(InsufficientBalanceException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(ex.getMessage()));
    }
    
    @ExceptionHandler(InvalidUpiIdException.class)
    public ResponseEntity<ApiResponse<Void>> handleInvalidUpiId(InvalidUpiIdException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(ex.getMessage()));
    }
    
    @ExceptionHandler(TransactionNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleTransactionNotFound(TransactionNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(ex.getMessage()));
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidationException(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(message));
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("An error occurred: " + ex.getMessage()));
    }
}
