package org.example.task;


public class Task1 {
    public static void main(String[] args) {
        String query= """
                 SELECT
                         relative_details.familyName,
                         relative_details.givenName,
                         relative_details.middleName,
                         relative_details.birthDate,
                         relative.contactRelationship
                     FROM HPRelatedPersonSysId as relative
                     JOIN HPPersonGeneric as relative_details on relative.HPRelatedPersonSysId=relative_details.sysId
                     JOIN HPPersonGeneric employee_details on relative.HPPersonGenericSysId=employee_details.sysId
                     WHERE
                        employee_details.personId='test'
                """;
    }
}
