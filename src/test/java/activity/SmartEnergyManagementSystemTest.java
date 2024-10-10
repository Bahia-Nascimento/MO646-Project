package activity;

import org.junit.Test;
import org.junit.Before;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SmartEnergyManagementSystemTest {
    private SmartEnergyManagementSystem system;
    private Map<String, Integer> devicePriorities;
    private List<SmartEnergyManagementSystem.DeviceSchedule> scheduledDevices;
    @Before
    public void setup() {
        system = new SmartEnergyManagementSystem();
        devicePriorities = new HashMap<>();
        scheduledDevices = new ArrayList<>();
    }
    @Test
    public void testEnergySavingModeBasedOnPriceThreshold() {
        devicePriorities.put("Heating", 1);
        devicePriorities.put("Lights", 2);
        devicePriorities.put("Appliances", 3);
        SmartEnergyManagementSystem.EnergyManagementResult result = system.manageEnergy(
            0.25, 0.20, devicePriorities, LocalDateTime.now(), 22.0, new double[]{20.0, 24.0}, 30, 25, scheduledDevices);
        assertTrue(result.energySavingMode);
        assertFalse(result.deviceStatus.get("Heating"));
        assertFalse(result.deviceStatus.get("Lights"));
        assertFalse(result.deviceStatus.get("Appliances"));
    }
    @Test
    public void testNoEnergySavingModeWhenPriceBelowThreshold() {
        devicePriorities.put("Heating", 1);
        devicePriorities.put("Lights", 2);
        devicePriorities.put("Appliances", 3);
        SmartEnergyManagementSystem.EnergyManagementResult result = system.manageEnergy(
            0.15, 0.20, devicePriorities, LocalDateTime.now(), 22.0, new double[]{20.0, 24.0}, 30, 25, scheduledDevices);
        assertFalse(result.energySavingMode);
        assertFalse(result.deviceStatus.get("Heating"));
        assertTrue(result.deviceStatus.get("Lights"));
        assertTrue(result.deviceStatus.get("Appliances"));
    }
    @Test
    public void testNightMode() {
        devicePriorities.put("Security", 1);
        devicePriorities.put("Lights", 2);
        devicePriorities.put("Appliances", 3);
        SmartEnergyManagementSystem.EnergyManagementResult result = system.manageEnergy(
            0.15, 0.20, devicePriorities, LocalDateTime.of(2024, 10, 1, 23, 30), 22.0, new double[]{20.0, 24.0}, 30, 25, scheduledDevices);
        assertTrue(result.deviceStatus.get("Security"));
        assertFalse(result.deviceStatus.get("Lights"));
        assertFalse(result.deviceStatus.get("Appliances"));
    }
    @Test
    public void testTemperatureRegulationHeating() {
        devicePriorities.put("Heating", 1);
        SmartEnergyManagementSystem.EnergyManagementResult result = system.manageEnergy(
            0.15, 0.20, devicePriorities, LocalDateTime.now(), 18.0, new double[]{20.0, 24.0}, 30, 25, scheduledDevices);
        assertTrue(result.deviceStatus.get("Heating"));
        assertTrue(result.temperatureRegulationActive);
    }
    @Test
    public void testTemperatureRegulationCooling() {
        devicePriorities.put("Cooling", 1);
        SmartEnergyManagementSystem.EnergyManagementResult result = system.manageEnergy(
        0.15, 0.20, devicePriorities, LocalDateTime.now(), 25.0, new double[]{20.0, 24.0}, 30, 25, scheduledDevices);
        assertTrue(result.deviceStatus.get("Cooling"));
        assertTrue(result.temperatureRegulationActive);
   }
    @Test
    public void testTemperatureWithinRange() {
        devicePriorities.put("Heating", 1);
        devicePriorities.put("Cooling", 1);
        SmartEnergyManagementSystem.EnergyManagementResult result = system.manageEnergy(
            0.15, 0.20, devicePriorities, LocalDateTime.now(), 22.0, new double[]{20.0, 24.0}, 30, 25, scheduledDevices);
        assertFalse(result.deviceStatus.get("Heating"));
        assertFalse(result.deviceStatus.get("Cooling"));
        assertFalse(result.temperatureRegulationActive);
    }
    @Test
    public void testEnergyUsageLimit() {
        devicePriorities.put("Heating", 1);
        devicePriorities.put("Lights", 2);
        devicePriorities.put("Appliances", 3);
        SmartEnergyManagementSystem.EnergyManagementResult result = system.manageEnergy(
            0.15, 0.20, devicePriorities, LocalDateTime.now(), 18.0, new double[]{20.0, 24.0}, 30, 31, scheduledDevices);
        assertTrue(result.deviceStatus.get("Heating"));
        assertFalse(result.deviceStatus.get("Lights"));
        assertFalse(result.deviceStatus.get("Appliances"));
        assertEquals(29, result.totalEnergyUsed, 0.01);
    }
    @Test
    public void testScheduledDevices() {
        devicePriorities.put("Oven", 1);
        scheduledDevices.add(new SmartEnergyManagementSystem.DeviceSchedule("Oven", LocalDateTime.of(2024, 10, 1, 18, 0)));
        SmartEnergyManagementSystem.EnergyManagementResult result = system.manageEnergy(
            0.15, 0.20, devicePriorities, LocalDateTime.of(2024, 10, 1, 18, 0), 22.0, new double[]{20.0, 24.0}, 30, 25, scheduledDevices);
        assertTrue(result.deviceStatus.get("Oven"));
    }
    @Test
    public void testScheduledDevicesOverrideEnergySavingMode() {
        devicePriorities.put("Oven", 1);
        devicePriorities.put("Lights", 2);
        scheduledDevices.add(new SmartEnergyManagementSystem.DeviceSchedule("Oven", LocalDateTime.of(2024, 10, 1, 18, 0)));
        SmartEnergyManagementSystem.EnergyManagementResult result = system.manageEnergy(
            0.25, 0.20, devicePriorities, LocalDateTime.of(2024, 10, 1, 18, 0), 22.0, new double[]{20.0, 24.0}, 30, 25, scheduledDevices);
        assertTrue(result.deviceStatus.get("Oven"));
        assertFalse(result.deviceStatus.get("Lights"));
    }
    @Test
    public void testScheduledDevicesOverrideNightMode() {
        devicePriorities.put("Oven", 1);
        devicePriorities.put("Security", 1);
        scheduledDevices.add(new SmartEnergyManagementSystem.DeviceSchedule("Oven", LocalDateTime.of(2024, 10, 1, 23, 30)));
        SmartEnergyManagementSystem.EnergyManagementResult result = system.manageEnergy(
            0.15, 0.20, devicePriorities, LocalDateTime.of(2024, 10, 1, 23, 30), 22.0, new double[]{20.0, 24.0}, 30, 25, scheduledDevices);
        assertTrue(result.deviceStatus.get("Oven"));
        assertTrue(result.deviceStatus.get("Security"));
    }
    @Test
    public void testDeviceScheduleConstructor() {
        LocalDateTime scheduledTime = LocalDateTime.of(2024, 10, 1, 18, 0);
        SmartEnergyManagementSystem.DeviceSchedule schedule = new SmartEnergyManagementSystem.DeviceSchedule("Oven", scheduledTime);
        assertEquals("Oven", schedule.deviceName);
        assertEquals(scheduledTime, schedule.scheduledTime);
    }
    @Test
    public void testEnergyManagementResultConstructor() {
        Map<String, Boolean> deviceStatus = new HashMap<>();
        deviceStatus.put("Heating", true);
        deviceStatus.put("Cooling", false);
        SmartEnergyManagementSystem.EnergyManagementResult result = new SmartEnergyManagementSystem.EnergyManagementResult(
            deviceStatus, true, true, 25.0);
        assertEquals(deviceStatus, result.deviceStatus);
        assertTrue(result.energySavingMode);
        assertTrue(result.temperatureRegulationActive);
        assertEquals(25.0, result.totalEnergyUsed, 0.01);
    }
    @Test
    public void testNightModeAfter11PM() {
        devicePriorities.put("Security", 1);
        devicePriorities.put("Lights", 2);
        devicePriorities.put("Appliances", 3);
        SmartEnergyManagementSystem.EnergyManagementResult result = system.manageEnergy(
            0.15, 0.20, devicePriorities, LocalDateTime.of(2024, 10, 1, 23, 30), 22.0, new double[]{20.0, 24.0}, 30, 25, scheduledDevices);
        assertTrue(result.deviceStatus.get("Security"));
        assertFalse(result.deviceStatus.get("Lights"));
        assertFalse(result.deviceStatus.get("Appliances"));
    }
    @Test
    public void testNightModeBefore6AM() {
        devicePriorities.put("Security", 1);
        devicePriorities.put("Lights", 2);
        devicePriorities.put("Appliances", 3);
        SmartEnergyManagementSystem.EnergyManagementResult result = system.manageEnergy(
            0.15, 0.20, devicePriorities, LocalDateTime.of(2024, 10, 1, 5, 30), 22.0, new double[]{20.0, 24.0}, 30, 25, scheduledDevices);
        assertTrue(result.deviceStatus.get("Security"));
        assertFalse(result.deviceStatus.get("Lights"));
        assertFalse(result.deviceStatus.get("Appliances"));
    }

    @Test
    public void testNoNightModeDuringDay() {
        devicePriorities.put("Security", 1);
        devicePriorities.put("Lights", 2);
        devicePriorities.put("Appliances", 3);
        SmartEnergyManagementSystem.EnergyManagementResult result = system.manageEnergy(
            0.15, 0.20, devicePriorities, LocalDateTime.of(2024, 10, 1, 12, 0), 22.0, new double[]{20.0, 24.0}, 30, 25, scheduledDevices);
        assertTrue(result.deviceStatus.get("Security"));
        assertTrue(result.deviceStatus.get("Lights"));
        assertTrue(result.deviceStatus.get("Appliances"));
    }
    @Test
    public void testEnergyUsageLimitExceeded() {
        devicePriorities.put("Heating", 1);
        devicePriorities.put("Lights", 2);
        devicePriorities.put("Appliances", 3);

        SmartEnergyManagementSystem.EnergyManagementResult result = system.manageEnergy(
            0.15, 0.20, devicePriorities, LocalDateTime.now(), 22.0, new double[]{20.0, 24.0}, 30, 31, scheduledDevices);

        assertFalse(result.deviceStatus.get("Heating"));
        assertFalse(result.deviceStatus.get("Lights"));
        assertFalse(result.deviceStatus.get("Appliances"));
        assertEquals(29, result.totalEnergyUsed, 0.01);
    }

    @Test
    public void testEnergyUsageLimitNotExceeded() {
        devicePriorities.put("Heating", 1);
        devicePriorities.put("Lights", 2);
        devicePriorities.put("Appliances", 3);

        SmartEnergyManagementSystem.EnergyManagementResult result = system.manageEnergy(
            0.15, 0.20, devicePriorities, LocalDateTime.now(), 22.0, new double[]{20.0, 24.0}, 30, 29, scheduledDevices);

        assertFalse(result.deviceStatus.get("Heating"));
        assertTrue(result.deviceStatus.get("Lights"));
        assertTrue(result.deviceStatus.get("Appliances"));
        assertEquals(29, result.totalEnergyUsed, 0.01);
    }

    @Test
    public void testNoDevicesOn() {
        devicePriorities.put("Lights", 2);
        devicePriorities.put("Appliances", 3);

        SmartEnergyManagementSystem.EnergyManagementResult result = system.manageEnergy(
            0.3, 0.20, devicePriorities, LocalDateTime.now(), 22.0, new double[]{20.0, 24.0}, 30, 31, scheduledDevices);

        result.deviceStatus.put("Heating", false);
        result.deviceStatus.put("Lights", false);
        result.deviceStatus.put("Appliances", false);

        assertFalse(result.deviceStatus.containsValue(true));
    }

    @Test
    public void testLowPriorityDevicesTurnedOff() {
        devicePriorities.put("Heating", 1);
        devicePriorities.put("Lights", 2);
        devicePriorities.put("Appliances", 3);

        SmartEnergyManagementSystem.EnergyManagementResult result = system.manageEnergy(
            0.15, 0.20, devicePriorities, LocalDateTime.now(), 22.0, new double[]{20.0, 24.0}, 30, 31, scheduledDevices);

        assertFalse(result.deviceStatus.get("Heating"));
        assertFalse(result.deviceStatus.get("Lights"));
        assertFalse(result.deviceStatus.get("Appliances"));
        assertEquals(29, result.totalEnergyUsed, 0.01);
    }

    @Test
    public void testHighPriorityDevicesRemainOn() {
        devicePriorities.put("Heating", 1);
        devicePriorities.put("Lights", 2);
        devicePriorities.put("Appliances", 3);

        SmartEnergyManagementSystem.EnergyManagementResult result = system.manageEnergy(
            0.15, 0.20, devicePriorities, LocalDateTime.now(), 22.0, new double[]{20.0, 24.0}, 30, 31, scheduledDevices);

        assertFalse(result.deviceStatus.get("Heating"));
        assertFalse(result.deviceStatus.get("Lights"));
        assertFalse(result.deviceStatus.get("Appliances"));
        assertEquals(29, result.totalEnergyUsed, 0.01);
    }
    @Test
    public void testScheduledDeviceTurnsOnAtScheduledTime() {
        devicePriorities.put("Oven", 1);
        scheduledDevices.add(new SmartEnergyManagementSystem.DeviceSchedule("Oven", LocalDateTime.of(2024, 10, 1, 18, 0)));

        SmartEnergyManagementSystem.EnergyManagementResult result = system.manageEnergy(
            0.15, 0.20, devicePriorities, LocalDateTime.of(2024, 10, 1, 18, 0), 22.0, new double[]{20.0, 24.0}, 30, 25, scheduledDevices);

        assertTrue(result.deviceStatus.get("Oven"));
    }

    @Test
    public void testScheduledDeviceDoesNotTurnOnBeforeScheduledTime() {
        devicePriorities.put("Oven", 1);
        scheduledDevices.add(new SmartEnergyManagementSystem.DeviceSchedule("Oven", LocalDateTime.of(2024, 10, 1, 18, 0)));

        SmartEnergyManagementSystem.EnergyManagementResult result = system.manageEnergy(
            0.15, 0.20, devicePriorities, LocalDateTime.of(2024, 10, 1, 17, 59), 22.0, new double[]{20.0, 24.0}, 30, 25, scheduledDevices);

        assertTrue(result.deviceStatus.get("Oven"));
    }
    @Test
    public void testSecurityDeviceRemainsOnDuringNightMode() {
        devicePriorities.put("Security", 1);
        devicePriorities.put("Lights", 2);
        devicePriorities.put("Appliances", 3);
        SmartEnergyManagementSystem.EnergyManagementResult result = system.manageEnergy(
            0.15, 0.20, devicePriorities, LocalDateTime.of(2024, 10, 1, 23, 30), 22.0, new double[]{20.0, 24.0}, 30, 25, scheduledDevices);
        assertTrue(result.deviceStatus.get("Security"));
        assertFalse(result.deviceStatus.get("Lights"));
        assertFalse(result.deviceStatus.get("Appliances"));
    }
    @Test
    public void testRefrigeratorDeviceRemainsOnDuringNightMode() {
        devicePriorities.put("Refrigerator", 1);
        devicePriorities.put("Lights", 2);
        devicePriorities.put("Appliances", 3);
        SmartEnergyManagementSystem.EnergyManagementResult result = system.manageEnergy(
        0.15, 0.20, devicePriorities, LocalDateTime.of(2024, 10, 1, 23, 30), 22.0, new double[]{20.0, 24.0}, 30, 25, scheduledDevices);
        assertTrue(result.deviceStatus.get("Refrigerator"));
        assertFalse(result.deviceStatus.get("Lights"));
        assertFalse(result.deviceStatus.get("Appliances"));
    }
    @Test
    public void testNonEssentialDevicesTurnOffDuringNightMode() {
        devicePriorities.put("Security", 1);
        devicePriorities.put("Refrigerator", 1);
        devicePriorities.put("Lights", 2);
        devicePriorities.put("Appliances", 3);
        SmartEnergyManagementSystem.EnergyManagementResult result = system.manageEnergy(
            0.15, 0.20, devicePriorities, LocalDateTime.of(2024, 10, 1, 23, 30), 22.0, new double[]{20.0, 24.0}, 30, 25, scheduledDevices);
        assertTrue(result.deviceStatus.get("Security"));
        assertTrue(result.deviceStatus.get("Refrigerator"));
        assertFalse(result.deviceStatus.get("Lights"));
        assertFalse(result.deviceStatus.get("Appliances"));
    }
}
