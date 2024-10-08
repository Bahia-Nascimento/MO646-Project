package activity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;


import static org.junit.Assert.*;

class FlightBookingSystemTest {
    public class Flight {
        public int passengers;
        public LocalDateTime bookingTime;
        public int availableSeats;
        public double currentPrice;
        public int previousSales;
        public boolean isCancellation;
        public LocalDateTime departureTime;
        public int rewardPointsAvailable;
    }

    private FlightBookingSystem flightBookingSystem;
    private Flight flight;
    
    @BeforeEach
    void setUp() {
        flightBookingSystem = new FlightBookingSystem();
        flight = new Flight();
    }

    @Test
    void testHalfRefundCase() {
        flight.passengers = 2;
        flight.bookingTime = LocalDateTime.now();
        flight.departureTime = LocalDateTime.now().plusHours(20);
        flight.availableSeats = 10;
        flight.currentPrice = 500;
        flight.previousSales = 10;
        flight.isCancellation = true;
        flight.rewardPointsAvailable = 10;
    
        FlightBookingSystem.BookingResult result = flightBookingSystem.bookFlight(flight.passengers, flight.bookingTime, flight.availableSeats,
        flight.currentPrice, flight.previousSales, flight.isCancellation, flight.departureTime, flight.rewardPointsAvailable);
    
        assertFalse(result.confirmation);
        assertEquals(0, result.totalPrice, 0.01);
        assertFalse(result.pointsUsed);
        assertEquals(89.95, result.refundAmount, 0.01);
    }
    
    @Test
    void testFullRefundCase() {
        flight.passengers = 2;
        flight.bookingTime = LocalDateTime.now();
        flight.departureTime = LocalDateTime.now().plusHours(50);
        flight.availableSeats = 10;
        flight.currentPrice = 500;
        flight.previousSales = 10;
        flight.isCancellation = true;
        flight.rewardPointsAvailable = 0;
    
        FlightBookingSystem.BookingResult result = flightBookingSystem.bookFlight(flight.passengers, flight.bookingTime, flight.availableSeats,
        flight.currentPrice, flight.previousSales, flight.isCancellation, flight.departureTime, flight.rewardPointsAvailable);
    
        assertFalse(result.confirmation);
        assertEquals(0, result.totalPrice, 0.01);
        assertFalse(result.pointsUsed);
        assertEquals(80, result.refundAmount, 0.01);
    }
    
    @Test
    void test500PointsCase() {
        flight.passengers = 2;
        flight.bookingTime = LocalDateTime.now();
        flight.departureTime = LocalDateTime.now().plusHours(25);
        flight.availableSeats = 10;
        flight.currentPrice = 500;
        flight.previousSales = 10;
        flight.isCancellation = false;
        flight.rewardPointsAvailable = 500;
    
        FlightBookingSystem.BookingResult result = flightBookingSystem.bookFlight(flight.passengers, flight.bookingTime, flight.availableSeats,
        flight.currentPrice, flight.previousSales, flight.isCancellation, flight.departureTime, flight.rewardPointsAvailable);
    
        assertTrue(result.confirmation);
        assertEquals(75, result.totalPrice, 0.01);
        assertTrue(result.pointsUsed);
        assertEquals(0, result.refundAmount, 0.01);
    }
    
    @Test
    void testLastMinuteFeeCase() {
        flight.passengers = 2;
        flight.bookingTime = LocalDateTime.now();
        flight.departureTime = LocalDateTime.now().plusHours(20);
        flight.availableSeats = 10;
        flight.currentPrice = 500;
        flight.previousSales = 10;
        flight.isCancellation = false;
        flight.rewardPointsAvailable = 0;
    
        FlightBookingSystem.BookingResult result = flightBookingSystem.bookFlight(flight.passengers, flight.bookingTime, flight.availableSeats,
        flight.currentPrice, flight.previousSales, flight.isCancellation, flight.departureTime, flight.rewardPointsAvailable);
    
        assertTrue(result.confirmation);
        assertEquals(180, result.totalPrice, 0.01);
        assertFalse(result.pointsUsed);
        assertEquals(0, result.refundAmount, 0.01);
    }
    
    @Test
    void testNotEnoughSeatsCase() {
        flight.passengers = 4;
        flight.bookingTime = LocalDateTime.now();
        flight.departureTime = LocalDateTime.now().plusHours(50);
        flight.availableSeats = 2;
        flight.currentPrice = 500;
        flight.previousSales = 10;
        flight.isCancellation = false;
        flight.rewardPointsAvailable = 0;
    
        FlightBookingSystem.BookingResult result = flightBookingSystem.bookFlight(flight.passengers, flight.bookingTime, flight.availableSeats,
        flight.currentPrice, flight.previousSales, flight.isCancellation, flight.departureTime, flight.rewardPointsAvailable);
    
        assertFalse(result.confirmation);
        assertEquals(0, result.totalPrice, 0.01);
        assertFalse(result.pointsUsed);
        assertEquals(0, result.refundAmount, 0.01);
    }
    
    @Test
    void testGroupDiscountCase() {
        flight.passengers = 5;
        flight.bookingTime = LocalDateTime.now();
        flight.departureTime = LocalDateTime.now().plusHours(50);
        flight.availableSeats = 10;
        flight.currentPrice = 500;
        flight.previousSales = 10;
        flight.isCancellation = false;
        flight.rewardPointsAvailable = 0;
    
        FlightBookingSystem.BookingResult result = flightBookingSystem.bookFlight(flight.passengers, flight.bookingTime, flight.availableSeats,
        flight.currentPrice, flight.previousSales, flight.isCancellation, flight.departureTime, flight.rewardPointsAvailable);
    
        assertTrue(result.confirmation);
        assertEquals(190, result.totalPrice, 0.01);
        assertFalse(result.pointsUsed);
        assertEquals(0, result.refundAmount, 0.01);
    }
    

    // @Test
    // void testWeirdRefundInteraction() {
    //     //  Will return 0 refund because passengers < available seats. but that should not matter in a cancelattion. INCOSISTENCY DETECTED
    //     flight.passengers = 2;
    //     flight.bookingTime = LocalDateTime.now();
    //     flight.departureTime = LocalDateTime.now().plusHours(20);
    //     flight.availableSeats = 1;
    //     flight.currentPrice = 500;
    //     flight.previousSales = 10;
    //     flight.isCancellation = true;
    //     flight.rewardPointsAvailable = 10;
        
    //     FlightBookingSystem.BookingResult result = flightBookingSystem.bookFlight(flight.passengers, flight.bookingTime, flight.availableSeats,
    //     flight.currentPrice, flight.previousSales, flight.isCancellation, flight.departureTime, flight.rewardPointsAvailable);
        
    //     assertFalse(result.confirmation);
    //     assertEquals(0, result.totalPrice, 0.01);
    //     assertFalse(result.pointsUsed);
    //     assertEquals(89.95, result.refundAmount, 0.01);
    // }

}
