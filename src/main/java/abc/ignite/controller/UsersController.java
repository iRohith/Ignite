package abc.ignite.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import abc.ignite.dto.APIResponse;
import abc.ignite.dto.ErrorDTO;
import abc.ignite.dto.UserRequestDTO;
import abc.ignite.dto.UserResponseDTO;
import abc.ignite.service.UserService;
import abc.ignite.util.ValueMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
@Slf4j
public class UsersController {

    public static final String SUCCESS = "Success";
    public static final String FAILURE = "Failure";

    private UserService userService;

    @PostMapping
    public ResponseEntity<APIResponse<UserResponseDTO>> createNewUser(@RequestBody @Valid UserRequestDTO userReqestDTO){
        log.debug("UserController::createNewUser request body {}", ValueMapper.jsonAsString(userReqestDTO));

        UserResponseDTO userResponseDTO = userService.createNewUser(userReqestDTO);

        APIResponse<UserResponseDTO> responseDTO = APIResponse
                .<UserResponseDTO>builder()
                .status(SUCCESS)
                .result(userResponseDTO)
                .build();

        log.info("UserController::createNewUser response {}", ValueMapper.jsonAsString(responseDTO));

        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<APIResponse<List<UserResponseDTO>>> getUsers(@RequestParam("role") Optional<String> role) {

        List<UserResponseDTO> users;

        if (!role.isPresent() || role.get().isBlank()) {
            users = userService.getUsers();
        } else {
            users = userService.getUsersByRole(role.get());
        }

        APIResponse<List<UserResponseDTO>> responseDTO = APIResponse
                .<List<UserResponseDTO>>builder()
                .status(SUCCESS)
                .result(users)
                .build();

        // log.info("UserController::getUsers response {}", ValueMapper.jsonAsString(responseDTO));

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/{username}")
    public ResponseEntity<APIResponse<UserResponseDTO>> getUser(@PathVariable String username) {
        UserResponseDTO userResponseDTO = userService.getUserByUsername(username);
        APIResponse<UserResponseDTO> responseDTO = APIResponse
                .<UserResponseDTO>builder()
                .status(SUCCESS)
                .result(userResponseDTO)
                .build();

        if (userResponseDTO == null){
            responseDTO.setErrors(Collections.singletonList(new ErrorDTO("username", "User '%s' does not exist".formatted(username))));
        }

        log.info("UserController::getUser by username  {} response {}", username, ValueMapper
                .jsonAsString(userResponseDTO));

        return new ResponseEntity<>(responseDTO, userResponseDTO != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<APIResponse<UserResponseDTO>> deleteUser(@PathVariable String username) {
        UserResponseDTO userResponseDTO = userService.deleteUser(username);
        APIResponse<UserResponseDTO> responseDTO = APIResponse
                .<UserResponseDTO>builder()
                .status(userResponseDTO != null ? SUCCESS : FAILURE)
                .result(userResponseDTO)
                .build();

        if (userResponseDTO == null){
            responseDTO.setErrors(Collections.singletonList(new ErrorDTO("username", "User '%s' does not exist".formatted(username))));
        }

        log.info("UserController::deleteUser by username  {} response {}", username, ValueMapper
                .jsonAsString(responseDTO));

        return new ResponseEntity<>(responseDTO, userResponseDTO != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<APIResponse<Map<String, String>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<ErrorDTO> errors = ex.getBindingResult().getAllErrors().stream().map((error) -> new ErrorDTO(
            error instanceof FieldError ? ((FieldError)error).getField() : null,
            error.getDefaultMessage()
        )).toList();

        APIResponse<Map<String, String>> responseDTO = APIResponse
                .<Map<String, String>>builder()
                .status(FAILURE)
                .errors(errors)
                .build();

        log.error("UserController validation errors {}", ValueMapper.jsonAsString(responseDTO));

        return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
    }
}
