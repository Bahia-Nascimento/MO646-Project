package activity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;


public class FraudDetectionSystemTest {

    private FraudDetectionSystem system;
    private List<FraudDetectionSystem.Transaction> previousTransactions;
    private List<String> blacklistedLocations;

    @Before
    public void setup() {
        system = new FraudDetectionSystem();
        previousTransactions = new ArrayList<>();
        blacklistedLocations = new ArrayList<>();
    }

    @Test
    public void testTransactionAboveLimit() {
        FraudDetectionSystem.Transaction transaction = 
            new FraudDetectionSystem.Transaction(10001.00, LocalDateTime.now(), "BRAZIL");

        FraudDetectionSystem.FraudCheckResult result = system.checkForFraud(transaction, previousTransactions, blacklistedLocations);

        assertTrue(result.isFraudulent);
        assertFalse(result.isBlocked);
        assertTrue(result.verificationRequired);
        assertEquals(50, result.riskScore);
    }

    @Test
    public void testTransactionInLimit() {
        FraudDetectionSystem.Transaction transaction = 
            new FraudDetectionSystem.Transaction(10000.00, LocalDateTime.now(), "BRAZIL");

        FraudDetectionSystem.FraudCheckResult result = system.checkForFraud(transaction, previousTransactions, blacklistedLocations);

        assertFalse(result.isFraudulent);
        assertFalse(result.isBlocked);
        assertFalse(result.verificationRequired);
        assertEquals(0, result.riskScore);
    }

