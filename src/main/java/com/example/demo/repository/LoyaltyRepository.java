package com.example.demo.repository;

import com.example.demo.models.Loyalty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoyaltyRepository extends JpaRepository<Loyalty, String> {
}
