package org.example.domain.ext;

import lombok.Data;

import java.time.Instant;

/**
 * @date : 2021/8/9
 */
@Data
public class Metadata {

    private UpcomingOccurrence upcomingOccurrence;

    private EventAlternativeHost alternativeHost;

    @Data
    public static class UpcomingOccurrence {
        private String occurrenceId;
        private Instant startTime;
        private Long duration;
    }

    @Data
    public static class EventAlternativeHost {
        private String name;
        private String emailAddress;
    }
}
