# Spyro The Dragon - Android App 🐉

### Introducción
Esta aplicación es un tributo a la legendaria saga de **Spyro the Dragon**. El propósito de la aplicación es ofrecer a los usuarios una enciclopedia interactiva donde pueden explorar los personajes icónicos, los mundos mágicos y los coleccionables que definen la franquicia. El proyecto pone un foco especial en la experiencia de usuario a través de una guía interactiva y varios "Easter Eggs" ocultos que premian la curiosidad del jugador.

---

### Características principales

- **Guía Interactiva (Onboarding):** Un sistema de 6 pasos que explica el funcionamiento de la app mediante un contenedor de guía dinámico, bloqueando la navegación principal hasta completar el aprendizaje.
- **Exploración de Secciones:**
    - **Personajes:** Listado detallado de héroes y villanos con descripciones.
    - **Mundos:** Información sobre las tierras de los Dragones, Avalar y los Reinos Olvidados.
    - **Coleccionables:** Seguimiento de los objetos más importantes de la saga.
- **Easter Eggs (Secretos):**
    - **Video Secreto:** Al pulsar tres veces consecutivas sobre un mundo en la pestaña de Mundos, se reproduce un video temático (`videoeasternegg`) a pantalla completa.
    - **Magia de Ripto:** Al realizar una pulsación prolongada sobre Ripto en la lista de personajes, se activa una animación personalizada mediante **Canvas API** (clase `RiptoMagicView`) que simula energía mágica en las coordenadas exactas de la pulsación.
- **Interfaz Animada:** Uso de animaciones de escala, giro y salto (`R.anim.jump`, `R.anim.giro`, etc.) para mejorar el feedback visual.

---

### Tecnologías utilizadas

- **Kotlin:** Lenguaje de programación principal.
- **View Binding:** Para una gestión segura de las vistas del layout.
- **Navigation Component:** Gestión de fragmentos y flujo de navegación mediante `NavController`.
- **Canvas API:** Desarrollo de gráficos y animaciones programáticas para los efectos mágicos.
- **MediaPlayer:** Gestión de efectos de sonido (`spyro_click.mp3`).
- **VideoView:** Reproducción de contenido multimedia dinámico.
- **Material Design:** Implementación de `BottomNavigationView`, `CardViews` y componentes modernos.

---

### Instrucciones de uso

1. **Clonar el repositorio:**
2. **Abrir en Android Studio:**
    - Selecciona "Open" y busca la carpeta `spyro`.
    - Deja que Gradle descargue las dependencias (AndroidX, Navigation, Material).
3. **Ejecutar:**
    - Conecta un dispositivo físico o emulador con API 24 (Android 7.0) o superior.
    - Pulsa el botón `Run`.

---

### Conclusiones del desarrollador

El desarrollo de esta aplicación ha permitido profundizar en aspectos avanzados de Android:
- **Control de UI Dinámica:** Aprender a bloquear e interceptar clics en el `BottomNavigationView` para guiar al usuario.
- **Personalización con Canvas:** La creación de la clase `RiptoMagicView` permitió entender cómo dibujar en pantalla píxel a píxel y animar colores y radios en tiempo real.
- **Arquitectura:** El uso de un único `guideContainer` en la `MainActivity` para gestionar tanto la guía como los Easter Eggs resultó en un código más limpio y eficiente.

---

**Desarrollado por:** Pedro A. Guirado Ranea
**Proyecto:** PMDM - Spyro the Dragon