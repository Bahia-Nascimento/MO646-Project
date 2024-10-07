package activity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;


import static org.junit.Assert.*;

public class FlightBookingSystemTest {
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
    void testCase1() {
        // Passengers edge value case.
        flight.passengers = 1;
        flight.bookingTime = LocalDateTime.now();
        flight.departureTime = LocalDateTime.now().plusHours(25);
        flight.availableSeats = 10;
        flight.currentPrice = 500;
        flight.previousSales = 10;
        flight.isCancellation = false;
        flight.rewardPointsAvailable = 0;
        
        FlightBookingSystem.BookingResult result = flightBookingSystem.bookFlight(flight.passengers, flight.bookingTime, flight.availableSeats,
        flight.currentPrice, flight.previousSales, flight.isCancellation, flight.departureTime, flight.rewardPointsAvailable);
        
        assertTrue(result.confirmation);
        assertEquals(40, result.totalPrice, 0.01);
        assertFalse(result.pointsUsed);
        assertEquals(0, result.refundAmount, 0.01);
    }

    @Test
    void testCase2() {
        //  Will return 0 refund because passengers < available seats. but that should not matter in a cancelattion. ERROR DETECTED
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
    void testCase3() {
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
    void testCase4() {
        // Previous sales edge value case.
        flight.passengers = 2;
        flight.bookingTime = LocalDateTime.now();
        flight.departureTime = LocalDateTime.now().plusHours(25);
        flight.availableSeats = 10;
        flight.currentPrice = 500;
        flight.previousSales = 0;
        flight.isCancellation = false;
        flight.rewardPointsAvailable = 0;
        
        FlightBookingSystem.BookingResult result = flightBookingSystem.bookFlight(flight.passengers, flight.bookingTime, flight.availableSeats,
        flight.currentPrice, flight.previousSales, flight.isCancellation, flight.departureTime, flight.rewardPointsAvailable);
        
        assertTrue(result.confirmation);
        assertEquals(0, result.totalPrice, 0.01);
        assertFalse(result.pointsUsed);
        assertEquals(0, result.refundAmount, 0.01);
    }

    @Test
    void testCase5() {
        // Previous sales edge value case.
        flight.passengers = 2;
        flight.bookingTime = LocalDateTime.now();
        flight.departureTime = LocalDateTime.now().plusHours(25);
        flight.availableSeats = 10;
        flight.currentPrice = 500;
        flight.previousSales = 1;
        flight.isCancellation = false;
        flight.rewardPointsAvailable = 0;
        
        FlightBookingSystem.BookingResult result = flightBookingSystem.bookFlight(flight.passengers, flight.bookingTime, flight.availableSeats,
        flight.currentPrice, flight.previousSales, flight.isCancellation, flight.departureTime, flight.rewardPointsAvailable);
        
        assertTrue(result.confirmation);
        assertEquals(8, result.totalPrice, 0.01);
        assertFalse(result.pointsUsed);
        assertEquals(0, result.refundAmount, 0.01);
    }

    @Test
    void testCase6() {
        // Points available edge value case.
        flight.passengers = 2;
        flight.bookingTime = LocalDateTime.now();
        flight.departureTime = LocalDateTime.now().plusHours(25);
        flight.availableSeats = 10;
        flight.currentPrice = 500;
        flight.previousSales = 10;
        flight.isCancellation = false;
        flight.rewardPointsAvailable = 1;
        
        FlightBookingSystem.BookingResult result = flightBookingSystem.bookFlight(flight.passengers, flight.bookingTime, flight.availableSeats,
        flight.currentPrice, flight.previousSales, flight.isCancellation, flight.departureTime, flight.rewardPointsAvailable);
        
        assertTrue(result.confirmation);
        assertEquals(79.99, result.totalPrice, 0.01);
        assertTrue(result.pointsUsed);
        assertEquals(0, result.refundAmount, 0.01);
    }

    @Test
    void testCase7() {
        // Valid 500 points case
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
    void testCase8() {
        // Valid Last-Minute fee case
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

    // @Test
    // void testCase9() {
    //     //  Will return 0 refund because passengers < available seats. but that should not matter in a cancelattion. ERROR DETECTED
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

    // Failures
    // @Test
    // void testCaseFail1() {
    //     //  Should not confirm because there are no passengers. ERROR DETECTED
    //     flight.passengers = 0;
    //     flight.bookingTime = LocalDateTime.now();
    //     flight.departureTime = LocalDateTime.now().plusDays(3);
    //     flight.availableSeats = 10;
    //     flight.currentPrice = 500;
    //     flight.previousSales = 10;
    //     flight.isCancellation = false;
    //     flight.rewardPointsAvailable = 0;
        
    //     FlightBookingSystem.BookingResult result = flightBookingSystem.bookFlight(flight.passengers, flight.bookingTime, flight.availableSeats,
    //     flight.currentPrice, flight.previousSales, flight.isCancellation, flight.departureTime, flight.rewardPointsAvailable);
        
    //     assertFalse(result.confirmation);
    //     assertEquals(0, result.totalPrice, 0.01);
    //     assertFalse(result.pointsUsed);
    //     assertEquals(0, result.refundAmount, 0.01);
    // }

    // @Test
    // void testCaseFail2() {
    //     //  Should not confirm because there are negative passengers. ERROR DETECTED
    //     flight.passengers = -1;
    //     flight.bookingTime = LocalDateTime.now();
    //     flight.departureTime = LocalDateTime.now().plusDays(3);
    //     flight.availableSeats = 10;
    //     flight.currentPrice = 500;
    //     flight.previousSales = 10;
    //     flight.isCancellation = false;
    //     flight.rewardPointsAvailable = 0;
        
    //     FlightBookingSystem.BookingResult result = flightBookingSystem.bookFlight(flight.passengers, flight.bookingTime, flight.availableSeats,
    //     flight.currentPrice, flight.previousSales, flight.isCancellation, flight.departureTime, flight.rewardPointsAvailable);
        
    //     assertFalse(result.confirmation);
    //     assertEquals(0, result.totalPrice, 0.01);
    //     assertFalse(result.pointsUsed);
    //     assertEquals(0, result.refundAmount, 0.01);
    // }

    // @Test
    // void testCaseFail3() {
    //     //  Should not confirm because the booking time is after the departure time. ERROR DETECTED
    //     flight.passengers = 1;
    //     flight.departureTime = LocalDateTime.now().plusDays(3);
    //     flight.bookingTime = flight.departureTime.plusSeconds(1);
    //     flight.availableSeats = 10;
    //     flight.currentPrice = 500;
    //     flight.previousSales = 10;
    //     flight.isCancellation = false;
    //     flight.rewardPointsAvailable = 0;
    
    //     FlightBookingSystem.BookingResult result = flightBookingSystem.bookFlight(flight.passengers, flight.bookingTime, flight.availableSeats,
    //     flight.currentPrice, flight.previousSales, flight.isCancellation, flight.departureTime, flight.rewardPointsAvailable);
    
    //     assertFalse(result.confirmation);
    //     assertEquals(0, result.totalPrice, 0.01);
    //     assertFalse(result.pointsUsed);
    //     assertEquals(0, result.refundAmount, 0.01);
    // }
    
    // @Test
    // void testCaseFail4() {
    //     //  Should not confirm because the departure time is before the booking time. ERROR DETECTED
    //     flight.passengers = 1;
    //     flight.bookingTime = LocalDateTime.now();
    //     flight.departureTime = LocalDateTime.now().minusSeconds(1);
    //     flight.availableSeats = 10;
    //     flight.currentPrice = 500;
    //     flight.previousSales = 10;
    //     flight.isCancellation = false;
    //     flight.rewardPointsAvailable = 0;
    
    //     FlightBookingSystem.BookingResult result = flightBookingSystem.bookFlight(flight.passengers, flight.bookingTime, flight.availableSeats,
    //     flight.currentPrice, flight.previousSales, flight.isCancellation, flight.departureTime, flight.rewardPointsAvailable);
    
    //     assertFalse(result.confirmation);
    //     assertEquals(0, result.totalPrice, 0.01);
    //     assertFalse(result.pointsUsed);
    //     assertEquals(0, result.refundAmount, 0.01);
    // }
    
    // @Test
    // void testCaseFail5() {
    //     //  Should not confirm because the booking time is the same as the departure time. ERROR DETECTED
    //     flight.passengers = 1;
    //     flight.bookingTime = LocalDateTime.now();
    //     flight.departureTime = flight.bookingTime;
    //     flight.availableSeats = 10;
    //     flight.currentPrice = 500;
    //     flight.previousSales = 10;
    //     flight.isCancellation = false;
    //     flight.rewardPointsAvailable = 0;
    
    //     FlightBookingSystem.BookingResult result = flightBookingSystem.bookFlight(flight.passengers, flight.bookingTime, flight.availableSeats,
    //     flight.currentPrice, flight.previousSales, flight.isCancellation, flight.departureTime, flight.rewardPointsAvailable);
    
    //     assertFalse(result.confirmation);
    //     assertEquals(0, result.totalPrice, 0.01);
    //     assertFalse(result.pointsUsed);
    //     assertEquals(0, result.refundAmount, 0.01);
    // }
    
    @Test
    void testCaseFail6() {
        // Will work as expected, but likely because passengers < availableSeats, which fails automatically. ERROR PROBABLY DETECTED
        flight.passengers = 1;
        flight.bookingTime = LocalDateTime.now();
        flight.departureTime = LocalDateTime.now().plusDays(3);
        flight.availableSeats = -1;
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
    
    // @Test
    // void testCaseFail7() {
    //     //  Should not confirm because of negative current price. ERROR DETECTED
    //     flight.passengers = 1;
    //     flight.bookingTime = LocalDateTime.now();
    //     flight.departureTime = LocalDateTime.now().plusDays(3);
    //     flight.availableSeats = 10;
    //     flight.currentPrice = -1;
    //     flight.previousSales = 10;
    //     flight.isCancellation = false;
    //     flight.rewardPointsAvailable = 0;
    
    //     FlightBookingSystem.BookingResult result = flightBookingSystem.bookFlight(flight.passengers, flight.bookingTime, flight.availableSeats,
    //     flight.currentPrice, flight.previousSales, flight.isCancellation, flight.departureTime, flight.rewardPointsAvailable);
    
    //     assertFalse(result.confirmation);
    //     assertEquals(0, result.totalPrice, 0.01);
    //     assertFalse(result.pointsUsed);
    //     assertEquals(0, result.refundAmount, 0.01);
    // }
    
    // @Test
    // void testCaseFail8() {
    //     //  Should not confirm because of negative previous sales. ERROR DETECTED
    //     flight.passengers = 1;
    //     flight.bookingTime = LocalDateTime.now();
    //     flight.departureTime = LocalDateTime.now().plusDays(3);
    //     flight.availableSeats = 10;
    //     flight.currentPrice = 500;
    //     flight.previousSales = -1;
    //     flight.isCancellation = false;
    //     flight.rewardPointsAvailable = 0;
    
    //     FlightBookingSystem.BookingResult result = flightBookingSystem.bookFlight(flight.passengers, flight.bookingTime, flight.availableSeats,
    //     flight.currentPrice, flight.previousSales, flight.isCancellation, flight.departureTime, flight.rewardPointsAvailable);
    
    //     assertFalse(result.confirmation);
    //     assertEquals(0, result.totalPrice, 0.01);
    //     assertFalse(result.pointsUsed);
    //     assertEquals(0, result.refundAmount, 0.01);
    // }
    
    // @Test
    // void testCaseFail9() {
    //     // Should not confirm because of negative reward points. ERROR DETECTED
    //     flight.passengers = 1;
    //     flight.bookingTime = LocalDateTime.now();
    //     flight.departureTime = LocalDateTime.now().plusDays(3);
    //     flight.availableSeats = 10;
    //     flight.currentPrice = 500;
    //     flight.previousSales = 10;
    //     flight.isCancellation = false;
    //     flight.rewardPointsAvailable = -1;
    
    //     FlightBookingSystem.BookingResult result = flightBookingSystem.bookFlight(flight.passengers, flight.bookingTime, flight.availableSeats,
    //     flight.currentPrice, flight.previousSales, flight.isCancellation, flight.departureTime, flight.rewardPointsAvailable);
    
    //     assertFalse(result.confirmation);
    //     assertEquals(0, result.totalPrice, 0.01);
    //     assertFalse(result.pointsUsed);
    //     assertEquals(0, result.refundAmount, 0.01);
    // }
    
}
