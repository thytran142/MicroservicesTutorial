package com.broadleafsamples.tutorials.domain;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name="TUTORIAL_CUSTOMER_ITEM")
public class CustomerItem {

    @Id
    @GeneratedValue(
            generator = "blcid"
    )
    @GenericGenerator(
            name = "blcid",
            strategy = "blcid"
    )
    @Type(
            type = "com.broadleafcommerce.data.tracking.jpa.hibernate.ULidType"
    )
    @Column(
            name = "ID",
            nullable = false,
            length = 36
    )
    private String id;

    @ManyToOne
    @JoinColumn(name="customer_id", nullable=false)
    private ExtendedCustomer customer;
 
    public CustomerItem() {}
     
}
