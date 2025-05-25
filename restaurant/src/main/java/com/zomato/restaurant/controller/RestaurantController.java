package com.zomato.restaurant.controller;

import com.zomato.restaurant.entity.Menu;
import com.zomato.restaurant.entity.Restaurant;
import com.zomato.restaurant.service.RestaurantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/restaurants")
@Tag(name = "Restaurant Management", description = "APIs for restaurant management operations")
public class RestaurantController {

    private final RestaurantService restaurantService;

    @Autowired
    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @Operation(summary = "Create restaurant", description = "Creates a new restaurant with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restaurant created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public ResponseEntity<Restaurant> createRestaurant(@Valid @RequestBody Restaurant restaurant) {
        return ResponseEntity.ok(restaurantService.createRestaurant(restaurant));
    }

    @Operation(summary = "Get restaurant by ID", description = "Retrieves restaurant details by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restaurant found successfully"),
            @ApiResponse(responseCode = "404", description = "Restaurant not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> getRestaurantById(@PathVariable Long id) {
        return ResponseEntity.ok(restaurantService.getRestaurantById(id));
    }

    @Operation(summary = "Get all restaurants", description = "Retrieves a list of all restaurants")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restaurants retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
        return ResponseEntity.ok(restaurantService.getAllRestaurants());
    }

    @Operation(summary = "Update restaurant", description = "Updates an existing restaurant's details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restaurant updated successfully"),
            @ApiResponse(responseCode = "404", description = "Restaurant not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Restaurant> updateRestaurant(@PathVariable Long id, @Valid @RequestBody Restaurant restaurant) {
        return ResponseEntity.ok(restaurantService.updateRestaurant(id, restaurant));
    }

    @Operation(summary = "Delete restaurant", description = "Deletes a restaurant by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Restaurant deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Restaurant not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable Long id) {
        restaurantService.deleteRestaurant(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Search restaurants", description = "Search restaurants by name, cuisine, and location")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Search completed successfully")
    })
    @GetMapping("/search")
    public ResponseEntity<List<Restaurant>> searchRestaurants(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String cuisine,
            @RequestParam(required = false) String location) {
        return ResponseEntity.ok(restaurantService.searchRestaurants(name, cuisine, location));
    }

    @Operation(summary = "Get restaurant menu", description = "Retrieves the menu of a specific restaurant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Menu retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Restaurant or menu not found")
    })
    @GetMapping("/{id}/menu")
    public ResponseEntity<Menu> getRestaurantMenu(@PathVariable Long id) {
        return ResponseEntity.ok(restaurantService.getRestaurantMenu(id));
    }

    @Operation(summary = "Update restaurant menu", description = "Updates the menu of a specific restaurant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Menu updated successfully"),
            @ApiResponse(responseCode = "404", description = "Restaurant not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PutMapping("/{restaurantId}/menu")
    public ResponseEntity<Menu> updateRestaurantMenu(@PathVariable Long restaurantId, @Valid @RequestBody Menu menu) {
        return ResponseEntity.ok(restaurantService.updateRestaurantMenu(restaurantId, menu));
    }

    @PostMapping("/menu")
    public ResponseEntity<Menu> createRestaurantMenu(@Valid @RequestBody Menu menu) {
        return ResponseEntity.ok(restaurantService.createRestaurantMenu(menu));
    }

    @DeleteMapping("/{restaurantId}/menu/{menuId}")
    public ResponseEntity<Void> deleteRestaurantMenu(@PathVariable Long restaurantId, @PathVariable Long menuId) {
        restaurantService.deleteRestaurantMenu(restaurantId, menuId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Restaurant> deactivateRestaurant(@PathVariable Long id) {
        Restaurant restaurant = restaurantService.getRestaurantById(id);
        restaurant.setActive(false);
        return ResponseEntity.ok(restaurantService.updateRestaurant(id, restaurant));
    }
}
