package activity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

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
    @BeforeEach
    void setup() {
        system = new SmartEnergyManagementSystem();
        devicePriorities = new HashMap<>();
        scheduledDevices = new ArrayList<>();
    }
    /*@Test
    void testEnergySavingModeBasedOnPriceThreshold() {
        devicePriorities.put("Heating", 1);
        devicePriorities.put("Lights", 2);
        devicePriorities.put("Appliances", 3);
        SmartEnergyManagementSystem.EnergyManagementResult result = system.manageEnergy(
            0.25, 0.20, devicePriorities, LocalDateTime.now(), 22.0, new double[]{20.0, 24.0}, 30, 25, scheduledDevices);
        assertTrue(result.energySavingMode);
        assertTrue(result.deviceStatus.get("Heating"));
        assertFalse(result.deviceStatus.get("Lights"));
        assertFalse(result.deviceStatus.get("Appliances"));
    }*/
//    @Test
//    void testNoEnergySavingModeWhenPriceBelowThreshold() {
//        devicePriorities.put("Heating", 1);
//        devicePriorities.put("Lights", 2);
//        devicePriorities.put("Appliances", 3);
//        SmartEnergyManagementSystem.EnergyManagementResult result = system.manageEnergy(
//            0.15, 0.20, devicePriorities, LocalDateTime.now(), 22.0, new double[]{20.0, 24.0}, 30, 25, scheduledDevices);
//        assertFalse(result.energySavingMode);
//        assertTrue(result.deviceStatus.get("Heating"));
//        assertTrue(result.deviceStatus.get("Lights"));
//        assertTrue(result.deviceStatus.get("Appliances"));
//    }
    @Test
    void testNightMode() {
        devicePriorities.put("Security", 1);
        devicePriorities.put("Lights", 2);
        devicePriorities.put("Appliances", 3);
        SmartEnergyManagementSystem.EnergyManagementResult result = system.manageEnergy(
            0.15, 0.20, devicePriorities, LocalDateTime.of(2024, 10, 1, 23, 30), 22.0, new double[]{20.0, 24.0}, 30, 25, scheduledDevices);
        assertTrue(result.deviceStatus.get("Security"));
        assertFalse(result.deviceStatus.get("Lights"));
        assertFalse(result.deviceStatus.get("Appliances"));
    }
    /*@Test
    void testTemperatureRegulationHeating() {
        devicePriorities.put("Heating", 1);
        devicePriorities.put("Cooling", 1);
        SmartEnergyManagementSystem.EnergyManagementResult result = system.manageEnergy(
            0.15, 0.20, devicePriorities, LocalDateTime.now(), 18.0, new double[]{20.0, 24.0}, 30, 25, scheduledDevices);
        assertTrue(result.deviceStatus.get("Heating"));
        assertFalse(result.deviceStatus.get("Cooling"));
        assertTrue(result.temperatureRegulationActive);
    }*/
    /*@Test
    void testTemperatureRegulationCooling() {
        devicePriorities.put("Heating", 1);
        devicePriorities.put("Cooling", 1);
        SmartEnergyManagementSystem.EnergyManagementResult result = system.manageEnergy(
        0.15, 0.20, devicePriorities, LocalDateTime.now(), 25.0, new double[]{20.0, 24.0}, 30, 25, scheduledDevices);
        assertFalse(result.deviceStatus.get("Heating"));
        assertTrue(result.deviceStatus.get("Cooling"));
        assertTrue(result.temperatureRegulationActive);
   }*/
    @Test
    void testTemperatureWithinRange() {
        devicePriorities.put("Heating", 1);
        devicePriorities.put("Cooling", 1);
        SmartEnergyManagementSystem.EnergyManagementResult result = system.manageEnergy(
            0.15, 0.20, devicePriorities, LocalDateTime.now(), 22.0, new double[]{20.0, 24.0}, 30, 25, scheduledDevices);
        assertFalse(result.deviceStatus.get("Heating"));
        assertFalse(result.deviceStatus.get("Cooling"));
        assertFalse(result.temperatureRegulationActive);
    }
    //    @Test
//    void testEnergyUsageLimit() {
//        devicePriorities.put("Heating", 1);
//        devicePriorities.put("Lights", 2);
//        devicePriorities.put("Appliances", 3);
//        SmartEnergyManagementSystem.EnergyManagementResult result = system.manageEnergy(
//            0.15, 0.20, devicePriorities, LocalDateTime.now(), 22.0, new double[]{20.0, 24.0}, 30, 29, scheduledDevices);
//        assertTrue(result.deviceStatus.get("Heating"));
//        assertFalse(result.deviceStatus.get("Lights"));
//        assertFalse(result.deviceStatus.get("Appliances"));
//        assertEquals(28, result.totalEnergyUsed, 0.01);  // Simulando a redução de energia
//    }
    @Test
    void testScheduledDevices() {
        devicePriorities.put("Oven", 1);
        scheduledDevices.add(new SmartEnergyManagementSystem.DeviceSchedule("Oven", LocalDateTime.of(2024, 10, 1, 18, 0)));
        SmartEnergyManagementSystem.EnergyManagementResult result = system.manageEnergy(
            0.15, 0.20, devicePriorities, LocalDateTime.of(2024, 10, 1, 18, 0), 22.0, new double[]{20.0, 24.0}, 30, 25, scheduledDevices);
        assertTrue(result.deviceStatus.get("Oven"));
    }
    @Test
    void testScheduledDevicesOverrideEnergySavingMode() {
        devicePriorities.put("Oven", 1);
        devicePriorities.put("Lights", 2);
        scheduledDevices.add(new SmartEnergyManagementSystem.DeviceSchedule("Oven", LocalDateTime.of(2024, 10, 1, 18, 0)));
        SmartEnergyManagementSystem.EnergyManagementResult result = system.manageEnergy(
            0.25, 0.20, devicePriorities, LocalDateTime.of(2024, 10, 1, 18, 0), 22.0, new double[]{20.0, 24.0}, 30, 25, scheduledDevices);
        assertTrue(result.deviceStatus.get("Oven"));
        assertFalse(result.deviceStatus.get("Lights"));
    }
    @Test
    void testScheduledDevicesOverrideNightMode() {
        devicePriorities.put("Oven", 1);
        devicePriorities.put("Security", 1);
        scheduledDevices.add(new SmartEnergyManagementSystem.DeviceSchedule("Oven", LocalDateTime.of(2024, 10, 1, 23, 30)));
        SmartEnergyManagementSystem.EnergyManagementResult result = system.manageEnergy(
            0.15, 0.20, devicePriorities, LocalDateTime.of(2024, 10, 1, 23, 30), 22.0, new double[]{20.0, 24.0}, 30, 25, scheduledDevices);
        assertTrue(result.deviceStatus.get("Oven"));
        assertTrue(result.deviceStatus.get("Security"));
    }
    @Test
    void testDeviceScheduleConstructor() {
        LocalDateTime scheduledTime = LocalDateTime.of(2024, 10, 1, 18, 0);
        SmartEnergyManagementSystem.DeviceSchedule schedule = new SmartEnergyManagementSystem.DeviceSchedule("Oven", scheduledTime);
        assertEquals("Oven", schedule.deviceName);
        assertEquals(scheduledTime, schedule.scheduledTime);
    }
    @Test
    void testEnergyManagementResultConstructor() {
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
}
