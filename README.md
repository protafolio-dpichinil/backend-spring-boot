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

## Git SSH vs HTTPS (evitar solicitar usuario/contraseña)

Si tu entorno te pide `username/password` al intentar push/commit, probablemente el `remote.origin.url` está configurado usando HTTPS en lugar de SSH.

Verifica el remote:

```bash
git remote -v
# si muestra https://... cambia a SSH con:
git remote set-url origin git@github.com:protafolio-dpichinil/backend-spring-boot.git
```

Si no tienes una clave SSH en este entorno crea una nueva y añádela a tu cuenta GitHub:

```bash
# genero un par ed25519 (sin passphrase en el contenedor)
ssh-keygen -t ed25519 -f ~/.ssh/id_ed25519 -C "you@example.com"
# imprime la pública para copiar y añadir en https://github.com/settings/keys
cat ~/.ssh/id_ed25519.pub
# añade la clave al agente (opcional)
eval "$(ssh-agent -s)"
ssh-add ~/.ssh/id_ed25519
# probar conexión
ssh -T git@github.com
```

He incluido un pequeño script para automatizar esto en el contenedor: `scripts/generate-ssh-key-and-test.sh`
