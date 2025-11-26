📌 Objetivo

Explicar por qué no puedes hacer push desde dentro del devcontainer y cómo exponer tu configuración global de Git y acceso SSH de forma segura.

🔐 Resumen rápido (recomendado)

- No copies tu clave privada al contenedor.
- Usa el "ssh-agent" del host y móntalo en el contenedor; así el contenedor usa la clave cargada en el host sin exponerla.
- Monta también `~/.gitconfig` si quieres que `git` en el contenedor use tu nombre/email/aliases.

Qué hemos configurado en `.devcontainer/devcontainer.json` (cambios realizados)

- Montamos `${localEnv:HOME}/.gitconfig` en `/home/vscode/.gitconfig` para que el contenedor tenga tu configuración global de git.
- Montamos `${localEnv:HOME}/.ssh/known_hosts` en `/home/vscode/.ssh/known_hosts` para evitar prompts de verificación de host.
- Montamos `${localEnv:SSH_AUTH_SOCK}` en `/ssh-agent` y configuramos `SSH_AUTH_SOCK=/ssh-agent` dentro del contenedor para que el cliente SSH del contenedor use el agente del host.
- Creamos `~/.ssh` con permisos 700 en `postCreateCommand`.

Comprobar desde el host antes de abrir el contenedor

1) Verifica que el agente esté corriendo y tu clave esté cargada:

```bash
# debe listar tu(s) clave(s)
ssh-add -l
```

2) Si no ves claves, añade la tuya:

```bash
# inicia agente si es necesario y añade tu clave
eval "$(ssh-agent -s)"
ssh-add ~/.ssh/id_rsa
```

Prueba desde dentro del contenedor

1) Abre el shell en el devcontainer (VS Code -> Rebuild/Reopen in Container si es necesario).
2) Ejecuta:

```bash
# comprobar que SSH_AUTH_SOCK está presente
echo "$SSH_AUTH_SOCK"
ls -l $SSH_AUTH_SOCK || true

# listar claves disponibles según el agente (debería listar las claves cargadas en el host)
ssh-add -l

# probar conexión con GitHub (para GitLab/Gitea modifica el host)
ssh -T git@github.com

# comprobar configuración git
git config --list --show-origin
```

Si `ssh-add -l` devuelve claves y `ssh -T git@github.com` te responde correctamente, `git push` por SSH debería funcionar.

Alternativa (NO recomendada): copiar la clave privada al contenedor

- Si aún quieres hacerlo (no recomendado por seguridad), puedes montar la clave privada específica en el contenedor añadiendo un `mount` como:

```json
"mounts": [
  "source=${localEnv:HOME}/.ssh/id_rsa,target=/home/vscode/.ssh/id_rsa,type=bind" 
]
```

- Y asegurarte de que el archivo tiene permisos 600 y que la propiedad pertenece al usuario `vscode`:

```bash
# dentro del contenedor
chmod 600 ~/.ssh/id_rsa
chown vscode:vscode ~/.ssh/id_rsa
```

⚠️ Advertencia: Nunca subas, compartas ni registres el contenido de tu clave privada. Montarla en un contenedor implica que otros procesos con acceso al contenedor podrían usar tu clave.

Notas finales y troubleshooting

- Si la variable `${localEnv:SSH_AUTH_SOCK}` no está definida en tu host, la montura fallará; asegúrate antes de abrir el contenedor de que tu agente esté activo.
- Si usas WSL/Windows o plataformas con rutas de socket especiales, la ruta de `SSH_AUTH_SOCK` puede requerir ajustes.
- Para aplicar cambios en `.devcontainer/devcontainer.json`, usa "Rebuild Container" o "Reopen in Container" desde VS Code.

Si quieres, puedo:

- añadir una opción (no recomendada por seguridad) para montar tu clave privada en `.devcontainer/devcontainer.json` para pruebas locales rápidas, o
- ayudarte a verificar y depurar la conexión SSH desde dentro del contenedor si compartes los errores concretos que obtienes al hacer `git push`.
