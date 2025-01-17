package abc.ignite.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import abc.ignite.dto.APIResponse;
import abc.ignite.dto.ClassRequestDTO;
import abc.ignite.dto.ClassResponseDTO;
import abc.ignite.dto.ErrorDTO;
import abc.ignite.service.ClassService;
import abc.ignite.util.ValueMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/classes")
@AllArgsConstructor
@Slf4j
public class ClassesController {

    public static final String SUCCESS = "Success";
    public static final String FAILURE = "Failure";

    private ClassService classService;

    @PostMapping
    public ResponseEntity<APIResponse<ClassResponseDTO>> createNewClass(@RequestBody @Valid ClassRequestDTO classReqestDTO){
        log.debug("ClassController::createNewClass request body {}", ValueMapper.jsonAsString(classReqestDTO));

        ClassResponseDTO classResponseDTO = classService.createNewClass(classReqestDTO);

        APIResponse<ClassResponseDTO> responseDTO = APIResponse
                .<ClassResponseDTO>builder()
                .status(SUCCESS)
                .result(classResponseDTO)
                .build();

        log.info("ClassController::createNewClass response {}", ValueMapper.jsonAsString(responseDTO));

        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<APIResponse<Void>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<ErrorDTO> errors = ex.getBindingResult().getAllErrors().stream().map((error) -> {
            return new ErrorDTO(
                error instanceof FieldError ? ((FieldError)error).getField() : null,
                error.getDefaultMessage()
            );
        }).toList();

        APIResponse<Void> responseDTO = APIResponse
                .<Void>builder()
                .status(FAILURE)
                .errors(errors)
                .build();

        log.error("ClassController validation errors {}", ValueMapper.jsonAsString(responseDTO));

        return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
    }
}
