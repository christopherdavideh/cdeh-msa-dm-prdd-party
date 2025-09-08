package com.banking.cdeh_msa_dm_prdd_party.util;

public class LogMessages {
    public static final String CREATE_CUSTOMER_REQUEST = "[createCustomerWithParty] Request received: {}";
    public static final String CREATE_CUSTOMER_SUCCESS = "[createCustomerWithParty] Success response: {}";
    public static final String CREATE_CUSTOMER_ERROR = "[createCustomerWithParty] Error creating Customer: {}";
    public static final String CREATE_PARTY_ERROR = "[createCustomerWithParty] Error creating Party: {}";

    public static final String GET_ALL_CUSTOMERS_REQUEST = "[getAllCustomerDTOs] Request received";
    public static final String GET_ALL_CUSTOMERS_SUCCESS = "[getAllCustomerDTOs] Success response: {}";
    public static final String GET_ALL_CUSTOMERS_ERROR = "[getAllCustomerDTOs] Error retrieving Customers: {}";
    public static final String GET_PARTY_FOR_CUSTOMER_ERROR = "[getAllCustomerDTOs] Error retrieving Party for Customer: {}";

    public static final String GET_CUSTOMER_BY_ID_REQUEST = "[getCustomerDTOById] Request received for customerId: {}";
    public static final String GET_CUSTOMER_BY_ID_SUCCESS = "[getCustomerDTOById] Success response: {}";
    public static final String GET_CUSTOMER_BY_ID_ERROR = "[getCustomerDTOById] Error retrieving Customer: {}";
    public static final String GET_PARTY_BY_ID_ERROR = "[getCustomerDTOById] Error retrieving Party for Customer: {}";

    public static final String UPDATE_CUSTOMER_REQUEST = "[updateCustomerWithParty] Request received for customerId: {} with data: {}";
    public static final String UPDATE_PARTY_SUCCESS = "[updateCustomerWithParty] Party updated successfully: {}";
    public static final String UPDATE_PARTY_ERROR = "[updateCustomerWithParty] Error updating Party: {}";
    public static final String UPDATE_CUSTOMER_SUCCESS = "[updateCustomerWithParty] Success response: {}";
    public static final String UPDATE_CUSTOMER_ERROR = "[updateCustomerWithParty] Error updating Customer: {}";

    public static final String DELETE_CUSTOMER_REQUEST = "[deleteCustomer] Request received for customerId: {}";
    public static final String DELETE_CUSTOMER_SUCCESS = "[deleteCustomer] Customer deleted successfully for customerId: {}";
    public static final String DELETE_CUSTOMER_ERROR = "[deleteCustomer] Error deleting Customer: {}";
    public static final String DELETE_PARTY_SUCCESS = "[deleteCustomer] Party deleted successfully for partyId: {}";
    public static final String DELETE_PARTY_ERROR = "[deleteCustomer] Error deleting Party: {}";

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

