package abc.ignite.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClassResponseDTO {

    private String classId;

    private String name;

    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime startTime; 

    private Integer duration; // Minutes

    private Integer capacity;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private List<LocalDate> dates;
}
