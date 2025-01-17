package abc.ignite.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import abc.ignite.dto.BookingRequestDTO;
import abc.ignite.dto.BookingResponseDTO;
import abc.ignite.dto.ClassRequestDTO;
import abc.ignite.dto.ClassResponseDTO;
import abc.ignite.dto.UserRequestDTO;
import abc.ignite.dto.UserResponseDTO;
import abc.ignite.entity.Booking;
import abc.ignite.entity.ClassEntity;
import abc.ignite.entity.User;

public class ValueMapper {
    public static User convertToEntity(UserRequestDTO userRequestDTO){
        return new User(
            userRequestDTO.getUsername(),
            userRequestDTO.getFullName(),
            userRequestDTO.getRole()
        );
    }

    public static UserResponseDTO convertToDTO(User user){
        return new UserResponseDTO(
            user.getUsername(),
            user.getFullName(),
            user.getRole()
        );
    }

    public static ClassEntity convertToEntity(ClassRequestDTO classEntityRequestDTO){
        return new ClassEntity(
            classEntityRequestDTO.getClassId(),
            classEntityRequestDTO.getName(),
            classEntityRequestDTO.getStartTime(),
            classEntityRequestDTO.getDuration(),
            classEntityRequestDTO.getCapacity(),
            classEntityRequestDTO.getStartDate().datesUntil(classEntityRequestDTO.getEndDate()).toList()
        );
    }

    public static ClassResponseDTO convertToDTO(ClassEntity classEntity){
        return new ClassResponseDTO(
            classEntity.getClassId(),
            classEntity.getName(),
            classEntity.getStartTime(),
            classEntity.getDuration(),
            classEntity.getCapacity(),
            classEntity.getDates()
        );
    }

    public static Booking convertToEntity(BookingRequestDTO bookingRequestDTO){
        return new Booking(
            null,
            bookingRequestDTO.getDate(),
            User.fromUsername(bookingRequestDTO.getUsername()),
            ClassEntity.fromId(bookingRequestDTO.getClassId())
        );
    }

    public static BookingResponseDTO convertToDTO(Booking booking){
        UserResponseDTO user = convertToDTO(booking.getUser());
        ClassResponseDTO cls = convertToDTO(booking.getClassEntity());
        cls.setDates(null);
        
        return new BookingResponseDTO(
            booking.getId(),
            booking.getDate(),
            user,
            cls
        );
    }

    public static String jsonAsString(Object obj){
        try {
            return objMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static ObjectMapper objMapper = new ObjectMapper();

    static {
        objMapper.findAndRegisterModules();
    }
}
