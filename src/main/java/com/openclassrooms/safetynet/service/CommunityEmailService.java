package com.openclassrooms.safetynet.service;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.safetynet.model.DataModel;

/**
 * Service responsible for retrieving email addresses of all residents living in a specified city.
 * This service is used to support the /communityEmail endpoint.
 */
@Service
public class CommunityEmailService {

    private final DataModelService dataModelService;
    private static final Logger logger = LogManager.getLogger(CommunityEmailService.class);

    /**
     * Constructs the CommunityEmailService with the provided DataModelService.
     *
     * @param dataModelService the service that provides access to application data
     */
    @Autowired
    public CommunityEmailService(DataModelService dataModelService) {
        this.dataModelService = dataModelService;
    }

    /**
     * Retrieves the current DataModel instance.
     *
     * @return the current DataModel containing all data
     */
    private DataModel getDataModel() {
        return dataModelService.getDataModel();
    }

    /**
     * Retrieves a list of email addresses of all persons living in the specified city.
     *
     * @param city the name of the city to filter persons by
     * @return a list of email addresses of residents in the given city
     */
    public List<String> getCommunityEmails(String city) {
        logger.debug("Retrieving emails for city: {}", city);

        List<String> result = getDataModel().getPersons().stream()
            .filter(person -> city.equals(person.getCity()))
            .map(person -> person.getEmail())
            .distinct()
            .collect(Collectors.toList());

        logger.debug("{} emails found for city: {}", result.size(), city);

        return result;
    }
}