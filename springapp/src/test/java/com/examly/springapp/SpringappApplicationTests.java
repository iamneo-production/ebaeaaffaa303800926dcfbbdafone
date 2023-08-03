package com.examly.springapp;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.examly.springapp.controller.ShoppingCartController;
import com.examly.springapp.model.Product;

import java.io.File;
import java.lang.reflect.Field;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SpringappApplicationTests {

	private ShoppingCartController shoppingCartController;

	private List<Product> cart;

	@Before
	public void setUp() {
		shoppingCartController = new ShoppingCartController();
		cart = new ArrayList<>();
		setField(shoppingCartController, "cart", cart);
	}

	private void setField(Object targetObject, String fieldName, Object fieldValue) {
		try {
			Field field = targetObject.getClass().getDeclaredField(fieldName);
			field.setAccessible(true);
			field.set(targetObject, fieldValue);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testAddToCart_Success() {
		// Given
		Product product = new Product("Product1", 10.99, 2);

		// When
		ResponseEntity<String> response = shoppingCartController.addToCart(product);

		// Then
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals("Product added to the cart successfully.", response.getBody());
		assertEquals(1, cart.size());
		assertEquals(product, cart.get(0));
	}

	@Test
	public void testAddToCart_InvalidRequest() {
		// Given
		Product product = new Product(null, 10.99, 2);

		// When
		ResponseEntity<String> response = shoppingCartController.addToCart(product);

		// Then
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals("Invalid request payload or missing required fields.", response.getBody());
		assertEquals(0, cart.size());
	}

	@Test
	public void testGetTotalPrice_Success() {
		// Given
		cart.add(new Product("Product1", 10.99, 2));
		cart.add(new Product("Product2", 5.49, 3));

		// When
		ResponseEntity<?> response = shoppingCartController.getTotalPrice();

		// Then
		assertEquals(HttpStatus.OK, response.getStatusCode());
		double totalPrice = (double) response.getBody();
		assertEquals(10.99 * 2 + 5.49 * 3, totalPrice, 0.001); // Using delta for floating-point comparison
	}

	@Test
	public void testGetTotalPrice_EmptyCart() {
		// When
		ResponseEntity<?> response = shoppingCartController.getTotalPrice();

		// Then
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertNull(response.getBody());
	}

	@Test
	public void testCheckout_Success() {
		// Given
		cart.add(new Product("Product1", 10.99, 2));
		cart.add(new Product("Product2", 5.49, 3));

		// When
		ResponseEntity<String> response = shoppingCartController.checkout();

		// Then
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Cart checked out successfully.", response.getBody());
		assertEquals(0, cart.size()); // Verify that the cart is cleared
	}

	@Test
	public void testCheckout_EmptyCart() {
		// When
		ResponseEntity<String> response = shoppingCartController.checkout();

		// Then
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertNull(response.getBody()
	@Test

	public void controllerfolder() {
		String directoryPath = "src/main/java/com/examly/springapp/controller"; // Replace with the path to your
																				// directory
		File directory = new File(directoryPath);
		assertTrue(directory.exists() && directory.isDirectory());
	}

	@Test
	public void controllerfile() {
		String filePath = "src/main/java/com/examly/springapp/controller/ShoppingCartController.java";
		File file = new File(filePath);
		assertTrue(file.exists() && file.isFile());
	}

	@Test
	public void testModelFolder() {
		String directoryPath = "src/main/java/com/examly/springapp/model"; // Replace with the path to your directory
		File directory = new File(directoryPath);
		assertTrue(directory.exists() && directory.isDirectory());
	}

	@Test
	public void testModelFile() {
		String filePath = "src/main/java/com/examly/springapp/model/Product.java";
		File file = new File(filePath);
		assertTrue(file.exists() && file.isFile());
	}
}
