package com.zomato.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.zomato.restaurant.entity.Restaurant;
import com.zomato.restaurant.entity.Menu;
import com.zomato.restaurant.entity.Address;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {


    List<Restaurant> findRestaurantByNameOrCuisine(String name,
                                        String cuisine);

    @Query("SELECT r.menuItems FROM Restaurant r WHERE r.id = :restaurantId")
    List<Menu> findMenuByRestaurantId(@Param("restaurantId") Long restaurantId);
}
