# TPE2-POD-PEATONES

Este proyecto hace uso del modelo de programación ***MapReduce*** junto con el framework ***HazelCast*** para el procesamiento de datos de sensores de peatones, basado en datos reales.

## Authors

- [Conca, María Victoria](https://github.com/Mickyconca)
- [Gordyn Biello, Guido](https://github.com/ggordyn)
- [Rodríguez, Martina](https://github.com/martirodriguez98)
- [Tarantino Pedrosa, Ana](https://github.com/anatarantino)

##Requisitos
- Java 1.8
- Maven 4.0

##Instalación

Acceder a la carpeta TP2-POD-Peatones y en el archivo run-all.sh $TP-PATH completar la segunda línea archivo con el PATH del trabajo. Luego ejecutar dicho archivo.

```bash
  ./run-all.sh
```

A continuación ingresar a la carpeta server/target/tpe2-g13-server-1.0-SNAPSHOT y ejecutar run-server.sh para iniciar el servidor.

```bash
  ./run-server.sh
```

Por último acceder a la carpeta client/target/tpe2-g13-client-1.0-SNAPSHOT y allí correr la query que se desee con los parámetros necesarios.

##Queries
Todas las queries generan dos archivos de salida, un .csv con los resultados y un .txt con los tiempos de ejecución.

La convención es:
```bash
   ./queryX -Daddresses='xx.xx.xx.xx:XXXX;yy.yy.yy.yy:YYYY' -DinPath=XX -DoutPath=YY
```
Donde:
* queryX es el script que corre la query x.
* -Daddresses hace referencia a las direcciones ip y puertos de los nodos a conectarse.
* -DinPath indica el path que contiene los archivos de entrada
* -DoutPath indica el path donde se quiere que se guarden los archivos resultantes
* [params] parámetros extras para algunas queries.

###Query 1
Esta query retorna el total de peatones por sensor

```bash
   ./query1 -Daddresses='xx.xx.xx.xx:XXXX;yy.yy.yy.yy:YYYY' -DinPath=XX -DoutPath=YY
```

###Query 2
Esta query retorna el total de peatones por momento de la semana para cada año.

```bash
   ./query2 -Daddresses='xx.xx.xx.xx:XXXX;yy.yy.yy.yy:YYYY' -DinPath=XX -DoutPath=YY
```

###Query 3
Esta query retorna la medición más alta de cada sensor mayores al parámetro min. Solo se listan aquellos sensores que tengan el estado Activo.

```bash
   ./query3 -Daddresses='xx.xx.xx.xx:XXXX;yy.yy.yy.yy:YYYY' -DinPath=XX -DoutPath=YY -Dmin=Z
```

###Query 4
Esta query retorna el Top n sensores con mayor promedio mensual para un cierto año recibido por parámetro también.

```bash
   ./query4 -Daddresses='xx.xx.xx.xx:XXXX;yy.yy.yy.yy:YYYY' -DinPath=XX -DoutPath=YY -Dn=Z -Dyear=YYYY
```
###Query 5
Esta query retorna los pares de sensores que registran la misma cantidad de millones de peatones.

```bash
   ./query5 -Daddresses='xx.xx.xx.xx:XXXX;yy.yy.yy.yy:YYYY' -DinPath=XX -DoutPath=YY
```

