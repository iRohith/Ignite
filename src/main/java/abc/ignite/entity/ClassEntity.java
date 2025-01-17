package abc.ignite.entity;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CLASSES")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassEntity {
    @Id
    @Column(unique = true)
    private String classId;
    private String name;
    private LocalTime startTime; 
    private Integer duration; // Minutes
    private Integer capacity;
    private List<LocalDate> dates;

    public static ClassEntity fromId(String classId){ return new ClassEntity(classId, null, null, null, null, null); }
}
