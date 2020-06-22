package com.broadleafsamples.tutorials.domain;

import org.modelmapper.ModelMapper;

import com.broadleafcommerce.customer.provider.jpa.domain.JpaCustomer;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "TUTORIAL_CUSTOMER")
@Data
@EqualsAndHashCode(callSuper = true)
public class ExtendedCustomer extends JpaCustomer {

    @Column(name = "EXTERNAL_SOURCE_ID")
    private String externalSourceId;

    @ManyToMany
    private List<JpaMovie> favoriteJpaMovies;

    @Override
    public ModelMapper toMe() {
        ModelMapper modelMapper = super.toMe();
        modelMapper.createTypeMap(E)
        return modelMapper;
    }
}
