package com.al.qdt.score.qry.domain;

import com.al.qdt.common.base.BaseEntity;
import com.al.qdt.common.enums.Player;
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
@Table(name = "score", indexes = {@Index(name = "idx_winner", columnList = "winner")})
public class Score extends BaseEntity {
    public static final String WINNER_MUST_NOT_BE_NULL = "Winner must not be null";

    @NotNull(message = WINNER_MUST_NOT_BE_NULL)
    @Enumerated(STRING)
    @Column(name = "winner")
    private Player winner;

    @Override
    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Score)) return false;
        final var other = (Score) o;
        if (!other.canEqual(this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if(this$id == null && other$id == null) return false;
        return Objects.equals(this$id, other$id);
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Score;
    }

    @Override
    public int hashCode() {
        final var PRIME = 31;
        var result = 1;
        final var $id = super.getId();
        return result * PRIME + (($id == null) ? 0 : $id.hashCode());
    }
}
