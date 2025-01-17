package abc.ignite.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import abc.ignite.dto.BookingRequestDTO;
import abc.ignite.dto.BookingResponseDTO;
import abc.ignite.entity.Booking;
import abc.ignite.exception.BookingServiceException;
import abc.ignite.repository.BookingRepository;
import abc.ignite.util.ValueMapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class BookingService {
    private BookingRepository bookingRepository;
    // private UserRepository userRepository;
    // private ClassRepository classRepository;

    @Transactional
    public BookingResponseDTO createNewBooking(BookingRequestDTO bookingRequestDTO) throws BookingServiceException {
        BookingResponseDTO bookingResponseDTO = null;

        try {
            Booking booking = ValueMapper.convertToEntity(bookingRequestDTO);
            // booking.setUser(userRepository.findById(bookingRequestDTO.getUsername()).orElseThrow());
            // booking.setClassEntity(classRepository.findById(bookingRequestDTO.getClassId()).orElseThrow());
            Booking bookingResult = bookingRepository.save(booking);
            bookingResponseDTO = ValueMapper.convertToDTO(bookingResult);
            log.debug("BookingService:createNewBooking received response from Database {}", ValueMapper.jsonAsString(bookingResponseDTO));
        } catch (Exception ex) {
            log.error("Exception occurred while persisting booking to database , Exception message {}", ex.getMessage());
            throw new BookingServiceException("Exception occurred while creating a new booking");
        }

        return bookingResponseDTO;
    }

    @Cacheable(value = "bookings")
    public List<BookingResponseDTO> getBookings(String username, Optional<LocalDate> fromDate, Optional<LocalDate> toDate) throws BookingServiceException {
        List<BookingResponseDTO> bookingResponseDTOS = null;

        LocalDate _fromDate = fromDate.orElse(LocalDate.ofYearDay(1980, 1));
        LocalDate _toDate = toDate.orElse(LocalDate.ofYearDay(3000, 1));

        try {
            List<Booking> bookingList = fromDate.isEmpty() && toDate.isEmpty() ?
                bookingRepository.findByUserUsername(username) :
                bookingRepository.findByUserUsernameAndDateBetween(username, _fromDate, _toDate);

            bookingResponseDTOS = bookingList.stream().map(ValueMapper::convertToDTO).toList();

            log.debug("BookingService:getBookings retrieving bookings by username, from date and to date from database  {}", ValueMapper.jsonAsString(bookingResponseDTOS));

        } catch (Exception ex) {
            log.error("Exception occurred while retrieving bookings by username, from date and to date from database , Exception message {}", ex.getMessage());
            throw new BookingServiceException("Exception occurred while fetching all bookings by username, from date and to date from Database");
        }

        return bookingResponseDTOS;
    }

    @Cacheable(value = "bookings")
    public List<BookingResponseDTO> getBookings(Optional<LocalDate> fromDate, Optional<LocalDate> toDate) throws BookingServiceException {
        List<BookingResponseDTO> bookingResponseDTOS = null;

        LocalDate _fromDate = fromDate.orElse(LocalDate.ofYearDay(1980, 1));
        LocalDate _toDate = toDate.orElse(LocalDate.ofYearDay(3000, 1));

        try {
            List<Booking> bookingList = bookingRepository.findByDateBetween(_fromDate, _toDate);

            bookingResponseDTOS = bookingList.stream().map(ValueMapper::convertToDTO).toList();

            log.debug("BookingService:getBookings retrieving bookings by username, from date and to date from database  {}", ValueMapper.jsonAsString(bookingResponseDTOS));

        } catch (Exception ex) {
            log.error("Exception occurred while retrieving bookings by username, from date and to date from database , Exception message {}", ex.getMessage());
            throw new BookingServiceException("Exception occurred while fetching all bookings by username, from date and to date from Database");
        }

        return bookingResponseDTOS;
    }
}
