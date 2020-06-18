package com.broadleafsamples.tutorials.domain;

import com.broadleafcommerce.customer.provider.jpa.domain.JpaCustomer;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "TUTORIAL_CUSTOMER")
@Data
@EqualsAndHashCode(callSuper = true)
public class ExtendedCustomer extends JpaCustomer {

    @Column(name = "EXTERNAL_SOURCE_ID")
    private String externalSourceId;

    @OneToMany(mappedBy="customer")
    private Set<CustomerItem> items;
}
