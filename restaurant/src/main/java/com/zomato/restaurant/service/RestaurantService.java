package com.zomato.restaurant.service;

import com.zomato.restaurant.entity.Menu;
import com.zomato.restaurant.entity.Restaurant;
import com.zomato.restaurant.exception.ResourceNotFoundException;
import com.zomato.restaurant.repository.RestaurantRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;

    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public Restaurant createRestaurant(@Valid Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    public Restaurant getRestaurantById(Long id) {
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + id));
    }

    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    public Restaurant updateRestaurant(Long id, @Valid Restaurant restaurant) {
        Restaurant existingRestaurant = getRestaurantById(id);
        if(!existingRestaurant.isActive()){
            throw new ResourceNotFoundException("Cannot update inactive restaurant with id: " + id);
        }


        if (restaurant.getName() != null) {
            existingRestaurant.setName(restaurant.getName());
        }
        if (restaurant.getCuisine() != null) {
            existingRestaurant.setCuisine(restaurant.getCuisine());
        }
        if (restaurant.getAddress() != null) {
            existingRestaurant.setAddress(restaurant.getAddress());
        }
        if (restaurant.getPhoneNumber() != null) {
            existingRestaurant.setPhoneNumber(restaurant.getPhoneNumber());
        }
        if (restaurant.getEmail() != null) {
            existingRestaurant.setEmail(restaurant.getEmail());
        }
        existingRestaurant.setActive(restaurant.isActive());

        return restaurantRepository.save(existingRestaurant);
    }

    public void deleteRestaurant(Long id) {
        Restaurant restaurant = getRestaurantById(id);
        restaurantRepository.delete(restaurant);
    }

    public List<Restaurant> searchRestaurants(String name, String cuisine, String city) {
        System.out.println("Searching for restaurants with name: " + name + ", cuisine: " + cuisine + ", city: " + city);
        return restaurantRepository.findRestaurantByNameOrCuisine(name, cuisine);
    }

    public Menu getRestaurantMenu(Long id) {
        List<Menu> menu = restaurantRepository.findMenuByRestaurantId(id);
        if (menu.isEmpty()) {
            throw new ResourceNotFoundException("Menu not found for restaurant id: " + id);
        }
        return menu.get(0);
    }

    public Menu updateRestaurantMenu(Long id, @Valid Menu menu) {
        if (menu.getRestaurant() == null) {
            throw new ResourceNotFoundException("Restaurant must be provided for the menu item.");
        }

        Restaurant restaurant = getRestaurantById(id);
        menu.setRestaurant(restaurant);
        return restaurantRepository.save(restaurant).getMenuItems().stream().filter(m -> m.getId().equals(menu.getId())).findFirst().orElseThrow(() -> new ResourceNotFoundException("Menu item not found for restaurant id: " + id));
    }

    public Menu createRestaurantMenu(@Valid Menu menu) {

        if (menu.getRestaurant() == null || menu.getRestaurant().getId() == null) {
            throw new ResourceNotFoundException("Restaurant ID must be provided for the menu item.");
        }

        Restaurant restaurant = restaurantRepository.findById(menu.getRestaurant().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + menu.getRestaurant().getId()));

        menu.setRestaurant(restaurant);
        restaurant.getMenuItems().add(menu);
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);

        return savedRestaurant.getMenuItems().stream()
                .filter(m -> m.getItemName().equals(menu.getItemName()))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Menu item not saved properly"));
    }

    public void deleteRestaurantMenu(Long restaurantId, Long menuId) {
        Restaurant restaurant = getRestaurantById(restaurantId);
        Menu menuToDelete = restaurant.getMenuItems().stream()
                .filter(menu -> menu.getId().equals(menuId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Menu item not found with id: " + menuId));

        restaurant.getMenuItems().remove(menuToDelete);
        restaurantRepository.save(restaurant);
    }
}
