package at.htl.entity;


import at.htl.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class AccidentTest {

    @Test
    public void testGetCar() {
        // Arrange
        Car car = new Car();
        Accident accident = new Accident();
        accident.setCar(car);

        // Act
        Car actualCar = accident.getCar();

        // Assert
        Assertions.assertEquals(car, actualCar);
    }

    @Test
    public void testGetUser() {
        // Arrange
        Users user = new Users();
        Accident accident = new Accident();
        accident.setUser(user);

        // Act
        Users actualUser = accident.getUser();

        // Assert
        Assertions.assertEquals(user, actualUser);
    }

    @Test
    public void testGetCosts() {
        // Arrange
        List<Costs> costs = new ArrayList<>();
        Accident accident = new Accident();
        accident.setCosts(costs);

        // Act
        List<Costs> actualCosts = accident.getCosts();

        // Assert
        Assertions.assertEquals(costs, actualCosts);
    }

    @Test
    public void testGetDrive() {
        // Arrange
        Drive drive = new Drive();
        Accident accident = new Accident();
        accident.setDrive(drive);

        // Act
        Drive actualDrive = accident.getDrive();

        // Assert
        Assertions.assertEquals(drive, actualDrive);
    }

    @Test
    public void testSetCar() {
        // Arrange
        Car car = new Car();
        Accident accident = new Accident();

        // Act
        accident.setCar(car);

        // Assert
        Assertions.assertEquals(car, accident.getCar());
    }

    @Test
    public void testSetUser() {
        // Arrange
        Users user = new Users();
        Accident accident = new Accident();

        // Act
        accident.setUser(user);

        // Assert
        Assertions.assertEquals(user, accident.getUser());
    }

    @Test
    public void testSetCosts() {
        // Arrange
        List<Costs> costs = new ArrayList<>();
        Accident accident = new Accident();

        // Act
        accident.setCosts(costs);

        // Assert
        Assertions.assertEquals(costs, accident.getCosts());
    }

    @Test
    public void testSetDrive() {
        // Arrange
        Drive drive = new Drive();
        Accident accident = new Accident();

        // Act
        accident.setDrive(drive);

        // Assert
        Assertions.assertEquals(drive, accident.getDrive());
    }

    @Test
    public void testEquals_SameObject() {
        // Arrange
        Accident accident = new Accident();

        // Act & Assert
        Assertions.assertEquals(accident, accident);
    }

    @Test
    public void testEquals_NullObject() {
        // Arrange
        Accident accident = new Accident();

        // Act & Assert
        Assertions.assertNotEquals(accident, null);
    }
}

