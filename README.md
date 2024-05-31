# Frogmi Store

### Requerimiento
Desarrollar una aplicación móvil en Android que permita obtener de un datasource remoto la lista de Stores.
<div align="center">
<img width="773" alt="Screenshot 2024-05-30 at 10 46 21 a m" src="https://github.com/mash6699/FrogmiTest/assets/11529233/86aaffd6-f3ec-4126-b724-308b0aa57ec8">
</div>

## Tareas principales
- [ ] El Código debe estar hecho en Kotlin.
- [ ] Una UI simple que despliegue la información arriba descrita en cada celda.
- [ ] Layout de la app (code), organización de las clases, class naming, convenciones, etc.
- [ ] Manejo de HTTP errors (4XX, 5XX).
- [ ] Implementar pruebas unitarias

## Extras
- [ ] Implementa una paginación usando del response *links.next*, cuando el usuario haga scroll para buscar el siguiente segmento de datos.
                             
## Requisitos
* Android Studio (AS)

## Acerca del la app

<div align="center">
  <video src="https://github.com/mash6699/FrogmiTest/assets/11529233/acf2f3f4-7210-4b47-bfb5-231d1065e444" width="400" />
</div>

### Importar el proyecto a android studio
Selecciona la opción de *Get from VCS* para importar el repositorio del proyecto
```bash
$ git https://github.com/mash6699/FrogmiTest.git
```
<img width="402" alt="Screenshot 2024-05-30 at 11 41 37 a m" src="https://github.com/mash6699/FrogmiTest/assets/11529233/3c762432-c36a-496e-8462-5c58ddf88adc">
<img width="401" alt="Screenshot 2024-05-30 at 11 48 15 a m" src="https://github.com/mash6699/FrogmiTest/assets/11529233/b99c88f3-7243-4b64-beed-1de26ebf75be">

## Configuración de tokens

Agregar los tokens de autorización de los headers dentro del archivo *gradle.properties*
 
```gradle
 auth.bearer = "Ingresa el token sin la palabra reservada Bearer"
 comp.uuid = "Ingresa el token del X-Company-Uuid"
```

### Nota

Al ejecutar la aplicación si no cuenta con los tokens de acceso, puede mostrar el mensaje siguiente:

<div align="center">
<img width="330" alt="Screenshot 2024-05-30 at 10 07 49 a m" src="https://github.com/mash6699/FrogmiTest/assets/11529233/01f5b1c4-f000-44b6-b836-4739ca9c7cbd">
</div>

### Run app

Una vez importado el proyecto, es necesario realizar el sync de gradle para descargar las dependencias y hacer click en la opción *Run 'app'*
<div align="center">
<img width="342" alt="Screenshot 2024-05-30 at 10 08 36 a m" src="https://github.com/mash6699/FrogmiTest/assets/11529233/4d50a002-ced4-4ea1-bc4d-558ad6b77ff3">
</div>


