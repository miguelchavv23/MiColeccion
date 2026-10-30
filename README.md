# Mi Colección – Entregable 1

**Alumno:** [Miguel Chaves Asencio]
**Módulo:** PMDM – 2º DAM

## Descripción
App Android en Java para llevar el catálogo personal de videojuegos.
Cada juego tiene título, categoría (Aventura, Guerra o Deportes),
estado (Pendiente o Completado) y marca de favorito. Funciona sin
conexión y guarda los datos en SQLite.

## Capturas
| Pantalla | Captura |
|---|---|
| Listado | ![](capturas/01_listado.png) |
| Listado vacío | ![](capturas/02_vacio.png) |
| Alta | ![](capturas/03_formulario.png) |
| Edición | ![](capturas/04_edicion.png) |
| Ajustes | ![](capturas/05_ajustes.png) |

## Layouts utilizados
- **LinearLayout** (vertical): en el formulario, en ajustes, en la
  pantalla del listado y en la fila de cada elemento. Coloca los
  elementos uno debajo de otro. Los botones usan un LinearLayout
  horizontal con `layout_weight` para repartir el ancho.
- **FrameLayout**: en el listado, para superponer en el mismo espacio
  el RecyclerView y el mensaje «Aún no hay elementos», que se alternan.

## Esquema de la tabla SQLite (`elementos`)
| Campo | Tipo | Descripción |
|---|---|---|
| id | INTEGER, clave primaria, autoincremental | Identificador |
| titulo | TEXT NOT NULL | Nombre (2–60 caracteres, único sin distinguir mayúsculas) |
| categoria | TEXT NOT NULL | Aventura, Guerra o Deportes |
| estado | TEXT NOT NULL | Pendiente o Completado |
| favorito | INTEGER NOT NULL DEFAULT 0 | 0 = no, 1 = sí |

## Requisitos cumplidos
| Req. | Estado | Dónde |
|---|---|---|
| R1 | Cumplido | ListadoActivity, FormularioActivity, AjustesActivity |
| R2 | Cumplido | LinearLayout y FrameLayout (ver apartado anterior) |
| R3 | Cumplido | Formulario: EditText, Spinner, RadioGroup, CheckBox |
| R4 | Cumplido | FormularioActivity implementa OnClickListener |
| R5 | Cumplido | ElementoAdapter, ViewHolder, item_elemento.xml, Elemento |
| R6 | Cumplido | Intents entre pantallas; el id viaja con putExtra |
| R7 | Cumplido | SharedPreferences (MODE_PRIVATE), aplicado al listado |
| R8 | Cumplido | BaseDatos extends SQLiteOpenHelper |
| R9 | Cumplido | insert, query/Cursor, update, delete con `?` |
| R10 | Cumplido | Los datos persisten al cerrar y reabrir |

## Notas
- El campo título tiene `maxLength=60`, por lo que no admite más
  caracteres; además se valida en código.