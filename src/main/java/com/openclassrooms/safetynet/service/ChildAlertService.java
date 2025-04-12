package com.openclassrooms.safetynet.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.safetynet.DTO.PersonIdentityDTO;
import com.openclassrooms.safetynet.DTO.childAlert.ChildAlertResponseDTO;
import com.openclassrooms.safetynet.DTO.childAlert.ChildDTO;
import com.openclassrooms.safetynet.model.DataModel;

/**
 * Service responsible for handling logic related to the /childAlert endpoint.
 * It provides functionalities to retrieve children and their family members at a given address.
 */
@Service
public class ChildAlertService {

    private final DataModelService dataModelService;
    private static final Logger logger = LogManager.getLogger(ChildAlertService.class);
    
    /**
     * Constructs the service with injected DataModelService.
     *
     * @param dataModelService the data model service providing access to application data
     */
    @Autowired
    public ChildAlertService(DataModelService dataModelService) {
        this.dataModelService = dataModelService;
    }

    /**
     * Retrieves the internal data model from the service.
     *
     * @return the current DataModel
     */
    private DataModel getDataModel() {
        return dataModelService.getDataModel();
    }

    /**
     * Returns all persons living at a given address.
     *
     * @param address the address to search
     * @return list of PersonIdentityDTO representing the persons at the address
     */
    public List<PersonIdentityDTO> getPersonsAtAddress(String address) {
        logger.debug("Retrieving persons at address: {}", address);
        
        List<PersonIdentityDTO> personsAtAddress = getDataModel().getPersons().stream()
                .filter(person -> address.equals(person.getAddress()))
                .map(person -> new PersonIdentityDTO(person.getFirstName(), person.getLastName()))
                .collect(Collectors.toList());

        logger.debug("{} persons found at address: {}", personsAtAddress.size(), address);
        return personsAtAddress;
    }

    /**
     * Returns children (<18 years old) living at the given address, along with other household members.
     *
     * @param address the address to search
     * @return a ChildAlertResponseDTO containing the list of children and other family members
     */
    public ChildAlertResponseDTO getChildrenAtAddress(String address) {
        logger.debug("Retrieving children and family members at address: {}", address);

        List<PersonIdentityDTO> personsAtAddress = getPersonsAtAddress(address);
        List<ChildDTO> children = new ArrayList<>();
        List<PersonIdentityDTO> otherFamilyMembers = new ArrayList<>();

        for (PersonIdentityDTO person : personsAtAddress) {
            int age = ServiceUtils.getAge(person.getFirstName(), person.getLastName(), getDataModel().getMedicalrecords());
            if (age < 18) {
                children.add(new ChildDTO(person.getFirstName(), person.getLastName(), String.valueOf(age)));
            } else {
                otherFamilyMembers.add(new PersonIdentityDTO(person.getFirstName(), person.getLastName()));
            }
        }

        if (children.isEmpty()) {
            return new ChildAlertResponseDTO(new ArrayList<>(), new ArrayList<>());
        }

        logger.debug("Child retrieval completed: {} children and {} other family members found.", children.size(), otherFamilyMembers.size());
        return new ChildAlertResponseDTO(children, otherFamilyMembers);
    }
}