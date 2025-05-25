package com.zomato.user.dto;

import com.zomato.user.dto.AddressDTO;

import java.util.Objects;

public class UserProfileDTO {

    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private AddressDTO activeAddress;

    public UserProfileDTO() {
    }

    public UserProfileDTO(Long id, String name, String email, String phoneNumber, AddressDTO activeAddress) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.activeAddress = activeAddress;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public AddressDTO getActiveAddress() {
        return activeAddress;
    }

    public void setActiveAddress(AddressDTO activeAddress) {
        this.activeAddress = activeAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserProfileDTO that = (UserProfileDTO) o;
        return Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    @Override
    public String toString() {
        return "UserProfileDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", activeAddress=" + activeAddress +
                '}';
    }
}
