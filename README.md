# Wizard card game

## Autor: Eduardo Alfonso Reyes López

## Pre-ejecución:

En la carpeta wizard/

Compilar -> javac -d . src/Estructuras/*.java


## Ejecutar juego 

Compilar : javac -d . src/Juego/*.java

Ejecutar : java edd.src.Juego.Wizard

## Estructura

En la carpeta "src/Estructuras", se encuentra la implementación
de listas doblemente ligadas.

En la carpeta "src/Juego", se encuentra lo relacionado al proyecto,
que son las clases de carta y baraja, donde uso POO para crear
instancias que pueda usar dentro de "Wizard.java". En este último
se encuentra toda la lógica para el desarrollo del juego, pues
ahí está el método main que simula una partida del juego de cartas
Wizard, llamando métodos que jueguen las rondas y trucos.

Otro detalle consiste en que el historial del juego se guarda en
archivo de texto en la carpeta Juego, llamado "Historial".

Hubo algunos inconvenientes a lo largo de la implementación,
principalmente relacionados con la lógica del juego, ya que
presenta una variedad de posibilidades por ronda. Además, de la
dificultad instrinseca de hacer una interfaz para que el usuario
pueda interactuar fluidamente.