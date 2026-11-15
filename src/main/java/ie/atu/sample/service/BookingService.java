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
        // 1. Check if room exists (returns 404 if missing)
        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Room not found with id: " + request.getRoomId()));

        // 2. Check if attendee count exceeds room capacity (returns 400 if too many people)
        if (request.getAttendeeCount() > room.getCapacity()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Attendee count (" + request.getAttendeeCount() + ") exceeds room capacity (" + room.getCapacity() + ")");
        }

        // 3. Create and save booking
        Booking booking = new Booking();
        booking.setRoom(room);
        booking.setOrganiserName(request.getOrganiserName());
        booking.setAttendeeCount(request.getAttendeeCount());
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
                booking.getRoom().getId(),
                booking.getRoom().getName(),
                booking.getOrganiserName(),
                booking.getAttendeeCount(),
                booking.getStatus()
        );
    }
}