    @Test
    public void testExcessiveTransactionsWithinShortTime() {
        LocalDateTime now = LocalDateTime.now();
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(13), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(12), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(11), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(10), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(9), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(8), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(7), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(6), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(5), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(4), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(3), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(2), "BRAZIL"));
        FraudDetectionSystem.Transaction currentTransaction = new FraudDetectionSystem.Transaction(100.00, now, "BRAZIL");

        FraudDetectionSystem.FraudCheckResult result = system.checkForFraud(currentTransaction, previousTransactions, blacklistedLocations);

        assertFalse(result.isFraudulent);
        assertTrue(result.isBlocked);
        assertFalse(result.verificationRequired);
        assertEquals(30, result.riskScore);
    }

    @Test
    public void testExcessiveTransactionsWithinLargeTime() {
        LocalDateTime now = LocalDateTime.now();
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(130), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(120), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(110), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(100), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(90), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(80), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(70), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(60), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(50), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(40), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(30), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(20), "BRAZIL"));
        FraudDetectionSystem.Transaction currentTransaction = new FraudDetectionSystem.Transaction(100.00, now, "BRAZIL");

        FraudDetectionSystem.FraudCheckResult result = system.checkForFraud(currentTransaction, previousTransactions, blacklistedLocations);

        assertFalse(result.isFraudulent);
        assertFalse(result.isBlocked);
        assertFalse(result.verificationRequired);
        assertEquals(0, result.riskScore);
    }

    @Test
    public void testLocationChangeWithinShortTime() {
        LocalDateTime now = LocalDateTime.now();
        previousTransactions.add(new FraudDetectionSystem.Transaction(100.00, now.minusMinutes(20), "BRAZIL"));
        FraudDetectionSystem.Transaction currentTransaction = new FraudDetectionSystem.Transaction(200.00, now, "PERU");

        FraudDetectionSystem.FraudCheckResult result = system.checkForFraud(currentTransaction, previousTransactions, blacklistedLocations);

        assertTrue(result.isFraudulent);
        assertFalse(result.isBlocked);
        assertTrue(result.verificationRequired);
        assertEquals(20, result.riskScore);
    }

    @Test
    public void testLocationSameWithinShortTime() {
        LocalDateTime now = LocalDateTime.now();
        previousTransactions.add(new FraudDetectionSystem.Transaction(100.00, now.minusMinutes(20), "BRAZIL"));
        FraudDetectionSystem.Transaction currentTransaction = new FraudDetectionSystem.Transaction(200.00, now, "BRAZIL");

        FraudDetectionSystem.FraudCheckResult result = system.checkForFraud(currentTransaction, previousTransactions, blacklistedLocations);

        assertFalse(result.isFraudulent);
        assertFalse(result.isBlocked);
        assertFalse(result.verificationRequired);
        assertEquals(0, result.riskScore);
    }

    @Test
    public void testTransactionFromBlacklistedLocation() {
        blacklistedLocations.add("UNITED STATES");
        FraudDetectionSystem.Transaction transaction = 
            new FraudDetectionSystem.Transaction(5000.00, LocalDateTime.now(), "UNITED STATES");

        FraudDetectionSystem.FraudCheckResult result = system.checkForFraud(transaction, previousTransactions, blacklistedLocations);

        assertFalse(result.isFraudulent);
        assertTrue(result.isBlocked);
        assertFalse(result.verificationRequired);
        assertEquals(100, result.riskScore);
    }

    @Test
    public void testTransactionOutsideBlacklistedLocation() {
        blacklistedLocations.add("UNITED STATES");
        FraudDetectionSystem.Transaction transaction = 
            new FraudDetectionSystem.Transaction(5000.00, LocalDateTime.now(), "BRAZIL");

        FraudDetectionSystem.FraudCheckResult result = system.checkForFraud(transaction, previousTransactions, blacklistedLocations);

        assertFalse(result.isFraudulent);
        assertFalse(result.isBlocked);
        assertFalse(result.verificationRequired);
        assertEquals(0, result.riskScore);
    }

    @Test
    public void testFraudAboveLimitExcessiveTransactionsWithinShortTime() {
        LocalDateTime now = LocalDateTime.now();
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(13), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(12), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(11), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(10), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(9), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(8), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(7), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(6), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(5), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(4), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(3), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(2), "BRAZIL"));
        FraudDetectionSystem.Transaction currentTransaction = new FraudDetectionSystem.Transaction(10001.00, now, "BRAZIL");

        FraudDetectionSystem.FraudCheckResult result = system.checkForFraud(currentTransaction, previousTransactions, blacklistedLocations);

        assertTrue(result.isFraudulent);
        assertTrue(result.isBlocked);
        assertTrue(result.verificationRequired);
        assertEquals(80, result.riskScore);
    }

    @Test
    public void testFraudAboveLimitLocationChange() {
        LocalDateTime now = LocalDateTime.now();
        previousTransactions.add(new FraudDetectionSystem.Transaction(100.00, now.minusMinutes(20), "BRAZIL"));
        FraudDetectionSystem.Transaction currentTransaction = new FraudDetectionSystem.Transaction(10001.00, now, "PERU");

        FraudDetectionSystem.FraudCheckResult result = system.checkForFraud(currentTransaction, previousTransactions, blacklistedLocations);

        assertTrue(result.isFraudulent);
        assertFalse(result.isBlocked);
        assertTrue(result.verificationRequired);
        assertEquals(70, result.riskScore);
    }

    @Test
    public void testFraudAboveLimitBlacklistedLocation() {
        blacklistedLocations.add("UNITED STATES");
        FraudDetectionSystem.Transaction transaction = 
            new FraudDetectionSystem.Transaction(10001.00, LocalDateTime.now(), "UNITED STATES");

        FraudDetectionSystem.FraudCheckResult result = system.checkForFraud(transaction, previousTransactions, blacklistedLocations);

        assertTrue(result.isFraudulent);
        assertTrue(result.isBlocked);
        assertTrue(result.verificationRequired);
        assertEquals(100, result.riskScore);
    }

    @Test
    public void testExcessiveTransactionsWithinShortTimeLocationChange() {
        LocalDateTime now = LocalDateTime.now();
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(13), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(12), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(11), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(10), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(9), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(8), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(7), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(6), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(5), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(4), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(3), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(2), "BRAZIL"));
        FraudDetectionSystem.Transaction currentTransaction = new FraudDetectionSystem.Transaction(100.00, now, "PERU");

        FraudDetectionSystem.FraudCheckResult result = system.checkForFraud(currentTransaction, previousTransactions, blacklistedLocations);

        assertTrue(result.isFraudulent);
        assertTrue(result.isBlocked);
        assertTrue(result.verificationRequired);
        assertEquals(50, result.riskScore);
    }

    @Test
    public void testExcessiveTransactionsBlacklistedLocation() {
        LocalDateTime now = LocalDateTime.now();
        blacklistedLocations.add("UNITED STATES");
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(55), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(53), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(50), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(47), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(45), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(43), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(40), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(37), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(35), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(33), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(32), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(30), "BRAZIL"));
        FraudDetectionSystem.Transaction currentTransaction = new FraudDetectionSystem.Transaction(100.00, now, "UNITED STATES");

        FraudDetectionSystem.FraudCheckResult result = system.checkForFraud(currentTransaction, previousTransactions, blacklistedLocations);

        assertFalse(result.isFraudulent);
        assertTrue(result.isBlocked);
        assertFalse(result.verificationRequired);
        assertEquals(100, result.riskScore);
    }

    @Test
    public void testLocationChangeBlacklistedLocation() {
        LocalDateTime now = LocalDateTime.now();
        blacklistedLocations.add("UNITED STATES");
        previousTransactions.add(new FraudDetectionSystem.Transaction(100.00, now.minusMinutes(20), "BRAZIL"));
        FraudDetectionSystem.Transaction currentTransaction = new FraudDetectionSystem.Transaction(200.00, now, "UNITED STATES");

        FraudDetectionSystem.FraudCheckResult result = system.checkForFraud(currentTransaction, previousTransactions, blacklistedLocations);

        assertTrue(result.isFraudulent);
        assertTrue(result.isBlocked);
        assertTrue(result.verificationRequired);
        assertEquals(100, result.riskScore);
    }

    @Test
    public void testFraudAboveLimitExcessiveTransactionsWithinShortTimeLocationChange() {
        LocalDateTime now = LocalDateTime.now();
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(13), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(12), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(11), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(10), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(9), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(8), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(7), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(6), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(5), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(4), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(3), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(2), "BRAZIL"));
        FraudDetectionSystem.Transaction currentTransaction = new FraudDetectionSystem.Transaction(10001.00, now, "PERU");

        FraudDetectionSystem.FraudCheckResult result = system.checkForFraud(currentTransaction, previousTransactions, blacklistedLocations);

        assertTrue(result.isFraudulent);
        assertTrue(result.isBlocked);
        assertTrue(result.verificationRequired);
        assertEquals(100, result.riskScore);
    }

    @Test
    public void testFraudAboveLimitExcessiveTransactionsWithinShortTimeBlacklistedLocation() {
        LocalDateTime now = LocalDateTime.now();
        blacklistedLocations.add("UNITED STATES");
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(13), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(12), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(11), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(10), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(9), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(8), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(7), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(6), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(5), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(4), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(3), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(2), "BRAZIL"));
        FraudDetectionSystem.Transaction currentTransaction = new FraudDetectionSystem.Transaction(10001.00, now, "UNITED STATES");

        FraudDetectionSystem.FraudCheckResult result = system.checkForFraud(currentTransaction, previousTransactions, blacklistedLocations);

        assertTrue(result.isFraudulent);
        assertTrue(result.isBlocked);
        assertTrue(result.verificationRequired);
        assertEquals(100, result.riskScore);
    }

    @Test
    public void testFraudAboveLimitLocationChangeBlacklistedLocation() {
        LocalDateTime now = LocalDateTime.now();
        blacklistedLocations.add("UNITED STATES");
        previousTransactions.add(new FraudDetectionSystem.Transaction(100.00, now.minusMinutes(20), "BRAZIL"));
        FraudDetectionSystem.Transaction currentTransaction = new FraudDetectionSystem.Transaction(10001.00, now, "UNITED STATES");

        FraudDetectionSystem.FraudCheckResult result = system.checkForFraud(currentTransaction, previousTransactions, blacklistedLocations);

        assertTrue(result.isFraudulent);
        assertTrue(result.isBlocked);
        assertTrue(result.verificationRequired);
        assertEquals(100, result.riskScore);
    }

    @Test
    public void testFraudExcessiveTransactionsWithinShortTimeLocationChangeBlacklistedLocation() {
        LocalDateTime now = LocalDateTime.now();
        blacklistedLocations.add("UNITED STATES");
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(13), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(12), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(11), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(10), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(9), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(8), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(7), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(6), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(5), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(4), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(3), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(2), "BRAZIL"));
        FraudDetectionSystem.Transaction currentTransaction = new FraudDetectionSystem.Transaction(10000.00, now, "UNITED STATES");

        FraudDetectionSystem.FraudCheckResult result = system.checkForFraud(currentTransaction, previousTransactions, blacklistedLocations);

        assertTrue(result.isFraudulent);
        assertTrue(result.isBlocked);
        assertTrue(result.verificationRequired);
        assertEquals(100, result.riskScore);
    }

    @Test
    public void testAllRuleBroked(){
        LocalDateTime now = LocalDateTime.now();
        blacklistedLocations.add("UNITED STATES");
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(13), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(12), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(11), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(10), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(9), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(8), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(7), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(6), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(5), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(4), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(3), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(2), "BRAZIL"));
        FraudDetectionSystem.Transaction currentTransaction = new FraudDetectionSystem.Transaction(10001.00, now, "UNITED STATES");

        FraudDetectionSystem.FraudCheckResult result = system.checkForFraud(currentTransaction, previousTransactions, blacklistedLocations);

        assertTrue(result.isFraudulent);
        assertTrue(result.isBlocked);
        assertTrue(result.verificationRequired);
        assertEquals(100, result.riskScore);
    }

    @Test
    public void testAllRulesCorrect(){
        LocalDateTime now = LocalDateTime.now();
        blacklistedLocations.add("UNITED STATES");
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(130), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(120), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(110), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(100), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(90), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(80), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(70), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(60), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(50), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(40), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(30), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(20), "BRAZIL"));
        FraudDetectionSystem.Transaction currentTransaction = new FraudDetectionSystem.Transaction(10000.00, now, "BRAZIL");

        FraudDetectionSystem.FraudCheckResult result = system.checkForFraud(currentTransaction, previousTransactions, blacklistedLocations);

        assertFalse(result.isFraudulent);
        assertFalse(result.isBlocked);
        assertFalse(result.verificationRequired);
        assertEquals(0, result.riskScore);
    }

    @Test
    public void testKillMutant1(){
        LocalDateTime now = LocalDateTime.now();
        blacklistedLocations.add("UNITED STATES");
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(60), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(60), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(60), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(60), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(60), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(60), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(60), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(60), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(60), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(60), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(60), "BRAZIL"));
        FraudDetectionSystem.Transaction currentTransaction = new FraudDetectionSystem.Transaction(10000.00, now, "BRAZIL");

        FraudDetectionSystem.FraudCheckResult result = system.checkForFraud(currentTransaction, previousTransactions, blacklistedLocations);

        assertFalse(result.isFraudulent);
        assertTrue(result.isBlocked);
        assertFalse(result.verificationRequired);
        assertEquals(30, result.riskScore);
    }
    @Test
    public void testKillMutant2(){
        LocalDateTime now = LocalDateTime.now();
        blacklistedLocations.add("UNITED STATES");
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(60), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(60), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(60), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(60), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(60), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(60), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(60), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(60), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(60), "BRAZIL"));
        previousTransactions.add(new FraudDetectionSystem.Transaction(500.00, now.minusMinutes(60), "BRAZIL"));
        FraudDetectionSystem.Transaction currentTransaction = new FraudDetectionSystem.Transaction(10000.00, now, "BRAZIL");

        FraudDetectionSystem.FraudCheckResult result = system.checkForFraud(currentTransaction, previousTransactions, blacklistedLocations);

        assertFalse(result.isFraudulent);
        assertFalse(result.isBlocked);
        assertFalse(result.verificationRequired);
        assertEquals(0, result.riskScore);
    }    
}
