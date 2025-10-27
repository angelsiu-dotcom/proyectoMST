/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uni.aed.tda.AnalizadorSintactico;

import uni.aed.tda.listTDA.ListTDA;
import uni.aed.tda.linkedlistTDA.LinkedListTDA;

public class AnalizadorSintactico {

    public String analizar(ListTDA<Token> tokens) {
        if (tokens.isEmpty()) return "Error: No hay tokens.";

        int pos = 1;
        var it = tokens.iterador();
        Token token = it.next();

       
        if (!token.getValor().equalsIgnoreCase("SELECT"))
            return "Error sintáctico en token " + pos + ": se esperaba 'SELECT'.";

      
        boolean esperaIdentificador = true;
        boolean encontroFrom = false;

        while (it.hasNext()) {
            token = it.next();
            pos++;

            if (token.getValor().equalsIgnoreCase("FROM")) {
                encontroFrom = true;
                break;
            }

            if (esperaIdentificador && token.getTipo() != TokenType.IDENTIFIER)
                return "Error sintáctico en token " + pos + ": se esperaba un IDENTIFIER.";

            if (!esperaIdentificador && token.getValor().equals(",")) {
                esperaIdentificador = true;
                continue;
            }

            esperaIdentificador = false;
        }

        if (!encontroFrom)
            return "Error: falta cláusula FROM.";

        // 3. Después de FROM debe venir una tabla
        if (!it.hasNext()) return "Error: falta nombre de tabla.";
        token = it.next(); pos++;
        if (token.getTipo() != TokenType.IDENTIFIER)
            return "Error en token " + pos + ": se esperaba nombre de tabla.";

        // 4. Si hay WHERE, validar condición
        if (it.hasNext()) {
            token = it.next(); pos++;
            if (!token.getValor().equalsIgnoreCase("WHERE"))
                return "Error en token " + pos + ": se esperaba 'WHERE'.";

            // después de WHERE: IDENTIFIER OPERATOR VALUE
            if (!it.hasNext()) return "Error: falta identificador en condición.";
            Token id = it.next(); pos++;
            if (id.getTipo() != TokenType.IDENTIFIER)
                return "Error en token " + pos + ": se esperaba IDENTIFIER.";

            if (!it.hasNext()) return "Error: falta operador.";
            Token op = it.next(); pos++;
            if (op.getTipo() != TokenType.OPERATOR)
                return "Error en token " + pos + ": se esperaba OPERADOR.";

            if (!it.hasNext()) return "Error: falta valor.";
            Token val = it.next(); pos++;
            if (val.getTipo() != TokenType.VALUE)
                return "Error en token " + pos + ": se esperaba VALOR.";
        }

        return "Consulta SQL valida";
    }
}
