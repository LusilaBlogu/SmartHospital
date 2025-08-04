package com.example.lusilab.SmartHospital.configuration;

import com.example.lusilab.SmartHospital.entity.Doctor;
import com.example.lusilab.SmartHospital.entity.Nurse;
import com.example.lusilab.SmartHospital.repository.DoctorRepository;
import com.example.lusilab.SmartHospital.repository.NurseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private NurseRepository nurseRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<Doctor> doctorOptional = doctorRepository.findByEmail(email);


        if (doctorOptional.isPresent()) {
            Doctor doctor = doctorOptional.get();
            return new CustomUserDetails(
                    doctor.getEmail(),
                    doctor.getPassword(),
                    "ROLE_Doctor"
            );
        }

        Optional<Nurse> nurse = nurseRepository.findByEmail(email);

        if ( nurse.isPresent() ) {
            Nurse nurse1 = nurse.get();

            return new CustomUserDetails(nurse1.getEmail(), nurse1.getPassword(), "Nurse");
        }

        throw new UsernameNotFoundException("User not found");
    }
}



