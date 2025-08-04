package com.example.lusilab.SmartHospital.service;

import com.example.lusilab.SmartHospital.entity.Admin;
import com.example.lusilab.SmartHospital.entity.Doctor;
import com.example.lusilab.SmartHospital.entity.Nurse;
import com.example.lusilab.SmartHospital.entity.Patient;
import com.example.lusilab.SmartHospital.exception.ResourceNotFoundException;
import com.example.lusilab.SmartHospital.repository.AdminRepository;
import com.example.lusilab.SmartHospital.repository.DoctorRepository;
import com.example.lusilab.SmartHospital.repository.NurseRepository;
import com.example.lusilab.SmartHospital.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    private final DoctorRepository doctorRepository;
    private final NurseRepository nurseRepository;
    private final PatientRepository patientRepository;
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminService(DoctorRepository doctorRepository,
                        NurseRepository nurseRepository,
                        PatientRepository patientRepository,
                        AdminRepository adminRepository,
                        PasswordEncoder passwordEncoder) {
        this.doctorRepository = doctorRepository;
        this.nurseRepository = nurseRepository;
        this.patientRepository = patientRepository;
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public Admin saveAdmin(Admin admin) {
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        return adminRepository.save(admin);
    }


    public Optional<Admin> login(String email, String password) {
        Optional<Admin> adminOptional = adminRepository.findByEmail(email);

        if (adminOptional.isPresent() && passwordEncoder.matches(password, adminOptional.get().getPassword())) {
            return adminOptional;
        }

        return Optional.empty();
    }

    public boolean existsByEmail(String email) {
        return adminRepository.existsByEmail(email);
    }



    public Doctor createDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    public Doctor updateDoctor(Long id, Doctor updatedDoctorData) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doktori me ID " + id + " nuk u gjet."));

        doctor.setFirstName(updatedDoctorData.getFirstName());
        doctor.setLastName(updatedDoctorData.getLastName());
        doctor.setEmail(updatedDoctorData.getEmail());
        doctor.setDoctorRole(updatedDoctorData.getDoctorRole());

        return doctorRepository.save(doctor);
    }

    public void deleteDoctor(Long id) {
        if (!doctorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Nuk mund të fshihet. Doktori me ID " + id + " nuk u gjet.");
        }
        doctorRepository.deleteById(id);
    }

    public Nurse createNurse(Nurse nurse) {
        return nurseRepository.save(nurse);
    }

    public List<Nurse> getAllNurses() {
        return nurseRepository.findAll();
    }

    public Nurse updateNurse(Long id, Nurse updatedNurseData) {
        Nurse nurse = nurseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Infermierja me ID " + id + " nuk u gjet."));

        nurse.setFirstName(updatedNurseData.getFirstName());
        nurse.setLastName(updatedNurseData.getLastName());
        nurse.setEmail(updatedNurseData.getEmail());

        return nurseRepository.save(nurse);
    }

    public void deleteNurse(Long id) {
        if (!nurseRepository.existsById(id)) {
            throw new ResourceNotFoundException("Nuk mund të fshihet. Infermierja me ID " + id + " nuk u gjet.");
        }
        nurseRepository.deleteById(id);
    }


    public Patient createPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Patient updatePatient(Long id, Patient updatedPatientData) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pacienti me ID " + id + " nuk u gjet."));

        patient.setFirstName(updatedPatientData.getFirstName());
        patient.setLastName(updatedPatientData.getLastName());
        patient.setEmail(updatedPatientData.getEmail());
        patient.setBirthDate(updatedPatientData.getBirthDate());
        patient.setGender(updatedPatientData.getGender());

        return patientRepository.save(patient);
    }

    public void deletePatient(Long id) {
        if (!patientRepository.existsById(id)) {
            throw new ResourceNotFoundException("Nuk mund të fshihet. Pacienti me ID " + id + " nuk u gjet.");
        }
        patientRepository.deleteById(id);
    }
}