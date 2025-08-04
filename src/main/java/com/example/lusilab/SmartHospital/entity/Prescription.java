package com.example.lusilab.SmartHospital.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "prescriptions")
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "medication", nullable = false)
    private String medication;

    @Column(nullable = false)
    private String dosage;

    @Column(nullable = false)
    private String instructions;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diagnosis_id", nullable = false)
    private Diagnosis diagnosis;

    public Prescription() {}


    public Prescription(String medication, String dosage, String instructions, Diagnosis diagnosis) {
        this.medication = medication;
        this.dosage = dosage;
        this.instructions = instructions;
        this.diagnosis = diagnosis;
    }


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getMedication() { return medication; }
    public void setMedication(String medication) { this.medication = medication; }
    public String getDosage() { return dosage; }
    public void setDosage(String dosage) { this.dosage = dosage; }
    public String getInstructions() { return instructions; }
    public void setInstructions(String instructions) { this.instructions = instructions; }
    public Diagnosis getDiagnosis() { return diagnosis; }
    public void setDiagnosis(Diagnosis diagnosis) { this.diagnosis = diagnosis; }

    @Override
    public String toString() {
        return "Prescription{" +
                "id=" + id +
                ", medication='" + medication + '\'' +
                ", dosage='" + dosage + '\'' +
                ", instructions='" + instructions + '\'' +
                // Shmangim printimin e objektit të plotë për të parandaluar cikle të pafundme
                ", diagnosisId=" + (diagnosis != null ? diagnosis.getId() : "null") +
                '}';
    }
}