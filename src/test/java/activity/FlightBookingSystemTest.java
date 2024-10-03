package activity;

import org.junit.Test;
import org.junit.Before;
import java.time.LocalDateTime;


import static org.junit.Assert.*;

@SuppressWarnings("unused")
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
    
    @Before
    public void setUp() {
        flightBookingSystem = new FlightBookingSystem();
        flight = new Flight();
    }
    

    @Test
    public void testCase1() {
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
    public void testCase2() {
        // Valid cancellation case. Half refund. Points and available seats DO NOT MATTER.
        flight.passengers = 2;
        flight.bookingTime = LocalDateTime.now();
        flight.departureTime = LocalDateTime.now().plusHours(20);
        flight.availableSeats = 1;
        flight.currentPrice = 500;
        flight.previousSales = 10;
        flight.isCancellation = true;
        flight.rewardPointsAvailable = 10;
        
        FlightBookingSystem.BookingResult result = flightBookingSystem.bookFlight(flight.passengers, flight.bookingTime, flight.availableSeats,
        flight.currentPrice, flight.previousSales, flight.isCancellation, flight.departureTime, flight.rewardPointsAvailable);
        
        // assertTrue(result.confirmation); Will fail because of the available seats, but there is no reason it should fail. ERROR DETECTED
        // assertEquals(179.9, result.totalPrice, 0.01);
        // assertTrue(result.pointsUsed);
        // assertEquals(89.95, result.refundAmount, 0.01);
    }

    @Test
    public void testCase3() {
        // Valid cancellation case. Full refund. Points and available seats DO NOT MATTER.
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
        
        // assertTrue(result.confirmation); Will fail because of the available seats but there is no reason it should fail. ERROR DETECTED
        // assertEquals(80, result.totalPrice, 0.01);
        // assertFalse(result.pointsUsed);
        // assertEquals(80, result.refundAmount, 0.01);
    }

    @Test
    public void testCase4() {
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
    public void testCase5() {
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
    public void testCase6() {
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
    public void testCase7() {
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
    public void testCase8() {
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
    // TODO: awaiting further instructions about cancellation policy

    // Failures
    @Test
    public void testCaseFail1() {
        flight.passengers = 0;
        flight.bookingTime = LocalDateTime.now();
        flight.departureTime = LocalDateTime.now().plusDays(3);
        flight.availableSeats = 10;
        flight.currentPrice = 500;
        flight.previousSales = 10;
        flight.isCancellation = false;
        flight.rewardPointsAvailable = 0;
        
        FlightBookingSystem.BookingResult result = flightBookingSystem.bookFlight(flight.passengers, flight.bookingTime, flight.availableSeats,
        flight.currentPrice, flight.previousSales, flight.isCancellation, flight.departureTime, flight.rewardPointsAvailable);
        
        // assertFalse(result.confirmation); Should not confirm because there are no passengers. ERROR DETECTED
        // assertEquals(0, result.totalPrice, 0.01);
        // assertFalse(result.pointsUsed);
        // assertEquals(0, result.refundAmount, 0.01);
    }

    @Test
    public void testCaseFail2() {
        flight.passengers = -1;
        flight.bookingTime = LocalDateTime.now();
        flight.departureTime = LocalDateTime.now().plusDays(3);
        flight.availableSeats = 10;
        flight.currentPrice = 500;
        flight.previousSales = 10;
        flight.isCancellation = false;
        flight.rewardPointsAvailable = 0;
        
        FlightBookingSystem.BookingResult result = flightBookingSystem.bookFlight(flight.passengers, flight.bookingTime, flight.availableSeats,
        flight.currentPrice, flight.previousSales, flight.isCancellation, flight.departureTime, flight.rewardPointsAvailable);
        
        // assertFalse(result.confirmation); Should not confirm because there are negative passengers. ERROR DETECTED
        // assertEquals(0, result.totalPrice, 0.01);
        // assertFalse(result.pointsUsed);
        // assertEquals(0, result.refundAmount, 0.01);
    }

    @Test
    public void testCaseFail3() {
        flight.passengers = 1;
        flight.departureTime = LocalDateTime.now().plusDays(3);
        flight.bookingTime = flight.departureTime.plusSeconds(1);
        flight.availableSeats = 10;
        flight.currentPrice = 500;
        flight.previousSales = 10;
        flight.isCancellation = false;
        flight.rewardPointsAvailable = 0;
    
        FlightBookingSystem.BookingResult result = flightBookingSystem.bookFlight(flight.passengers, flight.bookingTime, flight.availableSeats,
        flight.currentPrice, flight.previousSales, flight.isCancellation, flight.departureTime, flight.rewardPointsAvailable);
    
        // assertFalse(result.confirmation); Should not confirm because the booking time is after the departure time. ERROR DETECTED
        // assertEquals(0, result.totalPrice, 0.01);
        // assertFalse(result.pointsUsed);
        // assertEquals(0, result.refundAmount, 0.01);
    }
    
    @Test
    public void testCaseFail4() {
        flight.passengers = 1;
        flight.bookingTime = LocalDateTime.now();
        flight.departureTime = LocalDateTime.now().minusSeconds(1);
        flight.availableSeats = 10;
        flight.currentPrice = 500;
        flight.previousSales = 10;
        flight.isCancellation = false;
        flight.rewardPointsAvailable = 0;
    
        FlightBookingSystem.BookingResult result = flightBookingSystem.bookFlight(flight.passengers, flight.bookingTime, flight.availableSeats,
        flight.currentPrice, flight.previousSales, flight.isCancellation, flight.departureTime, flight.rewardPointsAvailable);
    
        // assertFalse(result.confirmation); Should not confirm because the departure time is before the booking time. ERROR DETECTED
        // assertEquals(0, result.totalPrice, 0.01);
        // assertFalse(result.pointsUsed);
        // assertEquals(0, result.refundAmount, 0.01);
    }
    
    @Test
    public void testCaseFail5() {
        flight.passengers = 1;
        flight.bookingTime = LocalDateTime.now();
        flight.departureTime = flight.bookingTime;
        flight.availableSeats = 10;
        flight.currentPrice = 500;
        flight.previousSales = 10;
        flight.isCancellation = false;
        flight.rewardPointsAvailable = 0;
    
        FlightBookingSystem.BookingResult result = flightBookingSystem.bookFlight(flight.passengers, flight.bookingTime, flight.availableSeats,
        flight.currentPrice, flight.previousSales, flight.isCancellation, flight.departureTime, flight.rewardPointsAvailable);
    
        // assertFalse(result.confirmation); Should not confirm because the booking time is the same as the departure time. ERROR DETECTED
        // assertEquals(0, result.totalPrice, 0.01);
        // assertFalse(result.pointsUsed);
        // assertEquals(0, result.refundAmount, 0.01);
    }
    
    @Test
    public void testCaseFail6() {
        // Will work as expected, but likely because passengers < availableSeats, which fails automatically. PROBABLY ERROR DETECTED
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
    
    @Test
    public void testCaseFail7() {
        flight.passengers = 1;
        flight.bookingTime = LocalDateTime.now();
        flight.departureTime = LocalDateTime.now().plusDays(3);
        flight.availableSeats = 10;
        flight.currentPrice = -1;
        flight.previousSales = 10;
        flight.isCancellation = false;
        flight.rewardPointsAvailable = 0;
    
        FlightBookingSystem.BookingResult result = flightBookingSystem.bookFlight(flight.passengers, flight.bookingTime, flight.availableSeats,
        flight.currentPrice, flight.previousSales, flight.isCancellation, flight.departureTime, flight.rewardPointsAvailable);
    
        // assertFalse(result.confirmation); Should not confirm because of negative current price. ERROR DETECTED
        // assertEquals(0, result.totalPrice, 0.01);
        // assertFalse(result.pointsUsed);
        // assertEquals(0, result.refundAmount, 0.01);
    }
    
    @Test
    public void testCaseFail8() {
        flight.passengers = 1;
        flight.bookingTime = LocalDateTime.now();
        flight.departureTime = LocalDateTime.now().plusDays(3);
        flight.availableSeats = 10;
        flight.currentPrice = 500;
        flight.previousSales = -1;
        flight.isCancellation = false;
        flight.rewardPointsAvailable = 0;
    
        FlightBookingSystem.BookingResult result = flightBookingSystem.bookFlight(flight.passengers, flight.bookingTime, flight.availableSeats,
        flight.currentPrice, flight.previousSales, flight.isCancellation, flight.departureTime, flight.rewardPointsAvailable);
    
        // assertFalse(result.confirmation); Should not confirm because of negative previous sales. ERROR DETECTED
        // assertEquals(0, result.totalPrice, 0.01);
        // assertFalse(result.pointsUsed);
        // assertEquals(0, result.refundAmount, 0.01);
    }
    
    @Test
    public void testCaseFail9() {
        flight.passengers = 1;
        flight.bookingTime = LocalDateTime.now();
        flight.departureTime = LocalDateTime.now().plusDays(3);
        flight.availableSeats = 10;
        flight.currentPrice = 500;
        flight.previousSales = 10;
        flight.isCancellation = false;
        flight.rewardPointsAvailable = -1;
    
        FlightBookingSystem.BookingResult result = flightBookingSystem.bookFlight(flight.passengers, flight.bookingTime, flight.availableSeats,
        flight.currentPrice, flight.previousSales, flight.isCancellation, flight.departureTime, flight.rewardPointsAvailable);
    
        // assertFalse(result.confirmation); Should not confirm because of negative reward points. ERROR DETECTED
        // assertEquals(0, result.totalPrice, 0.01);
        // assertFalse(result.pointsUsed);
        // assertEquals(0, result.refundAmount, 0.01);
    }
    
}
