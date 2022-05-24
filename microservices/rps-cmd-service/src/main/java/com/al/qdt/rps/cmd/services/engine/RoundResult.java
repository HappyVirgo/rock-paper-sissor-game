package com.al.qdt.rps.cmd.services.engine;

import com.al.qdt.common.enums.Hand;
import com.al.qdt.common.enums.Player;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RoundResult {
    Hand machineChoice;
    Player winner;
}
