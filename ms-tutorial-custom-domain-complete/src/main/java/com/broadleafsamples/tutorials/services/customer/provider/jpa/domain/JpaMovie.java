package com.broadleafsamples.tutorials.services.customer.provider.jpa.domain;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.broadleafcommerce.data.tracking.core.CustomerContextTrackable;
import com.broadleafcommerce.data.tracking.core.mapping.FilterAndSortAlias;
import com.broadleafcommerce.data.tracking.jpa.UlidConverter;
import com.broadleafcommerce.data.tracking.jpa.filtering.TrackingListener;
import com.broadleafcommerce.data.tracking.jpa.filtering.domain.CustomerContextJpaTracking;

import java.io.Serializable;
import java.util.Optional;

import javax.persistence.*;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "TUTORIAL_MOVIE")
@Data
@EqualsAndHashCode(exclude = "_id")
@EntityListeners(TrackingListener.class)
public class JpaMovie implements Serializable, CustomerContextTrackable<CustomerContextJpaTracking> {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "blcid")
    @GenericGenerator(name = "blcid", strategy = "blcid")
    @Type(type = "com.broadleafcommerce.data.tracking.jpa.hibernate.ULidType")
    @Column(name = "ID", nullable = false)
    @SuppressWarnings("squid:S00116")
    private String _id;

    @Column(name = "CONTEXT_ID")
    @Convert(converter = UlidConverter.class)
    @FilterAndSortAlias("id")
    private String contextId;

    @Embedded
    private CustomerContextJpaTracking tracking;

    @Column(name = "TITLE")
    private String title;

    @Transient
    private String name;

    @PostLoad
    public void postLoad() {
        this.name = this.getTitle();
    }

    @Override
    public Optional<String> getDisplay() {
        return Optional.ofNullable(getTitle());
    }
}
