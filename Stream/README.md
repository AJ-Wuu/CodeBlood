# Stream
* Once created, the stream instance will not modify its source, therefore allowing the creation of multiple instances from a single source
* Always try to use Stream instead of Loop and Condition for readability
```java
// build map from list
// `Function.identity()` is the same as lambda `t -> t` or simply `t`
// use `.distinct()` to avoid duplicates
Map<Integer, User> map = users.stream().collect(Collectors.toMap(User::getId, Function.identity()));
Map<String, Project> projectMap = projectKeys.stream().collect(Collectors.toMap(Function.identity(), projectService::getProjectByKey));

// get part of an object
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

// get a list from a range of index
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

