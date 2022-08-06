package com.al.qdt.common.base;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.domain.Persistable;

import java.time.OffsetDateTime;

@Getter
@Setter
public abstract class
BaseDocument implements Persistable<String> {

    @Id
    protected String id;

    @Version
    protected Integer version;

    @CreatedDate
    protected OffsetDateTime created;
}
