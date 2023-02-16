package wso2.example.fhir.server.controller;

import ca.uhn.fhir.context.FhirContext;
import org.hl7.fhir.r4.model.CapabilityStatement;
import org.hl7.fhir.r4.model.Enumerations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/fhir/metadata")
public class ConformanceController {

    @GetMapping
    public String getConformanceDocument() {
        FhirContext fhirContext = FhirContext.forR4();
        CapabilityStatement capabilityStatement = new CapabilityStatement();
        capabilityStatement.addFormat("json");
        capabilityStatement.addFormat("xml");
        capabilityStatement.setFhirVersion(Enumerations.FHIRVersion._4_0_1);
        capabilityStatement.setKind(CapabilityStatement.CapabilityStatementKind.INSTANCE);
        capabilityStatement.setName("Hapi FHIR Server");
        capabilityStatement.setStatus(Enumerations.PublicationStatus.DRAFT);
        capabilityStatement.setDate(new Date());
        CapabilityStatement.CapabilityStatementRestComponent rest = new CapabilityStatement.CapabilityStatementRestComponent();
        rest.setMode(CapabilityStatement.RestfulCapabilityMode.SERVER);
        CapabilityStatement.ResourceInteractionComponent searchInteraction = new CapabilityStatement.ResourceInteractionComponent();
        searchInteraction.setCode(CapabilityStatement.TypeRestfulInteraction.SEARCHTYPE);
        CapabilityStatement.ResourceInteractionComponent readInteraction = new CapabilityStatement.ResourceInteractionComponent();
        readInteraction.setCode(CapabilityStatement.TypeRestfulInteraction.READ);
        rest.addResource().setType("Patient").setProfile("http://hl7.org/fhir/StructureDefinition/Patient")
                .addInteraction(searchInteraction).addInteraction(readInteraction);
        capabilityStatement.addRest(rest);
        return fhirContext.newJsonParser().encodeResourceToString(capabilityStatement);
    }
}
