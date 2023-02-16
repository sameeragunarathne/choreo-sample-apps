package wso2.example.byoc.controller;

import ca.uhn.fhir.context.FhirContext;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Patient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/fhir/r4/Patient")
public class PatientController {

    private Map<String, Patient> patientMap = new HashMap<>();

    @GetMapping
    public String findAllPatients() {
        if (patientMap.isEmpty()) {
            loadDummyPatients();
        }
        FhirContext fhirContext = FhirContext.forR4();
        Bundle bundle = new Bundle();
        bundle.addEntry().setResource(new Patient());
        for (String patientId : patientMap.keySet()) {
            bundle.addEntry().setResource(patientMap.get(patientId));
        }
        return fhirContext.newJsonParser().encodeResourceToString(bundle);
    }

    @GetMapping("{id}")
    public String findPatientById(@PathVariable int id) {
        if (patientMap.isEmpty()) {
            loadDummyPatients();
        }
        FhirContext fhirContext = FhirContext.forR4();
        Patient patient = patientMap.get(Integer.toString(id));
        if (patient != null) {
            return fhirContext.newJsonParser().encodeResourceToString(patient);
        }
        return null;
    }

    private void loadDummyPatients() {

        Patient patient = new Patient();
        patient.setId("1");
        patient.addIdentifier().setSystem("http://optum.com/MRNs").setValue("007");
        patient.addName().setFamily("Chakravarty").addGiven("Mithun").addGiven("A");
        patient.addAddress().addLine("Address Line 1");
        patient.addAddress().setCity("Mumbai");
        patient.addAddress().setCountry("India");
        patient.addTelecom().setValue("111-111-1111");
        this.patientMap.put("1", patient);

        for (int i = 2; i < 5; i++) {
            patient = new Patient();
            patient.setId(Integer.toString(i));
            patient.addIdentifier().setSystem("http://optum.com/MRNs").setValue("007" + i);
            patient.addName().setFamily("Bond" + i).addGiven("James").addGiven("J");
            patient.addAddress().addLine("House Line " + i);
            patient.addAddress().setCity("Your City");
            patient.addAddress().setCountry("USA");
            this.patientMap.put(Integer.toString(i), patient);
        }
    }
}
