-- liquibase formatted sql
-- changeset broadleaf:customer-tutorial-demo

INSERT INTO BLC_PHONE
    (ID, COUNTRY_CODE, PHONE_NUMBER, EXTENSION)
VALUES ('aaaCustomerPhone', '1', '111-111-1111', NULL);
INSERT INTO BLC_PHONE
    (ID, COUNTRY_CODE, PHONE_NUMBER, EXTENSION)
VALUES ('hcCustomerPhone', '1', '222-222-2222', '1234');


INSERT INTO BLC_CUSTOMER
(ID, FULL_NAME, USERNAME, EMAIL, CUSTOMER_PHONE, TRK_TENANT_ID, TRK_CUSTOMER_CONTEXT_ID,
 TRK_ARCHIVED)
VALUES ('AAA_CUSTOMER', 'AAA Customer', 'aaacustomer@customer.com', 'aaacustomer@customer.com',
        'aaaCustomerPhone', '5DF1363059675161A85F576D', '1', 'N');

INSERT INTO BLC_CUSTOMER
(ID, FULL_NAME, USERNAME, EMAIL, CUSTOMER_PHONE, TRK_TENANT_ID, TRK_CUSTOMER_CONTEXT_ID,
 TRK_ARCHIVED)
VALUES ('HEATCLINIC_CUSTOMER', 'Heat Clinic Customer', 'heatcliniccustomer@customer.com',
        'heatcliniccustomer@customer.com', 'hcCustomerPhone', '5DF1363059675161A85F576D', '2', 'N');

INSERT INTO tutorial_customer(ID,
                       EXTERNAL_SOURCE_ID)
VALUES ('HEATCLINIC_CUSTOMER', 'abcdefghi');
