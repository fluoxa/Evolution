cd target
start cmd /k "java -jar evolution-1.0-SNAPSHOT.jar --spring.profiles.active=constMutationMixedParentSelection"
start cmd /k "java -jar evolution-1.0-SNAPSHOT.jar --spring.profiles.active=ageBasedIncreasingMutationMixedParentSelection"
start cmd /k "java -jar evolution-1.0-SNAPSHOT.jar --spring.profiles.active=ageBasedDecreasingMutationMixedParentSelection"