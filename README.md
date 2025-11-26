# backend-spring-boot

This repository has been updated to target Java 21 (LTS). The dev container has also been updated to a Java 21 image
(Temurin 21.0.9) so the VS Code Remote - Containers experience uses the correct runtime.

If you are using the dev container, rebuild it after pulling the latest changes so the new image is used:

```bash
# Rebuild the devcontainer from the VS Code command palette: "Remote-Containers: Rebuild Container"
# OR from the terminal (if you use the devcontainer CLI):
devcontainer build --workspace-folder . --workspace-mount-workspace true
```

For manual local testing/builds, set JAVA_HOME to your JDK 21 installation (or use the included wrapper and SDKMAN):

```bash
export JAVA_HOME=/usr/local/sdkman/candidates/java/21.0.9-tem
export PATH="$JAVA_HOME/bin:$PATH"
./mvnw -v
./mvnw clean test
```