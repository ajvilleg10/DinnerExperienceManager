package test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DiningExperienceManagerTest {

	@Test
	public void testNormalOrderTotalCost() {
		// Test a normal order with 2 pizzas and 3 sweet and sour chickens
		DiningExperienceManager manager = new DiningExperienceManager();
		Order order = new NormalOrder();
		order.addItem(new Meal("Pizza", 10.0), 2);
		order.addItem(new Meal("Sweet and Sour Chicken", 9.0), 3);

		double expectedTotalCost = 2 * 10.0 + 3 * 9.0;
		Assertions.assertEquals(expectedTotalCost, order.calculateTotalCost());
	}

	@Test
	public void testDiscount5OrMoreOrderTotalCost() {
		// Test a discount 5 or more order with 6 pastries and 4 spring rolls
		DiningExperienceManager manager = new DiningExperienceManager();
		Order order = new Discount5OrMoreOrder();
		order.addItem(new Meal("Pastries", 6.0), 6);
		order.addItem(new Meal("Spring Rolls", 6.0), 4);

		double expectedTotalCost = (6 * 6.0 + 4 * 6.0) * 0.9; // 10% discount
		Assertions.assertEquals(expectedTotalCost, order.calculateTotalCost());
	}

	@Test
	public void testDiscount10OrMoreOrderTotalCost() {
		// Test a discount 10 or more order with 12 pizzas and 9 sweet and sour chickens
		DiningExperienceManager manager = new DiningExperienceManager();
		Order order = new Discount10OrMoreOrder();
		order.addItem(new Meal("Pizza", 10.0), 12);
		order.addItem(new Meal("Sweet and Sour Chicken", 9.0), 9);

		double expectedTotalCost = (12 * 10.0 + 9 * 9.0) * 0.8; // 20% discount
		Assertions.assertEquals(expectedTotalCost, order.calculateTotalCost());
	}

	@Test
	public void testSpecialMealSurcharge() {
		// Test a discount 10 or more order with 1 chef's special soup and 2 chef's
		// special pasta
		DiningExperienceManager manager = new DiningExperienceManager();
		Order order = new Discount10OrMoreOrder();
		order.addItem(new Meal("Chef's Special Soup", 7.5), 1);
		order.addItem(new Meal("Chef's Special Pasta", 12.0), 2);

		double expectedTotalCost = (1 * 7.5 + 2 * 12.0) * 1.1; // 10% surcharge
		Assertions.assertEquals(expectedTotalCost, manager.applySpecialMealSurcharge(0.0, order));
	}

	@Test
	public void testSpecialOfferDiscount() {
		// Test total cost greater than $100
		double totalCost = 120.0;
		double expectedTotalCost = totalCost - DiningExperienceManager.SPECIAL_OFFER_DISCOUNT_100;
		Assertions.assertEquals(expectedTotalCost, DiningExperienceManager.applySpecialOfferDiscount(totalCost));

		// Test total cost between $50 and $100
		totalCost = 70.0;
		expectedTotalCost = totalCost - DiningExperienceManager.SPECIAL_OFFER_DISCOUNT_50;
		Assertions.assertEquals(expectedTotalCost, DiningExperienceManager.applySpecialOfferDiscount(totalCost));

		// Test total cost less than $50
		totalCost = 40.0;
		expectedTotalCost = totalCost;
		Assertions.assertEquals(expectedTotalCost, DiningExperienceManager.applySpecialOfferDiscount(totalCost));
	}

	@Test
	public void testValidateMealAvailability() {
		// Test a normal order with 1 spaghetti and 1 salad (not available in the menu)
		DiningExperienceManager manager = new DiningExperienceManager();
		Order order = new NormalOrder();
		order.addItem(new Meal("Spaghetti", 8.0), 1);
		order.addItem(new Meal("Salad", 6.0), 1);

		Meal[] menu = manager.getMenu();

		Assertions.assertFalse(manager.validateMealAvailability(order, menu));
	}

	@Test
	public void testValidateQuantities() {
		// Test a normal order with negative quantity
		DiningExperienceManager manager = new DiningExperienceManager();
		Order order = new NormalOrder();
		order.addItem(new Meal("Pizza", 10.0), -1);

		Assertions.assertFalse(manager.validateQuantities(order));

		// Test a normal order with quantity exceeding the maximum order quantity
		order = new NormalOrder();
		order.addItem(new Meal("Pizza", 10.0), DiningExperienceManager.MAX_ORDER_QUANTITY + 1);

		Assertions.assertFalse(manager.validateQuantities(order));

		// Test a normal order with valid quantities
		order = new NormalOrder();
		order.addItem(new Meal("Pizza", 10.0), 2);
		order.addItem(new Meal("Sweet and Sour Chicken", 9.0), 3);

		Assertions.assertTrue(manager.validateQuantities(order));
	}
}
