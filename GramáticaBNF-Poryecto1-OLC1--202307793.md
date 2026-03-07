# PROYECTO 1 - OLC1 (1S2026)
**Estudiante:** Rony Javier Alvarez Ordoñez  
**Carné:** 202307793

---

## PRODUCCIONES DEL LENGUAJE ELI

### Estructura Principal
* [span_0](start_span)`<programa> ::= <listaInstrucciones>`[span_0](end_span)
* `<listaInstrucciones> ::= <instruccion> <listaInstrucciones> | [span_1](start_span)<instruccion>`[span_1](end_span)
* `<instruccion> ::= <definicionDB> | <instruccionUse> | <definicionTabla> | <lectura> | <insercion> | <actualizacion> | <eliminacion> | [span_2](start_span)<exportacion>`[span_2](end_span)

### Definiciones y Configuración
* **[span_3](start_span)Base de Datos:** `<definicionDB> ::= DATABASE ID { <storeDecl> }`[span_3](end_span)
* **[span_4](start_span)Ruta de Almacenamiento:** `<storeDecl> ::= STORE AT STRING_LITERAL;`[span_4](end_span)
* **[span_5](start_span)Selección de DB:** `<instruccionUse> ::= USE ID;`[span_5](end_span)

### Gestión de Tablas
* **[span_6](start_span)Crear Tabla:** `<definicionTabla> ::= TABLE ID { <listaCamposTabla> }`[span_6](end_span)
* **Campos:** * `<listaCamposTabla> ::= <campo> <listaCamposTabla> | [span_7](start_span)<campo>`[span_7](end_span)
    * [span_8](start_span)`<campo> ::= ID : <tipoDato>;`[span_8](end_span)
* **[span_9](start_span)Tipos de Dato:** `<tipoDato> ::= int | float | bool | string | array | object | null`[span_9](end_span)

### Consultas (Lectura)
* [span_10](start_span)`<lectura> ::= READ ID { <cuerpoLectura> };`[span_10](end_span)
* `<cuerpoLectura> ::= <seccionCampos> <seccionFiltroRead> | [span_11](start_span)<seccionCampos>`[span_11](end_span)
* [span_12](start_span)`<seccionCampos> ::= FIELDS: <listaCamposRead> ;`[span_12](end_span)
* [span_13](start_span)`<listaCamposRead> ::= ID <MASCampos>`[span_13](end_span)
* `<MASCampos> ::= , ID <MASCampos> | [span_14](start_span)ε`[span_14](end_span)
* [span_15](start_span)`<seccionFiltroRead> ::= FILTER: <expresionFiltro> ;`[span_15](end_span)

### Expresiones Lógicas y Relacionales
* `<expresionFiltro> ::= <expresionFiltro> && <expresionFiltro> | <expresionFiltro> || <expresionFiltro> | ! <expresionFiltro> | ( <expresionFiltro> ) | [span_16](start_span)ID <operadorRelacional> <valor>`[span_16](end_span)
* `<operadorRelacional> ::= == | != | > | < | >= | [span_17](start_span)<=`[span_17](end_span)

### Manipulación de Datos
* **[span_18](start_span)Exportar:** `<exportacion> ::= EXPORT STRING_LITERAL;`[span_18](end_span)
* **[span_19](start_span)Insertar (ADD):** * `<insercion> ::= ADD ID { <listaAsignaciones> };`[span_19](end_span)
    * [span_20](start_span)`<listaAsignaciones> ::= <asignacion> <MASAsignaciones>`[span_20](end_span)
    * `<MASAsignaciones> ::= , <asignacion> <MASAsignaciones> | [span_21](start_span)ε`[span_21](end_span)
    * [span_22](start_span)`<asignacion> ::= ID : <valor>`[span_22](end_span)
* **Actualizar (UPDATE):**
    * [span_23](start_span)`<actualizacion> ::= UPDATE ID { <cuerpoActualizacion> };`[span_23](end_span)
    * `<cuerpoActualizacion> ::= <seccionCamposActualizar> <seccionFiltroActualizar> | [span_24](start_span)<seccionCamposActualizar>`[span_24](end_span)
    * [span_25](start_span)`<seccionCamposActualizar> ::= SET: <listaCamposActualizar> ;`[span_25](end_span)
    * [span_26](start_span)`<listaCamposActualizar> ::= <campoActualizar> <MASCamposActualizar>`[span_26](end_span)
    * [span_27](start_span)`<campoActualizar> ::= ID = <valor>`[span_27](end_span)
    * [span_28](start_span)`<seccionFiltroActualizar> ::= FILTER: <expresionFiltro>;`[span_28](end_span)
* **[span_29](start_span)Eliminar:** `<eliminacion> ::= CLEAR ID;`[span_29](end_span)

### Valores y Tipos Compuestos
* `<valor> ::= ENTERO | DECIMAL | STRING_LITERAL | true | false | null | [ <listaValores> ] | [span_30](start_span){ <listaAsignaciones> }`[span_30](end_span)
* [span_31](start_span)`<listaValores> ::= <valor> <MASValores>`[span_31](end_span)
* `<MASValores> ::= , <valor> <MASValores> | [span_32](start_span)ε`[span_32](end_span)

---

## TOKENS TERMINALES (Irreducibles)

| Token | Valor | Token | Valor |
| :--- | :--- | :--- | :--- |
| **DATABASE** | "database" | **ADD** | "add" |
| **USE** | "use" | **UPDATE** | "update" |
| **TABLE** | "table" | **SET** | "set" |
| **READ** | "read" | **CLEAR** | "clear" |
| **FIELDS** | "fields" | **TRUE** | "true" |
| **FILTER** | "filter" | **FALSE** | "false" |
| **STORE** | "store" | **NULL** | "null" |
| **AT** | "at" | **AND** | "&&" |
| **EXPORT** | "export" | **OR** | "\|\|" |
| **INT** | "int" | **NOT** | "!" |
| **FLOAT** | "float" | **IGUAL** | "==" |
| **BOOL** | "bool" | **DIFERENTE**| "!=" |
| **STRING** | "string" | **ASIGNAR** | "=" |

**Expresiones Regulares:**
* **[span_33](start_span)ID:** `[a-zA-Z_][a-zA-Z0-9_]*`[span_33](end_span)
* **[span_34](start_span)ENTERO:** `[0-9]+`[span_34](end_span)
* **[span_35](start_span)DECIMAL:** `[0-9]+"."[0-9]+`[span_35](end_span)
* **[span_36](start_span)STRING_LITERAL:** `\"[^\"]*\"`[span_36](end_span)
*