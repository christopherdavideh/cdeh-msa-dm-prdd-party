package com.banking.cdeh_msa_dm_prdd_party.util;

public class LogMessages {
    // Create Customer/Party
    public static final String CREATE_CUSTOMER_REQUEST = "[createCustomerWithParty] Request received: {}";
    public static final String CREATE_CUSTOMER_SUCCESS = "[createCustomerWithParty] Success response: {}";
    public static final String CREATE_CUSTOMER_ERROR = "[createCustomerWithParty] Error creating Customer: {}";
    public static final String CREATE_PARTY_ERROR = "[createCustomerWithParty] Error creating Party: {}";
    public static final String PARTY_SAVED_SUCCESS = "[createCustomerWithParty] Party saved successfully: {}";
    public static final String CUSTOMER_SAVED_SUCCESS = "[createCustomerWithParty] Customer saved successfully: {}";

    // Get All Customers
    public static final String GET_ALL_CUSTOMERS_REQUEST = "[getAllCustomerDTOs] Request received";
    public static final String GET_ALL_CUSTOMERS_SUCCESS = "[getAllCustomerDTOs] Success response: {}";
    public static final String GET_ALL_CUSTOMERS_ERROR = "[getAllCustomerDTOs] Error retrieving Customers: {}";
    public static final String GET_PARTY_FOR_CUSTOMER_ERROR = "[getAllCustomerDTOs] Error retrieving Party for Customer: {}";
    public static final String CUSTOMER_FOUND = "[getAllCustomerDTOs] Customer found: {}";
    public static final String PARTY_FOUND_FOR_CUSTOMER = "[getAllCustomerDTOs] Party found for customer: {}";

    // Get Customer By ID
    public static final String GET_CUSTOMER_BY_ID_REQUEST = "[getCustomerDTOById] Request received for customerId: {}";
    public static final String GET_CUSTOMER_BY_ID_SUCCESS = "[getCustomerDTOById] Success response: {}";
    public static final String GET_CUSTOMER_BY_ID_ERROR = "[getCustomerDTOById] Error retrieving Customer: {}";
    public static final String GET_PARTY_BY_ID_ERROR = "[getCustomerDTOById] Error retrieving Party for Customer: {}";
    public static final String CUSTOMER_BY_ID_FOUND = "[getCustomerDTOById] Customer found: {}";
    public static final String CUSTOMER_BY_ID_NOT_FOUND = "[getCustomerDTOById] Customer not found with id: {}";
    public static final String PARTY_BY_ID_FOUND = "[getCustomerDTOById] Party found: {}";

    // Update Customer/Party
    public static final String UPDATE_CUSTOMER_REQUEST = "[updateCustomerWithParty] Request received for customerId: {} with data: {}";
    public static final String UPDATE_PARTY_SUCCESS = "[updateCustomerWithParty] Party updated successfully: {}";
    public static final String UPDATE_PARTY_ERROR = "[updateCustomerWithParty] Error updating Party: {}";
    public static final String UPDATE_CUSTOMER_SUCCESS = "[updateCustomerWithParty] Success response: {}";
    public static final String UPDATE_CUSTOMER_ERROR = "[updateCustomerWithParty] Error updating Customer: {}";
    public static final String CUSTOMER_UPDATE_FOUND = "[updateCustomerWithParty] Customer found: {}";
    public static final String CUSTOMER_UPDATE_NOT_FOUND = "[updateCustomerWithParty] Customer not found with id: {}";
    public static final String PARTY_UPDATE_FOUND = "[updateCustomerWithParty] Party found: {}";
    public static final String PARTY_UPDATE_ERROR = "[updateCustomerWithParty] Error retrieving Party: {}";
    public static final String CUSTOMER_UPDATED_SUCCESS = "[updateCustomerWithParty] Customer updated successfully: {}";

    // Delete Customer
    public static final String DELETE_CUSTOMER_REQUEST = "[deleteCustomer] Request received for customerId: {}";
    public static final String DELETE_CUSTOMER_SUCCESS = "[deleteCustomer] Customer deleted successfully for customerId: {}";
    public static final String DELETE_CUSTOMER_ERROR = "[deleteCustomer] Error deleting Customer: {}";
    public static final String DELETE_PARTY_SUCCESS = "[deleteCustomer] Party deleted successfully for partyId: {}";
    public static final String DELETE_PARTY_ERROR = "[deleteCustomer] Error deleting Party: {}";

    // Create/Update Party
    public static final String CREATE_PARTY_REQUEST = "[createParty] Request received: {}";
    public static final String CREATE_PARTY_SUCCESS = "[createParty] Party created successfully: {}";

    public static final String GET_PARTY_BY_ID_REQUEST = "[getPartyById] Request received for partyId: {}";
    public static final String GET_PARTY_BY_ID_SUCCESS = "[getPartyById] Party found: {}";
    public static final String PARTY_NOT_FOUND = "[getPartyById] Party not found with id: {}";
    public static final String GET_PARTY_RETRIEVE_ERROR = "[getPartyById] Error retrieving Party: {}";

    public static final String UPDATE_PARTY_REQUEST = "[updateParty] Request received for partyId: {} with name: {}, gender: {}, age: {}, address: {}, phone: {}";
    public static final String UPDATE_PARTY_WITH_ID_REQUEST = "[updateParty] Request received for partyId: {} with name: {}, gender: {}, age: {}, identification: {}, address: {}, phone: {}";
    public static final String PARTY_UPDATE_NOT_FOUND = "[updateParty] Party not found with id: {}";
    public static final String ROWS_UPDATED = "[updateParty] Rows updated: {}";
    public static final String PARTY_UPDATED_SUCCESS = "[updateParty] Party updated successfully: {}";
    public static final String PARTY_UPDATED_NOT_FOUND = "[updateParty] Updated Party not found with id: {}";
    public static final String NO_ROWS_UPDATED = "[updateParty] No rows updated for partyId: {}";
    public static final String PARTY_FOUND_FOR_UPDATE = "[updateParty] Party found for update: {}";
    public static final String PARTY_ID_DUPLICATE_CHECK = "[updateParty] ¿Existe otro Party con la misma identificación?: {}";
    public static final String PARTY_ID_ALREADY_EXISTS = "[updateParty] Identification already exists: {}";

    // General Error Messages
    public static final String ERROR_CREATING_PARTY = "Error creating Party: ";
    public static final String ERROR_CREATING_CUSTOMER = "Error creating Customer: ";
    public static final String ERROR_RETRIEVING_CUSTOMERS = "Error retrieving Customers: ";
    public static final String ERROR_RETRIEVING_PARTY_FOR_CUSTOMER = "Error retrieving Party for Customer: ";
    public static final String ERROR_RETRIEVING_CUSTOMER = "Error retrieving Customer: ";
    public static final String ERROR_UPDATING_PARTY = "Error updating Party: ";
    public static final String ERROR_UPDATING_CUSTOMER = "Error updating Customer: ";
    public static final String ERROR_DELETING_CUSTOMER = "Error deleting Customer: ";
    public static final String ERROR_DELETING_PARTY = "Error deleting Party: ";
    public static final String CUSTOMER_NOT_FOUND = "Customer not found with id: ";
    public static final String PARTY_NOT_FOUND_FOR_CUSTOMER = "Party not found for Customer with id: ";
}
