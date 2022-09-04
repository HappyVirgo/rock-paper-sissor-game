package com.al.qdt.rps.qry.domain.entities;

import com.al.qdt.common.domain.base.BaseEntity;
import com.al.qdt.common.enums.Hand;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

import static javax.persistence.EnumType.STRING;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "game", indexes = {@Index(name = "idx_username", columnList = "username")})
public class Game extends BaseEntity {
    public static final String USERNAME_MUST_NOT_BE_BLANK = "Username must not be null or empty";
    public static final String HAND_MUST_NOT_BE_NULL = "Hand must not be null";

    @NotBlank(message = USERNAME_MUST_NOT_BE_BLANK)
    @Column(name = "username")
    private String username;

    @NotNull(message = HAND_MUST_NOT_BE_NULL)
    @Enumerated(STRING)
    @Column(name = "hand")
    private Hand hand;

    @Override
    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Game)) return false;
        final var other = (Game) o;
        if (!other.canEqual(this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if(this$id == null && other$id == null) return false;
        return Objects.equals(this$id, other$id);
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Game;
    }

    @Override
    public int hashCode() {
        final var PRIME = 31;
        var result = 1;
        final var $id = super.getId();
        return result * PRIME + (($id == null) ? 0 : $id.hashCode());
    }
}
