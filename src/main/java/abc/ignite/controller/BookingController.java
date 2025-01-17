package abc.ignite.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import abc.ignite.dto.APIResponse;
import abc.ignite.dto.BookingRequestDTO;
import abc.ignite.dto.BookingResponseDTO;
import abc.ignite.dto.ErrorDTO;
import abc.ignite.service.BookingService;
import abc.ignite.util.ValueMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/booking")
@AllArgsConstructor
@Slf4j
public class BookingController {

    public static final String SUCCESS = "Success";
    public static final String FAILURE = "Failure";

    private BookingService bookingService;

    @PostMapping
    public ResponseEntity<APIResponse<BookingResponseDTO>> createNewBooking(@RequestBody @Valid BookingRequestDTO bookingReqestDTO){
        log.debug("BookingController::createNewBooking request body {}", ValueMapper.jsonAsString(bookingReqestDTO));

        BookingResponseDTO bookingResponseDTO = bookingService.createNewBooking(bookingReqestDTO);

        APIResponse<BookingResponseDTO> responseDTO = APIResponse
                .<BookingResponseDTO>builder()
                .status(SUCCESS)
                .result(bookingResponseDTO)
                .build();

        log.info("BookingController::createNewBooking response {}", ValueMapper.jsonAsString(responseDTO));

        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{username}")
    public ResponseEntity<APIResponse<List<BookingResponseDTO>>> getBookings(
        @PathVariable
        String username,
        @RequestParam("fromDate")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        Optional<LocalDate> fromDate,
        @RequestParam("toDate")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        Optional<LocalDate> toDate
    ){    
        List<BookingResponseDTO> bookingResponseDTO = bookingService.getBookings(username, fromDate, toDate);

        APIResponse<List<BookingResponseDTO>> responseDTO = APIResponse
                .<List<BookingResponseDTO>>builder()
                .status(SUCCESS)
                .result(bookingResponseDTO)
                .build();

        log.info("BookingController::createNewBooking response {}", ValueMapper.jsonAsString(responseDTO));

        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<APIResponse<List<BookingResponseDTO>>> getBookings(
        @RequestParam("fromDate")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        Optional<LocalDate> fromDate,
        @RequestParam("toDate")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        Optional<LocalDate> toDate
    ){    
        List<BookingResponseDTO> bookingResponseDTO = bookingService.getBookings(fromDate, toDate);

        APIResponse<List<BookingResponseDTO>> responseDTO = APIResponse
                .<List<BookingResponseDTO>>builder()
                .status(SUCCESS)
                .result(bookingResponseDTO)
                .build();

        log.info("BookingController::createNewBooking response {}", ValueMapper.jsonAsString(responseDTO));

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

        log.error("BookingController validation errors {}", ValueMapper.jsonAsString(responseDTO));

        return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
    }
}
