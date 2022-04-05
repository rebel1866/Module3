package com.epam.esm.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class DeleteByIdRequest {
    @NotNull(message = "you forgot to enter id") @Positive(message = "id must be positive number")
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
