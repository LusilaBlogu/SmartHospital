package com.example.lusilab.SmartHospital.service;

import com.example.lusilab.SmartHospital.entity.Admin;
import com.example.lusilab.SmartHospital.repository.AdminRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminService {

    private final AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }


    public void saveAdmin(Admin admin) {
        adminRepository.save(admin);
    }


    public Optional<Admin> login(String email, String password) {
        return adminRepository.findByEmail(email)
                .filter(admin -> admin.getPassword().equals(password));
    }

    public boolean existsByEmail(String email) {
        return adminRepository.findByEmail(email).isPresent();
    }


}