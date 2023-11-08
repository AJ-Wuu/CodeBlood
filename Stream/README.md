# Stream
* Always try to use Stream instead of Loop and Condition
```java
// use
maybeTicket
    .filter(ticket -> !CollectionUtils.isEmpty(ticket.getSlaCollection()))
    .ifPresent(ticket ->
        ticket.getSlaCollection()
               .forEach(sla -> SlaValidators.validateSlaEntity(sla, "Failed to get sla: ")));

// instead of
if (maybeTicket.isPresent() && !CollectionUtils.isEmpty(maybeTicket.get().getSlaCollection())) {
    List<Sla> slaCollection = maybeTicket.get().getSlaCollection();
    for (Sla sla : slaCollection) {
        SlaValidators.validateSlaEntity(sla, "Failed to get sla: ");
    }
}
```
```java
// use
IntStream.range(0, LIMIT)
         .mapToObj(i ->
                        ProjectSummary.builder()
                                .externalId(EXTERNAL_ID + i)
                                .projectKey(KEY)
                                .displayName(DISPLAY_NAME)
                                .lifecycle(LifecycleState.ACTIVE)
                                .build())
         .collect(Collectors.toList());

// instead of
List<ProjectSummary> projectSummaries = new ArrayList<>();
for (int i = 0; i < LIMIT; i++) {
    projectSummaries.add(
            ProjectSummary.builder()
                    .externalId(EXTERNAL_ID + i)
                    .projectKey(KEY)
                    .displayName(DISPLAY_NAME)
                    .lifecycle(LifecycleState.ACTIVE)
                    .build());
}
```

