-- liquibase formatted sql
-- changeset broadleaf:sandbox-tutorial-demo

INSERT INTO BLC_SANDBOX (ID, NAME, DESCRIPTION, COLOR, APPLICATION, TRK_ARCHIVED, TRK_TENANT_ID)
VALUES ('default_global', 'Default Sandbox', 'The default, general use sandbox.', '#404040', NULL, 'N',
        '5DF1363059675161A85F576D');

INSERT INTO BLC_SANDBOX (ID, NAME, DESCRIPTION, COLOR, APPLICATION, TRK_ARCHIVED, TRK_TENANT_ID)
VALUES ('default_tutorial', 'Default Sandbox', 'The default, general use sandbox.', '#404040', '1', 'N',
        '5DF1363059675161A85F576D');