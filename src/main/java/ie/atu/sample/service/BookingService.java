package ie.atu.sample.service;

import ie.atu.sample.dto.BookingRequest;
import ie.atu.sample.dto.BookingResponse;
import ie.atu.sample.model.Booking;
import ie.atu.sample.model.Room;
import ie.atu.sample.repository.BookingRepository;
import ie.atu.sample.repository.RoomRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;

    public BookingService(BookingRepository bookingRepository, RoomRepository roomRepository) {
        this.bookingRepository = bookingRepository;
        this.roomRepository = roomRepository;
    }

    public BookingResponse createBooking(BookingRequest request) {
        Room room = roomRepository.findAll().stream()
                .filter(r -> r.getName().equalsIgnoreCase(request.getRoomName()))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Room not found with name: " + request.getRoomName()));

        if (request.getCount() > room.getCapacity()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Attendee count (" + request.getCount() + ") exceeds room capacity (" + room.getCapacity() + ")");
        }

        Booking booking = new Booking();
        booking.setRoomName(room.getName());
        booking.setOrganiserName(request.getOrganiserName());
        booking.setCount(request.getCount());
        booking.setStatus("ACTIVE");

        Booking savedBooking = bookingRepository.save(booking);

        return mapToResponse(savedBooking);
    }

    public BookingResponse getBookingById(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking not found with id: " + id));
        return mapToResponse(booking);
    }

    private BookingResponse mapToResponse(Booking booking) {
        return new BookingResponse(
                booking.getId(),
                booking.getRoomName(),
                booking.getOrganiserName(),
                booking.getCount(),
                booking.getStatus()
        );
    }

    public java.util.List<BookingResponse> searchBookingsByRoomName(String roomName) {
        return bookingRepository.findByRoomNameOrderedByCountDesc(roomName).stream()
                .map(this::mapToResponse)
                .toList();
    }
}
