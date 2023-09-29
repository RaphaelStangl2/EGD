//package at.htl.entity;
//
//
//import at.htl.model.Costs;
//import at.htl.model.Drive;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//
//public class CostsTest {
//
//    @Test
//    public void testGetId() {
//        // Arrange
//        Long id = 1L;
//        Costs costs = new Costs();
//        costs.setId(id);
//
//        // Act
//        Long actualId = costs.getId();
//
//        // Assert
//        Assertions.assertEquals(id, actualId);
//    }
//
//    @Test
//    public void testGetDescription() {
//        // Arrange
//        String description = "Repair costs";
//        Costs costs = new Costs();
//        costs.setDescription(description);
//
//        // Act
//        String actualDescription = costs.getDescription();
//
//        // Assert
//        Assertions.assertEquals(description, actualDescription);
//    }
//
//    @Test
//    public void testGetCosts() {
//        // Arrange
//        Double costValue = 500.0;
//        Costs costs = new Costs();
//        costs.setCosts(costValue);
//
//        // Act
//        Double actualCostValue = costs.getCosts();
//
//        // Assert
//        Assertions.assertEquals(costValue, actualCostValue);
//    }
//
//    @Test
//    public void testGetDrive() {
//        // Arrange
//        Drive drive = new Drive();
//        Costs costs = new Costs();
//        costs.setDrive(drive);
//
//        // Act
//        Drive actualDrive = costs.getDrive();
//
//        // Assert
//        Assertions.assertEquals(drive, actualDrive);
//    }
//
//    @Test
//    public void testSetId() {
//        // Arrange
//        Long id = 2L;
//        Costs costs = new Costs();
//
//        // Act
//        costs.setId(id);
//
//        // Assert
//        Assertions.assertEquals(id, costs.getId());
//    }
//
//    @Test
//    public void testSetDescription() {
//        // Arrange
//        String description = "Maintenance costs";
//        Costs costs = new Costs();
//
//        // Act
//        costs.setDescription(description);
//
//        // Assert
//        Assertions.assertEquals(description, costs.getDescription());
//    }
//
//    @Test
//    public void testSetCosts() {
//        // Arrange
//        Double costValue = 250.0;
//        Costs costs = new Costs();
//
//        // Act
//        costs.setCosts(costValue);
//
//        // Assert
//        Assertions.assertEquals(costValue, costs.getCosts());
//    }
//
//    @Test
//    public void testSetDrive() {
//        // Arrange
//        Drive drive = new Drive();
//        Costs costs = new Costs();
//
//        // Act
//        costs.setDrive(drive);
//
//        // Assert
//        Assertions.assertEquals(drive, costs.getDrive());
//    }
//
//    @Test
//    public void testEquals_SameObject() {
//        // Arrange
//        Costs costs = new Costs();
//
//        // Act & Assert
//        Assertions.assertEquals(costs, costs);
//    }
//
//    @Test
//    public void testEquals_NullObject() {
//        // Arrange
//        Costs costs = new Costs();
//
//        // Act & Assert
//        Assertions.assertNotEquals(costs, null);
//    }
//}
